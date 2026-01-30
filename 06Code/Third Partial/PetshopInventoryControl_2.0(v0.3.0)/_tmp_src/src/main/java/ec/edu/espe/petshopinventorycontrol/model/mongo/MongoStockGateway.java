package ec.edu.espe.petshopinventorycontrol.model.mongo;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import static com.mongodb.client.model.Filters.regex;
import static com.mongodb.client.model.Sorts.descending;

public class MongoStockGateway {

    private static final String COLLECTION_NAME = "Stock";
    private static final String FIELD_ID = "idStock";

    private MongoCollection<Document> collection() {
        MongoDatabase db = MongoConfig.getDatabase();
        return db.getCollection(COLLECTION_NAME);
    }

    public String findLastIdByPrefix(String prefix) {
        Document doc = collection()
                .find(regex(FIELD_ID, "^" + prefix + "-"))
                .sort(descending(FIELD_ID))
                .first();

        if (doc == null) {
            return null;
        }
        return doc.getString(FIELD_ID);
    }

    public void insertStock(Document stockDoc) {
        collection().insertOne(stockDoc);
    }
}

