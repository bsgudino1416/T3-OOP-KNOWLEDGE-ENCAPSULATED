package ec.edu.espe.petshopinventorycontrol.view;

import ec.edu.espe.petshopinventorycontrol.controller.SupplierService;
import ec.edu.espe.petshopinventorycontrol.controller.SupplierValidator;
import ec.edu.espe.petshopinventorycontrol.model.mongo.MongoSupplierGateway;
import java.awt.Color;
import java.util.Date;
import java.util.Map;
import javax.swing.*;

/**
 *
 * @author Steven Loza @ESPE
 */
public class FrmSupplier extends javax.swing.JFrame {

    private final SupplierService supplierService = new SupplierService(
            new MongoSupplierGateway(),
            new SupplierValidator()
    );

    private final Color errorColor = new Color(204, 0, 0);
    private final Color normalColor = Color.BLACK;

    /**
     * Creates new form FrmSupplier
     */
    public FrmSupplier() {
        initComponents();
        generateAndSetNewSupplierId();

    }

   private void generateAndSetNewSupplierId() {
    try {
        String nextId = supplierService.generateNextSupplierId(new Date());
        txtIdSupplier.setText(nextId);
    } catch (Exception ex) {
        // Fallback: genera el primer ID del día sin consultar Mongo
        String prefix = new java.text.SimpleDateFormat("ddMMyy").format(new Date());
        txtIdSupplier.setText(prefix + "-001");

        JOptionPane.showMessageDialog(
                this,
                "MongoDB no esta disponible.\nUsando ID alterno: " + txtIdSupplier.getText()
                        + "\nVerifica la conexion a MongoDB.",
                "Error de conexion MongoDB",
                JOptionPane.WARNING_MESSAGE
        );
    }
}


   private void applyErrors(Map<String, String> errors) {

    // Primero limpiamos todos los campos
    clearErrorState(txtTypeSupplier);
    clearErrorState(txtPhoneSupplier);
    clearErrorState(txtPhone2Supplier);
    clearErrorState(txtNameSupplier);
    clearErrorState(txtCitySupplier);
    clearErrorState(txtStateSupplier);
    clearErrorState(txtEnterpriselSupplier);
    clearErrorState(txtemailSupplier);

    // Date chooser (si aplica)
    clearDateErrorState();

    // Marcamos solo los campos con error
    if (errors.containsKey("txtTypeSupplier")) markError(txtTypeSupplier);
    if (errors.containsKey("txtPhoneSupplier")) markError(txtPhoneSupplier);
    if (errors.containsKey("txtPhone2Supplier")) markError(txtPhone2Supplier);
    if (errors.containsKey("txtNameSupplier")) markError(txtNameSupplier);
    if (errors.containsKey("txtCitySupplier")) markError(txtCitySupplier);
    if (errors.containsKey("txtStateSupplier")) markError(txtStateSupplier);
    if (errors.containsKey("txtEnterpriseSupplier")) markError(txtEnterpriselSupplier);
    if (errors.containsKey("txtemailSupplier")) markError(txtemailSupplier);

    if (errors.containsKey("jDateEntry")) {
        markDateError();
    }
}
private void markError(javax.swing.JTextField field) {
    field.setBorder(javax.swing.BorderFactory.createLineBorder(errorColor, 2));
}

private void clearErrorState(javax.swing.JTextField field) {
    field.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.GRAY, 1));
}

private void markDateError() {
    if (jDateEntry.getDateEditor() != null &&
        jDateEntry.getDateEditor().getUiComponent() instanceof javax.swing.JTextField tf) {
        markError(tf);
    }
}

private void clearDateErrorState() {
    if (jDateEntry.getDateEditor() != null &&
        jDateEntry.getDateEditor().getUiComponent() instanceof javax.swing.JTextField tf) {
        clearErrorState(tf);
    }
}
private void clearSupplierFields() {
    txtTypeSupplier.setText("");
    txtPhoneSupplier.setText("");
    txtPhone2Supplier.setText("");
    txtNameSupplier.setText("");
    txtCitySupplier.setText("");
    txtStateSupplier.setText("");
    txtEnterpriselSupplier.setText("");
    txtemailSupplier.setText("");
    jDateEntry.setDate(null);

    clearErrorState(txtTypeSupplier);
    clearErrorState(txtPhoneSupplier);
    clearErrorState(txtPhone2Supplier);
    clearErrorState(txtNameSupplier);
    clearErrorState(txtCitySupplier);
    clearErrorState(txtStateSupplier);
    clearErrorState(txtEnterpriselSupplier);
    clearErrorState(txtemailSupplier);
}
private void duplicateCurrentRegister() {
    String type = txtTypeSupplier.getText();
    String phone = txtPhoneSupplier.getText();
    String phone2 = txtPhone2Supplier.getText();
    String name = txtNameSupplier.getText();
    String city = txtCitySupplier.getText();
    String state = txtStateSupplier.getText();
    String enterprise = txtEnterpriselSupplier.getText();
    String email = txtemailSupplier.getText();
    Date entryDate = jDateEntry.getDate();

    Map<String, String> errors = supplierService.validateFields(
            type, phone, phone2, name, city, state, enterprise, email, entryDate
    );

    applyErrors(errors);

    if (!errors.isEmpty()) {
        JOptionPane.showMessageDialog(
                this,
                "No se puede duplicar. Completa todos los campos requeridos primero.",
                "Error de validacion",
                JOptionPane.WARNING_MESSAGE
        );
        return;
    }

    String oldId = txtIdSupplier.getText();
    String newId = supplierService.generateNextSupplierId(new Date());
    txtIdSupplier.setText(newId);

    JOptionPane.showMessageDialog(
            this,
            "Registro Duplicado exitosamente.\nNuevo ID: " + newId + "\nID anterior: " + oldId,
            "Duplicado",
            JOptionPane.INFORMATION_MESSAGE
    );
}



    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtIdSupplier = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtTypeSupplier = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtPhoneSupplier = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtPhone2Supplier = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtNameSupplier = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtCitySupplier = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtStateSupplier = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtEnterpriselSupplier = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtemailSupplier = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jDateEntry = new com.toedter.calendar.JDateChooser();
        jPanel2 = new javax.swing.JPanel();
        btnBackSupplier = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        MnuFile = new javax.swing.JMenu();
        itmNewRegisterSupplier = new javax.swing.JMenuItem();
        itmSaveRegisterSupplier = new javax.swing.JMenuItem();
        itmDuplicateRegisterSupplier = new javax.swing.JMenuItem();
        MnuOptions = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        MnuHelp = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 0, 119));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 3, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("PROVEEDORES");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(215, 215, 215)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jLabel2.setFont(new java.awt.Font("Bodoni MT", 1, 14)); // NOI18N
        jLabel2.setText("Id proveedor:");

        txtIdSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdSupplierActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Bodoni MT", 1, 14)); // NOI18N
        jLabel3.setText("Tipo de proveedor:");

        txtTypeSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTypeSupplierActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Bodoni MT", 1, 14)); // NOI18N
        jLabel4.setText("Telefono Contacto:");

        jLabel5.setFont(new java.awt.Font("Bodoni MT", 1, 14)); // NOI18N
        jLabel5.setText("Telefono 2:");

        jLabel6.setFont(new java.awt.Font("Bodoni MT", 1, 14)); // NOI18N
        jLabel6.setText("Nombre Contacto:");

        txtNameSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNameSupplierActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Bodoni MT", 1, 14)); // NOI18N
        jLabel7.setText("Ciudad:");

        jLabel8.setFont(new java.awt.Font("Bodoni MT", 1, 14)); // NOI18N
        jLabel8.setText("Provincia:");

        txtStateSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtStateSupplierActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Bodoni MT", 1, 14)); // NOI18N
        jLabel9.setText("Empresa:");

        jLabel11.setFont(new java.awt.Font("Bodoni MT", 1, 14)); // NOI18N
        jLabel11.setText("Correo electronico:");

        txtemailSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtemailSupplierActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Bodoni MT", 1, 14)); // NOI18N
        jLabel10.setText("Fecha de ingreso: ");

        jPanel2.setBackground(new java.awt.Color(0, 0, 119));

        btnBackSupplier.setText("Regresar");
        btnBackSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackSupplierActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(btnBackSupplier)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(btnBackSupplier)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtIdSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTypeSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtPhoneSupplier))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel10)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jDateEntry, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtNameSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtPhone2Supplier, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtCitySupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtStateSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtEnterpriselSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtemailSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtIdSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(txtTypeSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtPhoneSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txtPhone2Supplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtNameSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(txtCitySupplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtStateSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(txtEnterpriselSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtemailSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(jDateEntry, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        MnuFile.setText("Archivo");

        itmNewRegisterSupplier.setText("Nuevo Registro");
        itmNewRegisterSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmNewRegisterSupplierActionPerformed(evt);
            }
        });
        MnuFile.add(itmNewRegisterSupplier);

        itmSaveRegisterSupplier.setText("Guardar Registro");
        itmSaveRegisterSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmSaveRegisterSupplierActionPerformed(evt);
            }
        });
        MnuFile.add(itmSaveRegisterSupplier);

        itmDuplicateRegisterSupplier.setText("Duplicar Registro");
        itmDuplicateRegisterSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmDuplicateRegisterSupplierActionPerformed(evt);
            }
        });
        MnuFile.add(itmDuplicateRegisterSupplier);

        jMenuBar1.add(MnuFile);

        MnuOptions.setText("Opciones");

        jMenuItem1.setText("Grafica Proveedores");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        MnuOptions.add(jMenuItem1);

        jMenuItem2.setText("Informe Proveedores");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        MnuOptions.add(jMenuItem2);

        jMenuBar1.add(MnuOptions);

        MnuHelp.setText("Ayuda");

        jMenuItem3.setText("Información");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        MnuHelp.add(jMenuItem3);

        jMenuBar1.add(MnuHelp);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtIdSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdSupplierActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_txtIdSupplierActionPerformed

    private void txtTypeSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTypeSupplierActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTypeSupplierActionPerformed

    private void txtNameSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNameSupplierActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNameSupplierActionPerformed

    private void txtStateSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtStateSupplierActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtStateSupplierActionPerformed

    private void txtemailSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtemailSupplierActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtemailSupplierActionPerformed

    private void btnBackSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackSupplierActionPerformed

        FrmLobby lobby = new FrmLobby();
        lobby.setVisible(true);
        this.dispose();

        // new FrmEmployeeMenu().setVisible(true);
        // this.dispose();
    }//GEN-LAST:event_btnBackSupplierActionPerformed

    private void itmNewRegisterSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmNewRegisterSupplierActionPerformed
        clearSupplierFields();
        generateAndSetNewSupplierId();
        
    }//GEN-LAST:event_itmNewRegisterSupplierActionPerformed

    private void itmSaveRegisterSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmSaveRegisterSupplierActionPerformed
        String id = txtIdSupplier.getText();
    String type = txtTypeSupplier.getText();
    String phone = txtPhoneSupplier.getText();
    String phone2 = txtPhone2Supplier.getText();
    String name = txtNameSupplier.getText();
    String city = txtCitySupplier.getText();
    String state = txtStateSupplier.getText();
    String enterprise = txtEnterpriselSupplier.getText();
    String email = txtemailSupplier.getText();
    Date entryDate = jDateEntry.getDate();

    Map<String, String> errors = supplierService.validateFields(
            type, phone, phone2, name, city, state, enterprise, email, entryDate
    );

    applyErrors(errors);
    if (!errors.isEmpty()) {
        javax.swing.JOptionPane.showMessageDialog(
                this,
                "Completa todos los campos requeridos.",
                "Error de validacion",
                javax.swing.JOptionPane.WARNING_MESSAGE
        );
        return;
    }

    try {
        supplierService.saveSupplier(id, type, phone, phone2, name, city, state, enterprise, email, entryDate);

        javax.swing.JOptionPane.showMessageDialog(
                this,
                "Proveedor guardado correctamente.",
                "Exito",
                javax.swing.JOptionPane.INFORMATION_MESSAGE
        );

        
        clearSupplierFields();
        generateAndSetNewSupplierId();

    } catch (Exception ex) {
        javax.swing.JOptionPane.showMessageDialog(
                this,
                "Error inesperado: " + ex.getMessage(),
                "Error",
                javax.swing.JOptionPane.ERROR_MESSAGE
        );
    }
    }//GEN-LAST:event_itmSaveRegisterSupplierActionPerformed

    private void itmDuplicateRegisterSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmDuplicateRegisterSupplierActionPerformed
        duplicateCurrentRegister();
    }//GEN-LAST:event_itmDuplicateRegisterSupplierActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed

        JOptionPane.showMessageDialog(this,
                "Pendiente: abrir ventana de Gráfica de Proveedores.",
                "Navegación", JOptionPane.INFORMATION_MESSAGE);

    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed

        JOptionPane.showMessageDialog(this,
                "Pendiente: abrir ventana de Informe de Proveedores.",
                "Navegación", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed

        String info = ""
                + "El módulo de proveedores permite gestionar y organizar la información relacionada con las entidades que suministran productos o servicios al sistema. "
                + "Desde esta sección se pueden registrar, consultar y actualizar datos clave que facilitan la integración con otros procesos. "
                + "Su uso contribuye a mantener la información ordenada y coherente dentro del sistema.";

        JTextArea textArea = new JTextArea(info);
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        textArea.setEditable(false);
        textArea.setCaretPosition(0);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new java.awt.Dimension(520, 180));

        JOptionPane.showMessageDialog(this, scrollPane, "Informacion", JOptionPane.INFORMATION_MESSAGE);

    }//GEN-LAST:event_jMenuItem3ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmSupplier.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmSupplier.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmSupplier.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmSupplier.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmSupplier().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu MnuFile;
    private javax.swing.JMenu MnuHelp;
    private javax.swing.JMenu MnuOptions;
    private javax.swing.JButton btnBackSupplier;
    private javax.swing.JMenuItem itmDuplicateRegisterSupplier;
    private javax.swing.JMenuItem itmNewRegisterSupplier;
    private javax.swing.JMenuItem itmSaveRegisterSupplier;
    private com.toedter.calendar.JDateChooser jDateEntry;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTextField txtCitySupplier;
    private javax.swing.JTextField txtEnterpriselSupplier;
    private javax.swing.JTextField txtIdSupplier;
    private javax.swing.JTextField txtNameSupplier;
    private javax.swing.JTextField txtPhone2Supplier;
    private javax.swing.JTextField txtPhoneSupplier;
    private javax.swing.JTextField txtStateSupplier;
    private javax.swing.JTextField txtTypeSupplier;
    private javax.swing.JTextField txtemailSupplier;
    // End of variables declaration//GEN-END:variables
}
