package ec.edu.espe.petshopinventorycontrol.model.mongo;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
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

    public Document findByIdStock(String idStock) {
        if (idStock == null || idStock.trim().isEmpty()) {
            return null;
        }
        return collection().find(eq(FIELD_ID, idStock.trim())).first();
    }

    public Document findByCategoryNameBrand(String category, String name, String brand) {
        if (category == null || name == null || brand == null) {
            return null;
        }
        return collection().find(and(
                eq("category", category),
                eq("name", name),
                eq("brand", brand)
        )).first();
    }

    public void insertStock(Document stockDoc) {
        collection().insertOne(stockDoc);
    }
}
