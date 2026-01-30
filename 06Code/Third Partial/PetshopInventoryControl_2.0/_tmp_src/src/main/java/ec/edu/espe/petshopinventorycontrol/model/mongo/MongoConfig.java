package ec.edu.espe.petshopinventorycontrol.model.mongo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public final class MongoConfig {

    // Regla tuya: URI "quemada" aquí (compatible Compass y Atlas)
    public static final String MONGO_URI =
            "mongodb+srv://Steven:Steven2001@cluster0.mp8muds.mongodb.net/?appName=Cluster0t";
    public static final String DB_NAME = "PetshopInventoryControlBD";

    // Colecciones (deja estos nombres tal cual uses en Compass/Atlas)
    public static final String SUPPLIER_COLLECTION = "Supplier";
    public static final String PRODUCT_COLLECTION  = "Product";

    private static MongoClient client;

    private MongoConfig() {}

    public static MongoDatabase getDatabase() {
        if (client == null) {
            client = MongoClients.create(MONGO_URI);
        }
        return client.getDatabase(DB_NAME);
    }
//    public static MongoDatabase database() {
//        return getDatabase();
//    }
}

