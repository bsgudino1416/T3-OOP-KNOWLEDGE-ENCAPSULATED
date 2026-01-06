package ec.edu.espe.petshop.model;

import java.util.Date;

public class Bill {
    private int id;
    private Date date;
    private Order order;

    public Bill(int id, Order order) {
        this.id = id;
        this.order = order;
        this.date = new Date();
    }

    public void showInvoice() {
        System.out.println("\n=== BILL #" + id + " ===");
        System.out.println(order);
        System.out.println("Date: " + date);
        System.out.println("Total: $" + order.calculateTotal());
    }
}
