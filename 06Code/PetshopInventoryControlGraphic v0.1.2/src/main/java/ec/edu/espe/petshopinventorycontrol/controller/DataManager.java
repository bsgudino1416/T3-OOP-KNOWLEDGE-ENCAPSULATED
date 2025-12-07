package ec.edu.espe.petshopinventorycontrol.controller;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

public class DataManager {

    // URI de CONEXIÓN (TU LLAVE)
    private static final String CONNECTION_STRING = 
        "mongodb+srv://Mikael:Mikael1897@cluster0.fpyoe9m.mongodb.net/?appName=Cluster0"; 

    private static MongoClient clienteMongo;
    private static MongoDatabase baseDeDatos;

    public static void conectar() {
        try {
            // El traductor usa la URI para abrir la conexión
            clienteMongo = MongoClients.create(CONNECTION_STRING);
            baseDeDatos = clienteMongo.getDatabase("PetShopDB"); 
            System.out.println(" ¡Conexión con Atlas establecida con éxito!");

        } catch (Exception e) {
            System.err.println(" ERROR: No se pudo conectar a Atlas. Revisa tu URI o la conexión.");
            e.printStackTrace();
        }
    }

    public static MongoDatabase getDB() {
        if (baseDeDatos == null) {
            conectar(); 
        }
        return baseDeDatos;
    }
    
    public static void guardarDocumento(String coleccion, Document documento) {
        try {
            MongoCollection<Document> coll = getDB().getCollection(coleccion);
            coll.insertOne(documento);
            System.out.println("Documento guardado en la colección: " + coleccion);
        } catch (Exception e) {
            System.err.println("Fallo al guardar: " + e.getMessage());
        }
    }

    public static void cerrar() {
        if (clienteMongo != null) {
            clienteMongo.close();
            System.out.println("Conexión con Atlas cerrada.");
        }
    }
}

