package ec.edu.espe.petshop.model;

public class Product {

    // ======================
    // ATRIBUTOS COMPLETOS
    // ======================
    private String id;         // CPG01, CPP01, MCG01, etc.
    private String name;       // Ej: FOOD - perro grande - DogChown
    private double price;
    private int stock;

    private String category;   // FOOD, MEDICINE, SNACK, ACCESORIES
    private String animal;     // perro, gato, caballo...
    private String size;       // grande, pequeño
    private String brand;      // DogChown, Purina, etc.

    // ======================
    // CONSTRUCTOR COMPLETO
    // ======================
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

    // ======================
    // GETTERS Y SETTERS
    // ======================
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

    // ======================
    // TO STRING
    // ======================
    @Override
    public String toString() {
        return "ID: " + id +
               " | " + name +
               " | Category: " + category +
               " | Animal: " + animal + " " + size +
               " | Brand: " + brand +
               " | $" + price +
               " | Stock: " + stock;
    }
}

