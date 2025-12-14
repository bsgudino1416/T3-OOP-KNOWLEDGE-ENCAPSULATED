<<<<<<< HEAD
package ec.edu.espe.petshopinventorycontrol.model;

import java.util.ArrayList;
import java.util.List;

public class Store {

    private String name;
    private Inventory inventory;
    private List<Employee> employees;
    private List<Supplier> suppliers;
    private List<Customer> customers;

    public Store(String name) {
        this.name = name;
        this.inventory = new Inventory();
        this.employees = new ArrayList<>();
        this.suppliers = new ArrayList<>();
        this.customers = new ArrayList<>();
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void addEmployee(Employee e) {
        employees.add(e);
    }

    public void addSupplier(Supplier s) {
        suppliers.add(s);
    }

    public void addCustomer(Customer c) {
        customers.add(c);
    }

    @Override
    public String toString() {
        return "Tienda: " + name;
    }
}
=======
package ec.edu.espe.petshopinventorycontrol.model;

import java.util.ArrayList;
import java.util.List;

public class Store {

    private String name;
    private Inventory inventory;
    private List<Employee> employees;
    private List<Supplier> suppliers;
    private List<Customer> customers;

    public Store(String name) {
        this.name = name;
        this.inventory = new Inventory();
        this.employees = new ArrayList<>();
        this.suppliers = new ArrayList<>();
        this.customers = new ArrayList<>();
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void addEmployee(Employee e) {
        employees.add(e);
    }

    public void addSupplier(Supplier s) {
        suppliers.add(s);
    }

    public void addCustomer(Customer c) {
        customers.add(c);
    }

    @Override
    public String toString() {
        return "Tienda: " + name;
    }
}
>>>>>>> 23a19a791ee45b0b7a40ad42c0dd08930a62491d
