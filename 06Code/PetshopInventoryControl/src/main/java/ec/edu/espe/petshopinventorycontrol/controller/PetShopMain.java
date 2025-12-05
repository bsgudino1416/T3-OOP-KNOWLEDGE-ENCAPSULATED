package ec.edu.espe.petshopinventorycontrol.controller;

import ec.edu.espe.petshopinventorycontrol.model.Inventory;
import ec.edu.espe.petshopinventorycontrol.model.Product;
import ec.edu.espe.petshopinventorycontrol.utils.FileUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.util.*;

public class PetShopMain {

    private static final Scanner SCANNER = new Scanner(System.in);

   
    private static final String DATA_FOLDER = "target/petshop-data/";

    private static final String MANAGER_FILE = DATA_FOLDER + "manager.txt";
    private static final String EMPLOYEE_FOLDER = DATA_FOLDER + "employees_txt/";
    private static final String PRODUCTS_JSON = DATA_FOLDER + "products.json";
    private static final String EMPLOYEES_JSON = DATA_FOLDER + "employees.json";

    private static final Set<String> VALID_CATEGORIES = Set.of("COMIDA", "MEDICINA", "SNACK", "ACCESORIOS");
    private static final Set<String> VALID_ANIMALS = Set.of("perro", "gato", "caballo", "conejo", "vaca", "gallina", "hamster", "cerdo");
    private static final Set<String> VALID_SIZES = Set.of("pequeno", "pequena", "grande");

    private static final String DEFAULT_BRAND = "dogchown";
    private static final Gson GSON = new Gson();

    public static void main(String[] args) {

        ensureDirectories();

        Inventory inventory = new Inventory();

        
        inventory.loadFromJson(PRODUCTS_JSON);

        
        migrateEmployeeTxtToJsonIfNeeded();

        runMainLoop(inventory);
    }

    private static void runMainLoop(Inventory inventory) {
        while (true) {
            System.out.println("\n=== SISTEMA PET SHOP ===");
            System.out.println("1. Iniciar sesion como gerente");
            System.out.println("2. Iniciar sesion como empleado");
            System.out.println("3. Salir");
            System.out.print("Selecciona una opcion: ");
            String option = SCANNER.nextLine().trim();

            switch (option) {
                case "1":
                    if (loginOrCreateManager()) managerMenu(inventory);
                    break;
                case "2":
                    if (loginEmployee()) employeeMenu(inventory);
                    break;
                case "3":
                    inventory.saveToJson(PRODUCTS_JSON);
                    System.out.println("Saliendo. ¡Hasta luego!");
                    return;
                default:
                    System.out.println("Opcion no válida. Intente de nuevo.");
            }
        }
    }

    /* ---------------- Manager menu ---------------- */

    private static void managerMenu(Inventory inventory) {
        while (true) {
            System.out.println("\n--- MENU DEL GERENTE ---");
            System.out.println("1. Agregar producto ");
            System.out.println("2. Mostrar inventario");
            System.out.println("3. Modificar inventario");
            System.out.println("4. Generar reporte ");
            System.out.println("5. Agregar empleado");
            System.out.println("6. Cerrar sesión");
            System.out.print("Elija una opción: ");
            String opt = SCANNER.nextLine().trim();

            switch (opt) {
                case "1":
                    addProductDetailed(inventory);
                    inventory.saveToJson(PRODUCTS_JSON);
                    break;
                case "2":
                    inventory.showInventory();
                    break;
                case "3":
                    inventory.modifyInventoryByCategory(SCANNER, PRODUCTS_JSON);
                    break;
                case "4":
                    inventory.generateReport();
                    inventory.saveToJson(PRODUCTS_JSON);
                    break;
                case "5":
                    createEmployeeInteractive();
                    break;
                case "6":
                    System.out.println("Cerrando sesión...");
                    return;
                default:
                    System.out.println("Opcion invalida.");
            }
        }
    }

    private static void employeeMenu(Inventory inventory) {
        while (true) {
            System.out.println("\n--- MENU DEL EMPLEADO ---");
            System.out.println("1. Buscar producto");
            System.out.println("2. Mostrar inventario");
            System.out.println("3. Vender producto");
            System.out.println("4. Generar reporte ");
            System.out.println("5. Cerrar sesion");
            System.out.print("Elija una opcion: ");
            String opt = SCANNER.nextLine().trim();

            switch (opt) {
                case "1":
                    System.out.print("Ingrese el nombre del producto o una parte del mismo: ");
                    String query = SCANNER.nextLine().trim();
                    List<Product> results = inventory.findProductsByName(query);
                    if (results.isEmpty()) System.out.println("No se encontraron productos.");
                    else results.forEach(p -> {
                        System.out.println("ID: " + p.getId() + " | " + p.getName()
                                + " | Precio: " + p.getPrice() + " | Stock: " + p.getStock());
                        if (p.getStock() < 4) System.out.println("  Producto con bajo stock");
                    });
                    break;
                case "2":
                    inventory.showInventory();
                    break;
                case "3":
                    inventory.sellProductInteractive(SCANNER);
                    inventory.saveToJson(PRODUCTS_JSON);
                    break;
                case "4":
                    inventory.generateReport();
                    inventory.saveToJson(PRODUCTS_JSON);
                    break;
                case "5":
                    System.out.println("Cerrando sesión...");
                    return;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }

    /* ---------------- LOGIN MANAGER ---------------- */

    private static boolean loginOrCreateManager() {
        File file = new File(MANAGER_FILE);
        if (!file.exists()) {
            System.out.println("No existe cuenta de gerente. Proceda a crear una.");
            return createManagerAccount();
        }

        for (int i = 0; i < 3; i++) {
            System.out.print("Usuario: ");
            String user = SCANNER.nextLine();
            System.out.print("Contraseña: ");
            String pass = SCANNER.nextLine();
            if (validateLogin(file, user, pass)) {
                System.out.println("¡Bienvenido, gerente!");
                return true;
            }
            System.out.println("Credenciales incorrectas. Intente nuevamente.");
        }
        System.out.println("Demasiados intentos fallidos.");
        return false;
    }

    private static boolean createManagerAccount() {
        System.out.print("Crear nombre de usuario: ");
        String user = SCANNER.nextLine().trim();
        System.out.print("Crear contraseña: ");
        String pass = SCANNER.nextLine().trim();
        if (user.isEmpty() || pass.isEmpty()) {
            System.out.println("El usuario y la contraseña no pueden estar vacíos.");
            return false;
        }
        boolean saved = FileUtils.writeText(MANAGER_FILE, user + "," + pass);
        if (saved) System.out.println("Cuenta de gerente creada.");
        return saved;
    }

    /* ---------------- LOGIN EMPLOYEE ---------------- */

    private static boolean loginEmployee() {
        for (int i = 0; i < 3; i++) {
            System.out.print("Usuario: ");
            String user = SCANNER.nextLine();
            System.out.print("Contraseña: ");
            String pass = SCANNER.nextLine();
            if (validateEmployeeLogin(user, pass)) {
                System.out.println("¡Bienvenido, empleado!");
                return true;
            }
            System.out.println("Credenciales incorrectas. Intente nuevamente.");
        }
        System.out.println("Demasiados intentos fallidos.");
        return false;
    }

    /* ---------------- VALIDACIONES ---------------- */

    private static boolean validateLogin(File file, String user, String pass) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine();
            if (line == null) return false;
            if (line.contains(",")) {
                String[] parts = line.split(",", 2);
                return parts[0].trim().equalsIgnoreCase(user)
                        && parts[1].trim().equalsIgnoreCase(pass);
            } else {
                String storedPass = br.readLine();
                return line.equalsIgnoreCase(user)
                        && storedPass != null
                        && storedPass.equalsIgnoreCase(pass);
            }
        } catch (IOException e) {
            System.out.println("Error al leer archivo de login: " + e.getMessage());
            return false;
        }
    }

    /* ---------------- AGREGAR PRODUCTOS ---------------- */

    private static void addProductDetailed(Inventory inventory) {
        while (true) {
            try {
                System.out.println("\n=== AGREGAR PRODUCTO ===");
                System.out.print("Ingresar categoría (COMIDA, MEDICINA, SNACK, ACCESORIOS): ");
                String categoryRaw = SCANNER.nextLine().trim();
                String category = categoryRaw.toUpperCase();
                if (!VALID_CATEGORIES.contains(category)) {
                    if (handleError() != 1) return;
                    else continue;
                }

                System.out.print("Producto para (ej: perro grande): ");
                String animalLine = SCANNER.nextLine().trim().toLowerCase();
                String[] parts = animalLine.split("\\s+");
                if (parts.length != 2) { 
                    if (handleError() != 1) return; 
                    else continue; 
                }

                String animal = normalize(parts[0]);
                String size = normalize(parts[1]);
                if (!VALID_ANIMALS.contains(animal) || !VALID_SIZES.contains(size)) {
                    if (handleError() != 1) return; 
                    else continue;
                }

                System.out.print("Proveedor/marca (ej: DogChown): ");
                String brand = SCANNER.nextLine().trim();
                if (brand.isEmpty()) { 
                    if (handleError() != 1) return; 
                    else continue; 
                }

                String id = generateProductId(inventory, category, animal, size, brand);
                String name = category + " - " + parts[0] + " " + parts[1] + " - " + brand;

                System.out.print("Ingrese el precio del producto: ");
                double price = Double.parseDouble(SCANNER.nextLine().trim());

                System.out.print("Ingrese la cantidad de stock: ");
                int stock = Integer.parseInt(SCANNER.nextLine().trim());

                Product product = new Product(id, name, price, stock, category, animal, size, brand);
                inventory.addProduct(product);
                inventory.saveToJson(PRODUCTS_JSON);

                System.out.println("Producto agregado exitosamente. ID: " + id);

                System.out.println("\n1. Agregar otro producto\n2. Regresar al menú\n3. Salir al menú principal");
                String opt = SCANNER.nextLine().trim();
                if ("1".equals(opt)) continue;
                if ("2".equals(opt)) return;
                return;
            } catch (Exception e) {
                System.out.println("Entrada inválida: " + e.getMessage());
                if (handleError() != 1) return;
            }
        }
    }

    /* ---------------- GENERADOR DE ID ---------------- */

    private static String generateProductId(Inventory inventory, String category, String animal, String size, String brand) {

        String cat = switch (category) {
            case "COMIDA" -> "C";
            case "MEDICINA" -> "M";
            case "SNACK" -> "S";
            case "ACCESORIOS" -> "A";
            default -> "X";
        };

        String animalCode = switch (animal) {
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

        String sizeCode = size.equalsIgnoreCase("grande") ? "G" : "P";

        String prefix = cat + animalCode + sizeCode;

        int maxSuffix = 0;
        for (Product p : inventory.getProducts()) {
            String pid = p.getId();
            if (pid != null && pid.startsWith(prefix)) {
                String suffix = pid.substring(prefix.length());
                try {
                    int n = Integer.parseInt(suffix);
                    if (n > maxSuffix) maxSuffix = n;
                } catch (Exception ignored) { }
            }
        }

        int next = maxSuffix + 1;
        return prefix + String.format("%02d", next);
    }

    /* ---------------- EMPLEADOS JSON ---------------- */

    @SuppressWarnings("unchecked")
    private static List<Map<String, String>> loadEmployees() {
        File f = new File(EMPLOYEES_JSON);
        if (!f.exists()) return new ArrayList<>();

        try (FileReader r = new FileReader(f)) {
            List<Map<String, String>> list =
                    GSON.fromJson(r, new TypeToken<List<Map<String, String>>>() {}.getType());
            return list == null ? new ArrayList<>() : list;
        } catch (Exception e) {
            System.out.println("Error al cargar employees.json: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private static void saveEmployees(List<Map<String, String>> employees) {
        FileUtils.saveJson(EMPLOYEES_JSON, employees);
    }

    private static boolean validateEmployeeLogin(String user, String pass) {
        if (user == null || pass == null) return false;
        List<Map<String, String>> employees = loadEmployees();
        for (Map<String, String> em : employees) {
            if (user.equalsIgnoreCase(em.get("username")) &&
                pass.equalsIgnoreCase(em.get("password"))) {
                return true;
            }
        }
        return false;
    }

    private static void createEmployeeInteractive() {
        System.out.println("\n--- Agregar empleado ---");
        System.out.print("Nuevo nombre de usuario: ");
        String user = SCANNER.nextLine().trim();
        System.out.print("Nueva contraseña: ");
        String pass = SCANNER.nextLine().trim();

        if (user.isEmpty() || pass.isEmpty()) {
            System.out.println("Usuario o contraseña vacíos.");
            return;
        }

        List<Map<String, String>> employees = loadEmployees();

        for (Map<String, String> e : employees)
            if (user.equalsIgnoreCase(e.get("username"))) {
                System.out.println("Ese usuario ya existe.");
                return;
            }

        Map<String, String> cred = new HashMap<>();
        cred.put("username", user);
        cred.put("password", pass);
        employees.add(cred);

        saveEmployees(employees);
        System.out.println("Empleado creado y guardado en employees.json.");
    }

    /* ---------------- MIGRAR .TXT A JSON ---------------- */

    private static void migrateEmployeeTxtToJsonIfNeeded() {

        File folder = new File(EMPLOYEE_FOLDER);
        if (!folder.exists()) return;

        List<Map<String, String>> employees = loadEmployees();
        boolean changed = false;

        File[] txtFiles = folder.listFiles((d, n) -> n.toLowerCase().endsWith(".txt"));
        if (txtFiles == null || txtFiles.length == 0) return;

        for (File f : txtFiles) {
            try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                String line = br.readLine();
                if (line == null) continue;
                String[] parts = line.split(",", 2);
                if (parts.length < 2) continue;

                String u = parts[0].trim();
                String p = parts[1].trim();

                boolean exists =
                        employees.stream().anyMatch(em -> u.equalsIgnoreCase(em.get("username")));

                if (!exists) {
                    Map<String, String> cred = new HashMap<>();
                    cred.put("username", u);
                    cred.put("password", p);
                    employees.add(cred);
                    changed = true;
                }
            } catch (Exception ignored) {}
        }

        if (changed) {
            saveEmployees(employees);
            System.out.println("Migración completada: TXT → employees.json");
        }
    }

    /* ---------------- UTILS ---------------- */

    private static String normalize(String input) {
        if (input == null) return "";
        return input.trim().toLowerCase()
                .replace('á', 'a').replace('é', 'e').replace('í', 'i')
                .replace('ó', 'o').replace('ú', 'u').replace('ñ', 'n');
    }

    private static int handleError() {
        System.out.println("Entrada inválida.\n1. Intentar de nuevo\n2. Volver al menú principal\n3. Salir");
        String opt = SCANNER.nextLine().trim();
        if ("1".equals(opt)) return 1;
        if ("2".equals(opt)) return 2;
        return 3;
    }

    private static void ensureDirectories() {
        new File(DATA_FOLDER).mkdirs();
        new File(EMPLOYEE_FOLDER).mkdirs();
    }
}

