package ec.edu.espe.petshopinventorycontrol.model.mongo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import static com.mongodb.client.model.Filters.regex;
import static com.mongodb.client.model.Sorts.descending;
import java.util.ArrayList;
import java.util.List;

public final class MongoSupplierGateway {

    // ✅ como pediste: URI quemada en el código
    private static final String MONGO_URI = "mongodb+srv://Steven:Steven2001@cluster0.mp8muds.mongodb.net/?appName=Cluster0t"; // o tu Atlas mongodb+srv://...
    private static final String DB_NAME = "PetshopInventoryControlBD";
    private static final String COLLECTION_NAME = "Supplier";

    private final MongoCollection<Document> suppliers;

    public MongoSupplierGateway() {
        MongoDatabase db = MongoConfig.getDatabase();
        this.suppliers = db.getCollection(MongoConfig.SUPPLIER_COLLECTION);
    }

    public String findLastIdByPrefix(String prefixDdMMyy) {
        // Busca IDs tipo: 110126-001, 110126-002, ...
        // Regex: ^110126-
        Document last = suppliers
                .find(regex("idSupplier", "^" + prefixDdMMyy + "-"))
                .sort(descending("idSupplier"))
                .limit(1)
                .first();

        return last == null ? null : last.getString("idSupplier");
    }

    public void insertSupplier(Document supplierDoc) {
        suppliers.insertOne(supplierDoc);
    }

    public List<String> findDistinctEnterprises() {
        List<String> enterprises = new ArrayList<>();
        for (String value : suppliers.distinct("EnterpriselSupplier", String.class)) {
            if (value != null) {
                String trimmed = value.trim();
                if (!trimmed.isEmpty() && !enterprises.contains(trimmed)) {
                    enterprises.add(trimmed);
                }
            }
        }
        return enterprises;
    }
}
