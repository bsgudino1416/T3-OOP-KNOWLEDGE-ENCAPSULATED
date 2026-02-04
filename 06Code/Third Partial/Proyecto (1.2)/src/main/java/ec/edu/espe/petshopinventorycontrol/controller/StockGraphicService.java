package ec.edu.espe.petshopinventorycontrol.controller;

import ec.edu.espe.petshopinventorycontrol.model.StockRecord;
import ec.edu.espe.petshopinventorycontrol.model.mongo.MongoStockGateway;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.bson.Document;

public final class StockGraphicService {

    private final MongoStockGateway gateway;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public StockGraphicService(MongoStockGateway gateway) {
        this.gateway = gateway;
    }

    public List<StockRecord> fetchAll() {
        List<Document> docs = gateway.findAllOrdered();
        List<StockRecord> records = new ArrayList<>();

        for (Document doc : docs) {
            StockRecord record = new StockRecord();
            record.setId(readString(doc, "idStock"));
            record.setCategory(readString(doc, "category"));
            record.setName(readString(doc, "name"));
            record.setBrand(readString(doc, "brand"));
            record.setCost(readString(doc, "cost"));
            record.setUnitEntry(readString(doc, "unitEntry"));
            record.setGainPercent(readString(doc, "gainPercent"));
            record.setFinalPrice(readString(doc, "finalPrice"));
            record.setGainValue(readString(doc, "gainValue"));
            record.setCreatedAt(formatDate(doc.get("createdAt")));
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
