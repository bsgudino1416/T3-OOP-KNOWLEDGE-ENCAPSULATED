package ec.edu.espe.petshop.controller;

import ec.edu.espe.petshop.model.Inventory;
import ec.edu.espe.petshop.model.Product;
import java.io.*;
import java.util.*;

/**
 * Simplified PetShop System with Manager & Employee Login
 * Only this class was modified.
 */
public class Main {

    private static final Scanner sc = new Scanner(System.in);
    private static final String MANAGER_FILE = "src/ec/edu/espe/petshop/utils/manager.txt";
    private static final String EMPLOYEE_FOLDER = "src/ec/edu/espe/petshop/utils/registeremployee/";

    public static void main(String[] args) {
        Inventory inventory = new Inventory();

        ensureDirectories();

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
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    // ===== LOGIN & CREATION =====

    private static boolean loginOrCreateManager() {
        System.out.println("\n--- Manager Login ---");

        File file = new File(MANAGER_FILE);
        if (!file.exists()) {
            System.out.println("No manager account found. Let's create one!");
            createManagerAccount();
            return true;
        }

        for (int i = 0; i < 3; i++) {
            System.out.print("Username: ");
            String user = sc.nextLine().toLowerCase();
            System.out.print("Password: ");
            String pass = sc.nextLine().toLowerCase();

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

    private static void createManagerAccount() {
        System.out.print("Create username: ");
        String user = sc.nextLine();
        System.out.print("Create password: ");
        String pass = sc.nextLine();

        saveCredentials(MANAGER_FILE, user, pass);
        System.out.println("Manager account created successfully!");
    }

    private static boolean loginEmployee() {
        System.out.println("\n--- Employee Login ---");

        File folder = new File(EMPLOYEE_FOLDER);
        if (!folder.exists() || folder.listFiles() == null || folder.listFiles().length == 0) {
            System.out.println("No employee registered yet. Ask the manager to create one.");
            return false;
        }

        for (int i = 0; i < 3; i++) {
            System.out.print("Username: ");
            String user = sc.nextLine().toLowerCase();
            System.out.print("Password: ");
            String pass = sc.nextLine().toLowerCase();

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

    // ===== MENUS =====

    private static void managerMenu(Inventory inventory) {
        while (true) {
            System.out.println("\n--- MANAGER MENU ---");
            System.out.println("1. Add Product");
            System.out.println("2. Show Inventory");
            System.out.println("3. Modify Inventory (Edit JSON or details)");
            System.out.println("4. Generate Report");
            System.out.println("5. Add Employee");
            System.out.println("6. Logout");
            System.out.print("Choose an option: ");

            String option = sc.nextLine();

            switch (option) {
                case "1":
                    inventory.addProduct(sc);
                    break;
                case "2":
                    inventory.showInventory();
                    break;
                case "3":
                    System.out.println("Feature placeholder: Modify Inventory from JSON.");
                    break;
                case "4":
                    System.out.println("Feature placeholder: Generate Report.");
                    break;
                case "5":
                    addEmployee();
                    break;
                case "6":
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void employeeMenu(Inventory inventory) {
        while (true) {
            System.out.println("\n--- EMPLOYEE MENU ---");
            System.out.println("1. Add Product");
            System.out.println("2. Show Inventory");
            System.out.println("3. Generate Report");
            System.out.println("4. Generate Invoice");
            System.out.println("5. Logout");
            System.out.print("Choose an option: ");

            String option = sc.nextLine();

            switch (option) {
                case "1":
                    inventory.addProduct(sc);
                    break;
                case "2":
                    inventory.showInventory();
                    break;
                case "3":
                    System.out.println("Feature placeholder: Generate Report.");
                    break;
                case "4":
                    System.out.println("Feature placeholder: Generate Invoice.");
                    break;
                case "5":
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    // ===== EMPLOYEE CREATION =====

    private static void addEmployee() {
        System.out.println("\n--- Add Employee ---");
        System.out.print("New employee username: ");
        String user = sc.nextLine();
        System.out.print("New employee password: ");
        String pass = sc.nextLine();

        File file = new File(EMPLOYEE_FOLDER + user.toLowerCase() + ".txt");
        saveCredentials(file.getPath(), user, pass);
        System.out.println("Employee account created successfully!");
    }

    // ===== UTIL METHODS =====

    private static boolean validateLogin(File file, String user, String pass) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String storedUser = br.readLine();
            String storedPass = br.readLine();
            return storedUser != null && storedPass != null &&
                   storedUser.equalsIgnoreCase(user) &&
                   storedPass.equalsIgnoreCase(pass);
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
            if (file.isFile()) {
                if (validateLogin(file, user, pass)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static void saveCredentials(String path, String user, String pass) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(path))) {
            pw.println(user);
            pw.println(pass);
        } catch (IOException e) {
            System.out.println("Error saving credentials: " + e.getMessage());
        }
    }

    private static void ensureDirectories() {
        new File("src/ec/edu/espe/petshop/utils").mkdirs();
        new File(EMPLOYEE_FOLDER).mkdirs();
    }
}
