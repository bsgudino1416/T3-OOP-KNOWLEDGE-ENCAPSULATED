package ec.edu.espe.petshopinventorycontrol.view;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import ec.edu.espe.petshopinventorycontrol.controller.DataManager;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.bson.Document;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

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

        deshabilitarCamposProducto();
        agregarValidaciones();
    }

    public FrmEmployeeSale() {
        initComponents();
        agregarValidaciones();
    }

    private Document guardarCliente() {

        MongoDatabase db = DataManager.getDB();
        MongoCollection<Document> clientes = db.getCollection("cliente");

        Document cliente = new Document()
                .append("nombre", txtNameandLastNameEmployeeSale.getText())
                .append("tipo_identificacion", jComboBox1.getSelectedItem().toString())
                .append("identificacion", txtIdentificationEmployeeSale.getText())
                .append("telefono", txtPhoneEmployeeSale.getText())
                .append("direccion", txtAdressEmployeeSale.getText())
                .append("ciudad", txtCityEmployeeSale.getText())
                .append("registrado_por", txtEmployeeEmployeeSale.getText())
                .append("fecha_registro", new java.util.Date());

        clientes.insertOne(cliente);

        return cliente;
    }

    private void guardarFacturaCompleta() {

        MongoDatabase db = DataManager.getDB();
        MongoCollection<Document> facturas = db.getCollection("factura");

        // 1️⃣ Guardar cliente
        Document cliente = guardarCliente();

        // 2️⃣ Productos desde la tabla
        DefaultTableModel model
                = (DefaultTableModel) tblAddEmployeeSale.getModel();

        java.util.List<Document> productos = new java.util.ArrayList<>();

        for (int i = 0; i < model.getRowCount(); i++) {

            Document item = new Document()
                    .append("producto_id", model.getValueAt(i, 0))
                    .append("cantidad", model.getValueAt(i, 1))
                    .append("descripcion", model.getValueAt(i, 2))
                    .append("marca", model.getValueAt(i, 3))
                    .append("precio_unitario", model.getValueAt(i, 4))
                    .append("total", model.getValueAt(i, 5));

            productos.add(item);
        }

        // 3️⃣ Documento factura
        Document factura = new Document()
                .append("cliente", cliente)
                .append("productos", productos)
                .append("subtotal", Double.parseDouble(txtSubtotalEmployeeSale.getText()))
                .append("iva", Double.parseDouble(txtIVAEmployeeSale.getText()))
                .append("total_pagar", Double.parseDouble(txtTotalofSaleEmployeeSale.getText()))
                .append("observacion", TxtaObservationEmployeeSale.getText())
                .append("atendido_por", loggedUser)
                .append("fecha", new java.util.Date());

        facturas.insertOne(factura);

        JOptionPane.showMessageDialog(this,
                "Factura guardada correctamente",
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    private void agregarValidaciones() {

//        jComboBox1.addActionListener(e -> validarTipoIdentificacion());
//
//        txtIdentificationEmployeeSale.addKeyListener(new java.awt.event.KeyAdapter() {
//            public void keyTyped(java.awt.event.KeyEvent evt) {
//                validarLongitudIdentificacion(evt);
//            }
//        });
//
//        txtPhoneEmployeeSale.addKeyListener(new java.awt.event.KeyAdapter() {
//            public void keyTyped(java.awt.event.KeyEvent evt) {
//                validarTelefono(evt);
//            }
//        });
//
//        txtQuantityEmployeeSale.addActionListener(e -> calcularTotal());
//
//        txtIdEmployeeSale.addActionListener(e -> cargarProductoPorId());
//        txtproductemployesale.addActionListener(e -> cargarProductoPorDescripcion());
// Guardar cliente con ENTER en atendido por
        txtEmployeeEmployeeSale.addActionListener(e -> guardarClientSolo());

        // Guardar cliente con botón
        btnSaveClient.addActionListener(e -> guardarClientSolo());

        // Buscar producto por ID con ENTER
        txtIdEmployeeSale.addActionListener(e -> cargarProductoPorId());

        // Botón: total producto + agregar a tabla + subtotal + iva
        btnTotalProductEmployeeSale.addActionListener(e -> totalYAgregarFila());

        // Botón: total neto + guardar bill
        btnTotalNetoEmployeeSale.addActionListener(e -> totalNetoYGuardarBill());

        // Botón imprimir PDF
        bttnPrintEmployeeSale.addActionListener(e -> exportarPDF());

        // Botón "Añadir" (como pediste: igual que guardar bill)
        bttnAddintheCarEmployeeSale.addActionListener(e -> totalNetoYGuardarBill());

        // Enter en cantidad: limpiar panel2 y panel4 (como pediste)
        txtQuantityEmployeeSale.addActionListener(e -> limpiarPanel2y4());

        // Validaciones existentes:
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

    }

    private int maxDigitos = 0;

    private void deshabilitarCamposProducto() {
        txtPriceEmployeeSale.setEnabled(false);
        txtproduct.setEnabled(false);
        txtbrandproduct.setEnabled(false);
        txtTotalEmployeeSale.setEnabled(false);
    }

    private void habilitarCamposProducto() {
        txtPriceEmployeeSale.setEnabled(true);
        txtproduct.setEnabled(true);
        txtbrandproduct.setEnabled(true);
        txtTotalEmployeeSale.setEnabled(true);
    }

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

    private void bttnPrintEmployeeSaleActionPerformed(
            java.awt.event.ActionEvent evt) {

        if (tblAddEmployeeSale.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this,
                    "No hay productos en la factura");
            return;
        }

        guardarFacturaCompleta();
        limpiarPanel2y4();
    }

    private Document buildClientDoc() {
        return new Document()
                .append("name_lastname", txtNameandLastNameEmployeeSale.getText().trim())
                .append("id_type", jComboBox1.getSelectedItem().toString())
                .append("identification", txtIdentificationEmployeeSale.getText().trim())
                .append("phone", txtPhoneEmployeeSale.getText().trim())
                .append("address", txtAdressEmployeeSale.getText().trim())
                .append("city", txtCityEmployeeSale.getText().trim())
                .append("attended_by", txtEmployeeEmployeeSale.getText().trim())
                .append("created_at", new Date());
    }

    private boolean validarPanel2() {
        if (txtNameandLastNameEmployeeSale.getText().trim().isEmpty()
                || txtIdentificationEmployeeSale.getText().trim().isEmpty()
                || txtPhoneEmployeeSale.getText().trim().isEmpty()
                || txtAdressEmployeeSale.getText().trim().isEmpty()
                || txtCityEmployeeSale.getText().trim().isEmpty()
                || txtEmployeeEmployeeSale.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete todos los datos del cliente (Panel 2).");
            return false;
        }
        return true;
    }

    private void guardarClientSolo() {
        if (!validarPanel2()) {
            return;
        }

        Document client = buildClientDoc();
        DataManager.saveDocument("client", client);

        JOptionPane.showMessageDialog(this, "Cliente guardado en MongoDB (client).");
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

        MongoDatabase db = DataManager.getDB();
        MongoCollection<Document> coll = db.getCollection("productos");

        Document prod = coll.find(new Document("id", id)).first();

        if (prod == null) {
            JOptionPane.showMessageDialog(this, "Producto no encontrado");
            return;
        }

        txtproduct.setText(prod.getString("description"));
        txtPriceEmployeeSale.setText(String.valueOf(prod.getDouble("price")));
        txtbrandproduct.setText(prod.getString("brand")); // ✅ AQUÍ
        habilitarCamposProducto();
    }

    private void cargarProductoPorDescripcion() {
        String desc = txtproduct.getText().trim();
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

    private void calcularSubtotal() {

        DefaultTableModel model
                = (DefaultTableModel) tblAddEmployeeSale.getModel();

        double subtotal = 0;

        for (int i = 0; i < model.getRowCount(); i++) {
            subtotal += Double.parseDouble(model.getValueAt(i, 5).toString());
        }

        txtSubtotalEmployeeSale.setText(String.valueOf(subtotal));
        txtIVAEmployeeSale.setText(String.valueOf(subtotal * 0.15));
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

        DefaultTableModel model
                = (DefaultTableModel) tblAddEmployeeSale.getModel();

        String id = txtIdEmployeeSale.getText();
        int cantidad = Integer.parseInt(txtQuantityEmployeeSale.getText());
        String producto = txtproduct.getText();
        double precio = Double.parseDouble(txtPriceEmployeeSale.getText());
        double total = Double.parseDouble(txtTotalEmployeeSale.getText());

        // MARCA desde MongoDB
//        Document prod = buscarProducto(new Document("id", id));
//        String marca = prod != null ? prod.getString("brand") : "N/D";
//
//        model.addRow(new Object[]{
//            id,
//            cantidad,
//            producto,
//            marca,
//            precio,
//            total
//        });
        calcularSubtotal();
    }

    private void totalYAgregarFila() {
        try {
            if (txtIdEmployeeSale.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese un ID de producto.");
                return;
            }

            double precio = Double.parseDouble(txtPriceEmployeeSale.getText().trim());
            int cantidad = Integer.parseInt(txtQuantityEmployeeSale.getText().trim());
            if (cantidad <= 0) {
                JOptionPane.showMessageDialog(this, "Cantidad debe ser mayor a 0.");
                return;
            }

            double total = precio * cantidad;
            txtTotalEmployeeSale.setText(String.valueOf(total));

            DefaultTableModel model = (DefaultTableModel) tblAddEmployeeSale.getModel();

            model.addRow(new Object[]{
                txtIdEmployeeSale.getText().trim(),
                cantidad,
                txtproduct.getText().trim(),
                txtbrandproduct.getText().trim(),
                precio,
                total
            });

           recalcularSubtotalEIva();

          
            limpiarCamposProductoSolo();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Datos inválidos en producto/cantidad/precio.");
        }
    }

    private void recalcularSubtotalEIva() {
        DefaultTableModel model = (DefaultTableModel) tblAddEmployeeSale.getModel();
    double subtotal = 0;

    for (int i = 0; i < model.getRowCount(); i++) {
        subtotal += Double.parseDouble(model.getValueAt(i, 5).toString());
    }

    txtSubtotalEmployeeSale.setText(String.valueOf(subtotal));

    double iva = subtotal * 0.15;
    txtIVAEmployeeSale.setText(String.valueOf(iva));

    txtTotalofSaleEmployeeSale.setText(String.valueOf(subtotal + iva));
    }

    private void limpiarCamposProductoSolo() {
        txtIdEmployeeSale.setText("");
        txtproduct.setText("");
        txtbrandproduct.setText("");
        txtPriceEmployeeSale.setText("");
        txtQuantityEmployeeSale.setText("");
        txtTotalEmployeeSale.setText("");

        deshabilitarCamposProducto();
        txtIdEmployeeSale.requestFocus();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        java.awt.EventQueue.invokeLater(() -> {
            FrmEmployeeSale sale = new FrmEmployeeSale();
            sale.setLocationRelativeTo(null);
            sale.setVisible(true);
        });
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

    private Document buildBillDoc() {

        DefaultTableModel model = (DefaultTableModel) tblAddEmployeeSale.getModel();
        List<Document> items = new ArrayList<>();

        for (int i = 0; i < model.getRowCount(); i++) {
            items.add(new Document()
                    .append("id", model.getValueAt(i, 0))
                    .append("quantity", model.getValueAt(i, 1))
                    .append("product", model.getValueAt(i, 2))
                    .append("brand", model.getValueAt(i, 3))
                    .append("unit_price", model.getValueAt(i, 4))
                    .append("total", model.getValueAt(i, 5)));
        }

        double subtotal = Double.parseDouble(txtSubtotalEmployeeSale.getText().trim().isEmpty() ? "0" : txtSubtotalEmployeeSale.getText().trim());
        double iva = subtotal * 0.15;
        double totalPagar = subtotal + iva;

        // Asegurar que se refleje en pantalla
        txtIVAEmployeeSale.setText(String.valueOf(iva));
        txtTotalofSaleEmployeeSale.setText(String.valueOf(totalPagar));

        return new Document()
                .append("client", buildClientDoc())
                .append("items", items)
                .append("subtotal", subtotal)
                .append("iva", iva)
                .append("total_to_pay", totalPagar)
                .append("observation", TxtaObservationEmployeeSale.getText().trim())
                .append("attended_by", (loggedUser != null ? loggedUser : txtEmployeeEmployeeSale.getText().trim()))
                .append("created_at", new Date());
    }

    private void totalNetoYGuardarBill() {
        if (!validarPanel2()) {
            return;
        }

        DefaultTableModel model = (DefaultTableModel) tblAddEmployeeSale.getModel();
        if (model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "No hay productos en la tabla.");
            return;
        }

        // Guardar en BILL
        Document bill = buildBillDoc();
        DataManager.saveDocument("bill", bill);

        JOptionPane.showMessageDialog(this, "Venta guardada en MongoDB (bill).");
    }

    private void limpiarPanel2y4() {
        // Panel 2
        txtNameandLastNameEmployeeSale.setText("");
        txtIdentificationEmployeeSale.setText("");
        txtPhoneEmployeeSale.setText("");
        txtAdressEmployeeSale.setText("");
        txtCityEmployeeSale.setText("");
        // txtEmployeeEmployeeSale NO lo limpio si es el usuario logueado,
        // pero si tú quieres limpiarlo también, descomenta:
        // txtEmployeeEmployeeSale.setText("");

        // Panel 4 (producto y totales)
        limpiarCamposProductoSolo();

        // Tabla y totales generales
        DefaultTableModel model = (DefaultTableModel) tblAddEmployeeSale.getModel();
        model.setRowCount(0);

        txtSubtotalEmployeeSale.setText("");
        txtIVAEmployeeSale.setText("");
        txtTotalofSaleEmployeeSale.setText("");
        TxtaObservationEmployeeSale.setText("");
    }

//    private void limpiarTodo() {
//
//        txtNameandLastNameEmployeeSale.setText("");
//        txtIdentificationEmployeeSale.setText("");
//        txtPhoneEmployeeSale.setText("");
//        txtAdressEmployeeSale.setText("");
//        txtCityEmployeeSale.setText("");
//        txtIdEmployeeSale.setText("");
//        txtproduct.setText("");
//        txtPriceEmployeeSale.setText("");
//        txtQuantityEmployeeSale.setText("");
//        txtTotalEmployeeSale.setText("");
//        txtSubtotalEmployeeSale.setText("");
//        txtIVAEmployeeSale.setText("");
//        txtTotalofSaleEmployeeSale.setText("");
//        TxtaObservationEmployeeSale.setText("");
//
//        DefaultTableModel model = (DefaultTableModel) tblAddEmployeeSale.getModel();
//        model.setRowCount(0);
//    }
    private void agregarProductoALaTabla() {
        DefaultTableModel model = (DefaultTableModel) tblAddEmployeeSale.getModel();

        String id = txtIdEmployeeSale.getText();
        String cantidad = txtQuantityEmployeeSale.getText();
        String producto = txtproduct.getText();
        String marca = ""; // Marca viene del MongoDB → species
        String precio = txtPriceEmployeeSale.getText();
        String total = txtTotalEmployeeSale.getText();

        model.addRow(new Object[]{id, cantidad, producto, marca, precio, total});

        calcularSubtotal();
    }

    private void exportarPDF() {
        try {
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Guardar PDF");
            chooser.setSelectedFile(new File("FrmEmployeeSale.pdf"));

            int result = chooser.showSaveDialog(this);
            if (result != JFileChooser.APPROVE_OPTION) {
                return;
            }

            File file = chooser.getSelectedFile();
            if (!file.getName().toLowerCase().endsWith(".pdf")) {
                file = new File(file.getAbsolutePath() + ".pdf");
            }

            // Capturar el JFrame completo
            Dimension size = this.getSize();
            BufferedImage image = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_RGB);
            this.paint(image.getGraphics());

            // Guardar temporal como png
            File tempImg = File.createTempFile("frm_sale_", ".png");
            ImageIO.write(image, "png", tempImg);

            // Crear PDF con mismo tamaño visual
            try (PDDocument doc = new PDDocument()) {
                PDRectangle pageSize = new PDRectangle(size.width, size.height);
                PDPage page = new PDPage(pageSize);
                doc.addPage(page);

                PDImageXObject pdImage = PDImageXObject.createFromFileByContent(tempImg, doc);

                try (PDPageContentStream cs = new PDPageContentStream(doc, page)) {
                    cs.drawImage(pdImage, 0, 0, size.width, size.height);
                }

                doc.save(file);
            }

            tempImg.delete();

            JOptionPane.showMessageDialog(this, "PDF guardado en:\n" + file.getAbsolutePath());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al exportar PDF: " + e.getMessage());
        }
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
        btnSaveClient = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblAddEmployeeSale = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        TxtaObservationEmployeeSale = new javax.swing.JTextArea();
        jLabel8 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txtIdEmployeeSale = new javax.swing.JTextField();
        txtPriceEmployeeSale = new javax.swing.JTextField();
        txtproduct = new javax.swing.JTextField();
        txtQuantityEmployeeSale = new javax.swing.JTextField();
        txtTotalEmployeeSale = new javax.swing.JTextField();
        txtSubtotalEmployeeSale = new javax.swing.JTextField();
        txtIVAEmployeeSale = new javax.swing.JTextField();
        txtTotalofSaleEmployeeSale = new javax.swing.JTextField();
        btnTotalProductEmployeeSale = new javax.swing.JButton();
        btnTotalNetoEmployeeSale = new javax.swing.JButton();
        txtbrandproduct = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();

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
                .addGap(433, 433, 433)
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

        btnSaveClient.setText(">");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(40, 40, 40)
                                .addComponent(jLabel19)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtIdentificationEmployeeSale, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtAdressEmployeeSale, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)
                                    .addComponent(txtNameandLastNameEmployeeSale)
                                    .addComponent(txtPhoneEmployeeSale)
                                    .addComponent(txtCityEmployeeSale)
                                    .addComponent(txtEmployeeEmployeeSale))
                                .addGap(152, 152, 152)
                                .addComponent(btnSaveClient, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(419, 419, 419))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtPhoneEmployeeSale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnSaveClient, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
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

        jLabel14.setText("producto:");

        jLabel15.setText("Cantidad:");

        jLabel16.setText("Total:");

        jScrollPane1.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N

        tblAddEmployeeSale.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblAddEmployeeSale);

        jLabel2.setText("Observaciones:");

        TxtaObservationEmployeeSale.setColumns(20);
        TxtaObservationEmployeeSale.setRows(5);
        jScrollPane2.setViewportView(TxtaObservationEmployeeSale);

        jLabel8.setText("Subtotal:");

        jLabel17.setText("IVA 15%:");

        jLabel18.setText("Total a pagar:");

        btnTotalProductEmployeeSale.setText(">");
        btnTotalProductEmployeeSale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTotalProductEmployeeSaleActionPerformed(evt);
            }
        });

        btnTotalNetoEmployeeSale.setText(">");
        btnTotalNetoEmployeeSale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTotalNetoEmployeeSaleActionPerformed(evt);
            }
        });

        jLabel20.setText("Marca del producto:");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 428, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnTotalNetoEmployeeSale, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(12, 12, 12)
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
                        .addGap(72, 72, 72))
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
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtproduct, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel15)
                                .addGap(18, 18, 18)
                                .addComponent(txtQuantityEmployeeSale, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel20)
                                .addGap(18, 18, 18)
                                .addComponent(txtbrandproduct, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnTotalProductEmployeeSale, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel16)
                        .addGap(26, 26, 26)
                        .addComponent(txtTotalEmployeeSale, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 905, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(18, Short.MAX_VALUE))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(jLabel14)
                            .addComponent(txtIdEmployeeSale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtproduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15)
                            .addComponent(txtQuantityEmployeeSale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel13)
                                    .addComponent(txtPriceEmployeeSale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel20)
                                    .addComponent(txtbrandproduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnTotalProductEmployeeSale, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(11, 11, 11))))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(txtTotalEmployeeSale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                            .addComponent(txtTotalofSaleEmployeeSale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnTotalNetoEmployeeSale, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(0, 13, Short.MAX_VALUE))
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
        limpiarPanel2y4();
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

    private void btnTotalProductEmployeeSaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTotalProductEmployeeSaleActionPerformed
        // TODO add your handling code here:

        try {
            double precio = Double.parseDouble(txtPriceEmployeeSale.getText());
            int cantidad = Integer.parseInt(txtQuantityEmployeeSale.getText());

            double total = precio * cantidad;
            txtTotalEmployeeSale.setText(String.valueOf(total));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Ingrese cantidad válida",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnTotalProductEmployeeSaleActionPerformed

    private void btnTotalNetoEmployeeSaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTotalNetoEmployeeSaleActionPerformed
        // TODO add your handling code here:
        double subtotal = Double.parseDouble(txtSubtotalEmployeeSale.getText());
        double iva = subtotal * 0.15;

        txtIVAEmployeeSale.setText(String.valueOf(iva));
        txtTotalofSaleEmployeeSale.setText(String.valueOf(subtotal + iva));
    }//GEN-LAST:event_btnTotalNetoEmployeeSaleActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea TxtaObservationEmployeeSale;
    private javax.swing.JButton btnSaveClient;
    private javax.swing.JButton btnTotalNetoEmployeeSale;
    private javax.swing.JButton btnTotalProductEmployeeSale;
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
    private javax.swing.JLabel jLabel20;
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
    private javax.swing.JTable tblAddEmployeeSale;
    private javax.swing.JTextField txtAdressEmployeeSale;
    private javax.swing.JTextField txtCityEmployeeSale;
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
    private javax.swing.JTextField txtbrandproduct;
    private javax.swing.JTextField txtproduct;
    // End of variables declaration//GEN-END:variables
}
