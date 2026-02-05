package ec.edu.espe.petshopinventorycontrol.view;

import ec.edu.espe.petshopinventorycontrol.utils.ScrollUtils;
import ec.edu.espe.petshopinventorycontrol.controller.PersonalController;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class FrmNewPersonal extends javax.swing.JFrame {

    private final PersonalController controller;

    
    public FrmNewPersonal() {
        initComponents();
        ScrollUtils.applyScrollBars(this);
        controller = new PersonalController(this, new PersonalController.Bindings(
                txtIdPersonal,
                txtCiPersonal,
                txtName,
                txtAdress,
                cmbPost,
                cmbSchedule,
                cmbDay,
                cmbState,
                DateofIncorporated
        ));
        configureMenu();
        configureReportMenu();
        configureGraphicMenu();
        controller.onInit();
    }

    private void configureReadOnlyFields() {
        txtIdPersonal.setEditable(false);
    }

    private void configureMenu() {
        itmNewRegisterSupplier.setText("Nuevo Registro");
        itmSaveRegisterSupplier.setText("Guardar Registro");
        itmDeleteRegisterSupplier.setVisible(false);

        itmGraphicPersonal.setText("Grafica Personal");
        itmReport.setText("Informe Personal");
        jMenuItem3.setText("Informacion");
    }

    private void configureGraphicMenu() {
        itmGraphicPersonal.addActionListener(e -> controller.onOpenGraphicPersonal());
        itmGraphicRegister.addActionListener(e -> controller.onOpenGraphicRegister());
    }

    private void configureReportMenu() {
        itmReport.addActionListener(e -> controller.onReport());
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnReturnLobby = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtIdPersonal = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtCiPersonal = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        cmbPost = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        cmbSchedule = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        cmbDay = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        txtAdress = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        cmbState = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        DateofIncorporated = new com.toedter.calendar.JDateChooser();
        jMenuBar1 = new javax.swing.JMenuBar();
        MnuFile = new javax.swing.JMenu();
        itmNewRegisterSupplier = new javax.swing.JMenuItem();
        itmDeleteRegisterSupplier = new javax.swing.JMenuItem();
        itmSaveRegisterSupplier = new javax.swing.JMenuItem();
        MnuOptions = new javax.swing.JMenu();
        itmGraphicPersonal = new javax.swing.JMenuItem();
        itmGraphicRegister = new javax.swing.JMenuItem();
        itmReport = new javax.swing.JMenuItem();
        MnuHelp = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(0, 0, 119));

        jLabel10.setFont(new java.awt.Font("Bodoni MT", 1, 24)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Personal");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(341, 341, 341)
                .addComponent(jLabel10)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel10)
                .addContainerGap(29, Short.MAX_VALUE))
        );

        jPanel1.setBackground(new java.awt.Color(0, 0, 119));

        btnReturnLobby.setText("Regresar");
        btnReturnLobby.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReturnLobbyActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(btnReturnLobby)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(btnReturnLobby)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Bodoni MT", 0, 14)); // NOI18N
        jLabel1.setText("Id Personal:");

        jLabel2.setFont(new java.awt.Font("Bodoni MT", 0, 14)); // NOI18N
        jLabel2.setText("Cedula:");

        jLabel3.setFont(new java.awt.Font("Bodoni MT", 0, 14)); // NOI18N
        jLabel3.setText("Nombre Completo:");

        jLabel5.setFont(new java.awt.Font("Bodoni MT", 0, 14)); // NOI18N
        jLabel5.setText("Cargo:");

        cmbPost.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Vendedor", "Administrador", " " }));

        jLabel6.setFont(new java.awt.Font("Bodoni MT", 0, 14)); // NOI18N
        jLabel6.setText("Turno:");

        cmbSchedule.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "8:00AM-16:30PM", "12:00PM-20:00PM", " " }));

        jLabel7.setFont(new java.awt.Font("Bodoni MT", 0, 14)); // NOI18N
        jLabel7.setText("Asignado:");

        cmbDay.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Matutino", "Vespertino" }));

        jLabel8.setFont(new java.awt.Font("Bodoni MT", 0, 14)); // NOI18N
        jLabel8.setText("Dirreccion:");

        jLabel9.setFont(new java.awt.Font("Bodoni MT", 0, 14)); // NOI18N
        jLabel9.setText("Estado:");

        cmbState.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Vinculado", "Desvinculado" }));

        jLabel11.setFont(new java.awt.Font("Bodoni MT", 0, 14)); // NOI18N
        jLabel11.setText("Fecha de incorporación:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(69, 69, 69)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(txtIdPersonal, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(txtCiPersonal, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(18, 18, 18)
                        .addComponent(DateofIncorporated, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addComponent(txtAdress, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmbState, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmbPost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmbSchedule, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmbDay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(143, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtIdPersonal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(txtCiPersonal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(cmbPost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(cmbSchedule, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(cmbDay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8)
                        .addComponent(txtAdress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel9)
                    .addComponent(cmbState, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(DateofIncorporated, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        MnuFile.setText("Archivo");

        itmNewRegisterSupplier.setText("Nuevo Registro");
        itmNewRegisterSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmNewRegisterSupplierActionPerformed(evt);
            }
        });
        MnuFile.add(itmNewRegisterSupplier);

        itmDeleteRegisterSupplier.setText("Borrar Registro");
        itmDeleteRegisterSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmDeleteRegisterSupplierActionPerformed(evt);
            }
        });
        MnuFile.add(itmDeleteRegisterSupplier);

        itmSaveRegisterSupplier.setText("Guardar Registro");
        itmSaveRegisterSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmSaveRegisterSupplierActionPerformed(evt);
            }
        });
        MnuFile.add(itmSaveRegisterSupplier);

        jMenuBar1.add(MnuFile);

        MnuOptions.setText("Opciones");

        itmGraphicPersonal.setText("Grafica Personal");
        MnuOptions.add(itmGraphicPersonal);

        itmGraphicRegister.setText("Grafica Registros");
        MnuOptions.add(itmGraphicRegister);

        itmReport.setText("Informe Personal");
        MnuOptions.add(itmReport);

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
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void itmNewRegisterSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmNewRegisterSupplierActionPerformed
        controller.onNew();
    }//GEN-LAST:event_itmNewRegisterSupplierActionPerformed

    private void itmDeleteRegisterSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmDeleteRegisterSupplierActionPerformed
        controller.onNew();
    }//GEN-LAST:event_itmDeleteRegisterSupplierActionPerformed

    private void itmSaveRegisterSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmSaveRegisterSupplierActionPerformed
        controller.onSave();
    }//GEN-LAST:event_itmSaveRegisterSupplierActionPerformed

    private void btnReturnLobbyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReturnLobbyActionPerformed
        controller.onReturnLobby();
    }//GEN-LAST:event_btnReturnLobbyActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        String info = ""
                + "El módulo de personal permite gestionar y organizar la información relacionada con los colaboradores del sistema. "
                + "Desde esta sección se pueden registrar, consultar y actualizar datos del personal, facilitando su integración con los procesos internos. "
                + "Su uso contribuye a mantener un registro ordenado y actualizado de los recursos humanos.";

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
            java.util.logging.Logger.getLogger(FrmNewPersonal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmNewPersonal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmNewPersonal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmNewPersonal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmNewPersonal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser DateofIncorporated;
    private javax.swing.JMenu MnuFile;
    private javax.swing.JMenu MnuHelp;
    private javax.swing.JMenu MnuOptions;
    private javax.swing.JButton btnReturnLobby;
    private javax.swing.JComboBox<String> cmbDay;
    private javax.swing.JComboBox<String> cmbPost;
    private javax.swing.JComboBox<String> cmbSchedule;
    private javax.swing.JComboBox<String> cmbState;
    private javax.swing.JMenuItem itmDeleteRegisterSupplier;
    private javax.swing.JMenuItem itmGraphicPersonal;
    private javax.swing.JMenuItem itmGraphicRegister;
    private javax.swing.JMenuItem itmNewRegisterSupplier;
    private javax.swing.JMenuItem itmReport;
    private javax.swing.JMenuItem itmSaveRegisterSupplier;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTextField txtAdress;
    private javax.swing.JTextField txtCiPersonal;
    private javax.swing.JTextField txtIdPersonal;
    private javax.swing.JTextField txtName;
    // End of variables declaration//GEN-END:variables
}
