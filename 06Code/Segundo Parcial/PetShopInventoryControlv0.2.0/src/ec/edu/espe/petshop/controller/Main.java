package ec.edu.espe.petshop.controller;

import ec.edu.espe.petshop.model.*;
import java.util.*;
import java.util.InputMismatchException;


public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Store store = new Store("Happy Paws");

        Order currentOrder = null;

        int option = 0;
        do {
            System.out.println("\n=== PET SHOP SYSTEM ===");
            System.out.println("1. Add Product");
            System.out.println("2. Show Inventory");
            System.out.println("3. Create Order");
            System.out.println("4. Generate Invoice");
            System.out.println("5. Generate Report");
            System.out.println("6. Exit");
            //System.out.print("Select an option: ");
            //option = sc.nextInt();
            //sc.nextLine();

        
        boolean validInput = false;
        do {
            System.out.print("Select an option: ");
        try {
        option = sc.nextInt();
        sc.nextLine(); 
        if (option < 1 || option > 6) {
            System.out.println("️Error!!! Digite un número entre 1 y 6");
        } else {
            validInput = true; 
        }
        } catch (InputMismatchException e) {
        System.out.println("Error!!! Digite un número entre 1 y 6");
        sc.nextLine(); 
        }
        } while (!validInput);

            switch (option) {

                case 1 -> {
                    AddProductMain.main(null);
                }

                case 2 -> store.getInventory().showInventory();
                case 3 -> {
                    System.out.print("Enter customer name: ");
                    String cname = sc.nextLine();
                    System.out.print("Enter customer address: ");
                    String address = sc.nextLine();
                    System.out.print("Enter customer phone: ");
                    String cphone = sc.nextLine();
                    Customer cust = new Customer(1, cname, address, cphone);

                    System.out.print("Enter employee name: ");
                    String ename = sc.nextLine();
                    System.out.print("Enter employee role: ");
                    String role = sc.nextLine();
                    Employee emp = new Employee(1, ename, role);

                    currentOrder = new Order(1, cust, emp);

                    store.getInventory().showInventory();

                    System.out.print("Enter product ID to order (e.g., CPP01): ");
                    String pid = sc.nextLine().trim().toUpperCase();

                    Product selected = store.getInventory().findProductById(pid);

                    if (selected != null) {
                    int qty = 0;
                    boolean validQty = false;
                    while (!validQty) {
                    try {
                        System.out.print("Enter quantity: ");
                        qty = Integer.parseInt(sc.nextLine());
                    if (qty > 0) {
                        validQty = true;
                    } 
                    else {
                    System.out.println("Quantity must be greater than 0.");
                    }
                    } 
                    catch (NumberFormatException e) {
                        System.out.println("Invalid quantity. Please enter a numeric value.");
                    }
                    }

                    currentOrder.addDetail(new OrderDetail(selected, qty));
                    System.out.println("Order created successfully!");
                    } 
                    else {
                        System.out.println("Product not found! Please verify the ID and try again.");
                    }
                }

                case 4 -> {
                    if (currentOrder != null) {
                        Bill inv = new Bill(1, currentOrder);
                        inv.showInvoice();
                    } else {
                        System.out.println("️ No order created yet.");
                    }
                }
                case 5 -> {
                    Report rep = new Report();
                    rep.generateInventoryReport(store.getInventory());
                }
                case 6 -> System.out.println(" Exiting system...");
                default -> System.out.println("Invalid option.");
            }
        } while (option != 6);
        sc.close();
    }
}
