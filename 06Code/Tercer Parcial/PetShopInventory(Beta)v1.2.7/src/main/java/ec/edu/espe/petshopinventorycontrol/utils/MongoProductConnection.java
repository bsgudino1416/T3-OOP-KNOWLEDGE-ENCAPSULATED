package ec.edu.espe.petshopinventorycontrol.utils;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoProductConnection {

    private static final String CONNECTION_STRING = "mongodb+srv://Bryan:Bryan2000@cluster0.sx9cpnq.mongodb.net/?appName=Cluster0";

    private static final String DATABASE_NAME = "PetShopInventoryDB";

    private static MongoClient mongoClient;

    private MongoProductConnection() {
        
    }

    public static MongoDatabase getDatabase() {
        if (mongoClient == null) {
            mongoClient = MongoClients.create(CONNECTION_STRING);
        }
        return mongoClient.getDatabase(DATABASE_NAME);
    }

    public static void close() {
        if (mongoClient != null) {
            mongoClient.close();
            mongoClient = null;
        }
    }
}
