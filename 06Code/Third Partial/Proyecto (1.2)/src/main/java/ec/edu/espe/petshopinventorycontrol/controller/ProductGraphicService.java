package ec.edu.espe.petshopinventorycontrol.controller;

import ec.edu.espe.petshopinventorycontrol.model.ProductRecord;
import ec.edu.espe.petshopinventorycontrol.model.mongo.MongoProductGateway;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.bson.Document;

public final class ProductGraphicService {

    private final MongoProductGateway gateway;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public ProductGraphicService(MongoProductGateway gateway) {
        this.gateway = gateway;
    }

    public List<ProductRecord> fetchAll() {
        List<Document> docs = gateway.findAllOrdered();
        List<ProductRecord> records = new ArrayList<>();

        for (Document doc : docs) {
            ProductRecord record = new ProductRecord();
            record.setId(readString(doc, "IdProduct", "txtIdProduct"));
            record.setSupplier(readString(doc, "Supplier", "upplier"));
            record.setName(readString(doc, "NameProduct", "ameProduct", "txtNameProduct"));
            record.setTypeProduct(readString(doc, "TypeProduct", "cmbTypeProduct"));
            record.setAnimalType(readString(doc, "TypeAnimal", "cmbTypeAnimal"));
            record.setBrand(readString(doc, "BrandProduct", "txtBrandProduct"));
            record.setCost(readString(doc, "CostProduct", "txtCostProduct", "cost"));
            record.setUnit(readString(doc, "Unit", "cmbUnit", "unit"));
            record.setQuantity(readString(doc, "Quantity", "quantity", "txtQuantity"));
            record.setInvestment(readString(doc, "InvestmentCost", "investmentCost", "jTextField13"));
            record.setPounds(readString(doc, "Pounds", "txtPounds"));
            record.setTotalPounds(readString(doc, "TotalPounds", "txtTotalPounds"));
            record.setDateEntry(formatDate(doc.get("DateEntry")));
            record.setDateExit(formatDate(doc.get("DateExit")));
            records.add(record);
        }

        return records;
    }

    private String readString(Document doc, String... keys) {
        if (doc == null || keys == null) {
            return "";
        }
        for (String key : keys) {
            Object value = doc.get(key);
            if (value == null) {
                continue;
            }
            String text = String.valueOf(value).trim();
            if (!text.isEmpty()) {
                return text;
            }
        }
        return "";
    }

    private String formatDate(Object value) {
        if (value == null) {
            return "";
        }
        if (value instanceof Date date) {
            return dateFormat.format(date);
        }
        return String.valueOf(value);
    }
}
