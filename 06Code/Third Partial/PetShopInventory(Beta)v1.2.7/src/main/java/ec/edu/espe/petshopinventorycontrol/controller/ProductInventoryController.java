package ec.edu.espe.petshopinventorycontrol.controller;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import ec.edu.espe.petshopinventorycontrol.model.PurchaseProduct;
import ec.edu.espe.petshopinventorycontrol.utils.MongoProductConnection;
import org.bson.Document;

import java.util.List;

public class ProductInventoryController {

    private final MongoCollection<Document> collection;

    public ProductInventoryController() {
        MongoDatabase db = MongoProductConnection.getDatabase();
        collection = db.getCollection("productentry");
    }

    public void saveFromTable(List<PurchaseProduct> products) {
        for (PurchaseProduct product : products) {
            collection.insertOne(toDocument(product));
        }
    }

    private Document toDocument(PurchaseProduct p) {
        return new Document()
                .append("productId", p.getProductId())
                .append("name", p.getProductName())
                .append("type", p.getProductType())
                .append("animal", p.getAnimal())
                .append("quantity", p.getQuantity())
                .append("totalCost", p.getTotalCost());
    }
}

