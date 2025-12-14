package ec.edu.espe.petshopinventorycontrol.view;


import javax.swing.ImageIcon;
import java.awt.Image;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import ec.edu.espe.petshopinventorycontrol.controller.DataManager;
import org.bson.Document;

import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import java.io.File;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

/**
 *
 * @author Bryan Gudino, KNOWLEDGE ENCAPSULATE, @ESPE
 */
public class FrmManagerGenerateReport extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FrmManagerGenerateReport.class.getName());

    /**
     * Creates new form NewJFrame1
     */
    public FrmManagerGenerateReport() {
        initComponents();
        configurarTabla();
    }

    private void configurarTabla() {
        DefaultTableModel model = new DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "Id", "Tipo de animal", "Tipo de producto", "Especie", "Marca", "Precio", "Stock"
                }
        );
        tblShowProduct.setModel(model);
    }

    private void cargarProductos() {
        try {
            MongoDatabase db = DataManager.getDB();
            MongoCollection<Document> collection = db.getCollection("productos");

            MongoCursor<Document> cursor = collection.find().iterator();

            DefaultTableModel model = (DefaultTableModel) tblShowProduct.getModel();
            model.setRowCount(0); // limpiar tabla

            while (cursor.hasNext()) {
                Document doc = cursor.next();

                model.addRow(new Object[]{
                    doc.getString("id"),
                    doc.getString("typeAnimal"),
                    doc.getString("typeProduct"),
                    doc.getString("species"),
                    doc.getString("brand"),
                    doc.getDouble("price"),
                    doc.getInteger("stock")
                });
            }

            cursor.close();

            // CONTAR PRODUCTOS
            txtGenerateReport.setText(String.valueOf(model.getRowCount()));

            if (model.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "No hay productos registrados.");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al generar reporte: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */

    }

    private void generarPDF() {
    try {
        DefaultTableModel model = (DefaultTableModel) tblShowProduct.getModel();

        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        PDPageContentStream content = new PDPageContentStream(document, page);

        //content.setFont(PDType1Font.HELVETICA_BOLD, 14);
        content.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 14);

        content.beginText();
        content.newLineAtOffset(50, 750);
        content.showText("REPORTE DE INVENTARIO - PETSHOP");
        content.endText();

      content.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 10);

        int y = 720;

        for (int i = 0; i < model.getRowCount(); i++) {
            content.beginText();
            content.newLineAtOffset(50, y);

            String fila =
                    model.getValueAt(i, 0) + " | " +
                    model.getValueAt(i, 1) + " | " +
                    model.getValueAt(i, 2) + " | " +
                    model.getValueAt(i, 3) + " | " +
                    model.getValueAt(i, 4) + " | $" +
                    model.getValueAt(i, 5) + " | " +
                    model.getValueAt(i, 6);

            content.showText(fila);
            content.endText();

            y -= 15;
        }

        // TOTAL
        content.beginText();
        content.newLineAtOffset(50, y - 20);
        content.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);

        content.showText("Total de productos: " + model.getRowCount());
        content.endText();

        content.close();

        File file = new File("Reporte_Inventario_Petshop.pdf");
        document.save(file);
        document.close();

        JOptionPane.showMessageDialog(this,
                "Reporte PDF generado correctamente:\n" + file.getAbsolutePath(),
                "Éxito", JOptionPane.INFORMATION_MESSAGE);

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this,
                "Error al generar PDF: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
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
        jPanel3 = new javax.swing.JPanel();
        bttnExitGenerateReport = new javax.swing.JButton();
        bttnGenerateReport = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblShowProduct = new javax.swing.JTable();
        jLabel12ModifyProduct = new javax.swing.JLabel();
        txtGenerateReport = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 0, 119));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 3, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("GENERAR REPORTE");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(313, 313, 313)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(0, 0, 119));

        bttnExitGenerateReport.setText("Salir");
        bttnExitGenerateReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bttnExitGenerateReportActionPerformed(evt);
            }
        });

        bttnGenerateReport.setText("Imprimir");
        bttnGenerateReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bttnGenerateReportActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(164, 164, 164)
                .addComponent(bttnGenerateReport)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(bttnExitGenerateReport)
                .addGap(230, 230, 230))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bttnExitGenerateReport)
                    .addComponent(bttnGenerateReport))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(204, 204, 204));
        jPanel4.setForeground(new java.awt.Color(204, 204, 204));

        jScrollPane1.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N

        tblShowProduct.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Id", "Tipo de animal", "Producto", "Animal", "Marca ", "Precio", "Stock"
            }
        ));
        jScrollPane1.setViewportView(tblShowProduct);

        jLabel12ModifyProduct.setText("Total de productos en el Inventario:");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel12ModifyProduct)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtGenerateReport, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 779, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(34, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12ModifyProduct)
                    .addComponent(txtGenerateReport, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bttnExitGenerateReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bttnExitGenerateReportActionPerformed
        // TODO add your handling code here:
        FrmManagerMenu menu = new FrmManagerMenu();
        menu.setVisible(true);
        menu.setLocationRelativeTo(null);
        this.dispose();
    }//GEN-LAST:event_bttnExitGenerateReportActionPerformed

    private void bttnGenerateReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bttnGenerateReportActionPerformed
        // TODO add your handling code here:
        cargarProductos();
        generarPDF();
    }//GEN-LAST:event_bttnGenerateReportActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bttnExitGenerateReport;
    private javax.swing.JButton bttnGenerateReport;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12ModifyProduct;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblShowProduct;
    private javax.swing.JTextField txtGenerateReport;
    // End of variables declaration//GEN-END:variables
}
