package ec.edu.espe.petshop.model;

import java.util.*;

public class Order {
    private int id;
    private Customer customer;
    private Employee employee;
    private Date date;
    private List<OrderDetail> details;

    public Order(int id, Customer customer, Employee employee, Date date) {
        this.id = id;
        this.customer = customer;
        this.employee = employee;
        this.date = date;
        this.details = new ArrayList<>();
    }

    public void addDetail(OrderDetail detail) { details.add(detail); }

    public double calculateTotal() {
        return details.stream().mapToDouble(OrderDetail::getSubtotal).sum();
    }

    @Override
    public String toString() {
        return "Order #" + id + " | Customer: " + customer + " | Total: $" + calculateTotal();
    }
}
