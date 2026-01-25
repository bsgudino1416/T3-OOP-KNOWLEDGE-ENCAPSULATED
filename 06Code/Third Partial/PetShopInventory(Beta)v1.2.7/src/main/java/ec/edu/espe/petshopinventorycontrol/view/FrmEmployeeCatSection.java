package ec.edu.espe.petshopinventorycontrol.view;

import ec.edu.espe.petshopinventorycontrol.view.FrmEmployeeSale;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import ec.edu.espe.petshopinventorycontrol.controller.ActiveSale;

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
public class FrmEmployeeCatSection extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FrmEmployeeCatSection.class.getName());

    /**
     * Creates new form NewJFrame3
     */
    public FrmEmployeeCatSection() {
        initComponents();

        cargarImagenCat();

        
        SpinnerNumberModel foodModel = new SpinnerNumberModel(0, 0, 50, 1);
        spinnerFoodKg.setModel(foodModel);
        ((JSpinner.DefaultEditor) spinnerFoodKg.getEditor()).getTextField().setEditable(false);

   
        SpinnerNumberModel accModel = new SpinnerNumberModel(0, 0, 20, 1);
        spinnerAccesory.setModel(accModel);
        ((JSpinner.DefaultEditor) spinnerAccesory.getEditor()).getTextField().setEditable(false);

        
        SpinnerNumberModel toyModel = new SpinnerNumberModel(0, 0, 20, 1);
        spinnerToy.setModel(toyModel);
        ((JSpinner.DefaultEditor) spinnerToy.getEditor()).getTextField().setEditable(false);

        
        spinnerAccesory.setEnabled(false);
        spinnerToy.setEnabled(false);

        
        listAccesory.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                spinnerAccesory.setEnabled(listAccesory.getSelectedValue() != null);
                actualizarSubprecioAccesorio();
            }
        });

        listToy.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                spinnerToy.setEnabled(listToy.getSelectedValue() != null);
                actualizarSubprecioToy();
            }
        });

        
        ComboBoxMake.addActionListener(e -> actualizarSubprecioComida());
        ComboBoxRace.addActionListener(e -> actualizarSubprecioComida());
        ComboBoxFlavor.addActionListener(e -> actualizarSubprecioComida());

       
        spinnerFoodKg.addChangeListener(e -> actualizarSubprecioComida());

        
        actualizarSubprecioComida();

        
        listAccesory.addListSelectionListener(e -> actualizarSubprecioAccesorio());
        spinnerAccesory.addChangeListener(e -> actualizarSubprecioAccesorio());

        
        actualizarSubprecioAccesorio();

        
        listToy.addListSelectionListener(e -> actualizarSubprecioToy());
        spinnerToy.addChangeListener(e -> actualizarSubprecioToy());

        
        actualizarSubprecioToy();

        actualizarTotalCat();

    }

    private void guardarSeleccionCat() {

        
        int kg = (Integer) spinnerFoodKg.getValue();
        double subFood = Double.parseDouble(
                lblSubPriceFood.getText().replace("$", "").replace(",", ".").trim()
        );

        if (kg > 0 && subFood > 0) {
            ActiveSale.getSummary().addProductToTable(
                    "Mascotas de casa",
                    "Gato",
                    "Comida",
                    ComboBoxMake.getSelectedItem().toString(),
                    ComboBoxFlavor.getSelectedItem().toString(), // SOLO FLAVOR
                    kg,
                    subFood
            );
        }

        
        if (listAccesory.getSelectedValue() != null) {
            int units = (Integer) spinnerAccesory.getValue();
            double subAcc = Double.parseDouble(
                    lblSubPriceAccesory.getText().replace("$", "").replace(",", ".").trim()
            );

            if (units > 0 && subAcc > 0) {
                ActiveSale.getSummary().addProductToTable(
                        "Mascotas de casa",
                        "Gato",
                        "Accesorio",
                        listAccesory.getSelectedValue(),
                        "",
                        units,
                        subAcc
                );
            }
        }

        
        if (listToy.getSelectedValue() != null) {
            int units = (Integer) spinnerToy.getValue();
            double subToy = Double.parseDouble(
                    lblSubPriceToy.getText().replace("$", "").replace(",", ".").trim()
            );

            if (units > 0 && subToy > 0) {
                ActiveSale.getSummary().addProductToTable(
                        "Mascotas de casa",
                        "Gato",
                        "Juguete",
                        listToy.getSelectedValue(),
                        "",
                        units,
                        subToy
                );
            }
        }
    }

    private void cargarImagenCat() {
        lblCat.setText("");

        ImageIcon original = new ImageIcon(getClass().getResource("/Graphics/Cat.jpg"));

        int ancho = 150;
        int alto = 200;

        Image imagenEscalada = original.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);

        lblCat.setIcon(new ImageIcon(imagenEscalada));
    }

    private void actualizarSubprecioComida() {
        String marca = ComboBoxMake.getSelectedItem().toString();
        String raza = ComboBoxRace.getSelectedItem().toString();
        String sabor = ComboBoxFlavor.getSelectedItem().toString();
        double peso = (double) ((Integer) spinnerFoodKg.getValue());

        double precioUnitario = switch (marca) {
            case "Whiskas" ->
                switch (raza) {
                    case "Gatito" ->
                        switch (sabor) {
                            case "Sardina" ->
                                4.50;
                            case "Pollo" ->
                                4.00;
                            case "Carne" ->
                                4.00;
                            default ->
                                0;
                        };
                    case "Adulto" ->
                        switch (sabor) {
                            case "Sardina", "Pollo", "Carne" ->
                                3.50;
                            default ->
                                0;
                        };
                    case "Senior" ->
                        switch (sabor) {
                            case "Sardina", "Pollo", "Carne" ->
                                4.00;
                            default ->
                                0;
                        };
                    default ->
                        0;
                };
            case "ProCat" ->
                switch (raza) {
                    case "Gatito" ->
                        switch (sabor) {
                            case "Sardina" ->
                                3.60;
                            case "Pollo", "Carne" ->
                                3.30;
                            default ->
                                0;
                        };
                    case "Adulto" ->
                        switch (sabor) {
                            case "Sardina" ->
                                3.30;
                            case "Pollo", "Carne" ->
                                3.00;
                            default ->
                                0;
                        };
                    case "Senior" ->
                        switch (sabor) {
                            case "Sardina" ->
                                3.30;
                            case "Pollo", "Carne" ->
                                2.80;
                            default ->
                                0;
                        };
                    default ->
                        0;
                };
            case "Michu" ->
                switch (raza) {
                    case "Gatito", "Senior" ->
                        switch (sabor) {
                            case "Sardina", "Pollo", "Carne" ->
                                2.70;
                            default ->
                                0;
                        };
                    case "Adulto" ->
                        switch (sabor) {
                            case "Sardina", "Pollo", "Carne" ->
                                2.50;
                            default ->
                                0;
                        };
                    default ->
                        0;
                };
            default ->
                0;
        };

        double subprecio = precioUnitario * peso;
        lblSubPriceFood.setText(String.format("$ %.2f", subprecio));
        actualizarTotalCat();
    }

    private void actualizarSubprecioAccesorio() {
        String accesorioSeleccionado = listAccesory.getSelectedValue();

        if (accesorioSeleccionado == null) {
            lblSubPriceAccesory.setText("$ 0.00");
            return;
        }

        int unidades = (Integer) spinnerAccesory.getValue();
        double precioUnitario = switch (accesorioSeleccionado) {
            case "Collar" ->
                5;
            case "Placa" ->
                10;
            case "Plato" ->
                8;
            case "Peine" ->
                7;
            case "Arena" ->
                10;
            case "Arenero" ->
                15;
            default ->
                0;
        };

        double subprecio = precioUnitario * unidades;
        lblSubPriceAccesory.setText(String.format("$ %.2f", subprecio));
        actualizarTotalCat();
    }

    private void actualizarSubprecioToy() {
        // Obtener el juguete seleccionado
        String toySeleccionado = listToy.getSelectedValue();
        if (toySeleccionado == null) {
            lblSubPriceToy.setText("$ 0.00");
            return;
        }

        
        double precioUnitario = switch (toySeleccionado) {
            case "Pelota" ->
                3.0;
            case "Hueso" ->
                2.5;
            case "Bola de estambre" ->
                4.0;
            default ->
                0;
        };

        
        int unidades = (Integer) spinnerToy.getValue();

        
        double subprecio = precioUnitario * unidades;

        
        lblSubPriceToy.setText(String.format("$ %.2f", subprecio));
        actualizarTotalCat();
    }

    private void actualizarTotalCat() {
        
        String foodText = lblSubPriceFood.getText().replace("$", "").replace(",", ".").trim();
        String accText = lblSubPriceAccesory.getText().replace("$", "").replace(",", ".").trim();
        String toyText = lblSubPriceToy.getText().replace("$", "").replace(",", ".").trim();

        
        double comida = foodText.isEmpty() ? 0 : Double.parseDouble(foodText);
        double accesorio = accText.isEmpty() ? 0 : Double.parseDouble(accText);
        double juguete = toyText.isEmpty() ? 0 : Double.parseDouble(toyText);

        
        double total = comida + accesorio + juguete;

        
        lblTotalCat.setText("$ " + String.format("%.2f", total));
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
        spinnerAccesory = new javax.swing.JSpinner();
        lblSubPriceAccesory = new javax.swing.JLabel();
        lblAccesoryUnity = new javax.swing.JLabel();
        lblNoteCat = new javax.swing.JLabel();
        lblFood = new javax.swing.JLabel();
        lblPriceAccesory = new javax.swing.JLabel();
        lbllistToy = new javax.swing.JLabel();
        lblPriceSectionCat = new javax.swing.JLabel();
        lblTotalCat = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listToy = new javax.swing.JList<>();
        ComboBoxMake = new javax.swing.JComboBox<>();
        lblToyUnity = new javax.swing.JLabel();
        spinnerToy = new javax.swing.JSpinner();
        lblPriceToy = new javax.swing.JLabel();
        lblAccesory = new javax.swing.JLabel();
        lblSubPriceToy = new javax.swing.JLabel();
        lblMake = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        listAccesory = new javax.swing.JList<>();
        spinnerFoodKg = new javax.swing.JSpinner();
        ComboBoxFlavor = new javax.swing.JComboBox<>();
        ComboBoxRace = new javax.swing.JComboBox<>();
        lblFlavor = new javax.swing.JLabel();
        lblRace = new javax.swing.JLabel();
        lblWeightkg = new javax.swing.JLabel();
        lblPriceFood = new javax.swing.JLabel();
        lblSubPriceFood = new javax.swing.JLabel();
        txtDetailNote = new javax.swing.JTextField();
        lblCat = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        btnSaveReporte = new javax.swing.JButton();
        btnFinishSale = new javax.swing.JButton();
        btnBackMenu = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        MenuToHome = new javax.swing.JMenu();
        MenuItemDog = new javax.swing.JMenuItem();
        MenuItemConejillo = new javax.swing.JMenuItem();
        MenuToFarm = new javax.swing.JMenu();
        MenuItemCow = new javax.swing.JMenuItem();
        MenuItemHorse = new javax.swing.JMenuItem();
        MenuItemPig = new javax.swing.JMenuItem();
        MenuItemChicken = new javax.swing.JMenuItem();
        MenuToSale = new javax.swing.JMenu();
        MenuItemTable = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setPreferredSize(new java.awt.Dimension(670, 540));

        lblSubPriceAccesory.setText("$ 0.00");

        lblAccesoryUnity.setText("Unidades");

        lblNoteCat.setText("NOTA:");

        lblFood.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblFood.setText("COMIDA");

        lblPriceAccesory.setText("Precio");

        lbllistToy.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lbllistToy.setText("JUGUETES");

        lblPriceSectionCat.setText("Precio total en sección de gato");

        lblTotalCat.setText("$ 0.00");

        listToy.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Pelota", "Hueso", "Bola de estambre" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(listToy);

        ComboBoxMake.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ProCat", "Michu", "Whiskas" }));

        lblToyUnity.setText("Unidades");

        lblPriceToy.setText("Precio");

        lblAccesory.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblAccesory.setText("ACCESORIOS");

        lblSubPriceToy.setText("$ 0.00");

        lblMake.setText("Marca");

        listAccesory.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Collar", "Placa", "Plato", "Peine", "Arena", "Arenero" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(listAccesory);

        ComboBoxFlavor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sardina", "Pollo", "Carne" }));
        ComboBoxFlavor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboBoxFlavorActionPerformed(evt);
            }
        });

        ComboBoxRace.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Gatito", "Adulto", "Senior" }));

        lblFlavor.setText("Sabor");

        lblRace.setText("Raza");

        lblWeightkg.setText("Peso por kg");

        lblPriceFood.setText("Precio");

        lblSubPriceFood.setText("$ 0.00");

        lblCat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Graphics/Cat.jpg"))); // NOI18N
        lblCat.setText("<img.jugueteperro>");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(lblMake)
                                .addGap(84, 84, 84)
                                .addComponent(lblRace)
                                .addGap(101, 101, 101)
                                .addComponent(lblFlavor))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(ComboBoxMake, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(37, 37, 37)
                                .addComponent(ComboBoxRace, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(46, 46, 46)
                                .addComponent(ComboBoxFlavor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(57, 57, 57)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblWeightkg)
                            .addComponent(spinnerFoodKg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(57, 57, 57)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblSubPriceFood, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblPriceFood)))
                    .addComponent(lblFood)
                    .addComponent(lbllistToy)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblAccesory)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(37, 37, 37)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblAccesoryUnity)
                                    .addComponent(spinnerAccesory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(70, 70, 70)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblSubPriceAccesory, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblPriceAccesory)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(spinnerToy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblToyUnity))
                                .addGap(58, 58, 58)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblSubPriceToy, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblPriceToy))))
                        .addGap(18, 18, 18)
                        .addComponent(lblCat, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblPriceSectionCat)
                            .addComponent(lblTotalCat, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(78, 78, 78)
                        .addComponent(lblNoteCat)
                        .addGap(18, 18, 18)
                        .addComponent(txtDetailNote, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(36, 36, 36))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblFood)
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMake)
                    .addComponent(lblRace)
                    .addComponent(lblFlavor)
                    .addComponent(lblWeightkg)
                    .addComponent(lblPriceFood))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ComboBoxMake, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ComboBoxRace, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ComboBoxFlavor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spinnerFoodKg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSubPriceFood))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(76, 76, 76)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblAccesoryUnity, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblPriceAccesory))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblSubPriceAccesory)
                            .addComponent(spinnerAccesory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(lblAccesory)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblCat, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbllistToy)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblToyUnity)
                            .addComponent(lblPriceToy))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(spinnerToy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblSubPriceToy))
                        .addGap(54, 54, 54)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblPriceSectionCat)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTotalCat, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtDetailNote, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNoteCat))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(0, 0, 119));

        btnSaveReporte.setText("Guardar en reporte");
        btnSaveReporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveReporteActionPerformed(evt);
            }
        });

        btnFinishSale.setText("Finalizar compra");
        btnFinishSale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFinishSaleActionPerformed(evt);
            }
        });

        btnBackMenu.setText("Regresar al menu ventas");
        btnBackMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackMenuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnBackMenu)
                .addGap(88, 88, 88)
                .addComponent(btnSaveReporte)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnFinishSale)
                .addGap(56, 56, 56))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBackMenu)
                    .addComponent(btnSaveReporte)
                    .addComponent(btnFinishSale))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setBackground(new java.awt.Color(0, 0, 119));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 3, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("MENÚ  (Sección gato)");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(237, 237, 237)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        MenuToHome.setText("De casa");

        MenuItemDog.setText("Perro");
        MenuItemDog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItemDogActionPerformed(evt);
            }
        });
        MenuToHome.add(MenuItemDog);

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
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 683, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 537, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void ComboBoxFlavorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboBoxFlavorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ComboBoxFlavorActionPerformed

    private void btnSaveReporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveReporteActionPerformed

        guardarSeleccionCat();

        
        ec.edu.espe.petshopinventorycontrol.controller.ActiveSale
                .getSummary()
                .setVisible(true);

        this.setVisible(false);

        javax.swing.JOptionPane.showMessageDialog(this,
                "Productos del gato agregados a la venta");

        // TODO add your handling code here:
    }//GEN-LAST:event_btnSaveReporteActionPerformed

    private void btnBackMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackMenuActionPerformed

        FrmEmployeeSale.getInstance().setVisible(true);
        this.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_btnBackMenuActionPerformed

    private void btnFinishSaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFinishSaleActionPerformed

        guardarSeleccionCat();
        ActiveSale.getSummary().setVisible(true);
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnFinishSaleActionPerformed

    private void MenuItemDogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItemDogActionPerformed

        FrmEmployeeDogSection frm = new FrmEmployeeDogSection();
        frm.setVisible(true);
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_MenuItemDogActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new FrmEmployeeCatSection().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> ComboBoxFlavor;
    private javax.swing.JComboBox<String> ComboBoxMake;
    private javax.swing.JComboBox<String> ComboBoxRace;
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
    private javax.swing.JButton btnBackMenu;
    private javax.swing.JButton btnFinishSale;
    private javax.swing.JButton btnSaveReporte;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblAccesory;
    private javax.swing.JLabel lblAccesoryUnity;
    private javax.swing.JLabel lblCat;
    private javax.swing.JLabel lblFlavor;
    private javax.swing.JLabel lblFood;
    private javax.swing.JLabel lblMake;
    private javax.swing.JLabel lblNoteCat;
    private javax.swing.JLabel lblPriceAccesory;
    private javax.swing.JLabel lblPriceFood;
    private javax.swing.JLabel lblPriceSectionCat;
    private javax.swing.JLabel lblPriceToy;
    private javax.swing.JLabel lblRace;
    private javax.swing.JLabel lblSubPriceAccesory;
    private javax.swing.JLabel lblSubPriceFood;
    private javax.swing.JLabel lblSubPriceToy;
    private javax.swing.JLabel lblTotalCat;
    private javax.swing.JLabel lblToyUnity;
    private javax.swing.JLabel lblWeightkg;
    private javax.swing.JLabel lbllistToy;
    private javax.swing.JList<String> listAccesory;
    private javax.swing.JList<String> listToy;
    private javax.swing.JSpinner spinnerAccesory;
    private javax.swing.JSpinner spinnerFoodKg;
    private javax.swing.JSpinner spinnerToy;
    private javax.swing.JTextField txtDetailNote;
    // End of variables declaration//GEN-END:variables
}
