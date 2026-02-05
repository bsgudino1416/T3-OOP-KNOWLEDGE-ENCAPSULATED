package ec.edu.espe.petshopinventorycontrol.model.mongo;

import com.mongodb.client.DistinctIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import static com.mongodb.client.model.Filters.regex;
import static com.mongodb.client.model.Sorts.descending;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
//import com.mongodb.client.distinct.DistinctIterable;
import org.bson.Document;
import java.util.ArrayList;
import java.util.List;
import static com.mongodb.client.model.Filters.*;

public final class MongoProductGateway {

    private final MongoCollection<Document> products;

    public MongoProductGateway() {
        this.products = MongoConfig.getDatabase().getCollection(MongoConfig.PRODUCT_COLLECTION);
    }

    public String findLastIdByPrefix(String prefixDdMMyy) {
        Document last = products
                .find(regex("txtIdProduct", "^" + prefixDdMMyy + "-"))
                .sort(descending("txtIdProduct"))
                .limit(1)
                .first();

        return last == null ? null : last.getString("txtIdProduct");
    }

    public void insert(Document productDoc) {
        products.insertOne(productDoc);
    }

    public List<String> findDistinctAnimalTypesUsed() {
        MongoCollection<Document> col = productsCollection();
        DistinctIterable<String> it = col.distinct("TypeAnimal", String.class);

        List<String> result = new ArrayList<>();
        for (String v : it) {
            if (v != null && !v.trim().isEmpty()) {
                result.add(v.trim());
            }
        }
        return result;
    }

    public List<String> findDistinctNamesByAnimal(String animalType) {
        MongoCollection<Document> col = productsCollection();
        DistinctIterable<String> it = col.distinct("ameProduct", eq("TypeAnimal", animalType), String.class);

        List<String> result = new ArrayList<>();
        for (String v : it) {
            if (v != null && !v.trim().isEmpty()) {
                result.add(v.trim());
            }
        }
        return result;
    }

    public List<String> findDistinctBrandsByAnimalAndName(String animalType, String name) {
        MongoCollection<Document> col = productsCollection();
        DistinctIterable<String> it = col.distinct(
                "BrandProduct",
                and(eq("TypeAnimal", animalType), eq("ameProduct", name)),
                String.class
        );

        List<String> result = new ArrayList<>();
        for (String v : it) {
            if (v != null && !v.trim().isEmpty()) {
                result.add(v.trim());
            }
        }
        return result;
    }

    public Document findProductByAnimalNameBrand(String animalType, String name, String brand) {
        MongoCollection<Document> col = productsCollection();
        return col.find(and(
                eq("TypeAnimal", animalType),
                eq("ameProduct", name),
                eq("BrandProduct", brand)
        )).first();
    }

    public String readCostFromProduct(Document productDoc) {
        if (productDoc == null) {
            return null;
        }

        // NOMBRE CORRECTO
        String cost = productDoc.getString("CostProduct");
        if (cost != null && !cost.trim().isEmpty()) {
            return cost;
        }

        // POSIBLES NOMBRES VIEJOS (por si antes guardaste distinto)
        cost = productDoc.getString("txtCostProduct");
        if (cost != null && !cost.trim().isEmpty()) {
            return cost;
        }

        cost = productDoc.getString("cost");
        if (cost != null && !cost.trim().isEmpty()) {
            return cost;
        }

        // Si lo guardaste como número (Double/Int)
        Object any = productDoc.get("CostProduct");
        if (any != null) {
            return String.valueOf(any);
        }

        any = productDoc.get("txtCostProduct");
        if (any != null) {
            return String.valueOf(any);
        }

        any = productDoc.get("cost");
        if (any != null) {
            return String.valueOf(any);
        }

        return null;
    }

    private String readString(Document doc, String... keys) {
        for (String k : keys) {
            Object v = doc.get(k);
            if (v != null) {
                String s = String.valueOf(v).trim();
                if (!s.isEmpty()) {
                    return s;
                }
            }
        }
        return null;
    }

    public String readCost(Document productDoc) {
        return readString(productDoc, "CostProduct", "txtCostProduct", "cost", "txtCost");
    }

    public String readUnit(Document productDoc) {
        return readString(productDoc, "Unit", "cmbUnit", "unit", "cmbCostUnit");
    }

    /**
     * Usa el mismo MongoConfig y misma DB. Ajusta este método si tu colección
     * se llama diferente.
     */
    private MongoCollection<Document> productsCollection() {
        MongoDatabase db = MongoConfig.getDatabase();
        return db.getCollection("Product");
    }
    
}
