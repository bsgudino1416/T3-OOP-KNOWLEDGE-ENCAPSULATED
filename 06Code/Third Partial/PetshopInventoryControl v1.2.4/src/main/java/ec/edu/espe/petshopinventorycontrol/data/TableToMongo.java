package ec.edu.espe.petshopinventorycontrol.data;

import ec.edu.espe.petshopinventorycontrol.utils.MongoConnection;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.bson.Document;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.bson.types.ObjectId;

/**
 *
 * @author Bryan Gudino, KNOWLEDGE ENCAPSULATE, @ESPE
 */

public class TableToMongo {

    // ✅ CONSTANTES DE COLECCIONES
    private static final String SALE_COLLECTION = "SALE";
    private static final String CLIENT_COLLECTION = "CLIENT";
    


    public static void saveTableToMongo(JTable table) {

    MongoCollection<Document> collection =
            MongoConnection.getDatabase().getCollection(SALE_COLLECTION);

    DefaultTableModel model = (DefaultTableModel) table.getModel();

    for (int i = 0; i < model.getRowCount(); i++) {

        if (model.getValueAt(i, 0) == null) continue;

        Document doc = new Document()
                .append("category", model.getValueAt(i, 0).toString())
                .append("animal", model.getValueAt(i, 1).toString())
                .append("product", model.getValueAt(i, 2).toString())
                .append("brand", model.getValueAt(i, 3).toString())
                .append("flavor", model.getValueAt(i, 4).toString())
                .append("quantity", Integer.parseInt(model.getValueAt(i, 5).toString()))
                .append("price", Double.parseDouble(model.getValueAt(i, 6).toString()));

        collection.insertOne(doc);
    }
}

    public static void saveClientToMongo(
            String name,
            String id,
            String address,
            String email,
            double total
    ) {
        MongoCollection<Document> collection
                = MongoConnection.getDatabase().getCollection(CLIENT_COLLECTION);

        Document doc = new Document("name", name)
                .append("id", id)
                .append("address", address)
                .append("email", email)
                .append("total", total);

        collection.insertOne(doc);
    }

   public static void loadSalesFromMongo(JTable table) {

    MongoCollection<Document> collection =
            MongoConnection.getDatabase().getCollection(SALE_COLLECTION);

    DefaultTableModel model = (DefaultTableModel) table.getModel();
    model.setRowCount(0);

    try (MongoCursor<Document> cursor = collection.find().iterator()) {
        while (cursor.hasNext()) {
            Document doc = cursor.next();

            model.addRow(new Object[]{
                doc.getString("category"),
                doc.getString("animal"),
                doc.getString("product"),
                doc.getString("brand"),
                doc.getString("flavor"),
                doc.getInteger("quantity"),
                doc.getDouble("price")
            });
        }
    }
}


    // ocultar columna _id
//    table.getColumnModel () 
//        .getColumn(0).setMinWidth(0);
//        table.getColumnModel().getColumn(0).setMaxWidth(0);
//        table.getColumnModel().getColumn(0).setWidth(0);
    

}
