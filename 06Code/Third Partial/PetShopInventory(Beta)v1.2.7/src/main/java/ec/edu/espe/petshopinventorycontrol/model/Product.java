//package ec.edu.espe.petshopinventorycontrol.model;
//
//public class Product {
//
//    private String id;
//    private String name;
//    private double price;
//    private int stock;
//
//    private String category;
//    private String animal;
//    private String size;
//    private String brand;
//
//    public Product() {
//    }
//
//    public Product(String id, String name, double price, int stock,
//            String category, String animal, String size, String brand) {
//        this.id = id;
//        this.name = name;
//        this.price = price;
//        this.stock = stock;
//        this.category = category;
//        this.animal = animal;
//        this.size = size;
//        this.brand = brand;
//    }
//
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public double getPrice() {
//        return price;
//    }
//
//    public void setPrice(double price) {
//        this.price = price;
//    }
//
//    public int getStock() {
//        return stock;
//    }
//
//    public void setStock(int stock) {
//        this.stock = stock;
//    }
//
//    public String getCategory() {
//        return category;
//    }
//
//    public void setCategory(String category) {
//        this.category = category;
//    }
//
//    public String getAnimal() {
//        return animal;
//    }
//
//    public void setAnimal(String animal) {
//        this.animal = animal;
//    }
//
//    public String getSize() {
//        return size;
//    }
//
//    public void setSize(String size) {
//        this.size = size;
//    }
//
//    public String getBrand() {
//        return brand;
//    }
//
//    public void setBrand(String brand) {
//        this.brand = brand;
//    }
//
//    @Override
//    public String toString() {
//        return String.format("ID: %s | %s | Animal: %s %s | Marca: %s | Precio: %.2f | Stock: %d",
//                id, name, animal, size, brand, price, stock);
//    }
//}
///////////////////////////////////////////////////////
//package ec.edu.espe.petshopinventorycontrol.model;
//
//public class Product {
//
//    private String id;
//    private String name;
//    private String category;
//    private String animal;
//    private String brand;
//
//    // Cantidad comprada (ej: "3 quintales", "10 unidades")
//    private String purchaseQuantity;
//
//    // Total pagado por esa fila
//    private double totalCost;
//
//    public Product() {
//    }
//
//    public Product(String id, String name, String category,
//                   String animal, String brand,
//                   String purchaseQuantity, double totalCost) {
//
//        this.id = id;
//        this.name = name;
//        this.category = category;
//        this.animal = animal;
//        this.brand = brand;
//        this.purchaseQuantity = purchaseQuantity;
//        this.totalCost = totalCost;
//    }
//
//    // ===== GETTERS =====
//
//    public String getId() {
//        return id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public String getCategory() {
//        return category;
//    }
//
//    public String getAnimal() {
//        return animal;
//    }
//
//    public String getBrand() {
//        return brand;
//    }
//
//    public String getPurchaseQuantity() {
//        return purchaseQuantity;
//    }
//
//    public double getTotalCost() {
//        return totalCost;
//    }
//
//    // ===== SETTERS =====
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public void setCategory(String category) {
//        this.category = category;
//    }
//
//    public void setAnimal(String animal) {
//        this.animal = animal;
//    }
//
//    public void setBrand(String brand) {
//        this.brand = brand;
//    }
//
//    public void setPurchaseQuantity(String purchaseQuantity) {
//        this.purchaseQuantity = purchaseQuantity;
//    }
//
//    public void setTotalCost(double totalCost) {
//        this.totalCost = totalCost;
//    }
//
//    @Override
//    public String toString() {
//        return String.format(
//            "ID: %s | %s | %s | Animal: %s | Marca: %s | Cantidad: %s | Total: %.2f",
//            id, name, category, animal, brand, purchaseQuantity, totalCost
//        );
//    }
//}
///////////////////////

package ec.edu.espe.petshopinventorycontrol.model;

public class Product {

    private String id;
    private String name;
    private double price;
    private int stock;

    private String category;
    private String animal;
    private String size;
    private String brand;

    public Product() {
    }

    public Product(String id, String name, double price, int stock,
                   String category, String animal, String size, String brand) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.category = category;
        this.animal = animal;
        this.size = size;
        this.brand = brand;
    }

    // ===== GETTERS =====
    public String getId() { return id; }

    public String getName() { return name; }

    public double getPrice() { return price; }

    public int getStock() { return stock; }

    public String getCategory() { return category; }

    public String getAnimal() { return animal; }

    public String getSize() { return size; }

    public String getBrand() { return brand; }

    // ===== SETTERS =====
    public void setId(String id) { this.id = id; }

    public void setName(String name) { this.name = name; }

    public void setPrice(double price) { this.price = price; }

    public void setStock(int stock) { this.stock = stock; }

    public void setCategory(String category) { this.category = category; }

    public void setAnimal(String animal) { this.animal = animal; }

    public void setSize(String size) { this.size = size; }

    public void setBrand(String brand) { this.brand = brand; }

    @Override
    public String toString() {
        return String.format(
            "ID: %s | %s | Animal: %s | Size: %s | Brand: %s | Price: %.2f | Stock: %d",
            id, name, animal, size, brand, price, stock
        );
    }
}
