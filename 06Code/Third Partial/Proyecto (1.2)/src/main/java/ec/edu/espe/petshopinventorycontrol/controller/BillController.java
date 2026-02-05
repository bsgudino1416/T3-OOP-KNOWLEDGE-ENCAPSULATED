package ec.edu.espe.petshopinventorycontrol.controller;

import com.toedter.calendar.JDateChooser;
import ec.edu.espe.petshopinventorycontrol.model.mongo.MongoBillGateway;
import ec.edu.espe.petshopinventorycontrol.model.mongo.MongoProductGateway;
import ec.edu.espe.petshopinventorycontrol.model.mongo.MongoStockGateway;
import ec.edu.espe.petshopinventorycontrol.view.FrmLobby;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import org.bson.Document;

public final class BillController {

    private static final double IVA_RATE = 0.15;

    public static final class Bindings {
        private final JTextField txtidBill;
        private final JTextField txtName;
        private final JTextField txtCI;
        private final JTextField txtPhone;
        private final JTextField txtEmail;
        private final JTextField txtAdress;
        private final JDateChooser dateBill;
        private final JTextField txtPersonal;
        private final JTextField txtCodeProduct;
        private final JTextField txtQuantityBill;
        private final JTextField txtNameProductBill;
        private final JTextField txtPriceUnit;
        private final JTextField txtSubtotal;
        private final JTextField txtIVA;
        private final JTextField txtTotalBill;
        private final JTable tableBill;

        public Bindings(
                JTextField txtidBill,
                JTextField txtName,
                JTextField txtCI,
                JTextField txtPhone,
                JTextField txtEmail,
                JTextField txtAdress,
                JDateChooser dateBill,
                JTextField txtPersonal,
                JTextField txtCodeProduct,
                JTextField txtQuantityBill,
                JTextField txtNameProductBill,
                JTextField txtPriceUnit,
                JTextField txtSubtotal,
                JTextField txtIVA,
                JTextField txtTotalBill,
                JTable tableBill
        ) {
            this.txtidBill = txtidBill;
            this.txtName = txtName;
            this.txtCI = txtCI;
            this.txtPhone = txtPhone;
            this.txtEmail = txtEmail;
            this.txtAdress = txtAdress;
            this.dateBill = dateBill;
            this.txtPersonal = txtPersonal;
            this.txtCodeProduct = txtCodeProduct;
            this.txtQuantityBill = txtQuantityBill;
            this.txtNameProductBill = txtNameProductBill;
            this.txtPriceUnit = txtPriceUnit;
            this.txtSubtotal = txtSubtotal;
            this.txtIVA = txtIVA;
            this.txtTotalBill = txtTotalBill;
            this.tableBill = tableBill;
        }
    }

    private final JFrame owner;
    private final Bindings ui;
    private final BillService billService;
    private final MongoBillGateway billGateway;
    private final ReportService reportService;
    private final BillIdGenerator billIdGenerator;
    private final DecimalFormat moneyFormat;
    private DefaultTableModel billTableModel;

    public BillController(JFrame owner, Bindings ui) {
        this.owner = owner;
        this.ui = ui;
        this.billService = new BillService(new MongoStockGateway(), new MongoProductGateway());
        this.billGateway = new MongoBillGateway();
        this.reportService = ReportService.defaultService();
        this.billIdGenerator = new BillIdGenerator();
        this.moneyFormat = new DecimalFormat("0.00");
    }

    public void onInit() {
        configureReadOnlyFields();
        configureBillTable();
        setupCodeLookup();
        generateBillId();
    }

    public void onReport() {
        reportService.exportBillReport(owner);
    }

    public void onReturnLobby() {
        new FrmLobby().setVisible(true);
        owner.dispose();
    }

    public void onLookupProduct() {
        lookupProductByCode();
    }

    public void onAddItem() {
        addItemToTable();
    }

    public void onSave() {
        saveBill();
    }

    public void onNewBill() {
        clearBillForm(false);
    }

    private void configureReadOnlyFields() {
        ui.txtidBill.setEditable(false);
        ui.txtPriceUnit.setEditable(false);
        ui.txtSubtotal.setEditable(false);
        ui.txtIVA.setEditable(false);
        ui.txtTotalBill.setEditable(false);
    }

    private void configureBillTable() {
        billTableModel = new DefaultTableModel(
                new Object[]{"No", "Producto", "Cantidad", "Precio Unitario", "Total"},
                0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        ui.tableBill.setModel(billTableModel);
        updateTotals();
    }

    private void setupCodeLookup() {
        ui.txtCodeProduct.addActionListener(e -> lookupProductByCode());
        ui.txtCodeProduct.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                lookupProductByCode();
            }
        });
    }

    private boolean lookupProductByCode() {
        String code = ui.txtCodeProduct.getText();
        if (isBlank(code)) {
            clearProductSelection();
            return false;
        }

        BillService.ItemInfo item = billService.findItemByCode(code);
        if (item == null) {
            clearProductSelection();
            JOptionPane.showMessageDialog(
                    owner,
                    "El codigo no existe en stock.",
                    "Producto no disponible",
                    JOptionPane.WARNING_MESSAGE
            );
            return false;
        }

        ui.txtNameProductBill.setText(item.name());
        ui.txtPriceUnit.setText(formatMoney(item.unitPrice()));
        return true;
    }

    private void clearProductSelection() {
        ui.txtNameProductBill.setText("");
        ui.txtPriceUnit.setText("");
    }

    private void addItemToTable() {
        if (isBlank(ui.txtCodeProduct.getText())) {
            JOptionPane.showMessageDialog(
                    owner,
                    "Ingrese el codigo del producto.",
                    "Validacion",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        if (!lookupProductByCode()) {
            return;
        }

        String name = ui.txtNameProductBill.getText();
        Double unitPrice = parseDouble(ui.txtPriceUnit.getText());
        Double quantity = parseDouble(ui.txtQuantityBill.getText());

        if (isBlank(name) || unitPrice == null) {
            JOptionPane.showMessageDialog(
                    owner,
                    "Seleccione un producto valido.",
                    "Validacion",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        if (quantity == null || quantity <= 0) {
            JOptionPane.showMessageDialog(
                    owner,
                    "Cantidad invalida.",
                    "Validacion",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        double lineTotal = unitPrice * quantity;
        int index = billTableModel.getRowCount() + 1;
        billTableModel.addRow(new Object[]{
            index,
            name,
            formatMoney(quantity),
            formatMoney(unitPrice),
            formatMoney(lineTotal)
        });

        updateTotals();
        ui.txtCodeProduct.setText("");
        ui.txtQuantityBill.setText("");
        clearProductSelection();
    }

    private void updateTotals() {
        double subtotal = 0;
        for (int i = 0; i < billTableModel.getRowCount(); i++) {
            Object value = billTableModel.getValueAt(i, 4);
            Double total = parseDouble(value == null ? null : value.toString());
            if (total != null) {
                subtotal += total;
            }
        }

        double iva = subtotal * IVA_RATE;
        double total = subtotal + iva;

        ui.txtSubtotal.setText(formatMoney(subtotal));
        ui.txtIVA.setText(formatMoney(iva));
        ui.txtTotalBill.setText(formatMoney(total));
    }

    private void clearBillForm(boolean keepId) {
        if (!keepId) {
            generateBillId();
        }
        ui.txtName.setText("");
        ui.txtCI.setText("");
        ui.txtPhone.setText("");
        ui.txtEmail.setText("");
        ui.txtAdress.setText("");
        ui.dateBill.setDate(null);
        ui.txtPersonal.setText("");
        ui.txtCodeProduct.setText("");
        ui.txtQuantityBill.setText("");
        clearProductSelection();
        if (billTableModel != null) {
            billTableModel.setRowCount(0);
        }
        updateTotals();
    }

    private void generateBillId() {
        ui.txtidBill.setText(billIdGenerator.nextId());
    }

    private void saveBill() {
        if (billTableModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(
                    owner,
                    "Agrega al menos un producto.",
                    "Validacion",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        if (isBlank(ui.txtidBill.getText())
                || isBlank(ui.txtName.getText())
                || isBlank(ui.txtCI.getText())
                || isBlank(ui.txtPhone.getText())
                || isBlank(ui.txtEmail.getText())
                || isBlank(ui.txtAdress.getText())
                || ui.dateBill.getDate() == null
                || isBlank(ui.txtPersonal.getText())) {
            JOptionPane.showMessageDialog(
                    owner,
                    "Completa los datos de la factura.",
                    "Validacion",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        Document billDoc = buildBillDocument();
        try {
            billGateway.insertBill(billDoc);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    owner,
                    "No se pudo guardar la factura: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        JOptionPane.showMessageDialog(
                owner,
                "Factura guardada correctamente.",
                "Exito",
                JOptionPane.INFORMATION_MESSAGE
        );
        clearBillForm(false);
    }

    private Document buildBillDocument() {
        Document doc = new Document()
                .append("txtidBill", safeText(ui.txtidBill.getText()))
                .append("txtName", safeText(ui.txtName.getText()))
                .append("txtCI", safeText(ui.txtCI.getText()))
                .append("txtPhone", safeText(ui.txtPhone.getText()))
                .append("txtEmail", safeText(ui.txtEmail.getText()))
                .append("txtAdress", safeText(ui.txtAdress.getText()))
                .append("DateBill", ui.dateBill.getDate())
                .append("txtPersonal", safeText(ui.txtPersonal.getText()))
                .append("txtSubtotal", safeText(ui.txtSubtotal.getText()))
                .append("txtIVA", safeText(ui.txtIVA.getText()))
                .append("txttotalBill", safeText(ui.txtTotalBill.getText()));

        doc.append("items", buildBillItems());
        return doc;
    }

    private List<Document> buildBillItems() {
        List<Document> items = new ArrayList<>();
        if (billTableModel == null) {
            return items;
        }
        for (int row = 0; row < billTableModel.getRowCount(); row++) {
            Document item = new Document()
                    .append("No", safeValue(billTableModel.getValueAt(row, 0)))
                    .append("Producto", safeValue(billTableModel.getValueAt(row, 1)))
                    .append("Cantidad", safeValue(billTableModel.getValueAt(row, 2)))
                    .append("PrecioUnitario", safeValue(billTableModel.getValueAt(row, 3)))
                    .append("Total", safeValue(billTableModel.getValueAt(row, 4)));
            items.add(item);
        }
        return items;
    }

    private String safeText(String value) {
        return value == null ? "" : value.trim();
    }

    private String safeValue(Object value) {
        return value == null ? "" : String.valueOf(value);
    }

    private Double parseDouble(String value) {
        if (isBlank(value)) {
            return null;
        }
        try {
            return Double.parseDouble(value.trim().replace(",", "."));
        } catch (Exception ex) {
            return null;
        }
    }

    private String formatMoney(double value) {
        return moneyFormat.format(value);
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
