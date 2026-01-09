package ec.edu.espe.petshop.controller;

import ec.edu.espe.petshop.model.*;
import java.util.*;
import java.io.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class PetShopMain {

    private static final Scanner sc = new Scanner(System.in);
    private static final String MANAGER_FILE = "src/ec/edu/espe/petshop/utils/manager.txt";
    private static final String EMPLOYEE_FOLDER = "src/ec/edu/espe/petshop/utils/registeremployee/";
    private static final String PRODUCTS_JSON = "src/ec/edu/espe/petshop/utils/products.json";

    // Validaciones (guardadas en forma normalizada: sin tildes y sin ñ)
    private static final String[] VALID_CATEGORIES = {"COMIDA", "MEDICINA", "SNACK", "ACCESORIOS"};
    private static final String[] VALID_ANIMALS = {"perro", "gato", "caballo", "conejo", "vaca", "gallina", "hamster", "cerdo"};
    private static final String[] VALID_SIZES = {"pequeno", "grande"}; // "pequeno" == "pequeño"

    private static final String DEFAULT_BRAND = "dogchown";

    public static void main(String[] args) {
        Inventory inventory = new Inventory();

        ensureDirectories();

        inventory.loadFromJson(PRODUCTS_JSON);

        while (true) {
            System.out.println("\n=== SISTEMA DE PET SHOP ===");
            System.out.println("1. Iniciar sesión como gerente");
            System.out.println("2. Iniciar sesión como empleado");
            System.out.println("3. Salr");
            System.out.print("Selecciona una opción: ");
            String option = sc.nextLine();

            switch (option) {
                case "1":
                    if (loginOrCreateManager()) {
                        managerMenu(inventory);
                    }
                    break;
                case "2":
                    if (loginEmployee()) {
                        employeeMenu(inventory);
                    }
                    break;
                case "3":
                    System.out.println("Saliendo... ¡Adiós!");
                    // guardar inventory antes de salir
                    inventory.saveToJson(PRODUCTS_JSON);
                    return;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }

    // ===== LOGIN & CREATION =====
    private static boolean loginOrCreateManager() {
        System.out.println("\n--- Inicio de sesión de gerente ---");

        File file = new File(MANAGER_FILE);
        if (!file.exists()) {
            System.out.println("No se encontró ninguna cuenta de gerente. ¡Vamos a crear una!");
            return createManagerAccount();
        }

        for (int i = 0; i < 3; i++) {
            System.out.print("Nombre de usuario: ");
            String user = sc.nextLine();
            System.out.print("Contraseña: ");
            String pass = sc.nextLine();

            if (validateLogin(file, user, pass)) {
                System.out.println("¡Bienvenido, Gerente!");
                return true;
            } else {
                System.out.println("Credenciales incorrectas. Intente de nuevo.");
            }
        }
        System.out.println("Demasiados intentos fallidos. Regresando al menú principal.");
        return false;
    }

    private static boolean createManagerAccount() {
        try {
            System.out.print("Crear nombre de usuario: ");
            String user = sc.nextLine();
            System.out.print("Crear contraseña: ");
            String pass = sc.nextLine();

            if (user.trim().isEmpty() || pass.trim().isEmpty()) {
                System.out.println("¡El nombre de usuario y la contraseña no pueden estar vacíos!");
                return false;
            }

            boolean success = saveCredentials(MANAGER_FILE, user, pass);
            if (success) {
                System.out.println("¡Cuenta de gerente creada exitosamente!");
                System.out.println("Sus credenciales - Nombre de usuario: " + user + ", Contraseña:" + pass);
                return true;
            } else {
                System.out.println("Error al crear la cuenta de gerente.");
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error al crear la cuenta de gerente: " + e.getMessage());
            return false;
        }
    }

    private static boolean loginEmployee() {
        System.out.println("\n--- Inicio de sesión de empleado ---");

        File folder = new File(EMPLOYEE_FOLDER);
        File[] employeeFiles = folder.listFiles();

        if (employeeFiles == null || employeeFiles.length == 0) {
            System.out.println("Aún no hay empleados registrados. Pida al gerente que cree uno.");
            return false;
        }

        for (int i = 0; i < 3; i++) {
            System.out.print("Usuario: ");
            String user = sc.nextLine();
            System.out.print("Contraseña: ");
            String pass = sc.nextLine();

            if (validateEmployeeLogin(user, pass)) {
                System.out.println("Bienvenido, Empleado!");
                return true;
            } else {
                System.out.println("Credenciales incorrectas. Intente de nuevo.");
            }
        }
        System.out.println("Demasiados intentos fallidos. Regresando al menú principal");
        return false;
    }

    private static boolean saveCredentials(String filename, String username, String password) {
        try {
            File file = new File(filename);
            file.getParentFile().mkdirs(); 

            try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
                writer.println(username + "," + password);
            }
            return true;
        } catch (IOException e) {
            System.out.println("Error al guardar las credenciales: " + e.getMessage());
            return false;
        }
    }

    // ===== MENUS =====
    private static void managerMenu(Inventory inventory) {
        while (true) {
            System.out.println("\n--- MENÚ DEL GERENTE ---");
            System.out.println("1. Agregar producto (detallado)");
            System.out.println("2. Mostrar inventario");
            System.out.println("3. Modificar inventario");
            System.out.println("4. Generar reporte (guardar)");
            System.out.println("5. Agregar empleado");
            System.out.println("6. Cerrar sesión");
            System.out.print("Elija una opción: ");
            String opt = sc.nextLine().trim();

            switch (opt) {
                case "1":
                    addProductDetailed(inventory); 
                    
                    inventory.saveToJson(PRODUCTS_JSON);
                    break;
                case "2":
                    inventory.showInventory();
                    break;
                case "3":
                    inventory.modifyInventoryByCategory(sc, PRODUCTS_JSON);
                    break;
                case "4":
                    System.out.println("\nGenerando reporte...");
                    inventory.generateReport();
                    inventory.saveToJson(PRODUCTS_JSON);
                    break;
                case "5":
                    createEmployeeInteractive();
                    break;
                case "6":
                    System.out.println("Cerrando sesion...");
                    return;
                default:
                    System.out.println("Opcion invalida.");
            }
        }
    }

    private static void employeeMenu(Inventory inventory) {
        while (true) {
            System.out.println("\n--- EMPLOYEE MENU ---");
            System.out.println("1. Buscar producto");
            System.out.println("2. Mostrar inventario");
            System.out.println("3. Vender producto");
            System.out.println("4. Generar reporte (guardar)");
            System.out.println("5. Cerrar sesión");
            System.out.print("Elija una opción: ");
            String opt = sc.nextLine().trim();
            switch (opt) {
                case "1":
                    System.out.print("Ingrese el nombre del producto o una parte del mismo: ");
                    String q = sc.nextLine().trim();
                    List<Product> results = inventory.findProductsByName(q);
                    if (results.isEmpty()) {
                        System.out.println("No se encontraron productos.");
                    } else {
                        System.out.println("Productos encontrados:");
                        for (Product p : results) {
                            System.out.println("ID: " + p.getId() + " | " + p.getName() + " | Precio: " + p.getPrice() + " | Stock: " + p.getStock());
                            if (p.getStock() < 4) System.out.println("  Producto con bajo stock");
                        }
                    }
                    break;
                case "2":
                    inventory.showInventory();
                    break;
                case "3":
                    inventory.sellProductInteractive(sc);
                    inventory.saveToJson(PRODUCTS_JSON);
                    break;
                case "4":
                    System.out.println("\nGenerando reporte...");
                    inventory.generateReport();
                    inventory.saveToJson(PRODUCTS_JSON);
                    break;
                case "5":
                    System.out.println("Cerrando sesion...");
                    return;
                default:
                    System.out.println("Opcion invalida.");
            }
        }
    }

    // ---------------- Employee creation ----------------
    private static void createEmployeeInteractive() {
        System.out.println("\n--- Agregar empleado ---");
        System.out.print("Nuevo nombre de usuario del empleado: ");
        String user = sc.nextLine().trim();
        System.out.print("Nueva contraseña del empleado: ");
        String pass = sc.nextLine().trim();
        File f = new File(EMPLOYEE_FOLDER + user.toLowerCase() + ".txt");
        saveCredentials(f.getPath(), user, pass);
        System.out.println("¡Cuenta de empleado creada exitosamente!");
    }

    private static boolean validateLogin(File file, String user, String pass) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine();
            if (line != null) {
                if (line.contains(",")) {
                    String[] parts = line.split(",");
                    String storedUser = parts[0].trim();
                    String storedPass = parts[1].trim();
                    return storedUser.equalsIgnoreCase(user) && storedPass.equalsIgnoreCase(pass);
                }
                String storedPass = br.readLine();
                return line.equalsIgnoreCase(user) && storedPass != null && storedPass.equalsIgnoreCase(pass);
            }
            return false;
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de inicio de sesión.");
            return false;
        }
    }

    private static boolean validateEmployeeLogin(String user, String pass) {
        File folder = new File(EMPLOYEE_FOLDER);
        File[] files = folder.listFiles();
        if (files == null) return false;

        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(".txt")) {
                if (validateLogin(file, user, pass)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static void ensureDirectories() {
        new File("src/ec/edu/espe/petshop/utils").mkdirs();
        new File(EMPLOYEE_FOLDER).mkdirs();
    }

//    // ====================
//    // SUBMENÚ ADD PRODUCT 
//    // ====================
//    private static void addProductDetailed(Inventory inventory) {
//        List<Product> addedProducts = new ArrayList<>();
//        int option = 1;
//
//        while (option == 1) {
//            try {
//
//                System.out.println("\n=== ADD PRODUCT MODULE ===");
//                System.out.print("Ingresar categoria (COMIDA, MEDICINA, SNACK, ACCESORIOS): ");
//                String category = sc.nextLine().trim().toUpperCase();
//
//                if (!Arrays.asList(VALID_CATEGORIES).contains(category)) {
//                    option = handleError();
//                    continue;
//                }
//
//                System.out.print("Producto para? (ej: perro grande): ");
//                String animalLine = sc.nextLine().trim().toLowerCase();
//
//                String[] animalParts = animalLine.split(" ");
//                if (animalParts.length != 2) {
//                    option = handleError();
//                    continue;
//                }
//
//                // Normalizar (quita tildes/ñ) para comparar con VALID_* arrays
//                String rawAnimal = animalParts[0];
//                String rawSize = animalParts[1];
//                String animal = normalize(rawAnimal);
//                String size = normalize(rawSize);
//
//                if (!Arrays.asList(VALID_ANIMALS).contains(animal) ||
//                    !Arrays.asList(VALID_SIZES).contains(size)) {
//                    option = handleError();
//                    continue;
//                }
//
//                System.out.print("Proveedor/marca (ej: DogChown): ");
//                String supplierName = sc.nextLine().trim();
//
//                if (supplierName.isEmpty()) {
//                    option = handleError();
//                    continue;
//                }
//
//                // =========================================
//                // GENERATE ID 
//                // =========================================
//                String id = generateProductId(inventory,category, animal, size, supplierName);
//
//                String name = category + " - " + rawAnimal + " " + rawSize + " - " + supplierName;
//
//                System.out.print("Ingrese el precio del producto: ");
//                double price = Double.parseDouble(sc.nextLine().trim());
//
//                System.out.print("Ingrese la cantidad de stock: ");
//                int stock = Integer.parseInt(sc.nextLine().trim());
//
//
//                Product product = new Product(
//                    id,
//                    name,
//                    price,
//                    stock,
//                    category,
//                    animal,
//                    size,
//                    supplierName
//                );
//
//                inventory.addProduct(product);
//                addedProducts.add(product);
//
//                System.out.println("Producto agregado exitosamente!");
//                System.out.println("ID del producto: " + id);
//
//                // save inventory in JSON 
//                inventory.saveToJson(PRODUCTS_JSON);
//
//                System.out.println("\n1. Agregar otro producto");
//                System.out.println("2. Regresar al menu");
//                System.out.println("3. Salir al menu principal");
//                String opt = sc.nextLine().trim();
//
//                if (opt.equals("1")) option = 1;
//                else if (opt.equals("2")) option = 2;
//                else return;
//
//            } catch (Exception e) {
//                System.out.println("Error!!! Entrada no valida (" + e.getMessage() + ")");
//                option = handleError();
//            }
//        }
//    }
//
//    private static String generateProductId(Inventory inventory, String category, String animal, String size, String brand) {
//
//    // normalize
//    String a = normalize(animal);
//    String s = normalize(size);
//
//    // 1) category
//    String catCode = switch (category) {
//        case "COMIDA" -> "C";
//        case "MEDICINA" -> "M";
//        case "SNACK" -> "S";
//        case "ACCESORIOS" -> "A";
//        default -> "X";
//    };
//
//    // 2) animal
//    String animalCode = switch (a) {
//        case "perro" -> "P";
//        case "gato" -> "G";
//        case "conejo" -> "C";
//        case "caballo" -> "Cb";
//        case "vaca" -> "V";
//        case "gallina" -> "Gi";
//        case "hamster" -> "Hm";
//        case "cerdo" -> "Cr";
//        default -> "X";
//    };
//
//    // 3) size
//    String sizeCode = s.equals("grande") ? "G" : "P";
//    String prefix = catCode + animalCode + sizeCode;
//
//    // ===========================
//    // Search IDs 
//    // ===========================
//    int maxSuffix = 0;    // start in 01
//
//    for (Product p : inventory.getProducts()) {
//        if (p.getId().startsWith(prefix)) {
//            String suf = p.getId().substring(prefix.length());
//            try {
//                int n = Integer.parseInt(suf);
//                if (n > maxSuffix) maxSuffix = n;
//            } catch (Exception ignore) {}
//        }
//    }
//
//    // ===========================
//    // GENERAR SIGUIENTE SUFIJO +1
//    // ===========================
//    int nextSuffix = maxSuffix + 1;
//
//    String suffix = String.format("%02d", nextSuffix); // two dígits: 01, 02, 03...
//
//    return prefix + suffix;
//}
//
//    // ===================================================
//    // Normaliza strings: quita tildes minúsculas y ñ -> n
//    // ===================================================
//    private static String normalize(String input) {
//        if (input == null) return "";
//        String s = input.trim().toLowerCase();
//        s = s.replace('á','a').replace('é','e').replace('í','i').replace('ó','o').replace('ú','u').replace('ñ','n');
//        // quitar otros diacríticos si es necesario (mayoría cubierto)
//        return s;
//    }
//
//    private static int handleError() {
//        System.out.println("Error!! Entrada no válida.");
//        System.out.println("1. Intenta nuevamente");
//        System.out.println("2. Menu principal");
//        System.out.println("3. Salir");
//        String opt = sc.nextLine().trim();
//        if (opt.equals("1")) return 1;
//        if (opt.equals("2")) return 2;
//        return 3;
//    }
    // ====================
// SUBMENÚ ADD PRODUCT 
// ====================
private static void addProductDetailed(Inventory inventory) {
    List<Product> addedProducts = new ArrayList<>();
    int option = 1;

    while (option == 1) {
        try {

            System.out.println("\n=== ADD PRODUCT MODULE ===");
            System.out.print("Ingresar categoria (COMIDA, MEDICINA, SNACK, ACCESORIOS): ");
            String category = sc.nextLine().trim().toUpperCase();

            if (!Arrays.asList(VALID_CATEGORIES).contains(category)) {
                option = handleError();
                continue;
            }

            System.out.print("Producto para? (ej: perro grande): ");
            String animalLine = sc.nextLine().trim().toLowerCase();

            String[] animalParts = animalLine.split(" ");
            if (animalParts.length != 2) {
                option = handleError();
                continue;
            }

            // Normalizar (quita tildes/ñ) para comparar con VALID_* arrays
            String rawAnimal = animalParts[0];
            String rawSize = animalParts[1];
            String animal = normalize(rawAnimal);
            String size = normalize(rawSize);

            if (!Arrays.asList(VALID_ANIMALS).contains(animal) ||
                !Arrays.asList(VALID_SIZES).contains(size)) {
                option = handleError();
                continue;
            }

            System.out.print("Proveedor/marca (ej: DogChown): ");
            String supplierName = sc.nextLine().trim();

            if (supplierName.isEmpty()) {
                option = handleError();
                continue;
            }

            // =========================================
            // GENERATE ID 
            // =========================================
            String id = generateProductId(inventory, category, animal, size, supplierName);

            String name = category + " - " + rawAnimal + " " + rawSize + " - " + supplierName;

            System.out.print("Ingrese el precio del producto: ");
            double price = Double.parseDouble(sc.nextLine().trim());

            System.out.print("Ingrese la cantidad de stock: ");
            int stock = Integer.parseInt(sc.nextLine().trim());


            Product product = new Product(
                id,
                name,
                price,
                stock,
                category,
                animal,
                size,
                supplierName
            );

            inventory.addProduct(product);
            addedProducts.add(product);

            System.out.println("Producto agregado exitosamente!");
            System.out.println("ID del producto: " + id);

            // ==============================
            // Guardado silencioso en JSON
            // ==============================
            try (FileWriter writer = new FileWriter(PRODUCTS_JSON)) {
                new Gson().toJson(inventory.getProducts(), writer);
            } catch (Exception e) {
                System.out.println("Error al guardar JSON: " + e.getMessage());
            }

            System.out.println("\n1. Agregar otro producto");
            System.out.println("2. Regresar al menu");
            System.out.println("3. Salir al menu principal");
            String opt = sc.nextLine().trim();

            if (opt.equals("1")) option = 1;
            else if (opt.equals("2")) option = 2;
            else return;

        } catch (Exception e) {
            System.out.println("Error!!! Entrada no valida (" + e.getMessage() + ")");
            option = handleError();
        }
    }
}

// ====================
// GENERAR ID CON SUFIJO +1
// ====================
private static String generateProductId(Inventory inventory, String category, String animal, String size, String brand) {

    String a = normalize(animal);
    String s = normalize(size);

    // 1) category
    String catCode = switch (category) {
        case "COMIDA" -> "C";
        case "MEDICINA" -> "M";
        case "SNACK" -> "S";
        case "ACCESORIOS" -> "A";
        default -> "X";
    };

    // 2) animal
    String animalCode = switch (a) {
        case "perro" -> "P";
        case "gato" -> "G";
        case "conejo" -> "C";
        case "caballo" -> "Cb";
        case "vaca" -> "V";
        case "gallina" -> "Gi";
        case "hamster" -> "Hm";
        case "cerdo" -> "Cr";
        default -> "X";
    };

    // 3) size
    String sizeCode = s.equals("grande") ? "G" : "P";
    String prefix = catCode + animalCode + sizeCode;

    // ===========================
    // Buscar sufijo existente
    // ===========================
    int maxSuffix = 0;

    for (Product p : inventory.getProducts()) {
        if (p.getId().startsWith(prefix)) {
            String suf = p.getId().substring(prefix.length());
            try {
                int n = Integer.parseInt(suf);
                if (n > maxSuffix) maxSuffix = n;
            } catch (Exception ignore) {}
        }
    }

    // ===========================
    // Generar siguiente sufijo +1
    // ===========================
    int nextSuffix = maxSuffix + 1;
    String suffix = String.format("%02d", nextSuffix); // 01, 02, 03...

    return prefix + suffix;
}

// ====================
// Normaliza strings
// ====================
private static String normalize(String input) {
    if (input == null) return "";
    String s = input.trim().toLowerCase();
    s = s.replace('á','a').replace('é','e').replace('í','i')
         .replace('ó','o').replace('ú','u').replace('ñ','n');
    return s;
}

// ====================
// Handle Error
// ====================
private static int handleError() {
    System.out.println("Error!! Entrada no válida.");
    System.out.println("1. Intenta nuevamente");
    System.out.println("2. Menu principal");
    System.out.println("3. Salir");
    String opt = sc.nextLine().trim();
    if (opt.equals("1")) return 1;
    if (opt.equals("2")) return 2;
    return 3;
}
}

    
    
    