package ec.edu.espe.petshopinventorycontrol.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {

    private int id;
    private Customer customer;
    private Employee employee;
    private Date date;
    private List<OrderDetail> details;

    public Order() {
        this.details = new ArrayList<>();
    }

    public Order(int id, Customer customer, Employee employee, Date date) {
        this.id = 1;
        this.customer = customer;
        this.employee = employee;
        this.date = date;
        this.details = new ArrayList<>();
    }

    public void addDetail(OrderDetail detail) {
        details.add(detail);
    }

    public double calculateTotal() {
        return details.stream().mapToDouble(OrderDetail::getSubtotal).sum();
    }

    @Override
    public String toString() {
        return "Pedido #" + id + " | Cliente: " + customer + " | Total: $" + calculateTotal();
    }
}
