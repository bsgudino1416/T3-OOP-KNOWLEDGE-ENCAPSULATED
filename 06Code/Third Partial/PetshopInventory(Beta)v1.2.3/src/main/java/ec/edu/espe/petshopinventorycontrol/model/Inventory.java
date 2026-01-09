package ec.edu.espe.petshopinventorycontrol.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.util.*;

public class Inventory {

    private final List<Product> products = new ArrayList<>();
    private final Gson gson = new Gson();

    public void addProduct(Product product) {
        if (product != null) {
            products.add(product);
        }
    }

    @Deprecated
    public int getNextProductId() {
        int max = 0;
        for (Product p : products) {
            try {
                if (p.getId() == null) {
                    continue;
                }
                int num = Integer.parseInt(p.getId().replaceAll("[^0-9]", ""));
                if (num > max) {
                    max = num;
                }
            } catch (Exception ignored) {
            }
        }
        return max + 1;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void showInventory() {
        System.out.println("\n--- INVENTARIO ---");
        if (products.isEmpty()) {
            System.out.println("No hay productos en el inventario.");
            return;
        }
        for (Product p : products) {
            System.out.printf("ID: %s | %s | Animal: %s %s | Marca: %s | Precio: %.2f | Stock: %d%n",
                    safe(p.getId()), safe(p.getName()), safe(p.getAnimal()), safe(p.getSize()), safe(p.getBrand()), p.getPrice(), p.getStock());
        }
    }

    private String safe(String s) {
        return s == null ? "" : s;
    }

    public void updateStock(String id, int quantityChange) {
        Product target = products.stream().filter(p -> id != null && id.equals(p.getId())).findFirst().orElse(null);
        if (target == null) {
            System.out.println("Producto no encontrado: " + id);
            return;
        }
        int newStock = target.getStock() + quantityChange;
        if (newStock < 0) {
            System.out.println("No hay suficiente stock.");
            return;
        }
        target.setStock(newStock);
        System.out.println("Stock actualizado: " + target.getName() + " → " + newStock);
        if (newStock == 0) {
            System.out.println("Producto agotado.");
        }
        if (newStock < 4) {
            System.out.println("Producto con bajo stock.");
        }
    }

    public Product findProductByName(String name) {
        if (name == null) {
            return null;
        }
        return products.stream().filter(p -> p.getName() != null && p.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public List<Product> findProductsByName(String partial) {
        List<Product> results = new ArrayList<>();
        if (partial == null) {
            return results;
        }
        String low = partial.toLowerCase();
        for (Product p : products) {
            if (p.getName() != null && p.getName().toLowerCase().contains(low)) {
                results.add(p);
            }
        }
        return results;
    }

    public void sellProductInteractive(Scanner sc) {
        System.out.println("\n--- VENDER PRODUCTO ---");
        System.out.print("Ingrese el nombre del producto (se permiten coincidencias parciales): ");
        String q = sc.nextLine().trim().toLowerCase();
        Product match = products.stream().filter(p -> p.getName() != null && (p.getName().equalsIgnoreCase(q) || p.getName().toLowerCase().contains(q))).findFirst().orElse(null);
        if (match == null) {
            System.out.println("No se encontró el producto.");
            return;
        }
        System.out.println("Producto encontrado: " + match.getName() + " | Stock: " + match.getStock());
        System.out.print("Cantidad a vender: ");
        int qty;
        try {
            qty = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Cantidad inválida.");
            return;
        }
        if (qty <= 0) {
            System.out.println("La cantidad debe ser mayor que 0.");
            return;
        }
        if (qty > match.getStock()) {
            System.out.println("No hay suficiente stock.");
            return;
        }
        match.setStock(match.getStock() - qty);
        System.out.println("Venta realizada. Stock restante: " + match.getStock());
        if (match.getStock() < 4) {
            System.out.println("Producto con bajo stock.");
        }
    }

    public void saveToJson(String path) {
        try {
            File f = new File(path);
            f.getParentFile().mkdirs();
            try (Writer w = new FileWriter(f)) {
                gson.toJson(products, w);
            }
        } catch (Exception e) {
            System.out.println("Error al guardar products.json: " + e.getMessage());
        }
    }

    public void loadFromJson(String path) {
        File f = new File(path);
        if (!f.exists()) {
            return;
        }
        try (Reader r = new FileReader(f)) {
            List<Product> loaded = gson.fromJson(r, new TypeToken<List<Product>>() {
            }.getType());
            if (loaded != null) {
                products.clear();
                products.addAll(loaded);
            }
        } catch (Exception e) {
            System.out.println("Error al cargar products.json: " + e.getMessage());
        }
    }

    public void generateReport() {
        System.out.println("\n===== REPORTE DE INVENTARIO =====");
        if (products.isEmpty()) {
            System.out.println("No hay productos disponibles.");
            return;
        }
        for (Product p : products) {
            System.out.println("-----------------------------");
            System.out.println("ID: " + p.getId());
            System.out.println("Nombre: " + p.getName());
            System.out.println("Animal: " + safe(p.getAnimal()) + " " + safe(p.getSize()));
            System.out.println("Marca: " + safe(p.getBrand()));
            System.out.println("Precio: " + p.getPrice());
            System.out.println("Stock: " + p.getStock());
        }
        System.out.println("-----------------------------");
        System.out.println("Total de productos: " + products.size());
    }

    public void modifyInventoryByCategory(Scanner sc, String jsonPath) {
        if (products.isEmpty()) {
            System.out.println("No hay productos en el inventario.");
            return;
        }
        System.out.println("\n=== MODIFICAR INVENTARIO POR CATEGORÍA ===");
        System.out.print("Ingrese la categoría (COMIDA/FOOD, MEDICINA/MEDICINE, SNACK, ACCESORIOS/ACCESORIES): ");
        String raw = sc.nextLine().trim();
        if (raw.isEmpty()) {
            System.out.println("Categoría vacía. Cancelando.");
            return;
        }

        String key = raw.toUpperCase();
        String categoryEnglish = switch (key) {
            case "COMIDA", "FOOD" ->
                "FOOD";
            case "MEDICINA", "MEDICINE" ->
                "MEDICINE";
            case "SNACK" ->
                "SNACK";
            case "ACCESORIOS", "ACCESORIES" ->
                "ACCESORIES";
            default ->
                key;
        };

        List<Product> filtered = new ArrayList<>();
        for (Product p : products) {
            String pcat = p.getCategory();
            String pname = p.getName();
            boolean matches = (pcat != null && categoryEnglish.equalsIgnoreCase(pcat))
                    || (pname != null && (pname.toUpperCase().startsWith(categoryEnglish + " -") || pname.toUpperCase().startsWith(key + " -")));
            if (matches) {
                filtered.add(p);
            }
        }

        if (filtered.isEmpty()) {
            System.out.println("No existen productos con esa categoría.");
            return;
        }

        System.out.println("\nProductos encontrados:");
        filtered.forEach(p -> System.out.println("ID: " + p.getId() + " | " + p.getName() + " | Precio: " + p.getPrice() + " | Stock: " + p.getStock()));

        System.out.print("\nIngrese el ID del producto que desea modificar: ");
        String id = sc.nextLine().trim();
        if (id.isEmpty()) {
            System.out.println("ID vacío. Cancelando.");
            return;
        }

        Product target = filtered.stream().filter(p -> p.getId() != null && p.getId().equalsIgnoreCase(id)).findFirst().orElse(null);
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
                try {
                    double np = Double.parseDouble(sc.nextLine().trim());
                    target.setPrice(np);
                    System.out.println("Precio actualizado.");
                } catch (Exception e) {
                    System.out.println("Valor inválido.");
                }
                break;
            case "2":
                System.out.print("Nuevo stock: ");
                try {
                    int ns = Integer.parseInt(sc.nextLine().trim());
                    target.setStock(ns);
                    System.out.println("Stock actualizado.");
                } catch (Exception e) {
                    System.out.println("Valor inválido.");
                }
                break;
            default:
                System.out.println("Opción no válida.");
                return;
        }

        saveToJson(jsonPath);
    }
}
