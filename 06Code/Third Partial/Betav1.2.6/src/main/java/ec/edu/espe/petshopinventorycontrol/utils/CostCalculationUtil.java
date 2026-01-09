package ec.edu.espe.petshopinventorycontrol.utils;

public class CostCalculationUtil {

    private CostCalculationUtil() {}

    public static double calculateCostPerPound(
            double totalCost,
            int quantity,
            String purchaseUnit
    ) {

        if (quantity <= 0) return 0;

        switch (purchaseUnit) {
            case "Libra":
                return totalCost / quantity;

            case "Kilo":
                return (totalCost / quantity) / 2.20462;

            case "Quintal":
                return (totalCost / quantity) / 100;

            default:
                return 0;
        }
    }

    public static double calculateCostPerKg(double costPerPound) {
        if (costPerPound <= 0) return 0;
        return costPerPound / 0.453592;
    }
}

