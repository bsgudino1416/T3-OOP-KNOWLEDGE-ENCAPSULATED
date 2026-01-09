package ec.edu.espe.petshopinventorycontrol.view;

import ec.edu.espe.petshopinventorycontrol.controller.ActiveSale;
import ec.edu.espe.petshopinventorycontrol.view.FrmEmployeeSale;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import ec.edu.espe.petshopinventorycontrol.view.FrmEmployeeCatSection;
import ec.edu.espe.petshopinventorycontrol.view.FrmEmployeeDogSection;
import ec.edu.espe.petshopinventorycontrol.view.FrmEmployeeConejilloSection;
import ec.edu.espe.petshopinventorycontrol.view.FrmEmployeeCowSection;
import ec.edu.espe.petshopinventorycontrol.view.FrmEmployeeHorseSection;
import ec.edu.espe.petshopinventorycontrol.view.FrmEmployeePigSection;
import ec.edu.espe.petshopinventorycontrol.view.FrmEmployeeChickenSection;
import ec.edu.espe.petshopinventorycontrol.view.FrmEmployeeSummary;

/**
 *
 * @author Bryan Gudino, KNOWLEDGE ENCAPSULATE, @ESPE
 */
public class FrmEmployeeCowSection extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FrmEmployeeCowSection.class.getName());

    /**
     * Creates new form NewJFrame7
     */
    public FrmEmployeeCowSection() {
        initComponents();

        cargarImagenCow();

        // ==== SPINNERS DESACTIVADOS AL INICIO ====
        spinnerFoodLb.setEnabled(false);
        spinnerFoodLb.setValue(0);

        spinnerAccesory.setEnabled(false);
        spinnerAccesory.setValue(0);

// ==== SPINNERS CONFIGURADOS (SIN NEGATIVOS) ====
        SpinnerNumberModel foodModel = new SpinnerNumberModel(0, 0, 50, 1);
        spinnerFoodLb.setModel(foodModel);
        ((JSpinner.DefaultEditor) spinnerFoodLb.getEditor()).getTextField().setEditable(false);

        SpinnerNumberModel accModel = new SpinnerNumberModel(0, 0, 20, 1);
        spinnerAccesory.setModel(accModel);
        ((JSpinner.DefaultEditor) spinnerAccesory.getEditor()).getTextField().setEditable(false);

        // ==== LISTENERS COMIDA ====
        ComboBoxMake.addActionListener(e -> actualizarSubprecioComidaVaca());
        spinnerFoodLb.addChangeListener(e -> actualizarSubprecioComidaVaca());

// ==== LISTENERS ACCESORIOS ====
        listAccesory.addListSelectionListener(e -> actualizarSubprecioAccesorioVaca());
        spinnerAccesory.addChangeListener(e -> actualizarSubprecioAccesorioVaca());

// ==== INICIALIZAR ====
        lblSubPriceFood.setText("$ 0.00");
        lblSubPriceAccesory.setText("$ 0.00");
        lblTotalCow.setText("$ 0.00");
    }

    private void cargarImagenCow() {
        lblCow.setText("");

        ImageIcon original = new ImageIcon(getClass().getResource("/Graphics/Cow.jpg"));

        int ancho = 150;
        int alto = 200;

        Image imagenEscalada = original.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);

        lblCow.setIcon(new ImageIcon(imagenEscalada));
    }

    private void actualizarSubprecioComidaVaca() {

        String seleccionado = ComboBoxMake.getSelectedItem().toString();

        if (seleccionado == null) {
            spinnerFoodLb.setEnabled(false);
            spinnerFoodLb.setValue(0);
            lblSubPriceFood.setText("$ 0.00");
            actualizarTotalVaca();
            return;
        }

        spinnerFoodLb.setEnabled(true);

        int lb = (int) spinnerFoodLb.getValue();

        double precioLb = switch (seleccionado) {
            case "Morochillo entero" ->
                6.25;
            case "Morochillo partido" ->
                0.30;
            case "Morochillo vitaminizado" ->
                0.32;
            case "Moyuelo" ->
                0.30;
            case "Sal en grano" ->
                0.30;
            default ->
                0;
        };

        double subprecio = precioLb * lb;
        lblSubPriceFood.setText(String.format("$ %.2f", subprecio));

        actualizarTotalVaca();
    }

    private void actualizarSubprecioAccesorioVaca() {

        String seleccionado = listAccesory.getSelectedValue();

        if (seleccionado == null) {
            spinnerAccesory.setEnabled(false);
            spinnerAccesory.setValue(0);
            lblSubPriceAccesory.setText("$ 0.00");
            actualizarTotalVaca();
            return;
        }

        spinnerAccesory.setEnabled(true);

        int unidades = (int) spinnerAccesory.getValue();

        double precioUnit = switch (seleccionado) {
            case "Bebedero 2LT" ->
                2.50;
            case "Bebedero 4LT" ->
                4.00;
            default ->
                0;
        };

        double subprecio = precioUnit * unidades;
        lblSubPriceAccesory.setText(String.format("$ %.2f", subprecio));

        actualizarTotalVaca();
    }

    private void actualizarTotalVaca() {

        double food = Double.parseDouble(
                lblSubPriceFood.getText()
                        .replace("$", "")
                        .replace(",", ".")
                        .trim()
        );

        double acc = Double.parseDouble(
                lblSubPriceAccesory.getText()
                        .replace("$", "")
                        .replace(",", ".")
                        .trim()
        );

        double total = food + acc;

        lblTotalCow.setText("$ " + String.format("%.2f", total));
    }

    private void guardarSeleccionCow() {

        // ===== COMIDA =====
        String balanceado = ComboBoxMake.getSelectedItem() != null
                ? ComboBoxMake.getSelectedItem().toString()
                : "";

        int foodLb = (int) spinnerFoodLb.getValue();

        double foodPrice = Double.parseDouble(
                lblSubPriceFood.getText()
                        .replace("$", "")
                        .replace(",", ".")
                        .trim()
        );

        // ===== ACCESORIO =====
        String accessory = listAccesory.getSelectedValue() != null
                ? listAccesory.getSelectedValue()
                : "";

        int accQty = (int) spinnerAccesory.getValue();

        double accPrice = Double.parseDouble(
                lblSubPriceAccesory.getText()
                        .replace("$", "")
                        .replace(",", ".")
                        .trim()
        );

        // ===== AGREGAR A VENTA ACTIVA =====
        if (foodPrice > 0) {
            ActiveSale.getSummary().addProductToTable(
                    "Ganado",
                    "Vaca",
                    "Comida",
                    balanceado,
                    "",
                    foodLb,
                    foodPrice
            );
        }

        if (accPrice > 0) {
            ActiveSale.getSummary().addProductToTable(
                    "Ganado",
                    "Vaca",
                    "Accesorio",
                    accessory,
                    "",
                    accQty,
                    accPrice
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnSaveReporte = new javax.swing.JButton();
        btnFinishSale = new javax.swing.JButton();
        btnBackMenu = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        lblSubPriceAccesory = new javax.swing.JLabel();
        lblPriceFood = new javax.swing.JLabel();
        lblNoteCat = new javax.swing.JLabel();
        lblSubPriceFood = new javax.swing.JLabel();
        lblPriceSectionCat = new javax.swing.JLabel();
        ComboBoxMake = new javax.swing.JComboBox<>();
        lblTotalCow = new javax.swing.JLabel();
        lblMake = new javax.swing.JLabel();
        txtDetailNote = new javax.swing.JTextField();
        spinnerFoodLb = new javax.swing.JSpinner();
        lblAccesoryUnity = new javax.swing.JLabel();
        lblPriceAccesory = new javax.swing.JLabel();
        lblAccesory = new javax.swing.JLabel();
        lblFood = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        listAccesory = new javax.swing.JList<>();
        spinnerAccesory = new javax.swing.JSpinner();
        lblWeightLb = new javax.swing.JLabel();
        lblCow = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        MenuToHome = new javax.swing.JMenu();
        MenuItemDog = new javax.swing.JMenuItem();
        MenuItemCat = new javax.swing.JMenuItem();
        MenuItemConejillo = new javax.swing.JMenuItem();
        MenuToFarm = new javax.swing.JMenu();
        MenuItemHorse = new javax.swing.JMenuItem();
        MenuItemPig = new javax.swing.JMenuItem();
        MenuItemChicken = new javax.swing.JMenuItem();
        MenuToSale = new javax.swing.JMenu();
        MenuItemTable = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 0, 119));
        jPanel1.setPreferredSize(new java.awt.Dimension(615, 60));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 3, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("MENÚ  (Sección vaca)");

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
                .addGap(22, 22, 22)
                .addComponent(jLabel1)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(0, 0, 119));
        jPanel2.setPreferredSize(new java.awt.Dimension(615, 60));

        btnSaveReporte.setText("Guardar en reporte");
        btnSaveReporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveReporteActionPerformed(evt);
            }
        });

        btnFinishSale.setText("Finalizar compra");

        btnBackMenu.setText("Regresar al menu ventas");
        btnBackMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackMenuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnBackMenu)
                .addGap(88, 88, 88)
                .addComponent(btnSaveReporte)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnFinishSale)
                .addGap(56, 56, 56))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(10, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBackMenu)
                    .addComponent(btnSaveReporte)
                    .addComponent(btnFinishSale)))
        );

        jPanel3.setPreferredSize(new java.awt.Dimension(615, 430));

        lblSubPriceAccesory.setText("$ 0.00");

        lblPriceFood.setText("Precio");

        lblNoteCat.setText("NOTA:");

        lblSubPriceFood.setText("$ 0.00");

        lblPriceSectionCat.setText("Precio total en sección de vaca");

        ComboBoxMake.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Morochillo entero", "Morochillo partido", "Morochillo vitaminizado", "Moyuelo", "Sal en grano" }));

        lblTotalCow.setText("$ 0.00");

        lblMake.setText("Balanceado");

        lblAccesoryUnity.setText("Unidades");

        lblPriceAccesory.setText("Precio");

        lblAccesory.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblAccesory.setText("ACCESORIOS");

        lblFood.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblFood.setText("COMIDA");

        listAccesory.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Bebedero 2LT", "Bebedero 4LT" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(listAccesory);

        lblWeightLb.setText("Peso por lb");

        lblCow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Graphics/Cow.jpg"))); // NOI18N
        lblCow.setText("<img.jugueteperro>");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(lblTotalCow, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblNoteCat)
                                .addGap(3, 3, 3))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(lblPriceSectionCat)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addComponent(txtDetailNote, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(106, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblAccesoryUnity)
                                            .addComponent(spinnerAccesory, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(58, 58, 58))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                        .addComponent(lblMake)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lblWeightLb)
                                        .addGap(46, 46, 46)))
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblSubPriceAccesory, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblPriceAccesory)
                                    .addComponent(lblPriceFood)))
                            .addComponent(lblAccesory)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblFood)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(ComboBoxMake, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(spinnerFoodLb, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                                .addComponent(lblSubPriceFood, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(74, 74, 74)
                        .addComponent(lblCow, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(lblFood)
                        .addGap(10, 10, 10)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblMake)
                                    .addComponent(lblWeightLb)
                                    .addComponent(lblPriceFood))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(ComboBoxMake, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(spinnerFoodLb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addComponent(lblSubPriceFood)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                        .addComponent(lblAccesory)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblCow, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblAccesoryUnity, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblPriceAccesory))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblSubPriceAccesory)
                            .addComponent(spinnerAccesory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtDetailNote, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblNoteCat))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(lblPriceSectionCat)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTotalCow, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 656, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 656, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 656, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveReporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveReporteActionPerformed

        guardarSeleccionCow();

        // ABRIR RESUMEN DE VENTA (VENTA ACTIVA)
        ec.edu.espe.petshopinventorycontrol.controller.ActiveSale
                .getSummary()
                .setVisible(true);

        this.setVisible(false);

        javax.swing.JOptionPane.showMessageDialog(this,
                "Productos de vaca agregados a la venta");
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSaveReporteActionPerformed

    private void btnBackMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackMenuActionPerformed

        FrmEmployeeSale.getInstance().setVisible(true);
        this.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_btnBackMenuActionPerformed

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

    private void MenuItemHorseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItemHorseActionPerformed

        FrmEmployeeHorseSection frm = new FrmEmployeeHorseSection();
        frm.setVisible(true);
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_MenuItemHorseActionPerformed

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
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new FrmEmployeeCowSection().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> ComboBoxMake;
    private javax.swing.JMenuItem MenuItemCat;
    private javax.swing.JMenuItem MenuItemChicken;
    private javax.swing.JMenuItem MenuItemConejillo;
    private javax.swing.JMenuItem MenuItemDog;
    private javax.swing.JMenuItem MenuItemHorse;
    private javax.swing.JMenuItem MenuItemPig;
    private javax.swing.JMenuItem MenuItemTable;
    private javax.swing.JMenu MenuToFarm;
    private javax.swing.JMenu MenuToHome;
    private javax.swing.JMenu MenuToSale;
    private javax.swing.JButton btnBackMenu;
    private javax.swing.JButton btnFinishSale;
    private javax.swing.JButton btnSaveReporte;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblAccesory;
    private javax.swing.JLabel lblAccesoryUnity;
    private javax.swing.JLabel lblCow;
    private javax.swing.JLabel lblFood;
    private javax.swing.JLabel lblMake;
    private javax.swing.JLabel lblNoteCat;
    private javax.swing.JLabel lblPriceAccesory;
    private javax.swing.JLabel lblPriceFood;
    private javax.swing.JLabel lblPriceSectionCat;
    private javax.swing.JLabel lblSubPriceAccesory;
    private javax.swing.JLabel lblSubPriceFood;
    private javax.swing.JLabel lblTotalCow;
    private javax.swing.JLabel lblWeightLb;
    private javax.swing.JList<String> listAccesory;
    private javax.swing.JSpinner spinnerAccesory;
    private javax.swing.JSpinner spinnerFoodLb;
    private javax.swing.JTextField txtDetailNote;
    // End of variables declaration//GEN-END:variables
}
