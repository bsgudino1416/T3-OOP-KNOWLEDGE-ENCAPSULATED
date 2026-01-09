package ec.edu.espe.petshopinventorycontrol.utils;

public class PriceCalculationUtil {

    private PriceCalculationUtil() {}

    public static double calculateSuggestedPrice(
            double costPerUnit,
            double profitPercent
    ) {
        return costPerUnit + (costPerUnit * profitPercent / 100.0);
    }

    public static double calculateFinalPriceWithIVA(double suggestedPrice) {
        return suggestedPrice * 1.15;
    }
}

