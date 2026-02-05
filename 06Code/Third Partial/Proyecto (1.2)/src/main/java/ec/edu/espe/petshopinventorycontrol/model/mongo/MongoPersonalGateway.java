package ec.edu.espe.petshopinventorycontrol.model.mongo;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import static com.mongodb.client.model.Filters.regex;
import static com.mongodb.client.model.Sorts.descending;
import static com.mongodb.client.model.Sorts.ascending;
import java.util.ArrayList;
import java.util.List;

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

    public List<Document> findAllOrdered() {
        List<Document> result = new ArrayList<>();
        for (Document doc : collection.find().sort(ascending("_id"))) {
            result.add(doc);
        }
        if (!result.isEmpty()) {
            return result;
        }

        MongoDatabase db = MongoConfig.getDatabase();
        String[] fallbacks = new String[]{
            "personal",
            "Personals",
            "personals"
        };
        for (String name : fallbacks) {
            if (name.equalsIgnoreCase(MongoConfig.PERSONAL_COLLECTION)) {
                continue;
            }
            List<Document> alt = new ArrayList<>();
            MongoCollection<Document> col = db.getCollection(name);
            for (Document doc : col.find().sort(ascending("_id"))) {
                alt.add(doc);
            }
            if (!alt.isEmpty()) {
                return alt;
            }
        }
        return result;
    }
}
