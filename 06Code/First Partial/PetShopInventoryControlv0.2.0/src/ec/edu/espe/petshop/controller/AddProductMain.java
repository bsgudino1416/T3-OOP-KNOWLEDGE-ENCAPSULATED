package ec.edu.espe.petshop.controller;

import ec.edu.espe.petshop.model.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class AddProductMain {

    private static final String[] VALID_CATEGORIES = {"FOOD", "MEDICINE", "SNACK", "ACCESORIES"};
    private static final String[] VALID_ANIMALS = {"perro", "gato", "caballo", "conejo", "cerdo", "gallina", "vaca"};
    private static final String[] VALID_SIZES = {"pequeño", "grande"};

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Store store = new Store("Happy Paws");
        List<Product> addedProducts = new ArrayList<>();
        int option = 1;

        while (option == 1) {
            try {
                // ===== CATEGORÍA =====
                System.out.println("\n=== ADD PRODUCT MODULE ===");
                System.out.print("Enter category (FOOD, MEDICINE, SNACK, ACCESORIES): ");
                String category = sc.nextLine().trim().toUpperCase();

                if (!Arrays.asList(VALID_CATEGORIES).contains(category)) {
                    option = handleError(sc);
                    continue;
                }

                // ===== ANIMAL =====
                System.out.print("Food for? e.g: perro pequeño → ");
                String animalLine = sc.nextLine().trim().toLowerCase();

                String[] animalParts = animalLine.split(" ");
                if (animalParts.length != 2) {
                    option = handleError(sc);
                    continue;
                }

                String animal = animalParts[0];
                String size = animalParts[1];

                if (!Arrays.asList(VALID_ANIMALS).contains(animal) || !Arrays.asList(VALID_SIZES).contains(size)) {
                    option = handleError(sc);
                    continue;
                }

                // ===== PROVEEDOR =====
                System.out.print("Proveedor? e.g: DogChown → ");
                String supplierName = sc.nextLine().trim();

                if (supplierName.isEmpty()) {
                    option = handleError(sc);
                    continue;
                }

                // ===== GENERAR ID =====
                String idPrefix = generateIdPrefix(category, animal, size);
                int nextNumber = getNextIdNumber(addedProducts, idPrefix);
                String id = idPrefix + String.format("%02d", nextNumber);


                // ===== CREACIÓN =====
                Supplier supplier = new Supplier(nextNumber, supplierName, "N/A", "N/A");
                AnimalCategory cat = new AnimalCategory(nextNumber, category, animal + " " + size);
                Product product = new Product(id, category, animal + " " + size, supplierName);

                store.getInventory().addProduct(product);
                addedProducts.add(product);

                System.out.println("Product added successfully!");
                System.out.println("Product ID: " + id);
                System.out.println("Current inventory count: " + addedProducts.size());

                // ===== GUARDAR EN JSON =====
                saveProductsToJson(addedProducts);
                System.out.println("Products saved to products.json");

                // ===== MENÚ DE DECISIÓN =====
                System.out.println("\n1. Add another product");
                System.out.println("2. Return to main menu");
                System.out.println("3. Exit");
                option = sc.nextInt();
                sc.nextLine();

            } catch (Exception e) {
                System.out.println("Error: Invalid input. Please try again.");
                sc.nextLine();
                option = handleError(sc);
            }
        }

        System.out.println("? Returning to system...");
        sc.close();
    }

    // ==============================================
    // MÉTODOS AUXILIARES
    // ==============================================

    private static int handleError(Scanner sc) {
        System.out.println("⚠️ Error!! Invalid input.");
        System.out.println("1. Try again");
        System.out.println("2. Main menu");
        System.out.println("3. Exit");
        int opt = sc.nextInt();
        sc.nextLine();
        return opt;
    }

    private static String generateIdPrefix(String category, String animal, String size) {
        String prefix = "";
        if (category.equals("FOOD")) {
            switch (animal) {
                case "perro" -> prefix = "CP";  // Comida perro
                case "gato" -> prefix = "CG";
                case "gallina" -> prefix = "CGLL";
                case "caballo" -> prefix = "CCB";
                case "conejo" -> prefix = "CCJ";
                case "cerdo" -> prefix = "CCD";
                case "vaca" -> prefix = "CVC";
                default -> prefix = "CXX";
            }
        } else {
            prefix = category.substring(0, 2);
        }

        // Tamaño: pequeño (P) o grande (G)
        prefix += size.equals("pequeño") ? "P" : "G";
        return prefix;
    }

    private static int getNextIdNumber(List<Product> products, String prefix) {
    int max = 0;
    for (Product p : products) {
        String pid = p.getId(); // ahora sí usamos el ID real
        if (pid.startsWith(prefix)) {
            String numPart = pid.replaceAll("\\D+", ""); // solo dígitos
            if (!numPart.isEmpty()) {
                int num = Integer.parseInt(numPart);
                if (num > max) max = num;
            }
        }
    }
    return max + 1;
}


    private static void saveProductsToJson(List<Product> products) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter("products.json")) {
            gson.toJson(products, writer);
        } catch (IOException e) {
            System.out.println("Error saving products to JSON: " + e.getMessage());
        }
    }
}

