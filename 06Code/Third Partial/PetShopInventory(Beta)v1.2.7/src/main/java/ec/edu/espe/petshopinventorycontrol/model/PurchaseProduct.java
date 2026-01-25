package ec.edu.espe.petshopinventorycontrol.model;

public class PurchaseProduct {

    private String productId;
    private String productName;
    private String productType;
    private String animal;
    private String quantity;      
    private double totalCost;

    public PurchaseProduct(
            String productId,
            String productName,
            String productType,
            String animal,
            String quantity,
            double totalCost) {

        this.productId = productId;
        this.productName = productName;
        this.productType = productType;
        this.animal = animal;
        this.quantity = quantity;
        this.totalCost = totalCost;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductType() {
        return productType;
    }

    public String getAnimal() {
        return animal;
    }

    public String getQuantity() {
        return quantity;
    }

    public double getTotalCost() {
        return totalCost;
    }
}


