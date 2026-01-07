package ec.edu.espe.petshop.model;

import java.util.*;

public class Order {
    private int id;
    private Customer customer;
    private Employee employee;
    private Date date;
    private List<OrderDetail> details = new ArrayList<>();

    public Order(int id, Customer customer, Employee employee) {
        this.id = id;
        this.customer = customer;
        this.employee = employee;
        this.date = new Date();
    }

    public void addDetail(OrderDetail detail) {
        details.add(detail);
    }

    public double calculateTotal() {
        return details.stream().mapToDouble(OrderDetail::calculateSubtotal).sum();
    }

    @Override
    public String toString() {
        return "Order #" + id + " | Customer: " + customer + " | Employee: " + employee + " | Total: $" + calculateTotal();
    }
}
