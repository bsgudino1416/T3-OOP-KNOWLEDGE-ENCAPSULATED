package ec.edu.espe.petshopinventorycontrol.view;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import ec.edu.espe.petshopinventorycontrol.controller.DataManager;
import javax.swing.ImageIcon;
import java.awt.Image;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.bson.Document;

/**
 *
 * @author Bryan Gudino, KNOWLEDGE ENCAPSULATE, @ESPE
 */
public class FrmEmployeeSale extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FrmEmployeeSale.class.getName());

    /**
     * Creates new form NewJFrame1
     */
    private String loggedUser;

    public FrmEmployeeSale(String loggedUser) {
        initComponents();
        this.loggedUser = loggedUser;
        txtEmployeeEmployeeSale.setText(loggedUser);
        agregarValidaciones();
    }

    public FrmEmployeeSale() {

    }

    private void agregarValidaciones() {

        jComboBox1.addActionListener(e -> validarTipoIdentificacion());

        txtIdentificationEmployeeSale.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                validarLongitudIdentificacion(evt);
            }
        });

        txtPhoneEmployeeSale.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                validarTelefono(evt);
            }
        });

        txtQuantityEmployeeSale.addActionListener(e -> calcularTotal());

        txtIdEmployeeSale.addActionListener(e -> cargarProductoPorId());
        txtDescriptionEmployeeSale.addActionListener(e -> cargarProductoPorDescripcion());
    }

    private int maxDigitos = 0;

    private void validarTipoIdentificacion() {
        String tipo = jComboBox1.getSelectedItem().toString();

        switch (tipo) {
            case "Cédula" ->
                maxDigitos = 10;
            case "Pasaporte" ->
                maxDigitos = 20;
            case "RUC" ->
                maxDigitos = 13;
        }

        txtIdentificationEmployeeSale.setText("");
    }

    private void validarLongitudIdentificacion(java.awt.event.KeyEvent evt) {
        if (!Character.isDigit(evt.getKeyChar())) {
            evt.consume();
            return;
        }

        if (txtIdentificationEmployeeSale.getText().length() >= maxDigitos) {
            evt.consume();
        }
    }

    private void validarTelefono(java.awt.event.KeyEvent evt) {
        if (!Character.isDigit(evt.getKeyChar())) {
            evt.consume();
            return;
        }

        if (txtPhoneEmployeeSale.getText().length() >= 10) {
            evt.consume();
        }
    }

    private Document buscarProducto(Document filtro) {
        MongoDatabase db = DataManager.getDB();
        MongoCollection<Document> collection = db.getCollection("productos");
        return collection.find(filtro).first();
    }

    private void cargarProductoPorId() {
        String id = txtIdEmployeeSale.getText().trim();
        if (id.isEmpty()) {
            return;
        }

        Document prod = buscarProducto(new Document("id", id));

        if (prod == null) {
            JOptionPane.showMessageDialog(this, "Producto no encontrado.");
            return;
        }

        txtDescriptionEmployeeSale.setText(prod.getString("description"));
        txtPriceEmployeeSale.setText(String.valueOf(prod.getDouble("price")));
    }

    private void cargarProductoPorDescripcion() {
        String desc = txtDescriptionEmployeeSale.getText().trim();
        if (desc.isEmpty()) {
            return;
        }

        Document prod = buscarProducto(new Document("description", desc));

        if (prod == null) {
            JOptionPane.showMessageDialog(this, "Producto no encontrado.");
            return;
        }

        txtIdEmployeeSale.setText(prod.getString("id"));
        txtPriceEmployeeSale.setText(String.valueOf(prod.getDouble("price")));
    }

    private void calcularTotal() {
        try {
            double precio = Double.parseDouble(txtPriceEmployeeSale.getText());
            int cantidad = Integer.parseInt(txtQuantityEmployeeSale.getText());

            double total = precio * cantidad;
            txtTotalEmployeeSale.setText(String.valueOf(total));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Cantidad inválida.");
        }
    }

    private void agregarYGuardar() {

        DefaultTableModel model = (DefaultTableModel) tblAddinthecarEmployeeSale.getModel();

        String id = txtIdEmployeeSale.getText();
        String cantidad = txtQuantityEmployeeSale.getText();
        String producto = txtDescriptionEmployeeSale.getText();
        String precio = txtPriceEmployeeSale.getText();
        String total = txtTotalEmployeeSale.getText();

        // ← Debe leerse MARCA desde MongoDB
        Document prod = buscarProducto(new Document("id", id));
        String marca = prod != null ? prod.getString("brand") : "Desconocida";

        // Añadir a la tabla
        model.addRow(new Object[]{id, cantidad, producto, marca, precio, total});

        calcularSubtotal();

        // GUARDAR DIRECTO EN LA COLLECTION FACTURA
        guardarFacturaIndividual(id, cantidad, producto, marca, precio, total);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */

    }

    private void guardarFacturaIndividual(String id, String cantidad, String producto, String marca, String precio, String total) {

        MongoDatabase db = DataManager.getDB();
        MongoCollection<Document> facturas = db.getCollection("factura"); // nombre EXACTO que pediste

        Document detalle = new Document()
                .append("cliente", txtNameandLastNameEmployeeSale.getText())
                .append("documento", txtIdentificationEmployeeSale.getText())
                .append("telefono", txtPhoneEmployeeSale.getText())
                .append("direccion", txtAdressEmployeeSale.getText())
                .append("ciudad", txtCityEmployeeSale.getText())
                .append("atendido_por", loggedUser)
                .append("producto_id", id)
                .append("cantidad", cantidad)
                .append("descripcion", producto)
                .append("marca", marca)
                .append("precio_unitario", precio)
                .append("total", total)
                .append("observacion", TxtaObservationEmployeeSale.getText())
                .append("subtotal", txtSubtotalEmployeeSale.getText())
                .append("iva", txtIVAEmployeeSale.getText())
                .append("total_pagar", txtTotalofSaleEmployeeSale.getText());

        facturas.insertOne(detalle);
    }

    private void limpiarTodo() {

        txtNameandLastNameEmployeeSale.setText("");
        txtIdentificationEmployeeSale.setText("");
        txtPhoneEmployeeSale.setText("");
        txtAdressEmployeeSale.setText("");
        txtCityEmployeeSale.setText("");
        txtIdEmployeeSale.setText("");
        txtDescriptionEmployeeSale.setText("");
        txtPriceEmployeeSale.setText("");
        txtQuantityEmployeeSale.setText("");
        txtTotalEmployeeSale.setText("");
        txtSubtotalEmployeeSale.setText("");
        txtIVAEmployeeSale.setText("");
        txtTotalofSaleEmployeeSale.setText("");
        TxtaObservationEmployeeSale.setText("");

        DefaultTableModel model = (DefaultTableModel) tblAddinthecarEmployeeSale.getModel();
        model.setRowCount(0);
    }

    private void calcularSubtotal() {
        DefaultTableModel model = (DefaultTableModel) tblAddinthecarEmployeeSale.getModel();
        double subtotal = 0;

        for (int i = 0; i < model.getRowCount(); i++) {
            subtotal += Double.parseDouble(model.getValueAt(i, 5).toString());
        }

        txtSubtotalEmployeeSale.setText(String.valueOf(subtotal));

        double iva = subtotal * 0.15;
        txtIVAEmployeeSale.setText(String.valueOf(iva));

        txtTotalofSaleEmployeeSale.setText(String.valueOf(subtotal + iva));
    }

    private void agregarProductoALaTabla() {
        DefaultTableModel model = (DefaultTableModel) tblAddinthecarEmployeeSale.getModel();

        String id = txtIdEmployeeSale.getText();
        String cantidad = txtQuantityEmployeeSale.getText();
        String producto = txtDescriptionEmployeeSale.getText();
        String marca = ""; // Marca viene del MongoDB → species
        String precio = txtPriceEmployeeSale.getText();
        String total = txtTotalEmployeeSale.getText();

        model.addRow(new Object[]{id, cantidad, producto, marca, precio, total});

        calcularSubtotal();
    }

    private void salir() {
        // ← AQUÍ PONES A QUÉ FORM DEBE IR
        // Ejemplo:
        // FrmManagerMenu menu = new FrmManagerMenu(loggedUser);
        // menu.setVisible(true);
        // menu.setLocationRelativeTo(null);

        this.dispose();
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
        bttnCleanEmployeeSale = new javax.swing.JButton();
        bttnPrintEmployeeSale = new javax.swing.JButton();
        bttnAddintheCarEmployeeSale = new javax.swing.JButton();
        bttnExitEmployeeSale = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtNameandLastNameEmployeeSale = new javax.swing.JTextField();
        txtPhoneEmployeeSale = new javax.swing.JTextField();
        txtAdressEmployeeSale = new javax.swing.JTextField();
        txtCityEmployeeSale = new javax.swing.JTextField();
        txtEmployeeEmployeeSale = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel19 = new javax.swing.JLabel();
        txtIdentificationEmployeeSale = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblAddinthecarEmployeeSale = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        TxtaObservationEmployeeSale = new javax.swing.JTextArea();
        jLabel8 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txtIdEmployeeSale = new javax.swing.JTextField();
        txtPriceEmployeeSale = new javax.swing.JTextField();
        txtDescriptionEmployeeSale = new javax.swing.JTextField();
        txtQuantityEmployeeSale = new javax.swing.JTextField();
        txtTotalEmployeeSale = new javax.swing.JTextField();
        txtSubtotalEmployeeSale = new javax.swing.JTextField();
        txtIVAEmployeeSale = new javax.swing.JTextField();
        txtTotalofSaleEmployeeSale = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 0, 119));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 3, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("VENTAS");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(367, 367, 367)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(0, 0, 119));

        bttnCleanEmployeeSale.setText("Limpiar Todo");
        bttnCleanEmployeeSale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bttnCleanEmployeeSaleActionPerformed(evt);
            }
        });

        bttnPrintEmployeeSale.setText("Imprimir");

        bttnAddintheCarEmployeeSale.setText("Añadir");
        bttnAddintheCarEmployeeSale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bttnAddintheCarEmployeeSaleActionPerformed(evt);
            }
        });

        bttnExitEmployeeSale.setText("Salir");
        bttnExitEmployeeSale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bttnExitEmployeeSaleActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(215, 215, 215)
                .addComponent(bttnCleanEmployeeSale)
                .addGap(30, 30, 30)
                .addComponent(bttnPrintEmployeeSale)
                .addGap(27, 27, 27)
                .addComponent(bttnAddintheCarEmployeeSale)
                .addGap(28, 28, 28)
                .addComponent(bttnExitEmployeeSale)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bttnCleanEmployeeSale)
                    .addComponent(bttnPrintEmployeeSale)
                    .addComponent(bttnAddintheCarEmployeeSale)
                    .addComponent(bttnExitEmployeeSale))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));

        jLabel3.setText("Datos del cliente");

        jLabel4.setText("Nombres y Apellidos:");

        jLabel5.setText("tipo de identaficación:");

        jLabel6.setText("Telefono:");

        jLabel7.setText("Dirección:");

        jLabel9.setText("Ciudad:");

        jLabel10.setText("atendido por:");

        txtAdressEmployeeSale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAdressEmployeeSaleActionPerformed(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Cédula", "Pasaporte", "RUC", " " }));

        jLabel19.setText("Ingrese los datos:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jLabel3))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel11)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel5)
                                        .addComponent(jLabel4))
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel10))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtAdressEmployeeSale, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)
                                        .addComponent(txtNameandLastNameEmployeeSale)
                                        .addComponent(txtPhoneEmployeeSale)
                                        .addComponent(txtCityEmployeeSale)
                                        .addComponent(txtEmployeeEmployeeSale))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(40, 40, 40)
                                        .addComponent(jLabel19)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtIdentificationEmployeeSale, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addContainerGap(260, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtNameandLastNameEmployeeSale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19)
                    .addComponent(txtIdentificationEmployeeSale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(txtPhoneEmployeeSale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(txtAdressEmployeeSale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(txtCityEmployeeSale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(txtEmployeeEmployeeSale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(204, 204, 204));
        jPanel4.setForeground(new java.awt.Color(204, 204, 204));

        jLabel12.setText("Codigo:");

        jLabel13.setText("Precio:");

        jLabel14.setText("Descripción:");

        jLabel15.setText("Cantidad:");

        jLabel16.setText("Total:");

        jScrollPane1.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N

        tblAddinthecarEmployeeSale.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Id", "Cantidad", "Producto", "Marca de Producto", "Precio Unitario", "Total"
            }
        ));
        jScrollPane1.setViewportView(tblAddinthecarEmployeeSale);

        jLabel2.setText("Observaciones:");

        TxtaObservationEmployeeSale.setColumns(20);
        TxtaObservationEmployeeSale.setRows(5);
        jScrollPane2.setViewportView(TxtaObservationEmployeeSale);

        jLabel8.setText("Subtotal:");

        jLabel17.setText("IVA 15%:");

        jLabel18.setText("Total a pagar:");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(jLabel13)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtPriceEmployeeSale, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(jLabel12)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtIdEmployeeSale, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(96, 96, 96)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(jLabel15)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtQuantityEmployeeSale, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(jLabel14)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtDescriptionEmployeeSale, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel16)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtTotalEmployeeSale, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 801, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(16, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 428, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addGap(18, 18, 18)
                                .addComponent(txtIVAEmployeeSale))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel18)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTotalofSaleEmployeeSale))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addGap(18, 18, 18)
                                .addComponent(txtSubtotalEmployeeSale, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(72, 72, 72))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jLabel14)
                    .addComponent(jLabel16)
                    .addComponent(txtIdEmployeeSale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDescriptionEmployeeSale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTotalEmployeeSale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel15)
                    .addComponent(txtPriceEmployeeSale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtQuantityEmployeeSale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGap(38, 38, 38))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txtSubtotalEmployeeSale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(txtIVAEmployeeSale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(txtTotalofSaleEmployeeSale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(31, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 11, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bttnCleanEmployeeSaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bttnCleanEmployeeSaleActionPerformed
        // TODO add your handling code here
        limpiarTodo();
    }//GEN-LAST:event_bttnCleanEmployeeSaleActionPerformed

    private void bttnExitEmployeeSaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bttnExitEmployeeSaleActionPerformed
        // TODO add your handling code here:
        salir();

    }//GEN-LAST:event_bttnExitEmployeeSaleActionPerformed

    private void txtAdressEmployeeSaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAdressEmployeeSaleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAdressEmployeeSaleActionPerformed

    private void bttnAddintheCarEmployeeSaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bttnAddintheCarEmployeeSaleActionPerformed
        // TODO add your handling code here:
        agregarYGuardar();
    }//GEN-LAST:event_bttnAddintheCarEmployeeSaleActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea TxtaObservationEmployeeSale;
    private javax.swing.JButton bttnAddintheCarEmployeeSale;
    private javax.swing.JButton bttnCleanEmployeeSale;
    private javax.swing.JButton bttnExitEmployeeSale;
    private javax.swing.JButton bttnPrintEmployeeSale;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblAddinthecarEmployeeSale;
    private javax.swing.JTextField txtAdressEmployeeSale;
    private javax.swing.JTextField txtCityEmployeeSale;
    private javax.swing.JTextField txtDescriptionEmployeeSale;
    private javax.swing.JTextField txtEmployeeEmployeeSale;
    private javax.swing.JTextField txtIVAEmployeeSale;
    private javax.swing.JTextField txtIdEmployeeSale;
    private javax.swing.JTextField txtIdentificationEmployeeSale;
    private javax.swing.JTextField txtNameandLastNameEmployeeSale;
    private javax.swing.JTextField txtPhoneEmployeeSale;
    private javax.swing.JTextField txtPriceEmployeeSale;
    private javax.swing.JTextField txtQuantityEmployeeSale;
    private javax.swing.JTextField txtSubtotalEmployeeSale;
    private javax.swing.JTextField txtTotalEmployeeSale;
    private javax.swing.JTextField txtTotalofSaleEmployeeSale;
    // End of variables declaration//GEN-END:variables
}
