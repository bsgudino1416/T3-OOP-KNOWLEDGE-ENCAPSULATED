package ec.edu.espe.petshopinventorycontrol.data;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.bson.Document;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;


/**
 *
 * @author Bryan Gudino, KNOWLEDGE ENCAPSULATE, @ESPE
 */

public class TableToMongo {

    public static void saveTableToMongo(JTable table) {
        MongoDatabase db = MongoConnection.getDatabase();
        MongoCollection<Document> collection = db.getCollection("Sale");

        DefaultTableModel model = (DefaultTableModel) table.getModel();

        // Recorre filas y guarda cada fila como un documento
        for (int i = 0; i < model.getRowCount(); i++) {
            Object category = model.getValueAt(i, 0);
            Object animal = model.getValueAt(i, 1);
            Object product = model.getValueAt(i, 2);
            Object brand = model.getValueAt(i, 3);
            Object flavor = model.getValueAt(i, 4);
            Object quantity = model.getValueAt(i, 5);
            Object price = model.getValueAt(i, 6);

            if (category != null && price != null) { // Solo guarda filas con datos
                Document doc = new Document("category", category.toString())
                        .append("animal", animal.toString())
                        .append("product", product.toString())
                        .append("brand", brand.toString())
                        .append("flavor", flavor.toString())
                        .append("quantity", Integer.parseInt(quantity.toString()))
                        .append("price", Double.parseDouble(price.toString()));

                collection.insertOne(doc);
            }
        }

        System.out.println("Datos guardados correctamente en MongoDB!");
    }
}
