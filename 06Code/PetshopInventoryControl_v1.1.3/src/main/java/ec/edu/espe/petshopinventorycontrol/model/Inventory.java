package ec.edu.espe.petshopinventorycontrol.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ec.edu.espe.petshopinventorycontrol.utils.FileUtils;
import ec.edu.espe.petshopinventorycontrol.utils.LocalDateAdapter;
import ec.edu.espe.petshopinventorycontrol.utils.MongoDBConnection;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.io.FileWriter;
import java.io.Writer;
import java.time.LocalDate;
import java.util.*;

/**
 * Inventory class — Handles JSON + MongoDB synchronization
 */
public class Inventory {

    /* =============================================================
                       ATRIBUTOS PRINCIPALES
       ============================================================= */

    private final List<Product> products = new ArrayList<>();

    // Gson personalizado
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .create();

    /* =============================================================
                       CONSTRUCTOR
       ============================================================= */

    public Inventory() {
        loadProductsFromMongo();
    }

    /* =============================================================
                       MÉTODOS BÁSICOS
       ============================================================= */

    public void addProduct(Product product) {
        if (product != null) {
            products.add(product);
            saveProductsToMongo();
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
        for (Product p : products) System.out.println(p);
    }

    private String safe(String s) {
        return (s == null) ? "" : s.trim().toLowerCase();
    }

    public Product findProductByName(String name) {
        String n = safe(name);
        for (Product p : products)
            if (safe(p.getName()).contains(n))
                return p;
        return null;
    }

    public List<Product> findProductsByName(String partial) {
        String n = safe(partial);
        List<Product> result = new ArrayList<>();
        for (Product p : products)
            if (safe(p.getName()).contains(n))
                result.add(p);
        return result;
    }

    public void sellProductInteractive(Scanner sc) {

    if (products.isEmpty()) {
        System.out.println("No hay productos disponibles para vender.");
        return;
    }

    System.out.print("Ingrese el nombre del producto a vender: ");
    String name = sc.nextLine().trim();

    List<Product> matches = findProductsByName(name);

    if (matches.isEmpty()) {
        System.out.println("No se encontró ningún producto con ese nombre.");
        return;
    }

    System.out.println("\nProductos encontrados:");
    for (int i = 0; i < matches.size(); i++) {
        System.out.printf("%d. %s%n", i + 1, matches.get(i));
    }

    try {
        System.out.print("Seleccione el número del producto: ");
        int index = Integer.parseInt(sc.nextLine()) - 1;

        if (index < 0 || index >= matches.size()) {
            System.out.println("Selección inválida.");
            return;
        }

        Product selected = matches.get(index);

        System.out.print("Cantidad a vender: ");
        int qty = Integer.parseInt(sc.nextLine().trim());

        if (qty <= 0) {
            System.out.println("Cantidad inválida.");
            return;
        }

        if (selected.getStock() < qty) {
            System.out.println("No hay suficiente stock.");
            return;
        }

        selected.setStock(selected.getStock() - qty);

        saveProductsToMongo();

        double total = selected.getPrice() * qty;
        System.out.println("\nVenta realizada con éxito.");
        System.out.println("Total de la venta: $" + total);

    } catch (Exception e) {
        System.out.println("Entrada inválida: " + e.getMessage());
    }
}


    public void saveToJson(String jsonPath) {
        try {
            FileUtils.ensureFolder(
                    "src/main/java/ec/edu/espe/petshopinventorycontrol/utils/archivesJson/"
            );

            try (Writer w = new FileWriter(jsonPath)) {
                gson.toJson(products, w);
            }

            System.out.println("Inventario guardado en JSON.");

        } catch (Exception e) {
            System.out.println("❌ Error al guardar JSON: " + e.getMessage());
        }
    }

   

    public void saveProductsToMongo() {
        try {
            MongoDatabase db = MongoDBConnection.getDatabase();
            MongoCollection<Document> col = db.getCollection("products");

            col.drop();

            List<Document> docs = new ArrayList<>();
            for (Product p : products) {
                docs.add(new Document("id", p.getId())
                        .append("name", p.getName())
                        .append("price", p.getPrice())
                        .append("stock", p.getStock())
                        .append("category", p.getCategory())
                        .append("animal", p.getAnimal())
                        .append("size", p.getSize())
                        .append("brand", p.getBrand()));
            }

            if (!docs.isEmpty()) {
                col.insertMany(docs);
            }

            System.out.println("✔ Inventario sincronizado con MongoDB.");

        } catch (Exception e) {
            System.out.println("❌ Error MongoDB: " + e.getMessage());
        }
    }

    public void loadProductsFromMongo() {
        try {
            MongoDatabase db = MongoDBConnection.getDatabase();
            MongoCollection<Document> col = db.getCollection("products");

            List<Document> docs = col.find().into(new ArrayList<>());

            products.clear();

            for (Document d : docs) {
                products.add(new Product(
                        d.getString("id"),
                        d.getString("name"),
                        d.getDouble("price"),
                        d.getInteger("stock"),
                        d.getString("category"),
                        d.getString("animal"),
                        d.getString("size"),
                        d.getString("brand")
                ));
            }

            if (!docs.isEmpty())
                System.out.println("✔ Inventario cargado desde MongoDB.");

        } catch (Exception e) {
            System.out.println("❌ Error cargando desde MongoDB: " + e.getMessage());
        }
    }

    /* =============================================================
                       SECCIÓN: MODIFICAR INVENTARIO
       ============================================================= */

    public void modifyInventoryByCategory(Scanner sc, String jsonPath) {

        System.out.println("\n=== MODIFICAR INVENTARIO ===");
        System.out.print("Categoría: ");

        String category = sc.nextLine().trim().toUpperCase();

        List<Product> matches = new ArrayList<>();
        for (Product p : products)
            if (p.getCategory().equalsIgnoreCase(category))
                matches.add(p);

        if (matches.isEmpty()) {
            System.out.println("No hay productos en esa categoría.");
            return;
        }

        System.out.println("\nProductos:");
        for (int i = 0; i < matches.size(); i++)
            System.out.println((i + 1) + ". " + matches.get(i));

        try {
            System.out.print("Seleccione número: ");
            int index = Integer.parseInt(sc.nextLine()) - 1;

            Product selected = matches.get(index);

            System.out.println("\n1. Cambiar precio\n2. Cambiar stock");
            String op = sc.nextLine();

            switch (op) {
                case "1" -> {
                    System.out.print("Nuevo precio: ");
                    selected.setPrice(Double.parseDouble(sc.nextLine()));
                }
                case "2" -> {
                    System.out.print("Nuevo stock: ");
                    selected.setStock(Integer.parseInt(sc.nextLine()));
                }
                default -> {
                    System.out.println("Opción inválida.");
                    return;
                }
            }

            saveToJson(jsonPath);
            saveProductsToMongo();

            System.out.println("✔ Cambios guardados.");

        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    /* =============================================================
                       SECCIÓN: REPORTES
       ============================================================= */

    public void generateReport() {

        System.out.println("\n=== REPORTE ===");

        if (products.isEmpty()) {
            System.out.println("Inventario vacío.");
            return;
        }

        int count = products.size();
        double totalValue = 0;
        int low = 0;

        for (Product p : products) {
            totalValue += p.getStock() * p.getPrice();
            if (p.getStock() < 5) low++;
        }

        System.out.println("Total productos: " + count);
        System.out.println("Valor total: $" + totalValue);
        System.out.println("Bajo stock (<5): " + low);

        saveProductsToMongo();
    }
}
