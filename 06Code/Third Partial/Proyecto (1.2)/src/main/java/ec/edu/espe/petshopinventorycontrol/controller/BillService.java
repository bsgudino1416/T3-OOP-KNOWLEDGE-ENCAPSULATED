package ec.edu.espe.petshopinventorycontrol.controller;

import ec.edu.espe.petshopinventorycontrol.model.mongo.MongoProductGateway;
import ec.edu.espe.petshopinventorycontrol.model.mongo.MongoStockGateway;
import org.bson.Document;

public final class BillService {

    private final MongoStockGateway stockGateway;
    private final MongoProductGateway productGateway;

    public BillService(MongoStockGateway stockGateway, MongoProductGateway productGateway) {
        this.stockGateway = stockGateway;
        this.productGateway = productGateway;
    }

    public ItemInfo findItemByCode(String code) {
        String normalized = normalize(code);
        if (normalized == null) {
            return null;
        }

        Document stock = stockGateway.findByIdStock(normalized);
        if (stock == null) {
            Document product = productGateway.findProductById(normalized);
            if (product != null) {
                String category = readString(product, "TypeAnimal", "typeAnimal");
                String name = readString(product, "ameProduct", "NameProduct", "txtNameProduct");
                String brand = readString(product, "BrandProduct", "brand");
                if (category != null && name != null && brand != null) {
                    stock = stockGateway.findByCategoryNameBrand(category, name, brand);
                }
            }
        }

        if (stock == null) {
            return null;
        }

        String name = readString(stock, "name", "Name", "productName");
        String priceText = readString(stock, "finalPrice", "priceUnit", "PriceUnit", "final_price");
        Double unitPrice = tryParseDouble(priceText);
        if (name == null || unitPrice == null) {
            return null;
        }

        return new ItemInfo(name, unitPrice);
    }

    private String normalize(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    private Double tryParseDouble(String value) {
        if (value == null) {
            return null;
        }
        try {
            return Double.parseDouble(value.trim().replace(",", "."));
        } catch (Exception ex) {
            return null;
        }
    }

    private String readString(Document doc, String... keys) {
        if (doc == null || keys == null) {
            return null;
        }
        for (String k : keys) {
            Object value = doc.get(k);
            if (value != null) {
                String text = String.valueOf(value).trim();
                if (!text.isEmpty()) {
                    return text;
                }
            }
        }
        return null;
    }

    public record ItemInfo(String name, double unitPrice) {
    }
}
