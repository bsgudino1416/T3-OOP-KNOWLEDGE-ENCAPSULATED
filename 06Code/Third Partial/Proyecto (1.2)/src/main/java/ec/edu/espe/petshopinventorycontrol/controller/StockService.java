package ec.edu.espe.petshopinventorycontrol.controller;

import ec.edu.espe.petshopinventorycontrol.model.mongo.MongoProductGateway;
import ec.edu.espe.petshopinventorycontrol.model.mongo.MongoStockGateway;
import org.bson.Document;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class StockService {

    private final MongoStockGateway stockGateway;
    private final MongoProductGateway productGateway;
    private final StockValidator validator;

    public StockService(MongoStockGateway stockGateway, MongoProductGateway productGateway, StockValidator validator) {
        this.stockGateway = stockGateway;
        this.productGateway = productGateway;
        this.validator = validator;
    }

    public String generateNextStockId(Date now) {
        return generateNextStockId(now, null);
    }

    public String buildStockIdPrefix(Date now, String category) {
        String prefix = new SimpleDateFormat("ddMMyy").format(now);
        String categoryCode = IdCodeMapper.animalCode(category);

        StringBuilder fullPrefix = new StringBuilder(prefix);
        if (categoryCode != null) {
            fullPrefix.append("-").append(categoryCode);
        }
        return fullPrefix.toString();
    }

    public String generateNextStockId(Date now, String category) {
        String prefixValue = buildStockIdPrefix(now, category);
        String lastId = stockGateway.findLastIdByPrefix(prefixValue);

        int nextSeq = 1;
        if (lastId != null && lastId.matches("^" + Pattern.quote(prefixValue) + "-\\d{3}$")) {
            nextSeq = Integer.parseInt(lastId.substring(lastId.length() - 3)) + 1;
        }
        return prefixValue + "-" + pad3(nextSeq);
    }

    public List<String> getCategoriesFromProducts() {
        return productGateway.findDistinctAnimalTypesUsed();
    }

    public List<String> getProductNamesByCategory(String category) {
        return productGateway.findDistinctNamesByAnimal(category);
    }

    public List<String> getBrandsByCategoryAndName(String category, String name) {
        return productGateway.findDistinctBrandsByAnimalAndName(category, name);
    }

//    public ProductCostResult getCostForSelectionOrNull(String category, String name, String brand) {
//        Document p = productGateway.findProductByAnimalNameBrand(category, name, brand);
//        if (p == null) {
//            return null;
//        }
//
//        String costText = p.getString("cost"); // ajusta si tu campo en Mongo se llama distinto
//        if (costText == null) {
//            Object cost = p.get("cost");
//            costText = cost != null ? String.valueOf(cost) : null;
//        }
//
//        Double costValue = tryParseDouble(costText);
//        if (costValue == null) {
//            return null;
//        }
//
//        // Costo unitario: como en FrmStock tú tienes cmbCostUnit (textfield)
//        // Aquí lo dejamos igual al costo base si no tienes otra lógica.
//        return new ProductCostResult(costValue, costValue);
//    }
    public ProductCostResult getCostForSelectionOrNull(String category, String name, String brand) {
        Document p = productGateway.findProductByAnimalNameBrand(category, name, brand);
        if (p == null) {
            return null;
        }
        String costText = productGateway.readCost(p);
        Double costValue = tryParseDouble(costText);
        if (costValue == null) {
            return null;
        }
        double unitCost = costValue;
        return new ProductCostResult(costValue, unitCost);
    }

    public GainResult calculateGain(double cost, String gainPercentText) {
        double percent = parsePercent(gainPercentText); // "10%" => 0.10
        double gainValue = cost * percent;
        double finalPrice = cost + gainValue;

        DecimalFormat df = new DecimalFormat("0.00");
        return new GainResult(df.format(finalPrice), df.format(gainValue));
    }

    public PricingResult calculatePricing(String costText, String unitEntryText, String gainPercentText) {
        Double totalCost = tryParseDouble(costText);
        Double units = tryParseDouble(unitEntryText);
        if (totalCost == null || units == null || units <= 0) {
            return PricingResult.empty();
        }

        double unitCost = totalCost / units;
        Double percent = parsePercentNullable(gainPercentText);
        if (percent == null) {
            return PricingResult.withUnitCost(format(unitCost));
        }

        double priceUnit = unitCost * (1.0 + percent);
        double gainValue = priceUnit - unitCost;
        return new PricingResult(format(unitCost), format(priceUnit), format(gainValue));
    }

    public Map<String, String> validateFields(
            String idStock,
            String category,
            String name,
            String brand,
            String costText,
            String unitEntryText,
            String gain,
            String calculatedGainText,
            String calculatedPercentText
    ) {
        return validator.validateFields(
                idStock, category, name, brand,
                costText, unitEntryText, gain,
                calculatedGainText, calculatedPercentText
        );
    }

    public void saveStock(
            String idStock,
            String category,
            String name,
            String brand,
            String costText,
            String unitCostText,
            String unitEntryText,
            String gain,
            String calculatedGainText,
            String calculatedPercentText,
            Date now
    ) {
        double unitsToProcess = parsePositiveNumber(unitEntryText);
        Document productDoc = productGateway.findProductByAnimalNameBrand(category, name, brand);
        if (productDoc == null) {
            throw new IllegalStateException("Producto no encontrado para actualizar stock.");
        }

        String unit = productGateway.readUnit(productDoc);
        if (isPoundsUnit(unit)) {
            Double availablePounds = productGateway.readTotalPounds(productDoc);
            if (availablePounds == null) {
                throw new IllegalStateException("Total de libras no registrado para este producto.");
            }
            if (unitsToProcess > availablePounds) {
                throw new IllegalStateException("No hay libras disponibles. Disponible: " + format(availablePounds));
            }
            double remainingPounds = availablePounds - unitsToProcess;
            productGateway.updateProductById(
                    productDoc.get("_id"),
                    new Document("TotalPounds", format(remainingPounds))
            );
        } else {
            Double available = productGateway.readQuantity(productDoc);
            if (available == null) {
                throw new IllegalStateException("Cantidad disponible no registrada para este producto.");
            }
            if (unitsToProcess > available) {
                throw new IllegalStateException("No hay cantidad disponible. Disponible: " + format(available));
            }

            double remaining = available - unitsToProcess;
            productGateway.updateProductById(productDoc.get("_id"), new Document("Quantity", remaining));
        }

        Document doc = new Document()
                .append("idStock", idStock)
                .append("category", category)
                .append("name", name)
                .append("brand", brand)
                .append("cost", costText)
//                .append("costUnit", costUnitText)
                .append("unitEntry", unitEntryText)
                .append("gainPercent", gain)
                .append("finalPrice", calculatedGainText)
                .append("gainValue", calculatedPercentText)
                .append("createdAt", now);

        stockGateway.insertStock(doc);
    }

    private String pad3(int n) {
        return String.format("%03d", n);
    }

    private double parsePositiveNumber(String text) {
        Double value = tryParseDouble(text);
        if (value == null || value <= 0) {
            throw new IllegalArgumentException("Unidades inválidas.");
        }
        return value;
    }

    private double parsePercent(String text) {
        if (text == null) {
            return 0;
        }
        String t = text.trim().replace("%", "");
        Double v = tryParseDouble(t);
        if (v == null) {
            return 0;
        }
        return v / 100.0;
    }

    private Double parsePercentNullable(String text) {
        if (text == null) {
            return null;
        }
        String trimmed = text.trim();
        if (trimmed.isEmpty() || trimmed.equals("%")) {
            return null;
        }
        String t = trimmed.replace("%", "");
        Double v = tryParseDouble(t);
        if (v == null) {
            return null;
        }
        return v / 100.0;
    }

    private String format(double value) {
        return new DecimalFormat("0.00").format(value);
    }

    private Double tryParseDouble(String s) {
        try {
            if (s == null) {
                return null;
            }
            return Double.parseDouble(s.trim());
        } catch (Exception e) {
            return null;
        }
    }

    private boolean isPoundsUnit(String unit) {
        return unit != null && unit.trim().equalsIgnoreCase("Libras");
    }

    public static final class ProductCostResult {

        public final double cost;
        public final double unitCost;

        public ProductCostResult(double cost, double unitCost) {
            this.cost = cost;
            this.unitCost = unitCost;
        }
    }

    public static final class GainResult {

        public final String finalPrice;
        public final String gainValue;

        public GainResult(String finalPrice, String gainValue) {
            this.finalPrice = finalPrice;
            this.gainValue = gainValue;
        }
    }

    public static final class PricingResult {

        public final String unitCost;
        public final String priceUnit;
        public final String gainValue;

        public PricingResult(String unitCost, String priceUnit, String gainValue) {
            this.unitCost = unitCost;
            this.priceUnit = priceUnit;
            this.gainValue = gainValue;
        }

        public static PricingResult empty() {
            return new PricingResult("", "", "");
        }

        public static PricingResult withUnitCost(String unitCost) {
            return new PricingResult(unitCost, "", "");
        }
    }
}
