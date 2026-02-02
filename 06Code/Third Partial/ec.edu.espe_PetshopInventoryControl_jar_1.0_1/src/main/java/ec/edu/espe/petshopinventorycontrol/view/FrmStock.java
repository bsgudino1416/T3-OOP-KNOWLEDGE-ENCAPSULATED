package ec.edu.espe.petshopinventorycontrol.view;

import ec.edu.espe.petshopinventorycontrol.controller.StockService;
import ec.edu.espe.petshopinventorycontrol.controller.StockValidator;
import ec.edu.espe.petshopinventorycontrol.model.mongo.MongoProductGateway;
import ec.edu.espe.petshopinventorycontrol.model.mongo.MongoStockGateway;

import java.awt.Color;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

/**
 *
 * @author Steven Loza @ESPE
 */
public class FrmStock extends javax.swing.JFrame {
    private final StockService stockService = new StockService(
            new MongoStockGateway(),
            new MongoProductGateway(),
            new StockValidator()
    );

    private final Color errorColor = new Color(204, 0, 0);
    private String lastGainValueText = "";

    /**
     * Creates new form FrmStock
     */
    public FrmStock() {
        initComponents();
        configureReadOnlyFields();
        loadCategoriesFromProducts();
        generateAndSetNewStockId();
        setupComboListeners();
        setupGainListeners();
        setupUnitCostListener();
    }

  private void setupUnitCostListener() {
    txtUnitEntry.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
        @Override
        public void insertUpdate(javax.swing.event.DocumentEvent e) {
            updatePricing();
        }

        @Override
        public void removeUpdate(javax.swing.event.DocumentEvent e) {
            updatePricing();
        }

        @Override
        public void changedUpdate(javax.swing.event.DocumentEvent e) {
            updatePricing();
        }
    });
}


    private void configureReadOnlyFields() {
        IdStock.setEditable(false);
        txtCostUnit.setEditable(false);
        txtPriceUnit.setEditable(false);
    }

    private void generateAndSetNewStockId() {
        updateStockIdFromSelection();
    }

    private void updateStockIdFromSelection() {
        Date now = new Date();
        String category = getSelectedComboValue(cmbCategory);

        try {
            String nextId = stockService.generateNextStockId(now, category);
            IdStock.setText(nextId);
        } catch (Exception ex) {
            String prefix = stockService.buildStockIdPrefix(now, category);
            IdStock.setText(prefix + "-001");
        }
    }

    private String getSelectedComboValue(javax.swing.JComboBox<String> combo) {
        Object selected = combo.getSelectedItem();
        if (selected == null) {
            return null;
        }
        String value = selected.toString().trim();
        return value.isEmpty() ? null : value;
    }

    private void loadCategoriesFromProducts() {
        try {
            List<String> categories = stockService.getCategoriesFromProducts();
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();

            if (categories.isEmpty()) {
                model.addElement("No hay categorias");
            } else {
                for (String c : categories) {
                    model.addElement(c);
                }
            }
            cmbCategory.setModel(model);

            // Al cargar categorías, limpiamos combos dependientes
            setComboModel(cmbNameofProduct, List.of());
            setComboModel(cmbBrand, List.of());
            txtCost.setText("");
            txtUnitEntry.setText("");
            txtCostUnit.setText("");
            txtPriceUnit.setText("");
            lastGainValueText = "";

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "MongoDB no esta disponible. No se pueden cargar categorias.\n" + ex.getMessage(),
                    "Error de conexion MongoDB",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    private void setupComboListeners() {
        cmbCategory.addActionListener(e -> onCategoryChanged());
        cmbNameofProduct.addActionListener(e -> onNameChanged());
        cmbBrand.addActionListener(e -> onBrandChanged());
    }

    private void onCategoryChanged() {
        updateStockIdFromSelection();
        String category = (String) cmbCategory.getSelectedItem();
        if (category == null || category.startsWith("No ")) {
            return;
        }

        try {
            List<String> names = stockService.getProductNamesByCategory(category);
            setComboModel(cmbNameofProduct, names);

            // al cambiar categoría, limpiar brand y costo
            setComboModel(cmbBrand, List.of());
            txtCost.setText("");
            txtUnitEntry.setText("");
            txtCostUnit.setText("");
            txtPriceUnit.setText("");
            lastGainValueText = "";

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar productos: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onNameChanged() {
        String category = (String) cmbCategory.getSelectedItem();
        String name = (String) cmbNameofProduct.getSelectedItem();

        if (isBlank(category) || isBlank(name) || category.startsWith("No ")) {
            return;
        }

        try {
            List<String> brands = stockService.getBrandsByCategoryAndName(category, name);
            setComboModel(cmbBrand, brands);

            // limpiar costo hasta elegir marca válida
            txtCost.setText("");
            txtUnitEntry.setText("");
            txtCostUnit.setText("");
            txtPriceUnit.setText("");
            lastGainValueText = "";

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar marcas: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onBrandChanged() {
        String category = (String) cmbCategory.getSelectedItem();
        String name = (String) cmbNameofProduct.getSelectedItem();
        String brand = (String) cmbBrand.getSelectedItem();

        if (isBlank(category) || isBlank(name) || isBlank(brand) || category.startsWith("No ")) {
            return;
        }

        StockService.ProductCostResult cost = stockService.getCostForSelectionOrNull(category, name, brand);
        if (cost == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Estos datos no coinciden con la Base de datos.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );
            txtCost.setText("");
            txtUnitEntry.setText("");
            txtCostUnit.setText("");
            txtPriceUnit.setText("");
            lastGainValueText = "";
            return;
        }

        txtCost.setText(String.valueOf(cost.cost));
        txtUnitEntry.setText("");
        updatePricing();
    }

    private void setupGainListeners() {
        cmbGain.addActionListener(e -> updatePricing());
    }

    private void updatePricing() {
        String costText = txtCost.getText();
        String unitEntryText = txtUnitEntry.getText();
        String gainSel = (String) cmbGain.getSelectedItem();

        StockService.PricingResult pricing = stockService.calculatePricing(costText, unitEntryText, gainSel);
        txtCostUnit.setText(pricing.unitCost);
        txtPriceUnit.setText(pricing.priceUnit);
        lastGainValueText = pricing.gainValue;
    }

    private void setComboModel(javax.swing.JComboBox<String> combo, List<String> values) {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        if (values == null || values.isEmpty()) {
            model.addElement("Sin datos");
        } else {
            for (String v : values) {
                model.addElement(v);
            }
        }
        combo.setModel(model);
    }

    private boolean isBlank(String v) {
        return v == null || v.trim().isEmpty();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        cmbNameofProduct = new javax.swing.JComboBox<>();
        cmbBrand = new javax.swing.JComboBox<>();
        txtCost = new javax.swing.JTextField();
        txtUnitEntry = new javax.swing.JTextField();
        cmbGain = new javax.swing.JComboBox<>();
        txtCostUnit = new javax.swing.JTextField();
        IdStock = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        cmbCategory = new javax.swing.JComboBox<>();
        txtPriceUnit = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jMenuBar3 = new javax.swing.JMenuBar();
        MnuFile2 = new javax.swing.JMenu();
        itmNewRegisterProduct = new javax.swing.JMenuItem();
        itmSaveRegisterProduct = new javax.swing.JMenuItem();
        MnuOptions = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        MnuHelp = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel2.setText("ID Stock:");

        jLabel3.setText("Nombre del producto:");

        jLabel4.setText("Marca:");

        jLabel5.setText("Costo del Producto:");

        jLabel6.setText("Costo Unidad: ");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 51, 0));
        jLabel9.setText("Ganancia");

        jLabel8.setText("Porcentaje de Ganacia:");

        cmbNameofProduct.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cmbBrand.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cmbGain.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "10%", "20%", "30%", "40%", "50%", "60%", "70%", "80%", "90%", "100%", " " }));

        txtCostUnit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCostUnitActionPerformed(evt);
            }
        });

        jLabel10.setText("Categoria Producto:");

        cmbCategory.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        txtPriceUnit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPriceUnitActionPerformed(evt);
            }
        });

        jLabel16.setText("Unidades procesadas:");

        jLabel14.setText("Precio unitario:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(IdStock, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(cmbCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(38, 38, 38))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbNameofProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(cmbBrand, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(36, 36, 36))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(txtCost, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel7)
                        .addGap(129, 129, 129))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(44, 44, 44)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(txtUnitEntry, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(38, 38, 38)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(txtCostUnit, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel8)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cmbGain, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(60, 60, 60)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(txtPriceUnit, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbNameofProduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbBrand, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(IdStock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addComponent(jLabel7)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel16)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtUnitEntry, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtCostUnit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(txtCost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbGain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPriceUnit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(80, 80, 80))
        );

        jPanel2.setBackground(new java.awt.Color(0, 0, 119));

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Stock");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(319, 319, 319)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel1)
                .addContainerGap(32, Short.MAX_VALUE))
        );

        jPanel1.setBackground(new java.awt.Color(0, 0, 119));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 675, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 75, Short.MAX_VALUE)
        );

        MnuFile2.setText("Archivo");

        itmNewRegisterProduct.setText("Nuevo Registro");
        itmNewRegisterProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmNewRegisterProductActionPerformed(evt);
            }
        });
        MnuFile2.add(itmNewRegisterProduct);

        itmSaveRegisterProduct.setText("Guardar Registro");
        itmSaveRegisterProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmSaveRegisterProductActionPerformed(evt);
            }
        });
        MnuFile2.add(itmSaveRegisterProduct);

        jMenuBar3.add(MnuFile2);

        MnuOptions.setText("Opciones");

        jMenuItem1.setText("Grafica Stock");
        MnuOptions.add(jMenuItem1);

        jMenuItem2.setText("Informe Stock");
        MnuOptions.add(jMenuItem2);

        jMenuBar3.add(MnuOptions);

        MnuHelp.setText("Ayuda");

        jMenuItem3.setText("Informacion");
        MnuHelp.add(jMenuItem3);

        jMenuBar3.add(MnuHelp);

        setJMenuBar(jMenuBar3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void itmNewRegisterProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmNewRegisterProductActionPerformed
        clearStockFieldsButKeepId();
    }//GEN-LAST:event_itmNewRegisterProductActionPerformed

    private void itmSaveRegisterProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmSaveRegisterProductActionPerformed

        String idStock = IdStock.getText();
        String category = (String) cmbCategory.getSelectedItem();
        String name = (String) cmbNameofProduct.getSelectedItem();
        String brand = (String) cmbBrand.getSelectedItem();
        String costText = txtCost.getText();
        String unitEntryText = txtUnitEntry.getText();
        String gain = (String) cmbGain.getSelectedItem();
        updatePricing();
        String unitCostText = txtCostUnit.getText();
        String finalPriceText = txtPriceUnit.getText();
        String gainValueText = lastGainValueText;

        Map<String, String> errors = stockService.validateFields(
                idStock, category, name, brand,
                costText, unitEntryText,
                gain, finalPriceText, gainValueText
        );

        applyErrors(errors);

        if (!errors.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Todos los campos deben estar completos.",
                    "Error de validacion",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        try {
            stockService.saveStock(
                    idStock, category, name, brand,
                    costText, unitCostText, unitEntryText,
                    gain, finalPriceText, gainValueText,
                    new Date()
            );

            JOptionPane.showMessageDialog(this, "Stock guardado correctamente.", "Exito",
                    JOptionPane.INFORMATION_MESSAGE);

            clearStockFieldsButKeepId();
            generateAndSetNewStockId();

        } catch (IllegalStateException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Validación", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error inesperado: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

    }
    
    

    private void clearStockFieldsButKeepId() {
        cmbCategory.setSelectedIndex(cmbCategory.getItemCount() > 0 ? 0 : -1);
        setComboModel(cmbNameofProduct, List.of());
        setComboModel(cmbBrand, List.of());

        txtCost.setText("");
        txtUnitEntry.setText("");
        cmbGain.setSelectedIndex(0);
        txtCostUnit.setText("");
        txtPriceUnit.setText("");
        lastGainValueText = "";

        clearAllErrorStates();
    }

    private void applyErrors(Map<String, String> errors) {
        clearAllErrorStates();

        if (errors.containsKey("cmbCategory")) {
            markError(cmbCategory);
        }
        if (errors.containsKey("cmbNameofProduct")) {
            markError(cmbNameofProduct);
        }
        if (errors.containsKey("cmbBrand")) {
            markError(cmbBrand);
        }

        if (errors.containsKey("txtCost")) {
            markError(txtCost);
        }
        if (errors.containsKey("txtUnitEntry")) {
            markError(txtUnitEntry);
        }

        if (errors.containsKey("cmbGain")) {
            markError(cmbGain);
        }
    }

    private void clearAllErrorStates() {
        clearError(cmbCategory);
        clearError(cmbNameofProduct);
        clearError(cmbBrand);

        clearError(txtCost);
        clearError(txtUnitEntry);

        clearError(cmbGain);
        clearError(txtCostUnit);
        clearError(txtPriceUnit);
    }

    private void markError(JComponent c) {
        c.setBorder(javax.swing.BorderFactory.createLineBorder(errorColor, 2));
    }

    private void clearError(JComponent c) {
        c.setBorder(javax.swing.BorderFactory.createLineBorder(Color.GRAY, 1));
    }//GEN-LAST:event_itmSaveRegisterProductActionPerformed

    private void txtPriceUnitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPriceUnitActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPriceUnitActionPerformed

    private void txtCostUnitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCostUnitActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCostUnitActionPerformed

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
            java.util.logging.Logger.getLogger(FrmStock.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmStock.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmStock.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmStock.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmStock().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField IdStock;
    private javax.swing.JMenu MnuFile2;
    private javax.swing.JMenu MnuHelp;
    private javax.swing.JMenu MnuOptions;
    private javax.swing.JComboBox<String> cmbBrand;
    private javax.swing.JComboBox<String> cmbCategory;
    private javax.swing.JComboBox<String> cmbGain;
    private javax.swing.JComboBox<String> cmbNameofProduct;
    private javax.swing.JMenuItem itmNewRegisterProduct;
    private javax.swing.JMenuItem itmSaveRegisterProduct;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuBar jMenuBar3;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTextField txtCost;
    private javax.swing.JTextField txtCostUnit;
    private javax.swing.JTextField txtPriceUnit;
    private javax.swing.JTextField txtUnitEntry;
    // End of variables declaration//GEN-END:variables
}

