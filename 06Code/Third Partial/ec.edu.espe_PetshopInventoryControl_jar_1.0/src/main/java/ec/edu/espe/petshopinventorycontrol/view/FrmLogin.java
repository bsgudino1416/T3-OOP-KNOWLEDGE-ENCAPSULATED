public class FrmLogin extends javax.swing.JFrame {

    public FrmLogin() {
        initComponents();
        bttnCreate.setText("Crear cuenta");

        // Configuración del botón Crear
        bttnCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bttnCreateActionPerformed(evt);
            }
        });
        
        // Configuración del botón Iniciar Sesión
        bttnSignIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bttnSignInActionPerformed(evt);
            }
        });
    } // <--- AQUÍ TERMINA EL CONSTRUCTOR. ¡NO PONGAS OTRA LLAVE DEBAJO!
    
    // (Aquí abajo deben seguir tus métodos: bttnCreateActionPerformed, bttnSignInActionPerformed, etc.)
    // --- 1. BOTÓN CREAR CUENTA ---
    private void bttnCreateActionPerformed(java.awt.event.ActionEvent evt) {
        FrmRegister register = new FrmRegister();
        register.setVisible(true);
        this.dispose();
    }

    // --- 2. BOTÓN INICIAR SESIÓN (EL IMPORTANTE) ---
    private void bttnSignInActionPerformed(java.awt.event.ActionEvent evt) {                                           
        // Mensaje de prueba
        javax.swing.JOptionPane.showMessageDialog(this, "Bienvenido (Entrando...)");

        try {
            // Abre el menú principal usando tu Controlador
            ec.edu.espe.petshopinventorycontrol.controller.NavigationController.enterSystem(this);
        } catch (Throwable e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, "Error: " + e.toString());
        }
    } 

    // --- 3. EL ARRANQUE (MAIN) ---
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(FrmLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
               
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton bttnCreate;
    private javax.swing.JButton bttnSignIn;
    // (Aquí pueden haber más variables generadas por NetBeans como labels, etc.)
    // End of variables declaration                   

} // <--- ¡ESTA ES LA ÚLTIMA LLAVE! CIERRA TODO EL ARCHIVO.
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        bttnSigIn = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtUserLogin = new javax.swing.JTextField();
        txtPassLogin = new javax.swing.JPasswordField();
        jPanel3 = new javax.swing.JPanel();
        bttnSignIn = new javax.swing.JButton();
        bttnCreate = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(0, 0, 119));

        bttnSigIn.setFont(new java.awt.Font("Bodoni MT", 1, 24)); // NOI18N
        bttnSigIn.setForeground(new java.awt.Color(255, 255, 255));
        bttnSigIn.setText("Sign In");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(bttnSigIn)
                .addGap(149, 149, 149))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(bttnSigIn)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        jLabel3.setText("Usuario:");

        jLabel4.setText("Contraseña:");

        txtUserLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUserLoginActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtUserLogin)
                            .addComponent(txtPassLogin, javax.swing.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(146, 146, 146)
                        .addComponent(jLabel2)))
                .addContainerGap(61, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtUserLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtPassLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(68, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(0, 0, 119));

        bttnSignIn.setText("Iniciar Sesión");
        bttnSignIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bttnSignInActionPerformed(evt);
            }
        });

        bttnCreate.setText("Crear cuenta");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(bttnSignIn)
                .addGap(70, 70, 70)
                .addComponent(bttnCreate)
                .addContainerGap(59, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bttnSignIn)
                    .addComponent(bttnCreate))
                .addGap(22, 22, 22))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(12, 12, 12))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtUserLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUserLoginActionPerformed
        
    }//GEN-LAST:event_txtUserLoginActionPerformed

    private void bttnSignInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bttnSignInActionPerformed
package ec.edu.espe.petshopinventorycontrol.view;

public class FrmLogin extends javax.swing.JFrame {

    public FrmLogin() {
        initComponents(); // Esto carga tu diseño (botones y textos)
        
        // Configuraciones extra
        bttnCreate.setText("Crear cuenta");
        
        // Acción del botón Crear Cuenta
        bttnCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bttnCreateActionPerformed(evt);
            }
        });
    }

    // --- AQUÍ ESTÁ EL BOTÓN DE ENTRAR (EL QUE TU MANDASTE) ---
    private void bttnSignInActionPerformed(java.awt.event.ActionEvent evt) {                                           
        // 1. Mensaje de bienvenida
        javax.swing.JOptionPane.showMessageDialog(this, "Bienvenido al Sistema (Cargando Menú...)");

        try {
            // 2. Intentamos abrir el Menú Principal
            ec.edu.espe.petshopinventorycontrol.controller.NavigationController.enterSystem(this);
            
        } catch (Throwable e) {
            // 3. SI FALLA: Muestra el error
            e.printStackTrace(); 
            javax.swing.JOptionPane.showMessageDialog(this, 
                "No se pudo abrir el Menú Principal.\n\nError: " + e.toString(), 
                "Error al cargar ventana", 
                javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    } 

    // --- ACCIÓN DEL BOTÓN REGISTRAR ---
    private void bttnCreateActionPerformed(java.awt.event.ActionEvent evt) {
        FrmRegister register = new FrmRegister();
        register.setVisible(true);
        this.dispose();
    }
    
    // --- ESTA PARTE NO LA TOQUES (ES EL DISEÑO GENERADO) ---
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        // ... Aquí NetBeans regenerará tu código automáticamente al guardar ...
        // ... Si esto aparece vacío o da error, dale a "Design" y mueve un botón un poco para que se regenere ...
        
        bttnCreate = new javax.swing.JButton();
        bttnSignIn = new javax.swing.JButton();
        // (El resto de componentes se regeneran solos)

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        bttnSignIn.setText("Iniciar Sesión");
        bttnSignIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bttnSignInActionPerformed(evt);
            }
        });

        // Layout setup (se regenerará)
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>                        

    // --- EL MAIN (ARRANQUE) ---
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmLogin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton bttnCreate;
    private javax.swing.JButton bttnSignIn;
    // End of variables declaration                   
}
    }//GEN-LAST:event_bttnSignInActionPerformed
// Cierre del catch
// Cierre del método del botón

// (Aquí no debe haber ninguna llave bloqueando el paso)

public static void main(String args[]) { // Tu código empieza aquí...
{
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bttnCreate;
    private javax.swing.JLabel bttnSigIn;
    private javax.swing.JButton bttnSignIn;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPasswordField txtPassLogin;
    private javax.swing.JTextField txtUserLogin;
    // End of variables declaration//GEN-END:variables
}