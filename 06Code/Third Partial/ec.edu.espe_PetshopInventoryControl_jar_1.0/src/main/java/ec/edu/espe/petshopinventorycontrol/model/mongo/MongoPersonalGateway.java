package ec.edu.espe.petshopinventorycontrol.model.mongo;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

import static com.mongodb.client.model.Filters.regex;
import static com.mongodb.client.model.Sorts.descending;

public final class MongoPersonalGateway {

    private static final String FIELD_ID = "IdPersonal";
    private final MongoCollection<Document> collection;

    public MongoPersonalGateway() {
        this.collection = MongoConfig.getDatabase().getCollection(MongoConfig.PERSONAL_COLLECTION);
    }

    public String findLastIdByPrefix(String prefix) {
        Document doc = collection
                .find(regex(FIELD_ID, "^" + prefix + "-"))
                .sort(descending(FIELD_ID))
                .limit(1)
                .first();
        if (doc == null) {
            return null;
        }
        return doc.getString(FIELD_ID);
    }

    public void insert(Document personalDoc) {
        if (personalDoc == null) {
            return;
        }
        collection.insertOne(personalDoc);
    }
}
