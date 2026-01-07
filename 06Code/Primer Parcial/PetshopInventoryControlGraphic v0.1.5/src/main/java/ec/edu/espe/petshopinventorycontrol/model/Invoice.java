package ec.edu.espe.petshopinventorycontrol.model;

import java.util.Date;

public class Invoice {

    private int id;
    private Date date;
    private double total;
    private Customer customer;
    private Order order;
    private Employee employee;

    public Invoice() {
    }

    public Invoice(int id, Date date, double total, Customer customer, Order order, Employee employee) {
        this.id = id;
        this.date = date;
        this.total = total;
        this.customer = customer;
        this.order = order;
        this.employee = employee;
    }

    @Override
    public String toString() {
        return "Factura #" + id + " | Total: $" + total + " | Cliente: " + customer;
    }
}
