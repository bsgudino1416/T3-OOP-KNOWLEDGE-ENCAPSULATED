/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espe.petshopinventorycontrol.controller;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.time.LocalDateTime;

/**
 *
 * @author Steven Loza @ESPE
 */
public class AuthController {

    public Document login(String username, String password) {
        MongoDatabase db = DataEmployee.getDB();
        MongoCollection<Document> coll = db.getCollection("EmployeeData");

        return coll.find(new Document("username", username)
                .append("password", password)).first();
    }

    public String register(Document userDoc) {
        MongoDatabase db = DataEmployee.getDB();
        MongoCollection<Document> coll = db.getCollection("EmployeeData");

        if (coll.countDocuments() >= 20) {
            return "Máximo 20 usuarios permitidos.";
        }

        userDoc.append("created_at", LocalDateTime.now().toString());
        coll.insertOne(userDoc);
        return null; // null = OK
    }
}
