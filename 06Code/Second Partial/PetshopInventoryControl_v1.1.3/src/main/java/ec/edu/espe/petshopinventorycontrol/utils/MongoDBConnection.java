package ec.edu.espe.petshopinventorycontrol.utils;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoDBConnection {

    private static final String CONNECTION_STRING =
            "mongodb+srv://Steven:Steven2001@cluster0.mp8muds.mongodb.net/?appName=Cluster0";

    private static final String DATABASE_NAME = "petshop";

    private static MongoClient mongoClient;

    static {
        try {
            ConnectionString connString = new ConnectionString(CONNECTION_STRING);

            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(connString)
                    .serverApi(
                            com.mongodb.ServerApi.builder()
                                    .version(com.mongodb.ServerApiVersion.V1)
                                    .build()
                    )
                    .build();

            mongoClient = MongoClients.create(settings);

            System.out.println("✔ Conexión establecida con MongoDB Atlas.");

        } catch (Exception e) {
            System.out.println("❌ Error conectando a MongoDB: " + e.getMessage());
        }
    }

    public static MongoDatabase getDatabase() {
        return mongoClient.getDatabase(DATABASE_NAME);
    }

    public static void close() {
        if (mongoClient != null) {
            mongoClient.close();
            System.out.println("Conexión a MongoDB cerrada.");
        }
    }
}
