package ec.edu.espe.petshopinventorycontrol.view;

import ec.edu.espe.petshopinventorycontrol.utils.ScrollUtils;
import ec.edu.espe.petshopinventorycontrol.controller.GraphicProductController;
import ec.edu.espe.petshopinventorycontrol.controller.GraphicProductView;
import ec.edu.espe.petshopinventorycontrol.controller.ProductGraphicService;
import ec.edu.espe.petshopinventorycontrol.model.mongo.MongoProductGateway;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.table.TableModel;

public class FrmGraphicProduct extends javax.swing.JFrame implements GraphicProductView {

    private final GraphicProductController controller;
    private boolean initializing = true;

    private JPanel filterPanel;
    private javax.swing.JComboBox<String> cmbFilterField1;
    private javax.swing.JComboBox<String> cmbFilterValue1;
    private javax.swing.JComboBox<String> cmbFilterField2;
    private javax.swing.JComboBox<String> cmbFilterValue2;
    private javax.swing.JComboBox<String> cmbChartType;
    private javax.swing.JComboBox<String> cmbChartField;
    private javax.swing.JButton btnReturn;

    
    public FrmGraphicProduct() {
        initComponents();
        ScrollUtils.applyScrollBars(this);
        controller = new GraphicProductController(
                new ProductGraphicService(new MongoProductGateway())
        );
        initCustomLayout();
        configureListeners();
        controller.onInit(this);
        initializing = false;
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        GraphicProduct = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblGraphicProduct = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jMenuBar2 = new javax.swing.JMenuBar();
        MnuFile1 = new javax.swing.JMenu();
        itmShowRegisters = new javax.swing.JMenuItem();
        itmShowGraphic = new javax.swing.JMenuItem();
        MnuHelp1 = new javax.swing.JMenu();
        jMenuItem6 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        GraphicProduct.setText("GRAFICA");

        tblGraphicProduct.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tblGraphicProduct);

        jPanel2.setBackground(new java.awt.Color(0, 0, 119));

        jLabel1.setFont(new java.awt.Font("Bodoni MT", 1, 18)); // NOI18N
        jLabel1.setText("GRAFICA DE PRODUCTOS");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(300, 300, 300)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jLabel1)
                .addContainerGap(57, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 688, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(362, 362, 362)
                                .addComponent(GraphicProduct)))
                        .addGap(0, 15, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(75, 75, 75)
                .addComponent(GraphicProduct)
                .addContainerGap(170, Short.MAX_VALUE))
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

        MnuFile1.setText("Archivo");

        itmShowRegisters.setText("Mostrar Datos");
        itmShowRegisters.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmShowRegistersActionPerformed(evt);
            }
        });
        MnuFile1.add(itmShowRegisters);

        itmShowGraphic.setText("Mostrar Grafica");
        itmShowGraphic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmShowGraphicActionPerformed(evt);
            }
        });
        MnuFile1.add(itmShowGraphic);

        jMenuBar2.add(MnuFile1);

        MnuHelp1.setText("Ayuda");

        jMenuItem6.setText("Información");
        MnuHelp1.add(jMenuItem6);

        jMenuBar2.add(MnuHelp1);

        setJMenuBar(jMenuBar2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void itmShowRegistersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmShowRegistersActionPerformed
        controller.onShowAll(this);
    }//GEN-LAST:event_itmShowRegistersActionPerformed

    private void itmShowGraphicActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmShowGraphicActionPerformed
        controller.onShowGraphic(this);
    }//GEN-LAST:event_itmShowGraphicActionPerformed

    private void initCustomLayout() {
        filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblFilter1 = new JLabel("Filtro 1:");
        JLabel lblFilter2 = new JLabel("Filtro 2:");
        JLabel lblChartType = new JLabel("Grafica:");
        JLabel lblChartField = new JLabel("Campo:");

        cmbFilterField1 = new javax.swing.JComboBox<>();
        cmbFilterValue1 = new javax.swing.JComboBox<>();
        cmbFilterValue1.setEditable(true);

        cmbFilterField2 = new javax.swing.JComboBox<>();
        cmbFilterValue2 = new javax.swing.JComboBox<>();
        cmbFilterValue2.setEditable(true);

        cmbChartType = new javax.swing.JComboBox<>();
        cmbChartField = new javax.swing.JComboBox<>();

        filterPanel.add(lblFilter1);
        filterPanel.add(cmbFilterField1);
        filterPanel.add(cmbFilterValue1);
        filterPanel.add(lblFilter2);
        filterPanel.add(cmbFilterField2);
        filterPanel.add(cmbFilterValue2);
        filterPanel.add(lblChartType);
        filterPanel.add(cmbChartType);
        filterPanel.add(lblChartField);
        filterPanel.add(cmbChartField);

        GraphicProduct.setHorizontalAlignment(SwingConstants.CENTER);
        jScrollPane1.setPreferredSize(new Dimension(720, 160));
        GraphicProduct.setPreferredSize(new Dimension(720, 240));

        jPanel3.removeAll();
        jPanel3.setLayout(new javax.swing.BoxLayout(jPanel3, javax.swing.BoxLayout.Y_AXIS));
        jPanel3.add(filterPanel);
        jPanel3.add(jScrollPane1);
        jPanel3.add(GraphicProduct);
        jPanel3.revalidate();
        jPanel3.repaint();

        btnReturn = new javax.swing.JButton("Regresar");
        jPanel1.removeAll();
        jPanel1.setLayout(new FlowLayout(FlowLayout.LEFT));
        jPanel1.add(btnReturn);
        jPanel1.revalidate();
        jPanel1.repaint();
    }

    private void configureListeners() {
        cmbFilterField1.addActionListener(e -> {
            if (initializing) {
                return;
            }
            controller.onFilterFieldChanged(this, 1);
            controller.onApplyFilters(this);
        });
        cmbFilterField2.addActionListener(e -> {
            if (initializing) {
                return;
            }
            controller.onFilterFieldChanged(this, 2);
            controller.onApplyFilters(this);
        });
        cmbFilterValue1.addActionListener(e -> {
            if (initializing) {
                return;
            }
            controller.onApplyFilters(this);
        });
        cmbFilterValue2.addActionListener(e -> {
            if (initializing) {
                return;
            }
            controller.onApplyFilters(this);
        });
        cmbChartType.addActionListener(e -> {
            if (initializing) {
                return;
            }
            controller.onApplyFilters(this);
        });
        cmbChartField.addActionListener(e -> {
            if (initializing) {
                return;
            }
            controller.onApplyFilters(this);
        });
        btnReturn.addActionListener(e -> {
            new FrmProduct().setVisible(true);
            dispose();
        });
    }

    @Override
    public String getFilterField1() {
        return getComboSelection(cmbFilterField1);
    }

    @Override
    public String getFilterValue1() {
        return getComboSelection(cmbFilterValue1);
    }

    @Override
    public String getFilterField2() {
        return getComboSelection(cmbFilterField2);
    }

    @Override
    public String getFilterValue2() {
        return getComboSelection(cmbFilterValue2);
    }

    @Override
    public String getChartType() {
        return getComboSelection(cmbChartType);
    }

    @Override
    public String getChartField() {
        return getComboSelection(cmbChartField);
    }

    @Override
    public void setFilterFieldOptions(List<String> options) {
        setComboOptions(cmbFilterField1, options);
        setComboOptions(cmbFilterField2, options);
        selectComboItem(cmbFilterField1, "Id");
        selectComboItem(cmbFilterField2, "Nombre");
    }

    @Override
    public void setFilterValueOptions1(List<String> options) {
        setComboOptions(cmbFilterValue1, options);
    }

    @Override
    public void setFilterValueOptions2(List<String> options) {
        setComboOptions(cmbFilterValue2, options);
    }

    @Override
    public void setChartTypeOptions(List<String> options) {
        setComboOptions(cmbChartType, options);
        selectComboItem(cmbChartType, "Barras");
    }

    @Override
    public void setChartFieldOptions(List<String> options) {
        setComboOptions(cmbChartField, options);
        selectComboItem(cmbChartField, "Categoria");
    }

    @Override
    public void setTableModel(TableModel model) {
        tblGraphicProduct.setModel(model);
    }

    @Override
    public int getChartWidth() {
        int width = GraphicProduct.getWidth();
        return width > 0 ? width : 720;
    }

    @Override
    public int getChartHeight() {
        int height = GraphicProduct.getHeight();
        return height > 0 ? height : 240;
    }

    @Override
    public void setChartIcon(Icon icon) {
        GraphicProduct.setIcon(icon);
        GraphicProduct.setText("");
    }

    @Override
    public void setChartMessage(String message) {
        GraphicProduct.setIcon(null);
        GraphicProduct.setText(message);
    }

    @Override
    public void showMessage(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }

    private void setComboOptions(javax.swing.JComboBox<String> combo, List<String> options) {
        String[] data = options == null ? new String[0] : options.toArray(new String[0]);
        combo.setModel(new DefaultComboBoxModel<>(data));
        if (combo.getItemCount() > 0) {
            combo.setSelectedIndex(0);
        }
    }

    private String getComboSelection(javax.swing.JComboBox<String> combo) {
        if (combo == null) {
            return null;
        }
        Object selected = combo.isEditable() ? combo.getEditor().getItem() : combo.getSelectedItem();
        if (selected == null) {
            return null;
        }
        String value = selected.toString().trim();
        return value.isEmpty() ? null : value;
    }

    private void selectComboItem(javax.swing.JComboBox<String> combo, String label) {
        if (combo == null || label == null) {
            return;
        }
        for (int i = 0; i < combo.getItemCount(); i++) {
            String item = combo.getItemAt(i);
            if (label.equalsIgnoreCase(item)) {
                combo.setSelectedIndex(i);
                return;
            }
        }
    }

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
            java.util.logging.Logger.getLogger(FrmGraphicProduct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmGraphicProduct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmGraphicProduct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmGraphicProduct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmGraphicProduct().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel GraphicProduct;
    private javax.swing.JMenu MnuFile1;
    private javax.swing.JMenu MnuHelp1;
    private javax.swing.JMenuItem itmShowGraphic;
    private javax.swing.JMenuItem itmShowRegisters;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblGraphicProduct;
    // End of variables declaration//GEN-END:variables
}

