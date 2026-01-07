
package ec.edu.espe.petshop.model;

import java.util.*;
import java.io.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Inventory actualizado:
 * - conserva addProduct(Scanner), addProduct(Product), getNextProductId(), showInventory()
 * - añade:
 *   - updateStock(int id, int quantityChange)
 *   - sellProductInteractive(Scanner sc)
 *   - findProductByName(String name)
 *   - findProductsByName(String partial)
 *   - searchProductMenu(Scanner sc)
 *   - saveToJson(String path) / loadFromJson(String path)
 *   - modifyInventoryByCategory(Scanner sc, String jsonPath)
 *
 * Mantiene compatibilidad con tu Main original.
 */
public class Inventory {

    private List<Product> products;

    public Inventory() {
        this.products = new ArrayList<>();
    }

    // ---------- Métodos existentes (no borrados) ----------
    public void addProduct(Product product) {
        products.add(product);
    }

    // método que usabas desde Main originalmente (por Scanner)
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
                System.out.println("ID: " + p.getId() +
                        " | Name: " + p.getName() +
                        " | Price: $" + p.getPrice() +
                        " | Stock: " + p.getStock());
            }
        }
    }

    public int getNextProductId() {
        int max = 0;
        for (Product p : products) {
            if (p.getId() > max) max = p.getId();
        }
        return max + 1;
    }

    public List<Product> getProducts() {
        return products;
    }

    // ---------- Nuevos métodos añadidos ----------

    /**
     * updateStock: ajusta el stock de un producto por ID (puede sumar o restar).
     * quantityChange: positivo para agregar, negativo para reducir (vender).
     */
    public void updateStock(int id, int quantityChange) {
        for (Product p : products) {
            if (p.getId() == id) {
                int newStock = p.getStock() + quantityChange;
                if (newStock < 0) {
                    System.out.println("No hay suficiente stock para realizar la operación.");
                    return;
                }
                p.setStock(newStock);
                System.out.println("Stock actualizado: " + p.getName() + " → " + newStock);

                if (newStock == 0) {
                    System.out.println("No hay el producto mencionado.");
                } else if (newStock < 4) {
                    System.out.println("producto con bajo stock");
                }
                return;
            }
        }
        System.out.println("Producto no encontrado con ID: " + id);
    }

    /**
     * Buscar producto por nombre exacto (case-insensitive)
     */
    public Product findProductByName(String name) {
        if (name == null) return null;
        for (Product p : products) {
            if (p.getName().equalsIgnoreCase(name)) {
                return p;
            }
        }
        return null;
    }

    /**
     * Buscar productos por nombre parcial (case-insensitive)
     */
    public List<Product> findProductsByName(String partial) {
        List<Product> results = new ArrayList<>();
        if (partial == null || partial.trim().isEmpty()) return results;
        String low = partial.toLowerCase();
        for (Product p : products) {
            if (p.getName().toLowerCase().contains(low)) results.add(p);
        }
        return results;
    }

    /**
     * Interfaz simple de búsqueda (para menú de empleado)
     */
    public void searchProductMenu(Scanner sc) {
        System.out.print("\nIngrese el nombre o parte del nombre del producto: ");
        String query = sc.nextLine().trim().toLowerCase();
        boolean found = false;

        System.out.println("\n=== RESULTADOS DE BÚSQUEDA ===");
        for (Product p : products) {
            if (p.getName().toLowerCase().contains(query)) {
                System.out.println("ID: " + p.getId() + " | " + p.getName() + " | Price: " + p.getPrice() + " | Stock: " + p.getStock());
                if (p.getStock() < 4) System.out.println("  ⚠ producto con bajo stock");
                found = true;
            }
        }
        if (!found) {
            System.out.println("No se encontraron productos que coincidan con la búsqueda.");
        }
    }

    /**
     * Vender producto interactivo: busca por nombre (exacto o parcial), muestra stock,
     * valida cantidad y decrementa si procede. Muestra mensajes solicitados.
     */
    public void sellProductInteractive(Scanner sc) {
        System.out.println("\n--- Sell Product ---");
        System.out.print("Enter product name to sell (partial allowed): ");
        String qName = sc.nextLine().trim().toLowerCase();

        // buscar coincidencia: primero exacta, luego parcial
        Product match = null;
        for (Product p : products) {
            if (p.getName().equalsIgnoreCase(qName)) { match = p; break; }
        }
        if (match == null) {
            for (Product p : products) {
                if (p.getName().toLowerCase().contains(qName)) { match = p; break; }
            }
        }

        if (match == null) {
            System.out.println("no hay el producto mencionado");
            return;
        }

        System.out.println("Product found: " + match.getName());
        System.out.println("Current stock: " + match.getStock());
        System.out.print("Quantity to sell: ");
        int qty;
        try {
            qty = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid quantity.");
            return;
        }

        if (qty <= 0) {
            System.out.println("Quantity must be greater than 0.");
            return;
        }

        if (qty > match.getStock()) {
            System.out.println("No hay suficiente stock. Available: " + match.getStock());
            return;
        }

        // decrementar stock
        match.setStock(match.getStock() - qty);
        System.out.println("Venta realizada. Stock restante: " + match.getStock());

        if (match.getStock() < 4) {
            System.out.println("producto con bajo stock");
        }
    }

    // ---------- JSON save / load ----------
    public void saveToJson(String path) {
        try (FileWriter writer = new FileWriter(path)) {
            Gson gson = new Gson();
            gson.toJson(this.products, writer);
            System.out.println("Products saved to " + path);
        } catch (IOException e) {
            System.out.println("Error saving products.json: " + e.getMessage());
        }
    }

    public void loadFromJson(String path) {
        File f = new File(path);
        if (!f.exists()) {
            // no hay json, no es error
            return;
        }
        try (Reader reader = new FileReader(f)) {
            Gson gson = new Gson();
            List<Product> loaded = gson.fromJson(reader, new TypeToken<List<Product>>(){}.getType());
            if (loaded != null) {
                this.products = loaded;
                System.out.println("Loaded " + loaded.size() + " product(s) from " + path);
            }
        } catch (IOException e) {
            System.out.println("Error loading products.json: " + e.getMessage());
        }
    }

    /**
     * Edit inventory by category reading JSON (category assumed as prefix in name "CATEGORY - ...")
     * This method reads JSON from jsonPath, filters by category, allows edit price/stock and saves back.
     */
    public void modifyInventoryByCategory(Scanner sc, String jsonPath) {
        // intentar cargar (no sobreescribe si ya está cargado)
        loadFromJson(jsonPath);

        System.out.print("Enter category to edit (e.g. FOOD, MEDICINE, SNACK, ACCESSORIES): ");
        String category = sc.nextLine().trim().toUpperCase();
        List<Product> byCat = findProductsByCategoryPrefix(category);

        if (byCat.isEmpty()) {
            System.out.println("No products found for category: " + category);
            return;
        }

        System.out.println("\nProducts in category " + category + ":");
        for (Product p : byCat) {
            System.out.println("ID: " + p.getId() + " | " + p.getName() + " | Price: " + p.getPrice() + " | Stock: " + p.getStock());
        }

        System.out.print("\nEnter product ID to edit (or 0 to cancel): ");
        int id;
        try {
            id = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID.");
            return;
        }
        if (id == 0) return;

        Product selected = null;
        for (Product p : byCat) {
            if (p.getId() == id) { selected = p; break; }
        }
        if (selected == null) {
            System.out.println("Product ID not in category list.");
            return;
        }

        System.out.println("Editing: " + selected.getName());
        System.out.print("New price (enter to skip): ");
        String priceLine = sc.nextLine().trim();
        if (!priceLine.isEmpty()) {
            try {
                double newPrice = Double.parseDouble(priceLine);
                selected.setPrice(newPrice);
            } catch (NumberFormatException e) {
                System.out.println("Invalid price — keeping old value.");
            }
        }

        System.out.print("New stock (enter to skip): ");
        String stockLine = sc.nextLine().trim();
        if (!stockLine.isEmpty()) {
            try {
                int newStock = Integer.parseInt(stockLine);
                selected.setStock(newStock);
            } catch (NumberFormatException e) {
                System.out.println("Invalid stock — keeping old value.");
            }
        }

        // Save back to JSON
        saveToJson(jsonPath);
        System.out.println("Product updated: ID " + selected.getId() + " | Price: " + selected.getPrice() + " | Stock: " + selected.getStock());
    }

    // Helper to find by category prefix in name (CATEGORY - ...)
    private List<Product> findProductsByCategoryPrefix(String category) {
        List<Product> results = new ArrayList<>();
        if (category == null || category.isEmpty()) return results;
        String prefix = category.toUpperCase() + " -";
        for (Product p : products) {
            if (p.getName() != null && p.getName().toUpperCase().startsWith(prefix)) {
                results.add(p);
            }
        }
        return results;
    }
}
