package Utils;


import com.mongodb.client.MongoDatabase;

/**
 *
 * @author Bryan Gudino, KNOWLEDGE ENCAPSULATE, @ESPE
 */

public class MongoConnection {



    
     private MongoConnection() {
    }

    public static MongoDatabase getDatabase() {
        return ec.edu.espe.petshopinventorycontrol.controller.MongoConnection.getDatabase();
    }

    public static void close() {
        ec.edu.espe.petshopinventorycontrol.controller.MongoConnection.close();
    }
}

