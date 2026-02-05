package ec.edu.espe.petshopinventorycontrol.controller;

import com.toedter.calendar.JDateChooser;
import ec.edu.espe.petshopinventorycontrol.model.mongo.MongoPersonalGateway;
import ec.edu.espe.petshopinventorycontrol.view.FrmGraphicPersonal;
import ec.edu.espe.petshopinventorycontrol.view.FrmGraphicRegister;
import java.util.Date;
import java.util.Map;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public final class PersonalController {

    public static final class Bindings {
        private final JTextField txtIdPersonal;
        private final JTextField txtCiPersonal;
        private final JTextField txtName;
        private final JTextField txtAdress;
        private final JComboBox<String> cmbPost;
        private final JComboBox<String> cmbSchedule;
        private final JComboBox<String> cmbDay;
        private final JComboBox<String> cmbState;
        private final JDateChooser dateOfIncorporated;

        public Bindings(
                JTextField txtIdPersonal,
                JTextField txtCiPersonal,
                JTextField txtName,
                JTextField txtAdress,
                JComboBox<String> cmbPost,
                JComboBox<String> cmbSchedule,
                JComboBox<String> cmbDay,
                JComboBox<String> cmbState,
                JDateChooser dateOfIncorporated
        ) {
            this.txtIdPersonal = txtIdPersonal;
            this.txtCiPersonal = txtCiPersonal;
            this.txtName = txtName;
            this.txtAdress = txtAdress;
            this.cmbPost = cmbPost;
            this.cmbSchedule = cmbSchedule;
            this.cmbDay = cmbDay;
            this.cmbState = cmbState;
            this.dateOfIncorporated = dateOfIncorporated;
        }
    }

    private final JFrame owner;
    private final Bindings ui;
    private final PersonalService personalService;
    private final ReportService reportService;

    public PersonalController(JFrame owner, Bindings ui) {
        this.owner = owner;
        this.ui = ui;
        this.personalService = new PersonalService(
                new MongoPersonalGateway(),
                new PersonalValidator()
        );
        this.reportService = ReportService.defaultService();
    }

    public void onInit() {
        ui.txtIdPersonal.setEditable(false);
        resetSelections();
        generateAndSetNewPersonalId();
    }

    public void onReport() {
        reportService.exportPersonalReport(owner);
    }

    public void onOpenGraphicPersonal() {
        new FrmGraphicPersonal().setVisible(true);
        owner.dispose();
    }

    public void onOpenGraphicRegister() {
        new FrmGraphicRegister().setVisible(true);
        owner.dispose();
    }

    public void onReturnLobby() {
        new ec.edu.espe.petshopinventorycontrol.view.FrmLobby().setVisible(true);
        owner.dispose();
    }

    public void onNew() {
        clearPersonalFields(false);
        generateAndSetNewPersonalId();
    }

    public void onSave() {
        Map<String, String> errors = personalService.validateFields(
                ui.txtIdPersonal.getText(),
                ui.txtCiPersonal.getText(),
                ui.txtName.getText(),
                getSelectedValue(ui.cmbPost),
                getSelectedValue(ui.cmbSchedule),
                getSelectedValue(ui.cmbDay),
                ui.txtAdress.getText(),
                getSelectedValue(ui.cmbState),
                ui.dateOfIncorporated.getDate()
        );

        if (!errors.isEmpty()) {
            JOptionPane.showMessageDialog(
                    owner,
                    "Todos los campos deben estar completos y validos.",
                    "Error de validacion",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        try {
            personalService.savePersonal(
                    ui.txtIdPersonal.getText(),
                    ui.txtCiPersonal.getText(),
                    ui.txtName.getText(),
                    getSelectedValue(ui.cmbPost),
                    getSelectedValue(ui.cmbSchedule),
                    getSelectedValue(ui.cmbDay),
                    ui.txtAdress.getText(),
                    getSelectedValue(ui.cmbState),
                    ui.dateOfIncorporated.getDate()
            );

            JOptionPane.showMessageDialog(
                    owner,
                    "Personal guardado correctamente.",
                    "Exito",
                    JOptionPane.INFORMATION_MESSAGE
            );

            clearPersonalFields(false);
            generateAndSetNewPersonalId();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    owner,
                    "Error inesperado: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public void onDuplicate() {
        String newId = getNextPersonalId();
        String newCi = JOptionPane.showInputDialog(
                owner,
                "Ingrese nueva cedula:",
                "Duplicar Registro",
                JOptionPane.QUESTION_MESSAGE
        );
        if (newCi == null) {
            return;
        }
        if (!personalService.isNumericCi(newCi)) {
            JOptionPane.showMessageDialog(
                    owner,
                    "Cedula invalida. Solo numeros.",
                    "Error de validacion",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        ui.txtIdPersonal.setText(newId);
        ui.txtCiPersonal.setText(newCi.trim());
    }

    private void resetSelections() {
        ui.cmbPost.setSelectedIndex(-1);
        ui.cmbSchedule.setSelectedIndex(-1);
        ui.cmbDay.setSelectedIndex(-1);
        ui.cmbState.setSelectedIndex(-1);
    }

    private void generateAndSetNewPersonalId() {
        ui.txtIdPersonal.setText(getNextPersonalId());
    }

    private String getNextPersonalId() {
        try {
            return personalService.generateNextPersonalId(new Date());
        } catch (Exception ex) {
            String prefix = new java.text.SimpleDateFormat("ddMMyy").format(new Date());
            String fallback = prefix + "-001";
            JOptionPane.showMessageDialog(
                    owner,
                    "MongoDB no esta disponible.\nUsando ID alterno: " + fallback,
                    "Error de conexion MongoDB",
                    JOptionPane.WARNING_MESSAGE
            );
            return fallback;
        }
    }

    private void clearPersonalFields(boolean keepId) {
        if (!keepId) {
            ui.txtIdPersonal.setText("");
        }
        ui.txtCiPersonal.setText("");
        ui.txtName.setText("");
        ui.txtAdress.setText("");
        ui.dateOfIncorporated.setDate(null);
        resetSelections();
    }

    private String getSelectedValue(JComboBox<String> combo) {
        Object selected = combo.getSelectedItem();
        if (selected == null) {
            return null;
        }
        String value = selected.toString().trim();
        return value.isEmpty() ? null : value;
    }
}
