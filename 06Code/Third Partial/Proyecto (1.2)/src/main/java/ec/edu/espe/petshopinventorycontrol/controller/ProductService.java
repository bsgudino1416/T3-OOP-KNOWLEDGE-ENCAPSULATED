package ec.edu.espe.petshopinventorycontrol.controller;

import ec.edu.espe.petshopinventorycontrol.model.mongo.MongoProductGateway;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.regex.Pattern;
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
        return generateNextProductId(now, null, null);
    }

    public String buildProductIdPrefix(Date now, String typeProduct, String animalType) {
        String prefix = new SimpleDateFormat("ddMMyy").format(now);
        String typeCode = IdCodeMapper.typeCode(typeProduct);
        String animalCode = IdCodeMapper.animalCode(animalType);

        StringBuilder fullPrefix = new StringBuilder(prefix);
        if (typeCode != null) {
            fullPrefix.append("-").append(typeCode);
            if (animalCode != null) {
                fullPrefix.append("-").append(animalCode);
            }
        }
        return fullPrefix.toString();
    }

    public String generateNextProductId(Date now, String typeProduct, String animalType) {
        String prefixValue = buildProductIdPrefix(now, typeProduct, animalType);
        String lastId = gateway.findLastIdByPrefix(prefixValue);

        int next = 1;
        if (lastId != null && lastId.matches("^" + Pattern.quote(prefixValue) + "-\\d{3}$")) {
            next = Integer.parseInt(lastId.substring(lastId.length() - 3)) + 1;
        }
        return prefixValue + "-" + String.format("%03d", next);
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

    public Map<String, String> validateUpdateFields(
            String unit,
            Object quantityValue,
            String investmentText,
            String poundsText,
            String totalPoundsText
    ) {
        return validator.validateUpdateFields(unit, quantityValue, investmentText, poundsText, totalPoundsText);
    }

    public boolean existsByNameIgnoreCase(String name) {
        return gateway.findProductByNameIgnoreCase(name) != null;
    }

    public void updateExistingProductByName(
            String name,
            String unit,
            Number quantityValue,
            String investmentText,
            String poundsText,
            String totalPoundsText
    ) {
        Document existing = gateway.findProductByNameIgnoreCase(name);
        if (existing == null) {
            throw new IllegalStateException("Producto no encontrado para actualizar.");
        }

        double addQty = quantityValue == null ? 0 : quantityValue.doubleValue();
        double currentQty = valueOrZero(gateway.readQuantity(existing));
        double newQty = currentQty + addQty;

        double addInvestment = parseNumber(investmentText);
        double currentInvestment = valueOrZero(gateway.readInvestmentCost(existing));
        double newInvestment = currentInvestment + addInvestment;

        double addTotalPounds = parseNumber(totalPoundsText);
        double currentTotalPounds = valueOrZero(gateway.readTotalPounds(existing));
        double newTotalPounds = currentTotalPounds + addTotalPounds;

        Document updates = new Document()
                .append("Unit", unit)
                .append("Quantity", newQty)
                .append("InvestmentCost", format(newInvestment));

        if (isPoundsRequired(unit)) {
            updates.append("Pounds", poundsText)
                    .append("TotalPounds", format(newTotalPounds));
        } else {
            updates.append("Pounds", NOT_APPLIES)
                    .append("TotalPounds", NOT_APPLIES);
        }

        gateway.updateProductById(existing.get("_id"), updates);
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
                .append("Supplier", supplier)
                .append("NameProduct", name)
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

    private double parseNumber(String text) {
        try {
            if (text == null) {
                return 0;
            }
            return Double.parseDouble(text.trim());
        } catch (Exception e) {
            return 0;
        }
    }

    private double valueOrZero(Double value) {
        return value == null ? 0 : value;
    }

    private String format(double value) {
        return new java.text.DecimalFormat("0.00").format(value);
    }
}
