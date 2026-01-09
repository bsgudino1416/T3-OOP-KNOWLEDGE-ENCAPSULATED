package ec.edu.espe.petshopinventorycontrol.data;

public class PurchaseLogic {
    public double calculateStock(String saleUnit, String purchaseUnit, int quantity) {
        if (saleUnit == null || purchaseUnit == null) return quantity;
        if (saleUnit.equals("Libra")) {
            if (purchaseUnit.equals("Kilo")) return quantity * 2.20462;
            if (purchaseUnit.equals("Quintal")) return quantity * 100;
        } else if (saleUnit.equals("Kilo")) {
            if (purchaseUnit.equals("Libra")) return quantity * 0.453592;
            if (purchaseUnit.equals("Quintal")) return quantity * 45.3592;
        } else if (saleUnit.equals("Unidad") && purchaseUnit.equals("Docena")) {
            return quantity * 12;
        }
        return quantity;
    }

    public double calculatePriceWithProfit(double cost, double profitPercent) {
        return cost + (cost * profitPercent / 100.0);
    }

    public double calculateFinalPriceWithTax(double price) {
        return price * 1.15; 
    }
}