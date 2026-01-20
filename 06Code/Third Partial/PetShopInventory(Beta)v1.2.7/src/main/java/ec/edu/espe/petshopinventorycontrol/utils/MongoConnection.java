package Utils;


import com.mongodb.client.MongoDatabase;

/**
 *
 * @author Bryan Gudino, KNOWLEDGE ENCAPSULATE, @ESPE
 */

public class MongoConnection {

//    private static final String URI = "mongodb+srv://Steven:Steven2001@cluster0.mp8muds.mongodb.net/?appName=Cluster0t";
//    private static final String DATABASE_NAME = "PetShopInventoryDB";

    
     private MongoConnection() {
    }

    public static MongoDatabase getDatabase() {
        return ec.edu.espe.petshopinventorycontrol.controller.MongoConnection.getDatabase();
    }

    public static void close() {
        ec.edu.espe.petshopinventorycontrol.controller.MongoConnection.close();
    }
}

