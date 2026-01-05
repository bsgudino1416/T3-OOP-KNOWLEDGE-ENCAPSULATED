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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAnimal() {
        return animal;
    }

    public void setAnimal(String animal) {
        this.animal = animal;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Override
    public String toString() {
        return String.format("ID: %s | %s | Animal: %s %s | Marca: %s | Precio: %.2f | Stock: %d",
                id, name, animal, size, brand, price, stock);
    }
}
