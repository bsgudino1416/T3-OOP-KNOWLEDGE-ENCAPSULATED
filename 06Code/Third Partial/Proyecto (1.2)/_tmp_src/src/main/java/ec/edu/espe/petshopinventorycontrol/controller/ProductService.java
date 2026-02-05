package ec.edu.espe.petshopinventorycontrol.controller;

import ec.edu.espe.petshopinventorycontrol.model.mongo.MongoProductGateway;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import org.bson.Document;

public final class ProductService {

    private static final String NOT_APPLIES = "No aplica";

    private final MongoProductGateway gateway;
    private final ProductValidator validator;

    public ProductService(MongoProductGateway gateway, ProductValidator validator) {
        this.gateway = gateway;
        this.validator = validator;
    }

    public String generateNextProductId(Date now) {
        String prefix = new SimpleDateFormat("ddMMyy").format(now);
        String lastId = gateway.findLastIdByPrefix(prefix);

        int next = 1;
        if (lastId != null && lastId.matches("^" + prefix + "-\\d{3}$")) {
            next = Integer.parseInt(lastId.substring(lastId.length() - 3)) + 1;
        }
        return prefix + "-" + String.format("%03d", next);
    }

    public Map<String, String> validateFields(
            String idProduct,
            String supplier,
            String name,
            String typeProduct,
            String animalType,
            String brand,
            String costText,
            String unit,
            Object quantityValue,
            String investmentText,
            String poundsText,
            String totalPoundsText,
            Date entryDate,
            Date expiryDate
    ) {
        return validator.validate(
                idProduct, supplier, name, typeProduct, animalType, brand,
                costText, unit, quantityValue, investmentText,
                poundsText, totalPoundsText,
                entryDate, expiryDate
        );
    }

    public void saveProduct(
            String idProduct,
            String supplier,
            String name,
            String typeProduct,
            String animalType,
            String brand,
            String costText,
            String unit,
            Number quantityValue,
            String investmentText,
            String poundsText,
            String totalPoundsText,
            Date entryDate,
            Date expiryDate
    ) {
        Object expiryValue = isExpiryRequired(typeProduct) ? expiryDate : NOT_APPLIES;
        Object poundsValue = isPoundsRequired(unit) ? poundsText : NOT_APPLIES;
        Object totalPoundsValue = isPoundsRequired(unit) ? totalPoundsText : NOT_APPLIES;

        Document doc = new Document()
                .append("IdProduct", idProduct)
                .append("upplier", supplier)
                .append("ameProduct", name)
                .append("TypeProduct", typeProduct)
                .append("TypeAnimal", animalType) 
                .append("BrandProduct", brand)
                .append("CostProduct", costText)
                .append("Unit", unit)
                .append("Quantity", quantityValue)
                .append("InvestmentCost", investmentText)
                .append("Pounds", poundsValue)
                .append("TotalPounds", totalPoundsValue)
                .append("DateEntry", entryDate)
                .append("DateExit", expiryValue);

        gateway.insert(doc);
    }

    private boolean isExpiryRequired(String typeProduct) {
        if (typeProduct == null) {
            return false;
        }
        String t = typeProduct.trim().toUpperCase();
        return t.equals("COMIDA") || t.equals("MEDICINA");
    }

    private boolean isPoundsRequired(String unit) {
        if (unit == null) {
            return false;
        }
        return unit.trim().equalsIgnoreCase("Libras");
    }
}
