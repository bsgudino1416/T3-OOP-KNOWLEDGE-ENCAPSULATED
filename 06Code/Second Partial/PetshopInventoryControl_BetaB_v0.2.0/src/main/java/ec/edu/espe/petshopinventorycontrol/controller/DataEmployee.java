package ec.edu.espe.petshopinventorycontrol.controller;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

public class DataEmployee {

    private static final String CONNECTION_STRING =
        "mongodb+srv://Bryan:Bryan2000@cluster0.sx9cpnq.mongodb.net";

    private static MongoClient mongoClient = null;
    private static MongoDatabase database = null;

    public static void conectar() {
        try {
            if (mongoClient == null) {
                mongoClient = MongoClients.create(CONNECTION_STRING);
                System.out.println(" Conexión establecida con MongoDB Atlas.");
            }

            if (database == null) {
                database = mongoClient.getDatabase("PetShopInventoryDB");
                System.out.println(" Base de datos PetShopInventoryDB activa.");
            }

        } catch (Exception e) {
            System.err.println("ERROR al conectar: " + e.getMessage());
        }
    }

    public static MongoDatabase getDB() {
        if (database == null) {
            conectar(); 
        }
        return database;
    }

    public static MongoCollection<Document> getCollection(String collectionName) {
        return getDB().getCollection(collectionName);
    }

    public static void saveDocument(String collectionName, Document doc) {
        try {
            MongoCollection<Document> coll = getCollection(collectionName);
            coll.insertOne(doc);
            System.out.println(" Documento guardado en la colección: " + collectionName);
        } catch (Exception e) {
            System.err.println("ERROR al guardar documento: " + e.getMessage());
        }
    }
    
    public static void close() {
        if (mongoClient != null) {
            mongoClient.close();
            System.out.println(" Conexión con MongoDB cerrada correctamente.");
        }
    }
}