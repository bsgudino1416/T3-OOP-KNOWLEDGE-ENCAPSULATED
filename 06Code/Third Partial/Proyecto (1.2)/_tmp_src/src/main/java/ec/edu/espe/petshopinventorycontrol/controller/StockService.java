package ec.edu.espe.petshopinventorycontrol.controller;

import ec.edu.espe.petshopinventorycontrol.model.mongo.MongoProductGateway;
import ec.edu.espe.petshopinventorycontrol.model.mongo.MongoStockGateway;
import org.bson.Document;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
        String prefix = new SimpleDateFormat("ddMMyy").format(now);
        String lastId = stockGateway.findLastIdByPrefix(prefix);

        int nextSeq = 1;
        if (lastId != null && lastId.startsWith(prefix + "-")) {
            String[] parts = lastId.split("-");
            if (parts.length == 2) {
                nextSeq = safeParseInt(parts[1]) + 1;
            }
        }
        return prefix + "-" + pad3(nextSeq);
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

    public ProductCostResult getCostForSelectionOrNull(String category, String name, String brand) {
        Document p = productGateway.findProductByAnimalNameBrand(category, name, brand);
        if (p == null) {
            return null;
        }

        String costText = p.getString("cost"); // ajusta si tu campo en Mongo se llama distinto
        if (costText == null) {
            Object cost = p.get("cost");
            costText = cost != null ? String.valueOf(cost) : null;
        }

        Double costValue = tryParseDouble(costText);
        if (costValue == null) {
            return null;
        }

        // Costo unitario: como en FrmStock tú tienes cmbCostUnit (textfield)
        // Aquí lo dejamos igual al costo base si no tienes otra lógica.
        return new ProductCostResult(costValue, costValue);
    }

    public GainResult calculateGain(double cost, String gainPercentText) {
        double percent = parsePercent(gainPercentText); // "10%" => 0.10
        double gainValue = cost * percent;
        double finalPrice = cost + gainValue;

        DecimalFormat df = new DecimalFormat("0.00");
        return new GainResult(df.format(finalPrice), df.format(gainValue));
    }

    public Map<String, String> validateFields(
            String idStock,
            String category,
            String name,
            String brand,
            String costText,
            String costUnitText,
            String gain,
            String calculatedGainText,
            String calculatedPercentText
    ) {
        return validator.validate(
                idStock, category, name, brand,
                costText, costUnitText, gain,
                calculatedGainText, calculatedPercentText
        );
    }

    public void saveStock(
            String idStock,
            String category,
            String name,
            String brand,
            String costText,
            String costUnitText,
            String gain,
            String calculatedGainText,
            String calculatedPercentText,
            Date now
    ) {
        Document doc = new Document()
                .append("idStock", idStock)
                .append("category", category)
                .append("name", name)
                .append("brand", brand)
                .append("cost", costText)
                .append("costUnit", costUnitText)
                .append("gainPercent", gain)
                .append("finalPrice", calculatedGainText)
                .append("gainValue", calculatedPercentText)
                .append("createdAt", now);

        stockGateway.insertStock(doc);
    }

    private int safeParseInt(String s) {
        try {
            return Integer.parseInt(s.trim());
        } catch (Exception e) {
            return 0;
        }
    }

    private String pad3(int n) {
        return String.format("%03d", n);
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
}
