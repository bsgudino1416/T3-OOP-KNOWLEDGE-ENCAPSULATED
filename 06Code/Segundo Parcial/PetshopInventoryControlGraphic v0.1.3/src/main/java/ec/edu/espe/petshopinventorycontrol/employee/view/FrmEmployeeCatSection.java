package ec.edu.espe.petshopinventorycontrol.employee.view;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

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

        // SPINNER DE COMIDA (0 a 50, solo números, SIN escribir texto)
    SpinnerNumberModel foodModel = new SpinnerNumberModel(0, 0, 50, 1);
    spinnerFoodKg.setModel(foodModel);
    ((JSpinner.DefaultEditor) spinnerFoodKg.getEditor()).getTextField().setEditable(false);


    // SPINNER DE ACCESORIOS (0 a 20, solo números)
    SpinnerNumberModel accModel = new SpinnerNumberModel(0, 0, 20, 1);
    spinnerAccesory.setModel(accModel);
    ((JSpinner.DefaultEditor) spinnerAccesory.getEditor()).getTextField().setEditable(false);


    // SPINNER DE JUGUETES (0 a 20, solo números)
    SpinnerNumberModel toyModel = new SpinnerNumberModel(0, 0, 20, 1);
    spinnerToy.setModel(toyModel);
    ((JSpinner.DefaultEditor) spinnerToy.getEditor()).getTextField().setEditable(false);

    //
    spinnerAccesory.setEnabled(false);
    spinnerToy.setEnabled(false);

    //
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
        
    // Actualizar subprecio cuando cambie marca, raza o sabor
    ComboBoxMake.addActionListener(e -> actualizarSubprecioComida());
    ComboBoxRace.addActionListener(e -> actualizarSubprecioComida());
    ComboBoxFlavor.addActionListener(e -> actualizarSubprecioComida());

    // Actualizar subprecio cuando cambie el spinner de peso
    spinnerFoodKg.addChangeListener(e -> actualizarSubprecioComida());

    // Inicializar el subprecio al abrir la ventana
    actualizarSubprecioComida();
    
    // Actualizar subprecio de accesorios cuando cambie selección o unidades
    listAccesory.addListSelectionListener(e -> actualizarSubprecioAccesorio());
    spinnerAccesory.addChangeListener(e -> actualizarSubprecioAccesorio());

    // Inicializar subprecio al abrir la ventana
    actualizarSubprecioAccesorio();

    // Actualizar subprecio de juguetes cuando cambie selección o unidades
    listToy.addListSelectionListener(e -> actualizarSubprecioToy());
    spinnerToy.addChangeListener(e -> actualizarSubprecioToy());

    // Inicializar subprecio al abrir la ventana
    actualizarSubprecioToy();
    
    actualizarTotalCat();
    
    }
    
    private void actualizarSubprecioComida() {
    String marca = ComboBoxMake.getSelectedItem().toString();
    String raza = ComboBoxRace.getSelectedItem().toString();
    String sabor = ComboBoxFlavor.getSelectedItem().toString();
    double peso = (double) ((Integer) spinnerFoodKg.getValue());

    double precioUnitario = switch (marca) {
        case "Whiskas" -> switch (raza) {
            case "Gatito" -> switch (sabor) {
                case "Sardina" -> 4.50;
                case "Pollo" -> 4.00;
                case "Carne" -> 4.00;
                default -> 0;
            };
            case "Adulto" -> switch (sabor) {
                case "Sardina", "Pollo", "Carne" -> 3.50;
                default -> 0;
            };
            case "Senior" -> switch (sabor) {
                case "Sardina", "Pollo", "Carne" -> 4.00;
                default -> 0;
            };
            default -> 0;
        };
        case "ProCat" -> switch (raza) {
            case "Gatito" -> switch (sabor) {
                case "Sardina" -> 3.60;
                case "Pollo", "Carne" -> 3.30;
                default -> 0;
            };
            case "Adulto" -> switch (sabor) {
                case "Sardina" -> 3.30;
                case "Pollo", "Carne" -> 3.00;
                default -> 0;
            };
            case "Senior" -> switch (sabor) {
                case "Sardina" -> 3.30;
                case "Pollo", "Carne" -> 2.80;
                default -> 0;
            };
            default -> 0;
        };
        case "Michu" -> switch (raza) {
            case "Gatito", "Senior" -> switch (sabor) {
                case "Sardina", "Pollo", "Carne" -> 2.70;
                default -> 0;
            };
            case "Adulto" -> switch (sabor) {
                case "Sardina", "Pollo", "Carne" -> 2.50;
                default -> 0;
            };
            default -> 0;
        };
        default -> 0;
    };

    double subprecio = precioUnitario * peso;
    lblSubPriceFood.setText(String.format("$ %.2f", subprecio));
    actualizarTotalCat();
}

    private void actualizarSubprecioAccesorio() {
    String accesorioSeleccionado = listAccesory.getSelectedValue();

    // Si no hay nada seleccionado, no hacemos nada
    if (accesorioSeleccionado == null) {
        lblSubPriceAccesory.setText("$ 0.00");
        return;
    }

    int unidades = (Integer) spinnerAccesory.getValue();
    double precioUnitario = switch (accesorioSeleccionado) {
    case "Collar" -> 5;
    case "Placa" -> 10;
    case "Plato" -> 8;
    case "Peine" -> 7;
    case "Arena" -> 10;
    case "Arenero" -> 15;
    default -> 0;
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

    // Determinar el precio unitario según el juguete
    double precioUnitario = switch (toySeleccionado) {
        case "Pelota" -> 3.0;
        case "Hueso" -> 2.5;
        case "Bola de estambre" -> 4.0;
        default -> 0;
    };

    // Obtener la cantidad del spinner
    int unidades = (Integer) spinnerToy.getValue();

    // Calcular subprecio
    double subprecio = precioUnitario * unidades;

    // Actualizar etiqueta
    lblSubPriceToy.setText(String.format("$ %.2f", subprecio));
    actualizarTotalCat();
    }

private void actualizarTotalCat() {
    // Tomar subtotales como Strings y quitar "$"
    String foodText = lblSubPriceFood.getText().replace("$", "").replace(",", ".").trim();
    String accText = lblSubPriceAccesory.getText().replace("$", "").replace(",", ".").trim();
    String toyText = lblSubPriceToy.getText().replace("$", "").replace(",", ".").trim();

    // Convertir a double
    double comida = foodText.isEmpty() ? 0 : Double.parseDouble(foodText);
    double accesorio = accText.isEmpty() ? 0 : Double.parseDouble(accText);
    double juguete = toyText.isEmpty() ? 0 : Double.parseDouble(toyText);

    // Sumar
    double total = comida + accesorio + juguete;

    // Mostrar total
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
        jLabel19 = new javax.swing.JLabel();
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
        jPanel3 = new javax.swing.JPanel();
        btnSaveReporte = new javax.swing.JButton();
        btnFinishSale = new javax.swing.JButton();
        btnBackMenu = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

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

        jLabel19.setText("<img.juguetegato>");

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
                            .addComponent(lbllistToy)
                            .addComponent(lblAccesory)
                            .addComponent(lblFood)
                            .addComponent(lblTotalCat, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(37, 37, 37)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(spinnerToy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblToyUnity)))
                                    .addComponent(lblPriceSectionCat))
                                .addGap(58, 58, 58)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(lblNoteCat)
                                        .addGap(3, 3, 3)
                                        .addComponent(txtDetailNote, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(lblSubPriceToy, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblPriceToy))))
                        .addContainerGap(59, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(37, 37, 37)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblAccesoryUnity)
                                    .addComponent(spinnerAccesory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(70, 70, 70)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblSubPriceAccesory, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblPriceAccesory)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel19)
                                .addGap(143, 143, 143))))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
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
                        .addGap(37, 37, 37)
                        .addComponent(lblAccesory)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(lbllistToy))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(76, 76, 76)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblAccesoryUnity, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblPriceAccesory))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblSubPriceAccesory)
                            .addComponent(spinnerAccesory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(2, 2, 2)
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(52, 52, 52)
                        .addComponent(lblPriceSectionCat)
                        .addGap(24, 24, 24)
                        .addComponent(lblTotalCat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblToyUnity)
                            .addComponent(lblPriceToy))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(spinnerToy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblSubPriceToy))
                        .addGap(24, 24, 24)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtDetailNote, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblNoteCat))
                        .addGap(63, 63, 63))))
        );

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

        jPanel1.setBackground(java.awt.SystemColor.textHighlight);

        jLabel1.setFont(new java.awt.Font("Times New Roman", 3, 18)); // NOI18N
        jLabel1.setText("MENÚ EMPLEADO (Sección gato)");

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
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 711, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 562, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    

    private void ComboBoxFlavorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboBoxFlavorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ComboBoxFlavorActionPerformed

    private void btnSaveReporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveReporteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSaveReporteActionPerformed

    private void btnBackMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackMenuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnBackMenuActionPerformed

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
    private javax.swing.JButton btnBackMenu;
    private javax.swing.JButton btnFinishSale;
    private javax.swing.JButton btnSaveReporte;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblAccesory;
    private javax.swing.JLabel lblAccesoryUnity;
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
