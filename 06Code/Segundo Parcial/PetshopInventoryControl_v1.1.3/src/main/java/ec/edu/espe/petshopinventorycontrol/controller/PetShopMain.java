package ec.edu.espe.petshopinventorycontrol.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import ec.edu.espe.petshopinventorycontrol.model.Inventory;
import ec.edu.espe.petshopinventorycontrol.model.Product;

import ec.edu.espe.petshopinventorycontrol.utils.FileUtils;
import ec.edu.espe.petshopinventorycontrol.utils.LocalDateAdapter;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.time.LocalDate;
import java.util.*;

public class PetShopMain {

    /* =============================================================
                        CONFIGURACIÓN DE ARCHIVOS
       ============================================================= */

    private static final Scanner SCANNER = new Scanner(System.in);

    private static final String JSON_FOLDER =
            "src/main/java/ec/edu/espe/petshopinventorycontrol/utils/archivesJson/";

    private static final String MANAGER_JSON = JSON_FOLDER + "loginManager.json";
    private static final String EMPLOYEES_JSON = JSON_FOLDER + "loginEmployees.json";
    private static final String PRODUCTS_JSON = JSON_FOLDER + "products.json";

    /* Gson personalizado */
    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .setPrettyPrinting()
            .create();


    /* =============================================================
                        MÉTODO PRINCIPAL
       ============================================================= */

    public static void main(String[] args) {

        // Asegurar carpeta para JSON
        FileUtils.ensureFolder(JSON_FOLDER);

        Inventory inventory = new Inventory();

        // Cargar desde JSON si existe
        loadProductsFromJson(inventory);

        runMainLoop(inventory);
    }


    /* =============================================================
                        LOOP PRINCIPAL
       ============================================================= */

    private static void runMainLoop(Inventory inventory) {
        while (true) {

            System.out.println("\n===== SISTEMA PETSHOP =====");
            System.out.println("1. Iniciar sesión como gerente");
            System.out.println("2. Iniciar sesión como empleado");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");

            String option = SCANNER.nextLine().trim();

            switch (option) {
                case "1" -> {
                    if (loginOrCreateManager()) {
                        managerMenu(inventory);
                    }
                }
                case "2" -> {
                    if (loginEmployee()) {
                        employeeMenu(inventory);
                    }
                }
                case "3" -> {
                    System.out.println("Saliendo del sistema...");
                    inventory.saveToJson(PRODUCTS_JSON);
                    return;
                }
                default -> System.out.println("Opción inválida.");
            }
        }
    }

    /* =============================================================
                        MENÚ DEL GERENTE
       ============================================================= */

    private static void managerMenu(Inventory inventory) {
        while (true) {
            System.out.println("\n--- MENÚ DEL GERENTE ---");
            System.out.println("1. Agregar producto");
            System.out.println("2. Mostrar inventario");
            System.out.println("3. Modificar inventario");
            System.out.println("4. Generar reporte");
            System.out.println("5. Registrar empleado");
            System.out.println("6. Cerrar sesión");
            System.out.print("Opción: ");

            String op = SCANNER.nextLine().trim();

            switch (op) {
                case "1" -> {
                    addProductDetailed(inventory);
                    inventory.saveToJson(PRODUCTS_JSON);
                }
                case "2" -> inventory.showInventory();
                case "3" -> inventory.modifyInventoryByCategory(SCANNER, PRODUCTS_JSON);
                case "4" -> inventory.generateReport();
                case "5" -> createEmployeeInteractive();
                case "6" -> {
                    System.out.println("Sesión cerrada.");
                    return;
                }
                default -> System.out.println("Opción inválida.");
            }
        }
    }


    /* =============================================================
                        MENÚ DEL EMPLEADO
       ============================================================= */

    private static void employeeMenu(Inventory inventory) {

        while (true) {
            System.out.println("\n--- MENÚ DEL EMPLEADO ---");
            System.out.println("1. Buscar producto");
            System.out.println("2. Mostrar inventario");
            System.out.println("3. Vender producto");
            System.out.println("4. Generar reporte");
            System.out.println("5. Cerrar sesión");
            System.out.print("Opción: ");

            String op = SCANNER.nextLine().trim();

            switch (op) {
                case "1" -> {
                    System.out.print("Buscar: ");
                    List<Product> found = inventory.findProductsByName(SCANNER.nextLine());
                    if (found.isEmpty()) System.out.println("No encontrado.");
                    else found.forEach(System.out::println);
                }
                case "2" -> inventory.showInventory();
                case "3" -> inventory.sellProductInteractive(SCANNER);
                case "4" -> inventory.generateReport();
                case "5" -> {
                    System.out.println("Cerrando sesión...");
                    return;
                }
                default -> System.out.println("Opción inválida.");
            }
        }
    }


    /* =============================================================
                        LOGIN MANAGER (JSON)
       ============================================================= */

    private static boolean loginOrCreateManager() {

        File file = new File(MANAGER_JSON);

        if (!file.exists()) {
            System.out.println("No existe gerente. Creando cuenta...");
            return createManagerAccount();
        }

        Map<String, String> data = FileUtils.loadJson(MANAGER_JSON, Map.class);

        if (data == null) {
            System.out.println("Error cargando JSON del gerente.");
            return false;
        }

        for (int i = 0; i < 3; i++) {
            System.out.print("Usuario: ");
            String u = SCANNER.nextLine().trim();
            System.out.print("Contraseña: ");
            String p = SCANNER.nextLine().trim();

            if (u.equals(data.get("user")) && p.equals(data.get("pass"))) {
                System.out.println("Bienvenido gerente.");
                return true;
            }

            System.out.println("Credenciales incorrectas.");
        }

        return false;
    }


    private static boolean createManagerAccount() {
        System.out.print("Usuario nuevo: ");
        String user = SCANNER.nextLine().trim();

        System.out.print("Contraseña nueva: ");
        String pass = SCANNER.nextLine().trim();

        Map<String, String> data = new HashMap<>();
        data.put("user", user);
        data.put("pass", pass);

        boolean ok = FileUtils.saveJson(MANAGER_JSON, data);

        if (ok) {
            System.out.println("Gerente creado correctamente.");
        } else {
            System.out.println("Error guardando JSON.");
        }

        return ok;
    }


    /* =============================================================
                        LOGIN EMPLEADOS (JSON)
       ============================================================= */

    private static boolean loginEmployee() {

        List<Map<String, String>> employees = loadEmployeesFromJson();

        if (employees == null) {
            System.out.println("Error cargando empleados.");
            return false;
        }

        for (int i = 0; i < 3; i++) {

            System.out.print("Usuario: ");
            String u = SCANNER.nextLine().trim();
            System.out.print("Contraseña: ");
            String p = SCANNER.nextLine().trim();

            for (Map<String, String> em : employees) {
                if (u.equals(em.get("username")) && p.equals(em.get("password"))) {
                    System.out.println("Bienvenido empleado.");
                    return true;
                }
            }

            System.out.println("Credenciales incorrectas.");
        }

        return false;
    }


    private static List<Map<String, String>> loadEmployeesFromJson() {

        File f = new File(EMPLOYEES_JSON);
        if (!f.exists()) return new ArrayList<>();

        try (FileReader reader = new FileReader(f)) {
            return GSON.fromJson(reader, new TypeToken<List<Map<String, String>>>() {}.getType());
        } catch (Exception e) {
            System.out.println("Error leyendo employees.json");
            return new ArrayList<>();
        }
    }


    private static void createEmployeeInteractive() {

        System.out.println("\n--- Registrar empleado ---");

        System.out.print("Usuario nuevo: ");
        String user = SCANNER.nextLine().trim();

        System.out.print("Contraseña: ");
        String pass = SCANNER.nextLine().trim();

        System.out.print("Rol (empleado/gerente). Deje vacío para 'empleado': ");
        String role = SCANNER.nextLine().trim();
        if (role.isEmpty()) role = "empleado";

        List<Map<String, String>> employees = loadEmployeesFromJson();

        // Verificar duplicado
        for (Map<String, String> e : employees) {
            if (e.get("username").equalsIgnoreCase(user)) {
                System.out.println("Ese usuario ya existe.");
                return;
            }
        }

        Map<String, String> data = new HashMap<>();
        data.put("username", user);
        data.put("password", pass);
        data.put("role", role);
        data.put("createdAt", LocalDate.now().toString());

        employees.add(data);

        boolean ok = FileUtils.saveJson(EMPLOYEES_JSON, employees);

        if (ok) System.out.println("Empleado registrado.");
        else System.out.println("Error guardando JSON.");
    }


    /* =============================================================
                        AGREGAR PRODUCTO
       ============================================================= */

    private static void addProductDetailed(Inventory inventory) {

        try {
            System.out.println("\n=== AGREGAR PRODUCTO ===");

            System.out.print("Categoría: ");
            String cat = SCANNER.nextLine().trim().toUpperCase();

            System.out.print("Animal: ");
            String animal = SCANNER.nextLine().trim().toLowerCase();

            System.out.print("Tamaño (pequeño/grande): ");
            String size = SCANNER.nextLine().trim().toLowerCase();

            System.out.print("Marca: ");
            String brand = SCANNER.nextLine().trim();

            System.out.print("Precio: ");
            double price = Double.parseDouble(SCANNER.nextLine().trim());

            System.out.print("Stock: ");
            int stock = Integer.parseInt(SCANNER.nextLine().trim());

            String id = cat.substring(0, 1) + animal.substring(0, 1) + size.substring(0, 1)
                    + (inventory.getProducts().size() + 1);

            String name = cat + " - " + animal + " " + size + " - " + brand;

            Product p = new Product(id, name, price, stock, cat, animal, size, brand);

            inventory.addProduct(p);

            System.out.println("Producto agregado. ID asignado: " + id);

        } catch (Exception e) {
            System.out.println("Entrada inválida: " + e.getMessage());
        }
    }


    /* =============================================================
                        CARGA DE PRODUCTOS JSON
       ============================================================= */

    private static void loadProductsFromJson(Inventory inventory) {

        File f = new File(PRODUCTS_JSON);

        if (!f.exists()) return;

        try {
            Product[] arr = GSON.fromJson(new FileReader(f), Product[].class);

            if (arr != null) {
                inventory.getProducts().clear();
                inventory.getProducts().addAll(Arrays.asList(arr));
            }

            System.out.println("Productos cargados desde JSON.");

        } catch (IOException e) {
            System.out.println("Error cargando JSON de productos.");
        }
    }
}
