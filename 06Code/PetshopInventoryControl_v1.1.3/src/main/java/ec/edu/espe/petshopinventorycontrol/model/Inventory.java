package ec.edu.espe.petshopinventorycontrol.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ec.edu.espe.petshopinventorycontrol.utils.FileUtils;

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

    public List<Product> getProducts() {
        return products;
    }

    public void showInventory() {
        if (products.isEmpty()) {
            System.out.println("No hay productos en el inventario.");
            return;
        }
        System.out.println("=== Inventario Actual ===");
        for (Product p : products) {
            System.out.println(p);
        }
    }

    private String safe(String s) {
        return (s == null) ? "" : s.trim().toLowerCase();
    }

    public void updateStock(String id, int quantityChange) {
        for (Product p : products) {
            if (p.getId().equals(id)) {
                int newStock = p.getStock() + quantityChange;
                if (newStock < 0) {
                    System.out.println("No hay suficiente stock para la operacion.");
                    return;
                }
                p.setStock(newStock);
                System.out.println("Stock actualizado. Nuevo stock: " + newStock);
                return;
            }
        }
        System.out.println("Producto no encontrado.");
    }

    public Product findProductByName(String name) {
        String target = safe(name);
        for (Product p : products) {
            if (safe(p.getName()).equals(target)) {
                return p;
            }
        }
        return null;
    }

    public List<Product> findProductsByName(String partial) {
        String target = safe(partial);
        List<Product> result = new ArrayList<>();
        for (Product p : products) {
            if (safe(p.getName()).contains(target)) {
                result.add(p);
            }
        }
        return result;
    }

    public void sellProductInteractive(Scanner sc) {
        if (products.isEmpty()) {
            System.out.println("No hay productos disponibles para vender.");
            return;
        }

        System.out.println("Ingrese el nombre del producto a vender:");
        String name = sc.nextLine().trim();
        List<Product> matches = findProductsByName(name);

        if (matches.isEmpty()) {
            System.out.println("No se encontro ningun producto con ese nombre.");
            return;
        }

        System.out.println("Productos encontrados:");
        for (int i = 0; i < matches.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, matches.get(i));
        }
        System.out.print("Seleccione el numero del producto: ");
        try {
            int index = Integer.parseInt(sc.nextLine().trim()) - 1;
            if (index < 0 || index >= matches.size()) {
                System.out.println("Seleccion invalida.");
                return;
            }
            Product selected = matches.get(index);
            System.out.print("Ingrese la cantidad a vender: ");
            int qty = Integer.parseInt(sc.nextLine().trim());
            if (qty <= 0) {
                System.out.println("Cantidad invalida.");
                return;
            }
            if (selected.getStock() < qty) {
                System.out.println("No hay suficiente stock.");
                return;
            }
            selected.setStock(selected.getStock() - qty);
            double total = selected.getPrice() * qty;
            System.out.println("Venta realizada. Total: " + total);
        } catch (NumberFormatException e) {
            System.out.println("Entrada invalida.");
        }
    }

    public void saveToJson(String path) {
        try {
            // NUEVA CARPETA JSON DENTRO DE utils
            FileUtils.ensureFolder(
                    "src/main/java/ec/edu/espe/petshopinventorycontrol/utils/archivesJson/"
            );

            try (Writer w = new FileWriter(path)) {
                gson.toJson(products, w);
            }
            System.out.println("Inventario guardado en: " + path);
        } catch (IOException e) {
            System.out.println("Error al guardar inventario: " + e.getMessage());
        }
    }

    public void loadFromJson(String path) {
        File f = new File(path);

        if (!f.exists()) {
            return;
        }

        try (Reader r = new FileReader(f)) {
            List<Product> loaded = gson.fromJson(r, new TypeToken<List<Product>>() {}.getType());
            products.clear();
            if (loaded != null) {
                products.addAll(loaded);
            }
            System.out.println("Inventario cargado desde: " + path);
        } catch (IOException e) {
            System.out.println("Error al cargar inventario: " + e.getMessage());
        }
    }

    public void generateReport() {
        System.out.println("=== Reporte de Inventario ===");
        double totalValue = 0;
        int totalItems = 0;

        Map<String, Integer> categoryCount = new HashMap<>();

        for (Product p : products) {
            totalValue += p.getPrice() * p.getStock();
            totalItems += p.getStock();

            String key = safe(p.getCategory());
            categoryCount.put(key, categoryCount.getOrDefault(key, 0) + p.getStock());
        }

        System.out.println("Total de productos (unidades): " + totalItems);
        System.out.println("Valor total del inventario: " + totalValue);

        System.out.println("\nProductos por categoria:");
        for (Map.Entry<String, Integer> e : categoryCount.entrySet()) {
            System.out.printf("  %s: %d%n", e.getKey(), e.getValue());
        }
    }

    public void modifyInventoryByCategory(Scanner sc, String jsonPath) {
        System.out.print("Ingrese la categoria de productos a modificar: ");
        String category = sc.nextLine().trim().toLowerCase();
        List<Product> filtered = new ArrayList<>();
        for (Product p : products) {
            if (safe(p.getCategory()).equals(category)) {
                filtered.add(p);
            }
        }

        if (filtered.isEmpty()) {
            System.out.println("No se encontraron productos en esa categoria.");
            return;
        }

        System.out.println("Productos encontrados:");
        for (int i = 0; i < filtered.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, filtered.get(i));
        }

        System.out.print("Seleccione el numero del producto a modificar: ");
        try {
            int index = Integer.parseInt(sc.nextLine().trim()) - 1;
            if (index < 0 || index >= filtered.size()) {
                System.out.println("Seleccion invalida.");
                return;
            }
            Product target = filtered.get(index);

            System.out.println("¿Que desea modificar?");
            System.out.println("1. Precio");
            System.out.println("2. Stock");
            System.out.print("Opcion: ");
            String op = sc.nextLine().trim();

            switch (op) {
                case "1" -> {
                    System.out.print("Ingrese el nuevo precio: ");
                    double np = Double.parseDouble(sc.nextLine().trim());
                    target.setPrice(np);
                    System.out.println("Precio actualizado.");
                }
                case "2" -> {
                    System.out.print("Ingrese el nuevo stock: ");
                    int ns = Integer.parseInt(sc.nextLine().trim());
                    target.setStock(ns);
                    System.out.println("Stock actualizado.");
                }
                default -> {
                    System.out.println("Opcion no valida.");
                    return;
                }
            }

            saveToJson(jsonPath);

        } catch (NumberFormatException e) {
            System.out.println("Entrada invalida.");
        }
    }
}
