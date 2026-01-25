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
public class FrmEmployeeDogSection extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FrmEmployeeDogSection.class.getName());

    public FrmEmployeeDogSection() {
        initComponents();

        cargarImagenDog();

        
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

        
        actualizarTotalDog();
    }

    private void cargarImagenDog() {
        lblDog.setText("");

        ImageIcon original = new ImageIcon(getClass().getResource("/Graphics/Dog.jpg"));

        int ancho = 300;
        int alto = 200;

        Image imagenEscalada = original.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);

        lblDog.setIcon(new ImageIcon(imagenEscalada));
    }

    private void actualizarSubprecioComida() {
        String marca = ComboBoxMake.getSelectedItem().toString();
        String raza = ComboBoxRace.getSelectedItem().toString();
        String sabor = ComboBoxFlavor.getSelectedItem().toString();
        double peso = (double) ((Integer) spinnerFoodKg.getValue());

        double precioUnitario = switch (marca) {
            case "DogChown" ->
                switch (raza) {
                    case "Grande/Mediano" ->
                        switch (sabor) {
                            case "Carne", "Pollo" ->
                                2.50;
                            case "Premium" ->
                                3.75;
                            default ->
                                0;
                        };
                    case "Pequeño" ->
                        switch (sabor) {
                            case "Carne", "Pollo" ->
                                3.00;
                            case "Premium" ->
                                3.75;
                            default ->
                                0;
                        };
                    default ->
                        0;
                };
            case "Procan" ->
                switch (raza) {
                    case "Grande/Mediano" ->
                        switch (sabor) {
                            case "Carne", "Pollo" ->
                                2.30;
                            case "Premium" ->
                                3.40;
                            default ->
                                0;
                        };
                    case "Pequeño" ->
                        switch (sabor) {
                            case "Carne", "Pollo" ->
                                2.80;
                            case "Premium" ->
                                3.40;
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
        actualizarTotalDog();
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
                5;
            case "Peine" ->
                5;
            case "Cama" ->
                20;
            default ->
                0;
        };

        double subprecio = precioUnitario * unidades;
        lblSubPriceAccesory.setText(String.format("$ %.2f", subprecio));
        actualizarTotalDog();
    }

    private void actualizarSubprecioToy() {
        String toySeleccionado = listToy.getSelectedValue();

        if (toySeleccionado == null) {
            lblSubPriceToy.setText("$ 0.00");
            return;
        }

        double precioUnitario = switch (toySeleccionado) {
            case "Pelota" ->
                3.0;
            case "Cuerda" ->
                2.5;
            case "Huesito" ->
                4.0;
            case "Pollo chillón" ->
                5.0;
            default ->
                0;
        };

        int unidades = (Integer) spinnerToy.getValue();

        double subprecio = precioUnitario * unidades;
        lblSubPriceToy.setText(String.format("$ %.2f", subprecio));
        actualizarTotalDog();
    }

    private void actualizarTotalDog() {
        String foodText = lblSubPriceFood.getText().replace("$", "").replace(",", ".").trim();
        String accText = lblSubPriceAccesory.getText().replace("$", "").replace(",", ".").trim();
        String toyText = lblSubPriceToy.getText().replace("$", "").replace(",", ".").trim();

        double comida = foodText.isEmpty() ? 0 : Double.parseDouble(foodText);
        double accesorio = accText.isEmpty() ? 0 : Double.parseDouble(accText);
        double juguete = toyText.isEmpty() ? 0 : Double.parseDouble(toyText);

        double total = comida + accesorio + juguete;
        lblTotalDog.setText("$ " + String.format("%.2f", total));
    }

    private void guardarSeleccionDog() {

        
        String marca = ComboBoxMake.getSelectedItem().toString();
        String raza = ComboBoxRace.getSelectedItem().toString();
        String sabor = ComboBoxFlavor.getSelectedItem().toString();
        int kilos = (int) spinnerFoodKg.getValue();

        double foodPrice = Double.parseDouble(
                lblSubPriceFood.getText()
                        .replace("$", "")
                        .replace(",", ".")
                        .trim()
        );

        if (foodPrice > 0) {
            ActiveSale.getSummary().addProductToTable(
                    "Mascotas de casa",
                    "Perro",
                    "Comida",
                    marca + " - " + raza + " - " + sabor,
                    "",
                    kilos,
                    foodPrice
            );
        }

        
        if (listAccesory.getSelectedValue() != null) {
            int accQty = (int) spinnerAccesory.getValue();

            double accPrice = Double.parseDouble(
                    lblSubPriceAccesory.getText()
                            .replace("$", "")
                            .replace(",", ".")
                            .trim()
            );

            if (accPrice > 0) {
                ActiveSale.getSummary().addProductToTable(
                        "Mascotas de casa",
                        "Perro",
                        "Accesorio",
                        listAccesory.getSelectedValue(),
                        "",
                        accQty,
                        accPrice
                );
            }
        }

       
        if (listToy.getSelectedValue() != null) {
            int toyQty = (int) spinnerToy.getValue();

            double toyPrice = Double.parseDouble(
                    lblSubPriceToy.getText()
                            .replace("$", "")
                            .replace(",", ".")
                            .trim()
            );

            if (toyPrice > 0) {
                ActiveSale.getSummary().addProductToTable(
                        "Mascotas de casa",
                        "Perro",
                        "Comida",
                        marca, // BRAND
                        sabor, // FLAVOR
                        kilos,
                        foodPrice
                );

            }
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
        jLabel2 = new javax.swing.JLabel();
        lblDog = new javax.swing.JLabel();
        lblMake = new javax.swing.JLabel();
        spinnerFoodKg = new javax.swing.JSpinner();
        ComboBoxFlavor = new javax.swing.JComboBox<>();
        ComboBoxRace = new javax.swing.JComboBox<>();
        lblFlavor = new javax.swing.JLabel();
        lblRace = new javax.swing.JLabel();
        lblWeightkg = new javax.swing.JLabel();
        lblPriceFood = new javax.swing.JLabel();
        lblSubPriceFood = new javax.swing.JLabel();
        ComboBoxMake = new javax.swing.JComboBox<>();
        spinnerAccesory = new javax.swing.JSpinner();
        lblSubPriceAccesory = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        listAccesory = new javax.swing.JList<>();
        lblAccesoryUnity = new javax.swing.JLabel();
        lblPriceAccesory = new javax.swing.JLabel();
        lblAccesory = new javax.swing.JLabel();
        lblSubPriceToy = new javax.swing.JLabel();
        lbllistToy = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listToy = new javax.swing.JList<>();
        lblToyUnity = new javax.swing.JLabel();
        spinnerToy = new javax.swing.JSpinner();
        lblPriceToy = new javax.swing.JLabel();
        lblPriceSectionDog = new javax.swing.JLabel();
        lblTotalDog = new javax.swing.JLabel();
        txtDetailNote = new javax.swing.JTextField();
        lblNoteCat = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        btnBackMenu = new javax.swing.JButton();
        btnSaveReporte = new javax.swing.JButton();
        btnFinishSale = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        MenuToHome = new javax.swing.JMenu();
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

        jPanel1.setBackground(new java.awt.Color(0, 0, 119));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 3, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("MENÚ (Sección perro)");

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
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel2.setText("COMIDA");

        lblDog.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Graphics/Dog.jpg"))); // NOI18N
        lblDog.setText("<img.jugueteperro>");

        lblMake.setText("Marca");

        ComboBoxFlavor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pollo", "Carne", "Premium" }));
        ComboBoxFlavor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboBoxFlavorActionPerformed(evt);
            }
        });

        ComboBoxRace.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Grande/Mediano", "Pequeño" }));

        lblFlavor.setText("Sabor");

        lblRace.setText("Raza");

        lblWeightkg.setText("Peso por kg");

        lblPriceFood.setText("Precio");

        lblSubPriceFood.setText("$ 0.00");

        ComboBoxMake.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Procan", "DogChown" }));

        lblSubPriceAccesory.setText("$ 0.00");

        listAccesory.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Collar", "Placa", "Plato", "Peine", "Cama" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(listAccesory);

        lblAccesoryUnity.setText("Unidades");

        lblPriceAccesory.setText("Precio");

        lblAccesory.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblAccesory.setText("ACCESORIOS");

        lblSubPriceToy.setText("$ 0.00");

        lbllistToy.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lbllistToy.setText("JUGUETES");

        listToy.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Pelota", "Cuerda", "Huesito", "Pollo chillón" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(listToy);

        lblToyUnity.setText("Unidades");

        lblPriceToy.setText("Precio");

        lblPriceSectionDog.setText("Precio total en sección de gato");

        lblTotalDog.setText("$ 0.00");

        lblNoteCat.setText("NOTA:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(260, 260, 260)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(lblNoteCat)
                                                .addGap(3, 3, 3)
                                                .addComponent(txtDetailNote, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(lblSubPriceToy, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(ComboBoxMake, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(ComboBoxRace, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(38, 38, 38)
                                                .addComponent(ComboBoxFlavor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(49, 49, 49))
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGap(20, 20, 20)
                                                .addComponent(lblMake)
                                                .addGap(84, 84, 84)
                                                .addComponent(lblRace)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(lblFlavor)
                                                .addGap(74, 74, 74)))
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblWeightkg)
                                            .addComponent(spinnerFoodKg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(52, 52, 52)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblSubPriceFood, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblPriceFood))
                                        .addGap(645, 645, 645))))
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
                                            .addComponent(lblPriceAccesory)
                                            .addComponent(lblSubPriceAccesory, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(18, 18, 18)
                                .addComponent(lblDog, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addGap(645, 645, 645)))
                        .addContainerGap())
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(lblTotalDog, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblPriceSectionDog)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lbllistToy))
                                        .addGap(37, 37, 37)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(spinnerToy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblToyUnity))
                                        .addGap(58, 58, 58)
                                        .addComponent(lblPriceToy)))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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
                .addGap(36, 36, 36)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(lblAccesory)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblDog, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(lbllistToy)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(lblToyUnity)
                                            .addComponent(lblPriceToy))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(spinnerToy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblSubPriceToy))
                                        .addGap(26, 26, 26)))
                                .addGap(28, 28, 28)
                                .addComponent(lblPriceSectionDog)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblTotalDog, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtDetailNote, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblNoteCat)))
                        .addGap(14, 14, 14))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblAccesoryUnity, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblPriceAccesory))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblSubPriceAccesory)
                            .addComponent(spinnerAccesory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        jPanel3.setBackground(new java.awt.Color(0, 0, 119));

        btnBackMenu.setText("Regresar al menu ventas");
        btnBackMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackMenuActionPerformed(evt);
            }
        });

        btnSaveReporte.setText("Guardar en reporte");
        btnSaveReporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveReporteActionPerformed(evt);
            }
        });

        btnFinishSale.setText("Finalizar compra");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnBackMenu)
                .addGap(88, 88, 88)
                .addComponent(btnSaveReporte)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 130, Short.MAX_VALUE)
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

        MenuToHome.setText("De casa");

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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 635, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ComboBoxFlavorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboBoxFlavorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ComboBoxFlavorActionPerformed

    private void btnBackMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackMenuActionPerformed

        FrmEmployeeSale.getInstance().setVisible(true);
        this.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_btnBackMenuActionPerformed

    private void btnSaveReporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveReporteActionPerformed

        guardarSeleccionDog();

        
        ec.edu.espe.petshopinventorycontrol.controller.ActiveSale
                .getSummary()
                .setVisible(true);

        this.setVisible(false);

        javax.swing.JOptionPane.showMessageDialog(this,
                "Productos de perro agregados a la venta");
        
    }//GEN-LAST:event_btnSaveReporteActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new FrmEmployeeDogSection().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> ComboBoxFlavor;
    private javax.swing.JComboBox<String> ComboBoxMake;
    private javax.swing.JComboBox<String> ComboBoxRace;
    private javax.swing.JMenuItem MenuItemCat;
    private javax.swing.JMenuItem MenuItemChicken;
    private javax.swing.JMenuItem MenuItemConejillo;
    private javax.swing.JMenuItem MenuItemCow;
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
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblAccesory;
    private javax.swing.JLabel lblAccesoryUnity;
    private javax.swing.JLabel lblDog;
    private javax.swing.JLabel lblFlavor;
    private javax.swing.JLabel lblMake;
    private javax.swing.JLabel lblNoteCat;
    private javax.swing.JLabel lblPriceAccesory;
    private javax.swing.JLabel lblPriceFood;
    private javax.swing.JLabel lblPriceSectionDog;
    private javax.swing.JLabel lblPriceToy;
    private javax.swing.JLabel lblRace;
    private javax.swing.JLabel lblSubPriceAccesory;
    private javax.swing.JLabel lblSubPriceFood;
    private javax.swing.JLabel lblSubPriceToy;
    private javax.swing.JLabel lblTotalDog;
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
