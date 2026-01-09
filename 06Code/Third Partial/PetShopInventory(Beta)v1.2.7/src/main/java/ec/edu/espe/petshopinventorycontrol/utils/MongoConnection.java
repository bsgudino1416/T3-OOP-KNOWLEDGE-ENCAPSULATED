package ec.edu.espe.petshopinventorycontrol.utils;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;

/**
 *
 * @author Bryan Gudino, KNOWLEDGE ENCAPSULATE, @ESPE
 */

public class MongoConnection {

    private static final String URI = "mongodb+srv://Steven:Steven2001@cluster0.mp8muds.mongodb.net/?appName=Cluster0t";
    private static final String DATABASE_NAME = "PetShopInventoryDB";

    private static MongoClient mongoClient;
    private static MongoDatabase database;

    // Inicializa la conexión (lazy)
    public static MongoDatabase getDatabase() {
        if (database == null) {
            mongoClient = MongoClients.create(URI);
            database = mongoClient.getDatabase(DATABASE_NAME);
        }
        return database;
    }

    public static void close() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }
}

