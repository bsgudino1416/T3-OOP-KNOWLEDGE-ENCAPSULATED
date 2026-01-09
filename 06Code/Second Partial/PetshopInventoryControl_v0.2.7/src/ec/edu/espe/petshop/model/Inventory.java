package ec.edu.espe.petshop.model;

import java.util.*;
import java.io.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Inventory {

    private List<Product> products;

    public Inventory() {
        this.products = new ArrayList<>();
    }

    // =====================================================
    // ADD PRODUCT FROM THE addProductDetailed() METHOD
    // =====================================================
    public void addProduct(Product product) {
        products.add(product);
    }

    @Deprecated
    public int getNextProductId() {
        int max = 0;
        for (Product p : products) {
            try {
                if (p.getId() == null) continue;
                int num = Integer.parseInt(p.getId().replaceAll("[^0-9]", ""));
                if (num > max) max = num;
            } catch (Exception ignore) { }
        }
        return max + 1;
    }

    // ===========================
    // Get product list
    // ===========================
    public List<Product> getProducts() {
        return products;
    }

    // =========================
    // SHOW INVENTORY (UPDATED)
    // =========================
    public void showInventory() {
        System.out.println("\n--- INVENTARIO ---");
        if (products.isEmpty()) {
            System.out.println("No hay productos en el inventario");
            return;
        }

        for (Product p : products) {
            System.out.println(
                "ID: " + p.getId() +
                " | " + p.getName() +
                " | Animal: " + safe(p.getAnimal()) + " " + safe(p.getSize()) +
                " | Marca: " + safe(p.getBrand()) +
                " | $" + p.getPrice() +
                " | Stock: " + p.getStock()
            );
        }
    }

    // helper para evitar nulls en prints
    private String safe(String s) {
        return s == null ? "" : s;
    }

    // ===============
    // UPDATE STOCK
    // ===============
    public void updateStock(String id, int quantityChange) {
        for (Product p : products) {
            if (p.getId() != null && p.getId().equals(id)) {
                int newStock = p.getStock() + quantityChange;
                if (newStock < 0) {
                    System.out.println("No hay suficiente stock.");
                    return;
                }
                p.setStock(newStock);
                System.out.println("Stock actualizado: " + p.getName() + " → " + newStock);
                if (newStock == 0) System.out.println("No hay el producto mencionado.");
                if (newStock < 4) System.out.println("producto con bajo stock");
                return;
            }
        }
        System.out.println("Producto no encontrado con ID: " + id);
    }

    // =====================================================
    // SEARCH EXACTLY NAME
    // =====================================================
    public Product findProductByName(String name) {
        if (name == null) return null;
        for (Product p : products) {
            if (p.getName() != null && p.getName().equalsIgnoreCase(name)) return p;
        }
        return null;
    }

    // ===========================
    // SEARCH PARTIAL WITH NAME
    // ===========================
    public List<Product> findProductsByName(String partial) {
        List<Product> results = new ArrayList<>();
        if (partial == null) return results;

        String low = partial.toLowerCase();
        for (Product p : products) {
            if (p.getName() != null && p.getName().toLowerCase().contains(low)) results.add(p);
        }
        return results;
    }

    // ==================
    // MENÚ DE BÚSQUEDA
    // ==================
    public void searchProductMenu(Scanner sc) {
        System.out.print("\nIngrese el nombre o parte del nombre del producto: ");
        String query = sc.nextLine().trim().toLowerCase();

        boolean found = false;
        System.out.println("\n=== RESULTADOS ===");

        for (Product p : products) {
            if (p.getName() != null && p.getName().toLowerCase().contains(query)) {
                System.out.println("ID: " + p.getId() + " | " + p.getName() +
                        " | Precio: " + p.getPrice() + " | Stock: " + p.getStock());
                if (p.getStock() < 4) System.out.println(" producto con bajo stock");
                found = true;
            }
        }

        if (!found) System.out.println("No se encontraron productos.");
    }

    // =============
    // SELL PRODUCT
    // =============
    public void sellProductInteractive(Scanner sc) {
        System.out.println("\n--- Vender Producto ---");
        System.out.print("Ingrese el nombre del producto (se permiten coincidencias parciales): ");
        String qName = sc.nextLine().trim().toLowerCase();

        Product match = null;

        for (Product p : products) {
            if (p.getName() != null && p.getName().equalsIgnoreCase(qName)) {
                match = p;
                break;
            }
        }
        if (match == null) {
            for (Product p : products) {
                if (p.getName() != null && p.getName().toLowerCase().contains(qName)) {
                    match = p;
                    break;
                }
            }
        }

        if (match == null) {
            System.out.println("no hay el producto mencionado");
            return;
        }

        System.out.println("Producto encontrado: " + match.getName());
        System.out.println("Stock: " + match.getStock());

        System.out.print("Cantidad a vender ");
        int qty;
        try { qty = Integer.parseInt(sc.nextLine().trim()); }
        catch (NumberFormatException e) {
            System.out.println("Cantidad no válida.");
            return;
        }

        if (qty <= 0) {
            System.out.println("La cantidad debe ser > 0");
            return;
        }
        if (qty > match.getStock()) {
            System.out.println("No hay suficiente stock.");
            return;
        }

        match.setStock(match.getStock() - qty);
        System.out.println("Venta realizada. Stock restante: " + match.getStock());

        if (match.getStock() < 4) System.out.println("producto con bajo stock");
    }

    // ==================
    // JSON SAVE / LOAD
    // ==================
    public void saveToJson(String path) {
        try (FileWriter writer = new FileWriter(path)) {
            new Gson().toJson(products, writer);
            // mensaje de guardado suprimido intencionalmente
        } catch (Exception e) {
            System.out.println("Error al guardar el JSON: " + e.getMessage());
        }
    }

    public void loadFromJson(String path) {
        File f = new File(path);
        if (!f.exists()) return;

        try (Reader reader = new FileReader(f)) {
            List<Product> loaded = new Gson().fromJson(reader, new TypeToken<List<Product>>(){}.getType());
            if (loaded != null) products = loaded;
        } catch (Exception e) {
            System.out.println("Error al cargar el JSON: " + e.getMessage());
        }
    }

    // =====================================================
    // REPORT (actualizado)
    // =====================================================
    public void generateReport() {
        System.out.println("\n===== REPORT DE INVENTARIO =====");

        if (products.isEmpty()) {
            System.out.println("No hay productos disponibles");
            return;
        }

        for (Product p : products) {
            System.out.println("-----------------------------");
            System.out.println("ID: " + p.getId());
            System.out.println("Nombre: " + p.getName());
            System.out.println("Animal: " + safe(p.getAnimal()) + " " + safe(p.getSize()));
            System.out.println("Marca: " + safe(p.getBrand()));
            System.out.println("Precio: $" + p.getPrice());
            System.out.println("Stock: " + p.getStock());
        }

        System.out.println("-----------------------------");
        System.out.println("Total products: " + products.size());
    }

    
    //Acepta entradas en español (COMIDA, MEDICINA, ACCESORIOS) y las mapea a su equivalente en inglés.
    
    public void modifyInventoryByCategory(Scanner sc, String jsonPath) {
        if (products.isEmpty()) {
            System.out.println("No hay productos en el inventario.");
            return;
        }

        System.out.println("\n=== MODIFICAR INVENTARIO POR CATEGORÍA ===");
        System.out.print("Ingrese la categoría (FOOD/COMIDA, MEDICINE/MEDICINA, SNACK, ACCESORIES/ACCESORIOS): ");
        String rawInput = sc.nextLine().trim();
        if (rawInput.isEmpty()) {
            System.out.println("Categoría vacía. Cancelando.");
            return;
        }

        // normalizamos y mapeamos (acepta español o inglés)
        String inputUpper = rawInput.toUpperCase();
        String categoryEnglish = mapCategoryToEnglish(inputUpper);
        String categorySpanish = mapEnglishToSpanish(categoryEnglish);

        List<Product> filtered = new ArrayList<>();
        for (Product p : products) {
            String pcat = p.getCategory();
            String pname = p.getName();

            boolean matchesCategory = false;

            if (pcat != null && categoryEnglish.equalsIgnoreCase(pcat)) {
                matchesCategory = true;
            } else if (pname != null) {
                String upName = pname.toUpperCase();
                if (upName.startsWith(categoryEnglish + " -") || upName.startsWith(categorySpanish + " -")) {
                    matchesCategory = true;
                }
            }

            if (matchesCategory) filtered.add(p);
        }

        if (filtered.isEmpty()) {
            System.out.println("No existen productos con esa categoría.");
            return;
        }

        System.out.println("\nProductos encontrados:");
        for (Product p : filtered) {
            System.out.println("ID: " + p.getId() + " | " + p.getName() +
                    " | $" + p.getPrice() + " | Stock: " + p.getStock());
        }

        System.out.print("\nIngrese el ID del producto que desea modificar: ");
        String targetId = sc.nextLine().trim();
        if (targetId.isEmpty()) {
            System.out.println("ID vacío. Cancelando.");
            return;
        }

        Product target = null;
        for (Product p : filtered) {
            if (p.getId() != null && p.getId().equalsIgnoreCase(targetId)) {
                target = p;
                break;
            }
        }

        if (target == null) {
            System.out.println("ID no encontrado en esa categoría.");
            return;
        }

        System.out.println("\n¿Qué desea modificar?");
        System.out.println("1. Precio");
        System.out.println("2. Stock");
        System.out.print("Opción: ");
        String opt = sc.nextLine().trim();

        switch (opt) {
            case "1":
                System.out.print("Nuevo precio: ");
                double newPrice;
                try {
                    newPrice = Double.parseDouble(sc.nextLine());
                    target.setPrice(newPrice);
                    System.out.println("Precio actualizado.");
                } catch (Exception e) {
                    System.out.println("Valor inválido.");
                    return;
                }
                break;

            case "2":
                System.out.print("Nuevo stock: ");
                int newStock;
                try {
                    newStock = Integer.parseInt(sc.nextLine());
                    target.setStock(newStock);
                    System.out.println("Stock actualizado.");
                } catch (Exception e) {
                    System.out.println("Valor inválido.");
                    return;
                }
                break;

            default:
                System.out.println("Opción no válida.");
                return;
        }

        saveToJson(jsonPath);
    }

    // map category input (english or spanish) to english canonical
    private String mapCategoryToEnglish(String inputUpper) {
        switch (inputUpper) {
            case "COMIDA": case "FOOD": return "FOOD";
            case "MEDICINA": case "MEDICINE": return "MEDICINE";
            case "SNACK": return "SNACK";
            case "ACCESORIOS": case "ACCESORIES": return "ACCESORIES";
            default: return inputUpper; // leave as-is if unknown
        }
    }

    // reverse map english to spanish for name-prefixed products
    private String mapEnglishToSpanish(String englishUpper) {
        switch (englishUpper) {
            case "FOOD": return "COMIDA";
            case "MEDICINE": return "MEDICINA";
            case "SNACK": return "SNACK";
            case "ACCESORIES": return "ACCESORIOS";
            default: return englishUpper;
        }
    }
}

