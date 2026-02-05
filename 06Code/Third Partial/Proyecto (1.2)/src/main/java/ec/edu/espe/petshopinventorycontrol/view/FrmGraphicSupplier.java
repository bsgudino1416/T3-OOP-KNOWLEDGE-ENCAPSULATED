package ec.edu.espe.petshopinventorycontrol.view;

import ec.edu.espe.petshopinventorycontrol.utils.ScrollUtils;
import ec.edu.espe.petshopinventorycontrol.controller.GraphicSupplierController;
import ec.edu.espe.petshopinventorycontrol.controller.GraphicSupplierView;
import ec.edu.espe.petshopinventorycontrol.controller.SupplierGraphicService;
import ec.edu.espe.petshopinventorycontrol.model.mongo.MongoSupplierGateway;
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


public class FrmGraphicSupplier extends javax.swing.JFrame implements GraphicSupplierView {

    private final GraphicSupplierController controller;
    private boolean initializing = true;

    private JPanel filterPanel;
    private javax.swing.JComboBox<String> cmbFilterField1;
    private javax.swing.JComboBox<String> cmbFilterValue1;
    private javax.swing.JComboBox<String> cmbFilterField2;
    private javax.swing.JComboBox<String> cmbFilterValue2;
    private javax.swing.JComboBox<String> cmbChartType;
    private javax.swing.JComboBox<String> cmbChartField;
    private javax.swing.JButton btnReturn;

    
    public FrmGraphicSupplier() {
        initComponents();
        ScrollUtils.applyScrollBars(this);
        controller = new GraphicSupplierController(
                new SupplierGraphicService(new MongoSupplierGateway())
        );
        initCustomLayout();
        configureListeners();
        controller.onInit(this);
        initializing = false;
    }

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        GraphicSupplier = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblGraphicSupplier = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(0, 0, 119));

        jLabel1.setFont(new java.awt.Font("Bodoni MT", 0, 14)); // NOI18N
        jLabel1.setText("GRAFICA DE PROVEEDORES ");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(302, 302, 302)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(jLabel1)
                .addContainerGap(53, Short.MAX_VALUE))
        );

        GraphicSupplier.setText("GRAFICA");

        tblGraphicSupplier.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblGraphicSupplier);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(339, 339, 339)
                        .addComponent(GraphicSupplier))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 688, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(49, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(97, 97, 97)
                .addComponent(GraphicSupplier)
                .addContainerGap(148, Short.MAX_VALUE))
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
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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

        GraphicSupplier.setHorizontalAlignment(SwingConstants.CENTER);
        jScrollPane1.setPreferredSize(new Dimension(720, 160));
        GraphicSupplier.setPreferredSize(new Dimension(720, 240));

        jPanel3.removeAll();
        jPanel3.setLayout(new javax.swing.BoxLayout(jPanel3, javax.swing.BoxLayout.Y_AXIS));
        jPanel3.add(filterPanel);
        jPanel3.add(jScrollPane1);
        jPanel3.add(GraphicSupplier);
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
            new FrmSupplier().setVisible(true);
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
    public int getChartWidth() {
        int width = GraphicSupplier.getWidth();
        return width > 0 ? width : 720;
    }

    @Override
    public int getChartHeight() {
        int height = GraphicSupplier.getHeight();
        return height > 0 ? height : 240;
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
        selectComboItem(cmbChartField, "Empresa");
    }

    @Override
    public void setTableModel(TableModel model) {
        tblGraphicSupplier.setModel(model);
    }

    @Override
    public void setChartIcon(Icon icon) {
        GraphicSupplier.setIcon(icon);
        GraphicSupplier.setText("");
    }

    @Override
    public void setChartMessage(String message) {
        GraphicSupplier.setIcon(null);
        GraphicSupplier.setText(message);
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
            java.util.logging.Logger.getLogger(FrmGraphicSupplier.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmGraphicSupplier.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmGraphicSupplier.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmGraphicSupplier.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmGraphicSupplier().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel GraphicSupplier;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblGraphicSupplier;
    // End of variables declaration//GEN-END:variables
}

