package ec.edu.espe.petshopinventorycontrol.model;

public class StockRecord {

    private String id;
    private String category;
    private String name;
    private String brand;
    private String cost;
    private String unitEntry;
    private String gainPercent;
    private String finalPrice;
    private String gainValue;
    private String createdAt;

    public StockRecord() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getUnitEntry() {
        return unitEntry;
    }

    public void setUnitEntry(String unitEntry) {
        this.unitEntry = unitEntry;
    }

    public String getGainPercent() {
        return gainPercent;
    }

    public void setGainPercent(String gainPercent) {
        this.gainPercent = gainPercent;
    }

    public String getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(String finalPrice) {
        this.finalPrice = finalPrice;
    }

    public String getGainValue() {
        return gainValue;
    }

    public void setGainValue(String gainValue) {
        this.gainValue = gainValue;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
