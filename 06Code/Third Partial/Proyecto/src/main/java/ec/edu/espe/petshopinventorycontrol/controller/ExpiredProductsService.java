package ec.edu.espe.petshopinventorycontrol.controller;

import ec.edu.espe.petshopinventorycontrol.model.ExpiredProductRecord;
import ec.edu.espe.petshopinventorycontrol.model.mongo.MongoProductGateway;
import ec.edu.espe.petshopinventorycontrol.model.mongo.MongoStockGateway;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.bson.Document;

public final class ExpiredProductsService {

    private static final String SOURCE_INVENTORY = "inventory";
    private static final String SOURCE_STOCK = "stock";

    private final MongoProductGateway productGateway;
    private final MongoStockGateway stockGateway;

    private final DateTimeFormatter[] formatters = new DateTimeFormatter[] {
        DateTimeFormatter.ofPattern("yyyy-MM-dd"),
        DateTimeFormatter.ofPattern("dd/MM/yyyy"),
        DateTimeFormatter.ofPattern("yyyy/MM/dd"),
        DateTimeFormatter.ofPattern("dd-MM-yyyy")
    };

    public ExpiredProductsService(MongoProductGateway productGateway, MongoStockGateway stockGateway) {
        this.productGateway = productGateway;
        this.stockGateway = stockGateway;
    }

    public List<ExpiredProductRecord> fetchAll() {
        List<ExpiredProductRecord> result = new ArrayList<>();

        List<Document> productDocs = productGateway.findAllOrdered();
        Map<String, LocalDate> expiryByKey = new HashMap<>();

        for (Document doc : productDocs) {
            String id = readString(doc, "IdProduct", "txtIdProduct");
            String name = readString(doc, "NameProduct", "ameProduct", "txtNameProduct");
            String category = readString(doc, "TypeProduct", "cmbTypeProduct");
            String animalType = readString(doc, "TypeAnimal", "cmbTypeAnimal");
            String brand = readString(doc, "BrandProduct", "txtBrandProduct");
            LocalDate expiryDate = parseDate(doc.get("DateExit"));

            if (expiryDate != null) {
                String key = buildKey(animalType, name, brand);
                if (!key.isEmpty()) {
                    expiryByKey.put(key, expiryDate);
                }
            }

            if (expiryDate == null) {
                continue;
            }

            ExpiredProductRecord record = new ExpiredProductRecord();
            record.setId(id);
            record.setName(name);
            record.setCategory(category.isEmpty() ? animalType : category);
            record.setBrand(brand);
            record.setExpiryDate(expiryDate);
            record.setSource(SOURCE_INVENTORY);
            result.add(record);
        }

        List<Document> stockDocs = stockGateway.findAllOrdered();
        for (Document doc : stockDocs) {
            String id = readString(doc, "idStock");
            String category = readString(doc, "category");
            String name = readString(doc, "name");
            String brand = readString(doc, "brand");
            LocalDate expiryDate = parseDate(doc.get("DateExit"));

            if (expiryDate == null) {
                String key = buildKey(category, name, brand);
                expiryDate = expiryByKey.get(key);
            }

            if (expiryDate == null) {
                continue;
            }

            ExpiredProductRecord record = new ExpiredProductRecord();
            record.setId(id);
            record.setName(name);
            record.setCategory(category);
            record.setBrand(brand);
            record.setExpiryDate(expiryDate);
            record.setSource(SOURCE_STOCK);
            result.add(record);
        }

        return result;
    }

    private String buildKey(String category, String name, String brand) {
        String c = safeKey(category);
        String n = safeKey(name);
        String b = safeKey(brand);
        if (c.isEmpty() || n.isEmpty() || b.isEmpty()) {
            return "";
        }
        return c + "|" + n + "|" + b;
    }

    private String safeKey(String value) {
        if (value == null) {
            return "";
        }
        return value.trim().toLowerCase();
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

    private LocalDate parseDate(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Date date) {
            return Instant.ofEpochMilli(date.getTime())
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
        }
        String text = String.valueOf(value).trim();
        if (text.isEmpty()) {
            return null;
        }
        if (text.equalsIgnoreCase("no aplica")) {
            return null;
        }
        String cleaned = cleanupDateText(text);
        for (DateTimeFormatter formatter : formatters) {
            try {
                return LocalDate.parse(cleaned, formatter);
            } catch (Exception ignored) {
            }
        }
        try {
            return LocalDate.parse(cleaned);
        } catch (Exception ignored) {
        }
        try {
            DateFormat df = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
            Date parsed = df.parse(text);
            if (parsed != null) {
                return Instant.ofEpochMilli(parsed.getTime())
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    private String cleanupDateText(String text) {
        String cleaned = text;
        int tIndex = cleaned.indexOf('T');
        if (tIndex > 0) {
            cleaned = cleaned.substring(0, tIndex);
        }
        int spaceIndex = cleaned.indexOf(' ');
        if (spaceIndex > 0) {
            cleaned = cleaned.substring(0, spaceIndex);
        }
        return cleaned.trim();
    }
}
