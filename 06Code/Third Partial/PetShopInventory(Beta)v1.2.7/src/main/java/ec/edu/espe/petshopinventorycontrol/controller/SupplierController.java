/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espe.petshopinventorycontrol.controller;

import Utils.MongoConnection;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import org.bson.Document;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Steven Loza @ESPE
 */
public class SupplierController {

    private final MongoCollection<Document> colSuppliers;

    public SupplierController() {
        MongoDatabase db = MongoConnection.getDatabase();
        this.colSuppliers = db.getCollection("suppliers");
    }

    public boolean existsIdSupplier(String idSupplier) {
        return colSuppliers.find(Filters.eq("idSupplier", idSupplier)).first() != null;
    }

    public String getNextAvailableSupplierId() {
        String dateKey = new SimpleDateFormat("ddMMyy").format(new Date());

        Document last = colSuppliers.find(Filters.regex("idSupplier", "^" + dateKey + "-"))
                .sort(Sorts.descending("idSupplier"))
                .limit(1)
                .first();

        int nextSeq = 1;
        if (last != null) {
            String lastId = last.getString("idSupplier");
            if (lastId != null && lastId.contains("-")) {
                try {
                    int lastSeq = Integer.parseInt(lastId.split("-")[1]);
                    nextSeq = lastSeq + 1;
                } catch (Exception ignored) {
                    nextSeq = 1;
                }
            }
        }

        return String.format("%s-%03d", dateKey, nextSeq);
    }

    public void insertSupplier(Document supplierDoc) {
        colSuppliers.insertOne(supplierDoc);
    }
}
