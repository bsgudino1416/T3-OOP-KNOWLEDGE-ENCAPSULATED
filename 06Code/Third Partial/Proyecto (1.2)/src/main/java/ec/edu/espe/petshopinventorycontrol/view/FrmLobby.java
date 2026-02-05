package ec.edu.espe.petshopinventorycontrol.view;

import ec.edu.espe.petshopinventorycontrol.utils.ScrollUtils;
import ec.edu.espe.petshopinventorycontrol.controller.LobbyController;
import ec.edu.espe.petshopinventorycontrol.controller.LobbyService;
import ec.edu.espe.petshopinventorycontrol.controller.LobbyView;
import ec.edu.espe.petshopinventorycontrol.model.mongo.MongoBillGateway;
import ec.edu.espe.petshopinventorycontrol.model.mongo.MongoProductGateway;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;


public class FrmLobby extends javax.swing.JFrame implements LobbyView {

    private final LobbyController controller;
    
    public FrmLobby() {
        initComponents();
        ScrollUtils.applyScrollBars(this);
        controller = new LobbyController(
                new LobbyService(new MongoProductGateway(), new MongoBillGateway())
        );
        initCustomLayout();
        configureNavigation();
        controller.onInit(this);
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        Tittle = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        TittleInventory = new javax.swing.JLabel();
        TittleBill = new javax.swing.JLabel();
        TotalCostInvestment = new javax.swing.JLabel();
        TotalBill = new javax.swing.JLabel();
        Supplier = new javax.swing.JLabel();
        product = new javax.swing.JLabel();
        Bill = new javax.swing.JLabel();
        personal = new javax.swing.JLabel();
        labelstock = new javax.swing.JLabel();
        expiredproduct = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(0, 0, 119));

        Tittle.setFont(new java.awt.Font("Bodoni MT", 1, 18)); // NOI18N
        Tittle.setText("LOBBY");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(174, 174, 174)
                .addComponent(Tittle)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(Tittle)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        TittleInventory.setFont(new java.awt.Font("Bodoni MT", 1, 14)); // NOI18N
        TittleInventory.setText("Total Inventario:");

        TittleBill.setFont(new java.awt.Font("Bodoni MT", 1, 14)); // NOI18N
        TittleBill.setText("Total Factura");

        TotalCostInvestment.setFont(new java.awt.Font("Bodoni MT", 1, 14)); // NOI18N
        TotalCostInvestment.setText("$0.00");

        TotalBill.setFont(new java.awt.Font("Bodoni MT", 1, 14)); // NOI18N
        TotalBill.setText("$0.00");

        Supplier.setIcon(new javax.swing.ImageIcon("C:\\Users\\User\\Pictures\\fotos Proyecto\\proveedores.png")); // NOI18N

        product.setIcon(new javax.swing.ImageIcon("C:\\Users\\User\\Pictures\\fotos Proyecto\\producto.png")); // NOI18N

        Bill.setIcon(new javax.swing.ImageIcon("C:\\Users\\User\\Pictures\\fotos Proyecto\\orden de compra .png")); // NOI18N

        personal.setIcon(new javax.swing.ImageIcon("C:\\Users\\User\\Pictures\\fotos Proyecto\\personal.png")); // NOI18N

        labelstock.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ec/edu/espe/petshopinventorycontrol/utils/images/stock.png"))); // NOI18N

        expiredproduct.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ec/edu/espe/petshopinventorycontrol/utils/images/producto caducado.png"))); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(Supplier)
                            .addGap(44, 44, 44)
                            .addComponent(product)
                            .addGap(45, 45, 45)
                            .addComponent(personal))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(Bill)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(expiredproduct)
                            .addGap(44, 44, 44)
                            .addComponent(labelstock)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(TittleInventory)
                        .addGap(217, 217, 217)
                        .addComponent(TittleBill))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(TotalCostInvestment)
                        .addGap(265, 265, 265)
                        .addComponent(TotalBill)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TittleInventory)
                    .addComponent(TittleBill))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TotalCostInvestment)
                    .addComponent(TotalBill))
                .addGap(59, 59, 59)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(product)
                    .addComponent(Supplier)
                    .addComponent(personal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(expiredproduct)
                    .addComponent(labelstock)
                    .addComponent(Bill))
                .addGap(7, 7, 7))
        );

        jPanel1.setBackground(new java.awt.Color(0, 0, 119));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 75, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void initCustomLayout() {
        javax.swing.JPanel totalsPanel = new javax.swing.JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        totalsPanel.setOpaque(false);
        totalsPanel.add(TittleInventory);
        totalsPanel.add(TotalCostInvestment);
        totalsPanel.add(TittleBill);
        totalsPanel.add(TotalBill);

        javax.swing.JPanel navPanel = new javax.swing.JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        navPanel.setOpaque(false);
        navPanel.add(Supplier);
        navPanel.add(product);
        navPanel.add(personal);
        navPanel.add(labelstock);
        navPanel.add(Bill);
        navPanel.add(expiredproduct);

        jPanel3.removeAll();
        jPanel3.setLayout(new BoxLayout(jPanel3, BoxLayout.Y_AXIS));
        jPanel3.add(totalsPanel);
        jPanel3.add(navPanel);
        jPanel3.revalidate();
        jPanel3.repaint();
    }

    private void configureNavigation() {
        configureClickable(Supplier, () -> openForm(new FrmSupplier()));
        configureClickable(product, () -> openForm(new FrmProduct()));
        configureClickable(personal, () -> openForm(new FrmNewPersonal()));
        configureClickable(Bill, () -> openForm(new FrmBill()));
        configureClickable(labelstock, () -> openForm(new FrmStock()));
        configureClickable(expiredproduct, () -> openForm(new FrmProductsExpired()));
    }

    private void configureClickable(JLabel label, Runnable action) {
        label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                action.run();
            }
        });
    }

    private void openForm(javax.swing.JFrame frame) {
        frame.setVisible(true);
        dispose();
    }

    
    public static void main(String args[]) {
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
            java.util.logging.Logger.getLogger(FrmLobby.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmLobby.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmLobby.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmLobby.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmLobby().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Bill;
    private javax.swing.JLabel Supplier;
    private javax.swing.JLabel Tittle;
    private javax.swing.JLabel TittleBill;
    private javax.swing.JLabel TittleInventory;
    private javax.swing.JLabel TotalBill;
    private javax.swing.JLabel TotalCostInvestment;
    private javax.swing.JLabel expiredproduct;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel labelstock;
    private javax.swing.JLabel personal;
    private javax.swing.JLabel product;
    // End of variables declaration//GEN-END:variables

    @Override
    public void setTotalInventoryText(String value) {
        TotalCostInvestment.setText(value);
    }

    @Override
    public void setTotalBillText(String value) {
        TotalBill.setText(value);
    }

    @Override
    public void showMessage(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }
}
