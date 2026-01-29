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
import java.util.regex.Pattern;

public final class MongoProductGateway {

    private final MongoCollection<Document> products;

    public MongoProductGateway() {
        this.products = MongoConfig.getDatabase().getCollection(MongoConfig.PRODUCT_COLLECTION);
    }

//    public String findLastIdByPrefix(String prefixDdMMyy) {
//        Document last = products
//                .find(regex("txtIdProduct", "^" + prefixDdMMyy + "-"))
//                .sort(descending("txtIdProduct"))
//                .limit(1)
//                .first();
//
//        return last == null ? null : last.getString("txtIdProduct");
//    }
    public String findLastIdByPrefix(String prefixDdMMyy) {
        Document last = products.find(or(
                regex("txtIdProduct", "^" + prefixDdMMyy + "-"),
                regex("IdProduct", "^" + prefixDdMMyy + "-")
        ))
                .sort(descending("txtIdProduct")) // si tus docs usan IdProduct, igual sirve por orden lexical
                .limit(1)
                .first();

        if (last == null) {
            return null;
        }

        String id = last.getString("txtIdProduct");
        if (id == null) {
            id = last.getString("IdProduct");
        }

        return id;
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

    public Document findProductByNameIgnoreCase(String name) {
        if (name == null) {
            return null;
        }
        String pattern = "^" + Pattern.quote(name.trim()) + "$";
        MongoCollection<Document> col = productsCollection();
        return col.find(or(
                regex("ameProduct", pattern, "i"),
                regex("NameProduct", pattern, "i"),
                regex("txtNameProduct", pattern, "i")
        )).first();
    }

    public Document findProductById(String idProduct) {
        if (idProduct == null) {
            return null;
        }
        String trimmed = idProduct.trim();
        if (trimmed.isEmpty()) {
            return null;
        }
        MongoCollection<Document> col = productsCollection();
        return col.find(or(
                eq("IdProduct", trimmed),
                eq("txtIdProduct", trimmed)
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

//    public String readCost(Document productDoc) {
//        return readString(productDoc, "CostProduct", "txtCostProduct", "cost", "txtCost");
//    }
//
//    public String readUnit(Document productDoc) {
//        return readString(productDoc, "Unit", "cmbUnit", "unit", "cmbCostUnit");
//    }
    // === Helpers de lectura de campos (para no depender de 1 solo nombre en Mongo) ===
    public String readCost(Document productDoc) {
        if (productDoc == null) {
            return null;
        }

        // Tu DB está guardando el costo como "CostProduct" (según tu lógica actual)
        String v = productDoc.getString("CostProduct");
        if (v != null) {
            return v;
        }

        // Fallbacks por si en otros registros quedó con otro nombre
        v = productDoc.getString("txtCostProduct");
        if (v != null) {
            return v;
        }

        v = productDoc.getString("cost");
        if (v != null) {
            return v;
        }

        Object any = productDoc.get("CostProduct");
        return any != null ? String.valueOf(any) : null;
    }

    public String readUnit(Document productDoc) {
        if (productDoc == null) {
            return null;
        }

        // Si guardas unidad como texto
        String v = productDoc.getString("Unit");
        if (v != null) {
            return v;
        }

        v = productDoc.getString("cmbUnit");
        if (v != null) {
            return v;
        }

        v = productDoc.getString("unit");
        if (v != null) {
            return v;
        }

        Object any = productDoc.get("Unit");
        return any != null ? String.valueOf(any) : null;
    }

    public Double readQuantity(Document productDoc) {
        if (productDoc == null) {
            return null;
        }
        return readNumber(productDoc, "Quantity", "quantity", "txtQuantity");
    }

    public Double readInvestmentCost(Document productDoc) {
        if (productDoc == null) {
            return null;
        }
        return readNumber(productDoc, "InvestmentCost", "investmentCost", "jTextField13");
    }

    public Double readTotalPounds(Document productDoc) {
        if (productDoc == null) {
            return null;
        }
        return readNumber(productDoc, "TotalPounds", "totalPounds", "txtTotalPounds");
    }

    public void updateProductById(Object id, Document updates) {
        if (id == null || updates == null || updates.isEmpty()) {
            return;
        }
        products.updateOne(eq("_id", id), new Document("$set", updates));
    }

    private Double readNumber(Document doc, String... keys) {
        for (String k : keys) {
            Object v = doc.get(k);
            if (v instanceof Number) {
                return ((Number) v).doubleValue();
            }
            if (v != null) {
                try {
                    return Double.parseDouble(String.valueOf(v).trim());
                } catch (Exception ignored) {
                }
            }
        }
        return null;
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
