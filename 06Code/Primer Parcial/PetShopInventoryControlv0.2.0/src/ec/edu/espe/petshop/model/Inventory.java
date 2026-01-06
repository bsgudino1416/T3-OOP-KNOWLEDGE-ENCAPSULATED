package ec.edu.espe.petshop.model;

import java.util.*;

public class Inventory {
    private List<Product> products = new ArrayList<>();

    public void addProduct(Product p) {
        products.add(p);
    }

    public Product findProductById(String id) {
        for (Product p : products) {
            if (p.getId().equalsIgnoreCase(id)) {
                return p;
            }
        }
        return null;
    }

    public void showInventory() {
        System.out.println("\n--- INVENTORY ---");
        if (products.isEmpty()) {
            System.out.println("No products available.");
        } else {
            for (Product p : products) {
                System.out.println(p);
            }
        }
    }

    public List<Product> getProducts() {
        return products;
    }
}
