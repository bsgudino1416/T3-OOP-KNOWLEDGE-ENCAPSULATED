package ec.edu.espe.petshopinventorycontrol.controller;

import ec.edu.espe.petshopinventorycontrol.model.Inventory;
import ec.edu.espe.petshopinventorycontrol.model.Product;

import com.google.gson.Gson;
import java.io.File;
import java.util.*;

public class PetShopMain {

    private static final Scanner SCANNER = new Scanner(System.in);
    private static final Gson GSON = new Gson();

    
    private static final String JSON_FOLDER =
            "src/main/java/ec/edu/espe/petshopinventorycontrol/utils/archivesJson/";

    static {
        FileUtils.ensureFolder(JSON_FOLDER);
    }

    private static final String PRODUCTS_JSON = JSON_FOLDER + "products.json";
    private static final String EMPLOYEES_JSON = JSON_FOLDER + "employees.json";
    private static final String MANAGER_JSON = JSON_FOLDER + "loginmanager.json";
    private static final String EMPLOYEE_LOGIN_JSON = JSON_FOLDER + "loginemployee.json";

    
    private static final Set<String> VALID_CATEGORIES = Set.of("COMIDA", "MEDICINA", "SNACK", "ACCESORIOS");
    private static final Set<String> VALID_ANIMALS = Set.of("perro", "gato", "caballo", "conejo", "vaca", "gallina", "hamster", "cerdo");
    private static final Set<String> VALID_SIZES = Set.of("pequeno", "pequena", "grande");

    public static void main(String[] args) {

        Inventory inventory = new Inventory();

        
        inventory.loadFromJson(PRODUCTS_JSON);

        runMainLoop(inventory);
    }

    private static void runMainLoop(Inventory inventory) {
        while (true) {
            System.out.println("\n=== SISTEMA PET SHOP ===");
            System.out.println("1. Iniciar sesion como gerente");
            System.out.println("2. Iniciar sesion como empleado");
            System.out.println("3. Salir");
            System.out.print("Selecciona una opcion: ");
            String op = SCANNER.nextLine().trim();

            switch (op) {
                case "1" -> {
                    if (loginOrCreateManager()) managerMenu(inventory);
                }
                case "2" -> {
                    if (loginEmployee()) employeeMenu(inventory);
                }
                case "3" -> {
                    inventory.saveToJson(PRODUCTS_JSON);
                    System.out.println("Saliendo...");
                    return;
                }
                default -> System.out.println("Opcion invalida.");
            }
        }
    }

    

    private static boolean loginOrCreateManager() {

        File f = new File(MANAGER_JSON);

        if (!f.exists()) {
            System.out.println("No existe cuenta de gerente. Cree una:");
            return createManagerAccount();
        }

        Map<String, String> data = loadJsonMap(MANAGER_JSON);

        for (int i = 0; i < 3; i++) {

            System.out.print("Usuario: ");
            String user = SCANNER.nextLine().trim();
            System.out.print("Contrasenia: ");
            String pass = SCANNER.nextLine().trim();

            if (user.equals(data.get("username")) &&
                pass.equals(data.get("password"))) {

                System.out.println("Acceso permitido.");
                return true;
            }

            System.out.println("Credenciales incorrectas.");
        }

        System.out.println("Demasiados intentos.");
        return false;
    }

    private static boolean createManagerAccount() {
        Map<String, String> data = new HashMap<>();

        System.out.print("Crear usuario: ");
        data.put("username", SCANNER.nextLine().trim());

        System.out.print("Crear contrasenia: ");
        data.put("password", SCANNER.nextLine().trim());

        saveJsonMap(MANAGER_JSON, data);

        System.out.println("Gerente creado exitosamente.");
        return true;
    }

    

    private static boolean loginEmployee() {

        Map<String, String> users = loadJsonMap(EMPLOYEE_LOGIN_JSON);

        if (users == null || users.isEmpty()) {
            System.out.println("No hay empleados registrados.");
            return false;
        }

        for (int i = 0; i < 3; i++) {

            System.out.print("Usuario: ");
            String user = SCANNER.nextLine();
            System.out.print("Contrasenia: ");
            String pass = SCANNER.nextLine();

            if (user.equals(users.get("username")) &&
                pass.equals(users.get("password"))) {

                System.out.println("Bienvenido empleado.");
                return true;
            }

            System.out.println("Credenciales incorrectas.");
        }

        return false;
    }

    

    private static void createEmployeeInteractive() {

        System.out.println("\n--- Registrar empleado ---");

        Map<String, String> data = new HashMap<>();

        System.out.print("Usuario nuevo: ");
        data.put("username", SCANNER.nextLine().trim());

        System.out.print("Contrasenia: ");
        data.put("password", SCANNER.nextLine().trim());

        saveJsonMap(EMPLOYEE_LOGIN_JSON, data);

        System.out.println("Empleado registrado correctamente.");
    }

    

    private static Map<String, String> loadJsonMap(String path) {
        Map<String, String> map = FileUtils.loadJson(path, Map.class);
        return (map != null) ? map : new HashMap<>();
    }

    private static void saveJsonMap(String path, Map<String, String> data) {
        FileUtils.saveJson(path, data);
    }

    

    private static void managerMenu(Inventory inventory) {
        while (true) {
            System.out.println("\n--- MENU GERENTE ---");
            System.out.println("1. Agregar producto");
            System.out.println("2. Mostrar inventario");
            System.out.println("3. Modificar inventario");
            System.out.println("4. Generar reporte");
            System.out.println("5. Registrar empleado");
            System.out.println("6. Cerrar sesion");

            String op = SCANNER.nextLine();

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
                    System.out.println("Sesion cerrada.");
                    return;
                }
                default -> System.out.println("Opcion no valida.");
            }
        }
    }

    private static void employeeMenu(Inventory inventory) {

        while (true) {

            System.out.println("\n--- MENU EMPLEADO ---");
            System.out.println("1. Buscar producto");
            System.out.println("2. Mostrar inventario");
            System.out.println("3. Vender producto");
            System.out.println("4. Generar reporte");
            System.out.println("5. Cerrar sesion");

            String op = SCANNER.nextLine();

            switch (op) {
                case "1" -> {
                    System.out.print("Buscar: ");
                    String q = SCANNER.nextLine();
                    List<Product> results = inventory.findProductsByName(q);

                    if (results.isEmpty()) System.out.println("No encontrado.");
                    else results.forEach(System.out::println);
                }
                case "2" -> inventory.showInventory();
                case "3" -> {
                    inventory.sellProductInteractive(SCANNER);
                    inventory.saveToJson(PRODUCTS_JSON);
                }
                case "4" -> inventory.generateReport();
                case "5" -> {
                    System.out.println("Sesion cerrada.");
                    return;
                }
            }
        }
    }

    

    private static void addProductDetailed(Inventory inventory) {
        try {
            System.out.print("Categoria: ");
            String category = SCANNER.nextLine().toUpperCase();

            if (!VALID_CATEGORIES.contains(category)) {
                System.out.println("Categoria no valida.");
                return;
            }

            System.out.print("Animal (ej: perro): ");
            String animal = SCANNER.nextLine().toLowerCase();

            System.out.print("Tamano (pequeno / grande): ");
            String size = SCANNER.nextLine().toLowerCase();

            System.out.print("Marca: ");
            String brand = SCANNER.nextLine();

            System.out.print("Nombre producto: ");
            String name = SCANNER.nextLine();

            System.out.print("Precio: ");
            double price = Double.parseDouble(SCANNER.nextLine());

            System.out.print("Stock: ");
            int stock = Integer.parseInt(SCANNER.nextLine());

            String id = UUID.randomUUID().toString();

            Product p = new Product(id, name, price, stock, category, animal, size, brand);
            inventory.addProduct(p);

            System.out.println("Producto agregado: " + p);

        } catch (Exception e) {
            System.out.println("Error al agregar producto.");
        }
    }
}
