/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ec.edu.espe.petshopinventorycontrol.view;

import ec.edu.espe.petshopinventorycontrol.controller.*;
import ec.edu.espe.petshopinventorycontrol.model.mongo.*;
import java.util.*;
import javax.swing.*;

/**
 *
 * @author Steven Loza @ESPE
 */
public class FrmProduct extends javax.swing.JFrame {

    private final SupplierService supplierService = new SupplierService(
            new MongoSupplierGateway(),
            new SupplierValidator()
    );
    private final ProductService productService = new ProductService(
            new MongoProductGateway(),
            new ProductValidator()
    );

    private boolean lastSaveSuccessful = false;
    private final java.awt.Color errorColor = new java.awt.Color(204, 0, 0);

    /**
     * Creates new form FrmProduct
     */
    public FrmProduct() {
        initComponents();
        splQuiantity.setModel(new javax.swing.SpinnerNumberModel(0, null, null, 1));
        configureReadOnlyFields();
        setupDynamicBehaviors();
        generateAndSetNewProductId();
        loadSuppliersIntoCombo();
    }

    private void configureReadOnlyFields() {
        txtIdProduct.setEditable(false);
        jTextField13.setEditable(false);
    }

    private void setupDynamicBehaviors() {
        // estado inicial
        updateExpiryStateByType();
        updatePoundsStateByUnit();

        // listeners
        cmbTypeProduct.addActionListener(e -> updateExpiryStateByType());
        cmbUnit.addActionListener(e -> {
            updatePoundsStateByUnit();
            updateCalculations();
        });

        // recalculos automáticos
        splQuiantity.addChangeListener(e -> updateCalculations());

        txtCostProduct.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                updateCalculations();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                updateCalculations();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                updateCalculations();
            }
        });

        txtPounds.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                updateCalculations();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                updateCalculations();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                updateCalculations();
            }
        });

        updateCalculations();
    }

    private void updateExpiryStateByType() {
        String type = (String) cmbTypeProduct.getSelectedItem();
        boolean required = type != null && (type.equalsIgnoreCase("COMIDA") || type.equalsIgnoreCase("MEDICINA"));

        jDateChooser2.setEnabled(required);
        if (!required) {
            jDateChooser2.setDate(null);
        }
    }

    private void updatePoundsStateByUnit() {
        String unit = (String) cmbUnit.getSelectedItem();
        boolean isPounds = unit != null && unit.equalsIgnoreCase("Libras");

        txtPounds.setEnabled(isPounds);
        txtTotalPounds.setEnabled(isPounds);

        if (!isPounds) {
            txtPounds.setText("");
            txtTotalPounds.setText("");
        }
    }

    private void updateCalculations() {
        updateInvestment();
        updateTotalPounds();
    }

//    private void updateInvestment() {
//        try {
//            double cost = Double.parseDouble(txtCostProduct.getText().trim()); // permite negativos
//            double qty = ((Number) splQuiantity.getValue()).doubleValue();
//            jTextField13.setText(String.valueOf(cost * qty));
//        } catch (Exception ex) {
//            jTextField13.setText("");
//        }
//    }
    private void updateTotalPounds() {
        if (!txtPounds.isEnabled()) {
            txtTotalPounds.setText("");
            return;
        }
        try {
            double pounds = Double.parseDouble(txtPounds.getText().trim()); // permite negativos
            double qty = ((Number) splQuiantity.getValue()).doubleValue();
            txtTotalPounds.setText(String.valueOf(pounds * qty));
        } catch (Exception ex) {
            txtTotalPounds.setText("");
        }
    }

    private void generateAndSetNewProductId() {
        try {
            String nextId = productService.generateNextProductId(new Date());
            txtIdProduct.setText(nextId);
        } catch (Exception ex) {
            // fallback si Mongo está apagado
            String prefix = new java.text.SimpleDateFormat("ddMMyy").format(new Date());
            txtIdProduct.setText(prefix + "-001");

            JOptionPane.showMessageDialog(
                    this,
                    "MongoDB is not available.\nUsing fallback ID: " + txtIdProduct.getText(),
                    "MongoDB connection error",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    private void setupInvestmentAutoCalculation() {
        splQuiantity.addChangeListener(e -> updateInvestment());

        txtCostProduct.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                updateInvestment();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                updateInvestment();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                updateInvestment();
            }
        });

        updateInvestment();
    }

    private void updateInvestment() {
        String costText = txtCostProduct.getText();

        double qty = 0;
        Object q = splQuiantity.getValue();
        if (q instanceof Number) {
            qty = ((Number) q).doubleValue();
        } else {
            jTextField13.setText("");
            return;
        }

        try {
            double cost = Double.parseDouble(costText.trim());   // permite negativos
            jTextField13.setText(String.valueOf(cost * qty));
        } catch (Exception ex) {
            jTextField13.setText("");
        }
    }

    private void clearProductFields() {
        txtNameProduct.setText("");
//        txtSubtypeProduct.setText("");
        txtBrandProduct.setText("");
        txtCostProduct.setText("");
        jTextField13.setText("");

        cmbSupplier.setSelectedIndex(cmbSupplier.getItemCount() > 0 ? 0 : -1);
        cmbTypeProduct.setSelectedIndex(cmbTypeProduct.getItemCount() > 0 ? 0 : -1);
        cmbUnit.setSelectedIndex(cmbUnit.getItemCount() > 0 ? 0 : -1);

        splQuiantity.setValue(0);

        jDateChooser1.setDate(null);
        jDateChooser2.setDate(null);

        clearAllErrorStates();
        updateInvestment();

    }

    private void clearProductFieldsButKeepId() {
        txtNameProduct.setText("");
//        txtSubtypeProduct.setText("");
        txtBrandProduct.setText("");
        txtCostProduct.setText("");
        jTextField13.setText("");

        cmbSupplier.setSelectedIndex(cmbSupplier.getItemCount() > 0 ? 0 : -1);
        cmbTypeProduct.setSelectedIndex(cmbTypeProduct.getItemCount() > 0 ? 0 : -1);
        cmbUnit.setSelectedIndex(cmbUnit.getItemCount() > 0 ? 0 : -1);

        splQuiantity.setValue(0);

        jDateChooser1.setDate(null);
        jDateChooser2.setDate(null);

        clearAllErrorStates();
        updateInvestment();
    }

    private void clearAllErrorStates() {
        clearError(txtNameProduct);
//        clearError(txtSubtypeProduct);
        clearError(txtBrandProduct);
        clearError(txtCostProduct);
        clearError(jTextField13);

        clearErrorCombo(cmbSupplier);
        clearErrorCombo(cmbTypeProduct);
        clearErrorCombo(cmbUnit);

        clearErrorSpinner(splQuiantity);

        clearErrorDate(jDateChooser1);
        clearErrorDate(jDateChooser2);

        clearErrorCombo(cmbTypeAnimal);
        clearError(txtPounds);
        clearError(txtTotalPounds);
    }

    private void markError(javax.swing.JComponent c) {
        c.setBorder(javax.swing.BorderFactory.createLineBorder(errorColor, 2));
    }

    private void clearError(javax.swing.JComponent c) {
        c.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.GRAY, 1));
    }

    private void markErrorCombo(javax.swing.JComboBox<?> combo) {
        markError(combo);
    }

    private void clearErrorCombo(javax.swing.JComboBox<?> combo) {
        clearError(combo);
    }

    private void markErrorSpinner(javax.swing.JSpinner spinner) {
        java.awt.Component editor = spinner.getEditor();
        if (editor instanceof javax.swing.JComponent jc) {
            markError(jc);
        }
    }

    private void clearErrorSpinner(javax.swing.JSpinner spinner) {
        java.awt.Component editor = spinner.getEditor();
        if (editor instanceof javax.swing.JComponent jc) {
            clearError(jc);
        }
    }

    private void markErrorDate(com.toedter.calendar.JDateChooser chooser) {
        if (chooser.getDateEditor() != null
                && chooser.getDateEditor().getUiComponent() instanceof javax.swing.JComponent c) {
            markError(c);
        }
    }

    private void clearErrorDate(com.toedter.calendar.JDateChooser chooser) {
        if (chooser.getDateEditor() != null
                && chooser.getDateEditor().getUiComponent() instanceof javax.swing.JComponent c) {
            clearError(c);
        }
    }

    private void applyErrors(Map<String, String> errors) {
        clearAllErrorStates();

        if (errors.containsKey("cmbSupplier")) {
            markErrorCombo(cmbSupplier);
        }
        if (errors.containsKey("txtNameProduct")) {
            markError(txtNameProduct);
        }
        if (errors.containsKey("cmbTypeProduct")) {
            markErrorCombo(cmbTypeProduct);
        }
//        if (errors.containsKey("txtSubtypeProduct")) {
//            markError(txtSubtypeProduct);
//        }
        if (errors.containsKey("cmbTypeAnimal")) {
            markErrorCombo(cmbTypeAnimal);
        }
        if (errors.containsKey("txtPounds")) {
            markError(txtPounds);
        }
        if (errors.containsKey("txtTotalPounds")) {
            markError(txtTotalPounds);
        }
        if (errors.containsKey("txtBrandProduct")) {
            markError(txtBrandProduct);
        }
        if (errors.containsKey("txtCostProduct")) {
            markError(txtCostProduct);
        }
        if (errors.containsKey("cmbUnit")) {
            markErrorCombo(cmbUnit);
        }
        if (errors.containsKey("splQuiantity")) {
            markErrorSpinner(splQuiantity);
        }
        if (errors.containsKey("jTextField13")) {
            markError(jTextField13);
        }
        if (errors.containsKey("jDateChooser1")) {
            markErrorDate(jDateChooser1);
        }
        if (errors.containsKey("jDateChooser2")) {
            markErrorDate(jDateChooser2);
        }
    }

    private void loadSuppliersIntoCombo() {
        try {
            List<String> enterprises = supplierService.getEnterpriseOptions();
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();

            if (enterprises.isEmpty()) {
                model.addElement("No suppliers found");
            } else {
                for (String enterprise : enterprises) {
                    model.addElement(enterprise);
                }
            }

            cmbSupplier.setModel(model);
        } catch (Exception ex) {
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
            model.addElement("MongoDB unavailable");
            cmbSupplier.setModel(model);

            JOptionPane.showMessageDialog(
                    this,
                    "MongoDB is not available. Supplier list cannot be loaded.\n" + ex.getMessage(),
                    "MongoDB connection error",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        txtIdProduct = new javax.swing.JTextField();
        txtNameProduct = new javax.swing.JTextField();
        txtCostProduct = new javax.swing.JTextField();
        cmbSupplier = new javax.swing.JComboBox<>();
        cmbTypeProduct = new javax.swing.JComboBox<>();
        cmbUnit = new javax.swing.JComboBox<>();
        splQuiantity = new javax.swing.JSpinner();
        jTextField13 = new javax.swing.JTextField();
        txtBrandProduct = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtPounds = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtTotalPounds = new javax.swing.JTextField();
        cmbTypeAnimal = new javax.swing.JComboBox<>();
        jPanel1 = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        MnuFile = new javax.swing.JMenu();
        itmNewRegisterProduct = new javax.swing.JMenuItem();
        itmSaveRegisterProduct = new javax.swing.JMenuItem();
        MnuOptions = new javax.swing.JMenu();
        itmDuplicateRegisterProduct = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        MnuHelp = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(0, 0, 119));

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Agregar Producto Inventario");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(255, 255, 255)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel1)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        jLabel2.setText("ID Producto:");

        jLabel3.setText("Proveedor:");

        jLabel4.setText("Nombre del Producto:");

        jLabel5.setText("Categoria:");

        jLabel6.setText("SubCategoria:");

        jLabel7.setText("Marca:");

        jLabel8.setText("Costo Producto:");

        jLabel9.setText("Unidad:");

        jLabel10.setText("Libras producto:");

        jLabel11.setText("Costo Inversión:");

        jLabel12.setText("Fecha de Ingreso:");

        jLabel13.setText("Fecha de Caducidad:");

        txtIdProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdProductActionPerformed(evt);
            }
        });

        txtNameProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNameProductActionPerformed(evt);
            }
        });

        txtCostProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCostProductActionPerformed(evt);
            }
        });

        cmbSupplier.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cmbTypeProduct.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "COMIDA", "ACCESORIO", "JUGUETE", "MEDICINA", " " }));

        cmbUnit.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Libras", "Unidades" }));

        jTextField13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField13ActionPerformed(evt);
            }
        });

        txtBrandProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBrandProductActionPerformed(evt);
            }
        });

        jLabel14.setText("Cantidad :");

        txtPounds.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPoundsActionPerformed(evt);
            }
        });

        jLabel15.setText("Total libras ingresadas:");

        txtTotalPounds.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTotalPoundsActionPerformed(evt);
            }
        });

        cmbTypeAnimal.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "PERRO", "GATO", "HAMSTER", "CONEJO", "GALLINA", "VACA", "CABALLO", "CERDO" }));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(cmbTypeProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCostProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(244, 244, 244)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(cmbSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(325, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel15)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel11)
                                .addComponent(jLabel12)
                                .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.LEADING))
                            .addComponent(txtIdProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(39, 39, 39)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(jDateChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
                                                    .addComponent(jDateChooser2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addGap(19, 19, 19))
                                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel9)
                                                .addComponent(cmbUnit, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                                .addGap(15, 15, 15)
                                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel6)
                                                    .addComponent(cmbTypeAnimal, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addGap(33, 33, 33)
                                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel10)
                                                    .addComponent(txtPounds, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addGap(56, 56, 56)
                                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel7)
                                                    .addComponent(txtBrandProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel4)
                                        .addComponent(txtNameProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(splQuiantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel14))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTotalPounds, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtIdProduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNameProduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addGap(19, 19, 19)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbTypeProduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtBrandProduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbTypeAnimal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCostProduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbUnit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPounds, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(splQuiantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(24, 24, 24)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15)
                    .addComponent(txtTotalPounds, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel13))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(22, Short.MAX_VALUE))
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

        MnuFile.setText("Archivo");

        itmNewRegisterProduct.setText("Nuevo Registro");
        itmNewRegisterProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmNewRegisterProductActionPerformed(evt);
            }
        });
        MnuFile.add(itmNewRegisterProduct);

        itmSaveRegisterProduct.setText("Guardar Registro");
        itmSaveRegisterProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmSaveRegisterProductActionPerformed(evt);
            }
        });
        MnuFile.add(itmSaveRegisterProduct);

        jMenuBar1.add(MnuFile);

        MnuOptions.setText("Opciones");

        itmDuplicateRegisterProduct.setText("Duplicar Registro");
        itmDuplicateRegisterProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmDuplicateRegisterProductActionPerformed(evt);
            }
        });
        MnuOptions.add(itmDuplicateRegisterProduct);

        jMenuItem1.setText("Grafica Producto");
        MnuOptions.add(jMenuItem1);

        jMenuItem2.setText("Informe Producto");
        MnuOptions.add(jMenuItem2);

        jMenuBar1.add(MnuOptions);

        MnuHelp.setText("Ayuda");

        jMenuItem3.setText("Información");
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
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtIdProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdProductActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdProductActionPerformed

    private void txtNameProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNameProductActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNameProductActionPerformed

    private void txtCostProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCostProductActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCostProductActionPerformed

    private void jTextField13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField13ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField13ActionPerformed

    private void itmNewRegisterProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmNewRegisterProductActionPerformed

        if (!lastSaveSuccessful) {

            clearProductFieldsButKeepId();

            JOptionPane.showMessageDialog(
                    this,
                    "The current record was not saved.\nComplete and save before generating a new ID.",
                    "Information",
                    JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        clearProductFieldsButKeepId();
        generateAndSetNewProductId();
        lastSaveSuccessful = false;
    }//GEN-LAST:event_itmNewRegisterProductActionPerformed

    private void itmSaveRegisterProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmSaveRegisterProductActionPerformed
        String idProduct = txtIdProduct.getText();
        String supplier = (String) cmbSupplier.getSelectedItem();
        String name = txtNameProduct.getText();
        String type = (String) cmbTypeProduct.getSelectedItem();
        String animal = (String) cmbTypeAnimal.getSelectedItem();  // ✅ antes subtype textfield
        String brand = txtBrandProduct.getText();

        String costText = txtCostProduct.getText();
        String unit = (String) cmbUnit.getSelectedItem();
        Object qtyValue = splQuiantity.getValue();

        String investmentText = jTextField13.getText();
        String poundsText = txtPounds.getText();
        String totalPoundsText = txtTotalPounds.getText();

        Date entryDate = jDateChooser1.getDate();
        Date expiryDate = jDateChooser2.isEnabled() ? jDateChooser2.getDate() : null;

        Map<String, String> errors = productService.validateFields(
                idProduct, supplier, name, type, animal, brand,
                costText, unit, qtyValue, investmentText,
                poundsText, totalPoundsText,
                entryDate, expiryDate
        );

        applyErrors(errors);

        if (!errors.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "All fields must be completed.",
                    "Validation error",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        try {
            productService.saveProduct(
                    idProduct, supplier, name, type, animal, brand,
                    costText, unit, (Number) qtyValue, investmentText,
                    poundsText, totalPoundsText,
                    entryDate, expiryDate
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Product saved successfully.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            lastSaveSuccessful = true;           
            clearProductFields();
            generateAndSetNewProductId();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Unexpected error: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }

    }//GEN-LAST:event_itmSaveRegisterProductActionPerformed

    private void itmDuplicateRegisterProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmDuplicateRegisterProductActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_itmDuplicateRegisterProductActionPerformed

    private void txtBrandProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBrandProductActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBrandProductActionPerformed

    private void txtPoundsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPoundsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPoundsActionPerformed

    private void txtTotalPoundsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotalPoundsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalPoundsActionPerformed

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
            java.util.logging.Logger.getLogger(FrmProduct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmProduct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmProduct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmProduct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmProduct().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu MnuFile;
    private javax.swing.JMenu MnuHelp;
    private javax.swing.JMenu MnuOptions;
    private javax.swing.JComboBox<String> cmbSupplier;
    private javax.swing.JComboBox<String> cmbTypeAnimal;
    private javax.swing.JComboBox<String> cmbTypeProduct;
    private javax.swing.JComboBox<String> cmbUnit;
    private javax.swing.JMenuItem itmDuplicateRegisterProduct;
    private javax.swing.JMenuItem itmNewRegisterProduct;
    private javax.swing.JMenuItem itmSaveRegisterProduct;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
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
    private javax.swing.JTextField jTextField13;
    private javax.swing.JSpinner splQuiantity;
    private javax.swing.JTextField txtBrandProduct;
    private javax.swing.JTextField txtCostProduct;
    private javax.swing.JTextField txtIdProduct;
    private javax.swing.JTextField txtNameProduct;
    private javax.swing.JTextField txtPounds;
    private javax.swing.JTextField txtTotalPounds;
    // End of variables declaration//GEN-END:variables
}
