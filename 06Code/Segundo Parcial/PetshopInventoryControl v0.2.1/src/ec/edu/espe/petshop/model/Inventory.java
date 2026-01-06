package ec.edu.espe.petshop.model;

import java.util.*;

public class Inventory {
    private List<Product> products = new ArrayList<>();

    // Método para agregar producto desde teclado
    public void addProduct(Scanner sc) {
        System.out.println("\n--- Add Product ---");
        System.out.print("Enter product ID: ");
        int id = sc.nextInt();
        sc.nextLine(); // limpia buffer

        System.out.print("Enter product name: ");
        String name = sc.nextLine();

        System.out.print("Enter product price: ");
        double price = sc.nextDouble();

        System.out.print("Enter stock quantity: ");
        int stock = sc.nextInt();
        sc.nextLine();

        Product product = new Product(id, name, price, stock);
        products.add(product);
        System.out.println("Product added successfully!");
    }

    public void showInventory() {
        System.out.println("\n--- INVENTORY ---");
        if (products.isEmpty()) {
            System.out.println("No products in inventory.");
        } else {
            for (Product p : products) {
                System.out.println(p);
            }
        }
    }
}
