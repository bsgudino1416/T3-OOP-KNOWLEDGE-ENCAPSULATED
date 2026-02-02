package ec.edu.espe.petshopinventorycontrol.model.mongo;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;

import static com.mongodb.client.model.Sorts.ascending;

public final class MongoBillGateway {

    private static final String COLLECTION_BILL = "Bill";
    private static final String COLLECTION_FACTURA = "Factura";

    public List<Document> findAllOrdered() {
        List<Document> result = new ArrayList<>();
        MongoDatabase db = MongoConfig.getDatabase();

        String[] collections = new String[] {
            COLLECTION_BILL,
            COLLECTION_FACTURA,
            "Bill",
            "bills",
            "factura"
        };

        for (String name : collections) {
            MongoCollection<Document> col = db.getCollection(name);
            for (Document doc : col.find().sort(ascending("_id"))) {
                result.add(doc);
            }
        }

        return result;
    }

    public void insertBill(Document billDoc) {
        if (billDoc == null) {
            return;
        }
        MongoDatabase db = MongoConfig.getDatabase();
        MongoCollection<Document> bills = db.getCollection(COLLECTION_BILL);
        bills.insertOne(billDoc);
    }
}
