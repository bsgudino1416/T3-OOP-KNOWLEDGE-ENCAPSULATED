package ec.edu.espe.petshopinventorycontrol.employee.view;

import javax.swing.ImageIcon;
import java.awt.Image;
import ec.edu.espe.petshopinventorycontrol.employee.sale.FrmEmployeeCatSection;
import ec.edu.espe.petshopinventorycontrol.employee.sale.FrmEmployeeDogSection;
import ec.edu.espe.petshopinventorycontrol.employee.sale.FrmEmployeeConejilloSection;
import ec.edu.espe.petshopinventorycontrol.employee.sale.FrmEmployeeCowSection;
import ec.edu.espe.petshopinventorycontrol.employee.sale.FrmEmployeeHorseSection;
import ec.edu.espe.petshopinventorycontrol.employee.sale.FrmEmployeePigSection;
import ec.edu.espe.petshopinventorycontrol.employee.sale.FrmEmployeeChickenSection;


/**
 *
 * @author Bryan Gudino, KNOWLEDGE ENCAPSULATE, @ESPE
 */
public class FrmEmployeeSale extends javax.swing.JFrame {
    
    private static FrmEmployeeSale instance;
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FrmEmployeeSale.class.getName());

    /**
     * Creates new form NewJFrame1
     */
    private FrmEmployeeSale() {
        initComponents();
        cargarImagenDog();
        cargarImagenCat();
        cargarImagenConejillo();
        cargarImagenCow();
        cargarImagenChicken();
        cargarImagenHorse();
        cargarImagenPig();
    }
    
    public static FrmEmployeeSale getInstance() {
    if (instance == null) {
        instance = new FrmEmployeeSale();
    }
    return instance;
    }

    
    
    private void cargarImagenDog() {
        lblDog.setText("");

        ImageIcon original = new ImageIcon(getClass().getResource("/Graphics/Dog.jpg"));

        int ancho = 91;
        int alto = 64;

        Image imagenEscalada = original.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);

        lblDog.setIcon(new ImageIcon(imagenEscalada));
    }
    
    private void cargarImagenCat() {
    lblCat.setText("");
    ImageIcon original = new ImageIcon(getClass().getResource("/Graphics/Cat.jpg"));
    int ancho = 91;
    int alto = 64;
    Image imagenEscalada = original.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
    lblCat.setIcon(new ImageIcon(imagenEscalada));
    }

    private void cargarImagenConejillo() {
    lblConejillo.setText("");
    ImageIcon original = new ImageIcon(getClass().getResource("/Graphics/Conejillo.jpg"));
    int ancho = 91;
    int alto = 64;
    Image imagenEscalada = original.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
    lblConejillo.setIcon(new ImageIcon(imagenEscalada));
    }

private void cargarImagenCow() {
    lblCow.setText("");
    ImageIcon original = new ImageIcon(getClass().getResource("/Graphics/Cow.jpg"));
    int ancho = 84;
    int alto = 49;
    Image imagenEscalada = original.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
    lblCow.setIcon(new ImageIcon(imagenEscalada));
}

    private void cargarImagenHorse() {
    lblHorse.setText("");
    ImageIcon original = new ImageIcon(getClass().getResource("/Graphics/Horse.jpg"));
    int ancho = 84;
    int alto = 49;
    Image imagenEscalada = original.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
    lblHorse.setIcon(new ImageIcon(imagenEscalada));
    }

    private void cargarImagenPig() {
    lblPig.setText("");
    ImageIcon original = new ImageIcon(getClass().getResource("/Graphics/Pig.jpg"));
    int ancho = 84;
    int alto = 49;
    Image imagenEscalada = original.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
    lblPig.setIcon(new ImageIcon(imagenEscalada));
    }

    private void cargarImagenChicken() {
    lblChicken.setText("");
    ImageIcon original = new ImageIcon(getClass().getResource("/Graphics/Chicken.jpg"));
    int ancho = 84;
    int alto = 49;
    Image imagenEscalada = original.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
    lblChicken.setIcon(new ImageIcon(imagenEscalada));
    }
    
    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {
    FrmEmployee frmEmployee = new FrmEmployee();
    frmEmployee.setVisible(true);
    this.dispose(); // cierra ventas
    }

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {
    System.exit(0);
    }

    
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
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new FrmEmployeeSale().setVisible(true));
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
        btnBack = new javax.swing.JButton();
        btnExit = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        btnDog = new javax.swing.JButton();
        btnCat = new javax.swing.JButton();
        btnConejillo = new javax.swing.JButton();
        lblCat = new javax.swing.JLabel();
        lblConejillo = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        btnCow = new javax.swing.JButton();
        lblCow = new javax.swing.JLabel();
        btnHorse = new javax.swing.JButton();
        btnPig = new javax.swing.JButton();
        btnChicken = new javax.swing.JButton();
        lblHorse = new javax.swing.JLabel();
        lblPig = new javax.swing.JLabel();
        lblChicken = new javax.swing.JLabel();
        lblDog = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        MenuToHome = new javax.swing.JMenu();
        MenuItemDog = new javax.swing.JMenuItem();
        MenuItemCat = new javax.swing.JMenuItem();
        MenuItemConejillo = new javax.swing.JMenuItem();
        MenuToFarm = new javax.swing.JMenu();
        MenuItemCow = new javax.swing.JMenuItem();
        MenuItemHorse = new javax.swing.JMenuItem();
        MenuItemPig = new javax.swing.JMenuItem();
        MenuItemChicken = new javax.swing.JMenuItem();
        MenuToSale = new javax.swing.JMenu();
        MenuItemTable = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 3, 18)); // NOI18N
        jLabel1.setText("MENÚ EMPLEADO (Ventas)");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel1)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        btnBack.setText("Regresar");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        btnExit.setText("Salir");
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(btnBack)
                .addGap(95, 95, 95)
                .addComponent(btnExit)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBack)
                    .addComponent(btnExit))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel2.setText("Mascotas de casa");

        btnDog.setText("Perro");
        btnDog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDogActionPerformed(evt);
            }
        });

        btnCat.setText("Gato");
        btnCat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCatActionPerformed(evt);
            }
        });

        btnConejillo.setText("Conejillo");
        btnConejillo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConejilloActionPerformed(evt);
            }
        });

        lblCat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Graphics/Cat.jpg"))); // NOI18N
        lblCat.setText("<img.gato>");

        lblConejillo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Graphics/Conejillo.jpg"))); // NOI18N
        lblConejillo.setText("<img.coneji>");

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel8.setText("De granja");

        btnCow.setText("Vaca");
        btnCow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCowActionPerformed(evt);
            }
        });

        lblCow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Graphics/Cow.jpg"))); // NOI18N
        lblCow.setText("<img.vaca>");

        btnHorse.setText("Caballo");
        btnHorse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHorseActionPerformed(evt);
            }
        });

        btnPig.setText("Cerdo");
        btnPig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPigActionPerformed(evt);
            }
        });

        btnChicken.setText("Pollo");
        btnChicken.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChickenActionPerformed(evt);
            }
        });

        lblHorse.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Graphics/Horse.jpg"))); // NOI18N
        lblHorse.setText("<img.caballo>");

        lblPig.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Graphics/Pig.jpg"))); // NOI18N
        lblPig.setText("<img.cerdo>");

        lblChicken.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Graphics/Chicken.jpg"))); // NOI18N
        lblChicken.setText("<img.pollo>");

        lblDog.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Graphics/Dog.jpg"))); // NOI18N
        lblDog.setText("jLabel3");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnDog, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(30, 30, 30)
                                .addComponent(btnCat))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(lblDog, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(lblCat, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)))
                        .addGap(28, 28, 28)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnConejillo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblConejillo, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnCow, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblCow, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnHorse)
                                    .addComponent(lblHorse, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(lblPig, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(btnPig)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnChicken, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblChicken, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE))))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblDog, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblConejillo, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblCat, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnConejillo)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnDog)
                        .addComponent(btnCat)))
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCow, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblHorse, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPig, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblChicken, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCow)
                    .addComponent(btnHorse)
                    .addComponent(btnPig)
                    .addComponent(btnChicken))
                .addContainerGap())
        );

        MenuToHome.setText("De casa");

        MenuItemDog.setText("Perro");
        MenuItemDog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItemDogActionPerformed(evt);
            }
        });
        MenuToHome.add(MenuItemDog);

        MenuItemCat.setText("Gato");
        MenuItemCat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItemCatActionPerformed(evt);
            }
        });
        MenuToHome.add(MenuItemCat);

        MenuItemConejillo.setText("Conejillo");
        MenuItemConejillo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItemConejilloActionPerformed(evt);
            }
        });
        MenuToHome.add(MenuItemConejillo);

        jMenuBar1.add(MenuToHome);

        MenuToFarm.setText("De granja");

        MenuItemCow.setText("Vaca");
        MenuItemCow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItemCowActionPerformed(evt);
            }
        });
        MenuToFarm.add(MenuItemCow);

        MenuItemHorse.setText("Caballo");
        MenuItemHorse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItemHorseActionPerformed(evt);
            }
        });
        MenuToFarm.add(MenuItemHorse);

        MenuItemPig.setText("Cerdo");
        MenuItemPig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItemPigActionPerformed(evt);
            }
        });
        MenuToFarm.add(MenuItemPig);

        MenuItemChicken.setText("Pollo");
        MenuItemChicken.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItemChickenActionPerformed(evt);
            }
        });
        MenuToFarm.add(MenuItemChicken);

        jMenuBar1.add(MenuToFarm);

        MenuToSale.setText("Resumen de ventas");

        MenuItemTable.setText("Abrir tabla de ventas");
        MenuItemTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItemTableActionPerformed(evt);
            }
        });
        MenuToSale.add(MenuItemTable);

        jMenuBar1.add(MenuToSale);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCowActionPerformed
        FrmEmployeeCowSection frmCat = new FrmEmployeeCowSection();
        frmCat.setVisible(true);
        this.dispose();        
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCowActionPerformed

    private void btnConejilloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConejilloActionPerformed
        FrmEmployeeConejilloSection frmCat = new FrmEmployeeConejilloSection();
        frmCat.setVisible(true);
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnConejilloActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed

        FrmEmployee frmEmployee = new FrmEmployee();
        frmEmployee.setVisible(true);
        this.dispose();

        // TODO add your handling code here:
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed

        System.exit(0);
        // TODO add your handling code here:
    }//GEN-LAST:event_btnExitActionPerformed

    private void btnCatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCatActionPerformed

        FrmEmployeeCatSection frmCat = new FrmEmployeeCatSection();
        frmCat.setVisible(true);
        this.dispose(); 

        // TODO add your handling code here:
    }//GEN-LAST:event_btnCatActionPerformed

    private void btnDogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDogActionPerformed

        FrmEmployeeDogSection frmCat = new FrmEmployeeDogSection();
        frmCat.setVisible(true);
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnDogActionPerformed

    private void btnHorseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHorseActionPerformed
        FrmEmployeeHorseSection frmCat = new FrmEmployeeHorseSection();
        frmCat.setVisible(true);
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnHorseActionPerformed

    private void btnPigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPigActionPerformed
        FrmEmployeePigSection frmCat = new FrmEmployeePigSection();
        frmCat.setVisible(true);
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPigActionPerformed

    private void btnChickenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChickenActionPerformed
        FrmEmployeeChickenSection frmCat = new FrmEmployeeChickenSection();
        frmCat.setVisible(true);
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnChickenActionPerformed

    private void MenuItemHorseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItemHorseActionPerformed

    FrmEmployeeHorseSection frm = new FrmEmployeeHorseSection();
    frm.setVisible(true);
    this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_MenuItemHorseActionPerformed

    private void MenuItemDogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItemDogActionPerformed

    FrmEmployeeDogSection frm = new FrmEmployeeDogSection();
    frm.setVisible(true);
    this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_MenuItemDogActionPerformed

    private void MenuItemCatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItemCatActionPerformed

    FrmEmployeeCatSection frm = new FrmEmployeeCatSection();
    frm.setVisible(true);
    this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_MenuItemCatActionPerformed

    private void MenuItemConejilloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItemConejilloActionPerformed

    FrmEmployeeConejilloSection frm = new FrmEmployeeConejilloSection();
    frm.setVisible(true);
    this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_MenuItemConejilloActionPerformed

    private void MenuItemCowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItemCowActionPerformed

    FrmEmployeeCowSection frm = new FrmEmployeeCowSection();
    frm.setVisible(true);
    this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_MenuItemCowActionPerformed

    private void MenuItemPigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItemPigActionPerformed

    FrmEmployeePigSection frm = new FrmEmployeePigSection();
    frm.setVisible(true);
    this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_MenuItemPigActionPerformed

    private void MenuItemChickenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItemChickenActionPerformed

    FrmEmployeeChickenSection frm = new FrmEmployeeChickenSection();
    frm.setVisible(true);
    this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_MenuItemChickenActionPerformed

    private void MenuItemTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItemTableActionPerformed

    FrmEmployeeSummary summary = FrmEmployeeSummary.getInstance();
    summary.setVisible(true);
    
    this.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_MenuItemTableActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem MenuItemCat;
    private javax.swing.JMenuItem MenuItemChicken;
    private javax.swing.JMenuItem MenuItemConejillo;
    private javax.swing.JMenuItem MenuItemCow;
    private javax.swing.JMenuItem MenuItemDog;
    private javax.swing.JMenuItem MenuItemHorse;
    private javax.swing.JMenuItem MenuItemPig;
    private javax.swing.JMenuItem MenuItemTable;
    private javax.swing.JMenu MenuToFarm;
    private javax.swing.JMenu MenuToHome;
    private javax.swing.JMenu MenuToSale;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnCat;
    private javax.swing.JButton btnChicken;
    private javax.swing.JButton btnConejillo;
    private javax.swing.JButton btnCow;
    private javax.swing.JButton btnDog;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnHorse;
    private javax.swing.JButton btnPig;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lblCat;
    private javax.swing.JLabel lblChicken;
    private javax.swing.JLabel lblConejillo;
    private javax.swing.JLabel lblCow;
    private javax.swing.JLabel lblDog;
    private javax.swing.JLabel lblHorse;
    private javax.swing.JLabel lblPig;
    // End of variables declaration//GEN-END:variables
}
