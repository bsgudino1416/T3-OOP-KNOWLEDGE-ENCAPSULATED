package ec.edu.espe.petshop.controller;

import ec.edu.espe.petshop.model.*;
import java.util.*;
import java.io.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Main completo: preserve tu lógica original. He integrado Inventory mejorado.
 */
public class Main {

    private static final Scanner sc = new Scanner(System.in);
    private static final String MANAGER_FILE = "src/ec/edu/espe/petshop/utils/manager.txt";
    private static final String EMPLOYEE_FOLDER = "src/ec/edu/espe/petshop/utils/registeremployee/";
    private static final String PRODUCTS_JSON = "src/ec/edu/espe/petshop/utils/products.json";

    // categorías y validaciones que tenías
    private static final String[] VALID_CATEGORIES = {"FOOD", "MEDICINE", "SNACK", "ACCESORIES"};
    private static final String[] VALID_ANIMALS = {"perro", "gato", "caballo", "conejo", "cerdo", "gallina", "vaca"};
    private static final String[] VALID_SIZES = {"pequeño", "grande"};

    public static void main(String[] args) {
        Inventory inventory = new Inventory();

        ensureDirectories();

        // cargar productos guardados (si existe)
        inventory.loadFromJson(PRODUCTS_JSON);

        while (true) {
            System.out.println("\n=== PET SHOP SYSTEM ===");
            System.out.println("1. Login as Manager");
            System.out.println("2. Login as Employee");
            System.out.println("3. Exit");
            System.out.print("Select an option: ");
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
                    System.out.println("Exiting... Goodbye!");
                    // guardar inventory antes de salir
                    inventory.saveToJson(PRODUCTS_JSON);
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    // ===== LOGIN & CREATION ===== (igual que tu versión original, case-insensitive)
    private static boolean loginOrCreateManager() {
        System.out.println("\n--- Manager Login ---");

        File file = new File(MANAGER_FILE);
        if (!file.exists()) {
            System.out.println("No manager account found. Let's create one!");
            return createManagerAccount();
        }

        for (int i = 0; i < 3; i++) {
            System.out.print("Username: ");
            String user = sc.nextLine();
            System.out.print("Password: ");
            String pass = sc.nextLine();

            if (validateLogin(file, user, pass)) {
                System.out.println("Welcome, Manager!");
                return true;
            } else {
                System.out.println("Incorrect credentials. Try again.");
            }
        }
        System.out.println("Too many failed attempts. Returning to main menu.");
        return false;
    }

    private static boolean createManagerAccount() {
        try {
            System.out.print("Create username: ");
            String user = sc.nextLine();
            System.out.print("Create password: ");
            String pass = sc.nextLine();

            if (user.trim().isEmpty() || pass.trim().isEmpty()) {
                System.out.println("Username and password cannot be empty!");
                return false;
            }

            boolean success = saveCredentials(MANAGER_FILE, user, pass);
            if (success) {
                System.out.println("Manager account created successfully!");
                System.out.println("Your credentials - Username: " + user + ", Password: " + pass);
                return true;
            } else {
                System.out.println("Error creating manager account.");
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error creating manager account: " + e.getMessage());
            return false;
        }
    }

    private static boolean loginEmployee() {
        System.out.println("\n--- Employee Login ---");

        File folder = new File(EMPLOYEE_FOLDER);
        File[] employeeFiles = folder.listFiles();

        if (employeeFiles == null || employeeFiles.length == 0) {
            System.out.println("No employee registered yet. Ask the manager to create one.");
            return false;
        }

        for (int i = 0; i < 3; i++) {
            System.out.print("Username: ");
            String user = sc.nextLine();
            System.out.print("Password: ");
            String pass = sc.nextLine();

            if (validateEmployeeLogin(user, pass)) {
                System.out.println("Welcome, Employee!");
                return true;
            } else {
                System.out.println("Incorrect credentials. Try again.");
            }
        }
        System.out.println("Too many failed attempts. Returning to main menu.");
        return false;
    }

    private static boolean saveCredentials(String filename, String username, String password) {
        try {
            File file = new File(filename);
            file.getParentFile().mkdirs(); // Crear directorios si no existen

            try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
                writer.println(username + "," + password);
            }
            return true;
        } catch (IOException e) {
            System.out.println("Error saving credentials: " + e.getMessage());
            return false;
        }
    }

    // ===== MENUS ===== (mantengo tu menú largo, con integraciones nuevas)

    private static void managerMenu(Inventory inventory) {
        while (true) {
            System.out.println("\n--- MANAGER MENU ---");
            System.out.println("1. Add Product (detailed)");
            System.out.println("2. Show Inventory");
            System.out.println("3. Modify Inventory (edit products.json by category)");
            System.out.println("4. Generate Report (save)");
            System.out.println("5. Add Employee");
            System.out.println("6. Logout");
            System.out.print("Choose an option: ");
            String opt = sc.nextLine().trim();

            switch (opt) {
                case "1":
                    addProductDetailed(inventory); // tu submódulo ya existente
                    // después de añadir guardamos en JSON
                    inventory.saveToJson(PRODUCTS_JSON);
                    break;
                case "2":
                    inventory.showInventory();
                    break;
                case "3":
                    inventory.modifyInventoryByCategory(sc, PRODUCTS_JSON);
                    break;
                case "4":
                    
                    System.out.println("\nGenerating report...");
                    inventory.generateReport();             // ahora sí imprime
                    inventory.saveToJson(PRODUCTS_JSON);    // respeta tu estructura original
                    break;

                case "5":
                    createEmployeeInteractive();
                    break;
                case "6":
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void employeeMenu(Inventory inventory) {
        while (true) {
            System.out.println("\n--- EMPLOYEE MENU ---");
            System.out.println("1. Search product");
            System.out.println("2. Show inventory");
            System.out.println("3. Sell product");
            System.out.println("4. Generate report (save)");
            System.out.println("5. Logout");
            System.out.print("Choose an option: ");
            String opt = sc.nextLine().trim();
            switch (opt) {
                case "1":
                    System.out.print("Enter product name or part: ");
                    String q = sc.nextLine().trim();
                    List<Product> results = inventory.findProductsByName(q);
                    if (results.isEmpty()) {
                        System.out.println("No products found.");
                    } else {
                        System.out.println("Found products:");
                        for (Product p : results) {
                            System.out.println("ID: " + p.getId() + " | " + p.getName() + " | Price: " + p.getPrice() + " | Stock: " + p.getStock());
                            if (p.getStock() < 4) System.out.println("  ⚠ producto con bajo stock");
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
                    System.out.println("\nGenerating report...");
                    inventory.generateReport();             // imprime reporte
                    inventory.saveToJson(PRODUCTS_JSON);    // respeta tu funcionalidad
                    break;



                case "5":
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    // ---------------- Employee creation ----------------
    private static void createEmployeeInteractive() {
        System.out.println("\n--- Add Employee ---");
        System.out.print("New employee username: ");
        String user = sc.nextLine().trim();
        System.out.print("New employee password: ");
        String pass = sc.nextLine().trim();
        File f = new File(EMPLOYEE_FOLDER + user.toLowerCase() + ".txt");
        saveCredentials(f.getPath(), user, pass);
        System.out.println("Employee account created successfully!");
    }

    // validateLogin and validateEmployeeLogin — mantengo tu lógica original:
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
            System.out.println("Error reading login file.");
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

    // ===============================
    // Tu submenú "Add Product" (detallado) integrado aquí
    // ===============================
    private static void addProductDetailed(Inventory inventory) {
        List<Product> addedProducts = new ArrayList<>();
        int option = 1;

        while (option == 1) {
            try {
                System.out.println("\n=== ADD PRODUCT MODULE ===");
                System.out.print("Enter category (FOOD, MEDICINE, SNACK, ACCESORIES): ");
                String category = sc.nextLine().trim().toUpperCase();

                if (!Arrays.asList(VALID_CATEGORIES).contains(category)) {
                    option = handleError();
                    continue;
                }

                System.out.print("Food for? e.g: perro pequeño → ");
                String animalLine = sc.nextLine().trim().toLowerCase();

                String[] animalParts = animalLine.split(" ");
                if (animalParts.length != 2) {
                    option = handleError();
                    continue;
                }

                String animal = animalParts[0];
                String size = animalParts[1];

                if (!Arrays.asList(VALID_ANIMALS).contains(animal) || !Arrays.asList(VALID_SIZES).contains(size)) {
                    option = handleError();
                    continue;
                }

                System.out.print("Proveedor? e.g: DogChown → ");
                String supplierName = sc.nextLine().trim();

                if (supplierName.isEmpty()) {
                    option = handleError();
                    continue;
                }

                // Generar ID numérico autoincremental
                int id = inventory.getNextProductId();

                // Generar nombre a partir de los datos (se puede ajustar como quieras)
                String name = category + " - " + animal + " " + size + " - " + supplierName;

                // Pedir precio y stock (más acorde al modelo Product actual)
                System.out.print("Enter product price: ");
                double price = Double.parseDouble(sc.nextLine().trim());

                System.out.print("Enter stock quantity: ");
                int stock = Integer.parseInt(sc.nextLine().trim());

                Product product = new Product(id, name, price, stock);

                inventory.addProduct(product);
                addedProducts.add(product);

                System.out.println("Product added successfully!");
                System.out.println("Product ID: " + id);
                System.out.println("Current session inventory additions: " + addedProducts.size());

                // Guardar los productos añadidos en esta sesión a JSON
                saveProductsToJson(addedProducts);

                System.out.println("\n1. Add another product");
                System.out.println("2. Return to menu");
                System.out.println("3. Exit to main menu");
                String optStr = sc.nextLine().trim();
                if (optStr.equals("1")) {
                    option = 1;
                } else if (optStr.equals("2")) {
                    option = 2;
                } else if (optStr.equals("3")) {
                    System.out.println("Returning to main menu...");
                    return;
                } else {
                    System.out.println("Invalid choice, returning to menu.");
                    return;
                }

            } catch (Exception e) {
                System.out.println("Error: Invalid input. Please try again. (" + e.getMessage() + ")");
                option = handleError();
            }
        }
    }

    private static int handleError() {
        System.out.println("Error!! Invalid input.");
        System.out.println("1. Try again");
        System.out.println("2. Main menu");
        System.out.println("3. Exit");
        String opt = sc.nextLine().trim();
        if (opt.equals("1")) return 1;
        if (opt.equals("2")) return 2;
        return 3;
    }

    private static void saveProductsToJson(List<Product> products) {
        if (products == null || products.isEmpty()) return;

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(PRODUCTS_JSON)) {
            gson.toJson(products, writer);
            System.out.println("Products saved to " + PRODUCTS_JSON);
        } catch (IOException e) {
            System.out.println("Error saving products to JSON: " + e.getMessage());
        }
    }
}
