package ec.edu.espe.petshop.model;

public class Product {
    private int id;
    private String name;
    private double price;
    private int stock;
    int getPrice;

    public Product(int id, String name, double price, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "ID: " + id + " | " + name + " | $" + price + " | Stock: " + stock;
    }
}
