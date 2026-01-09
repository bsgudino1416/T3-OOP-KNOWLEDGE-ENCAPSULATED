<<<<<<< HEAD
package ec.edu.espe.petshopinventorycontrol.model;

/**
 * Order item with subtotal calculation.
 */
public class OrderDetail {

    private Product product;
    private int quantity;
    private double subtotal;

    public OrderDetail() {
    }

    public OrderDetail(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        this.subtotal = quantity * product.getPrice();
    }

    public double getSubtotal() {
        return subtotal;
    }

    @Override
    public String toString() {
        return product + " x" + quantity + " = $" + subtotal;
    }
}
=======
package ec.edu.espe.petshopinventorycontrol.model;

/**
 * Order item with subtotal calculation.
 */
public class OrderDetail {

    private Product product;
    private int quantity;
    private double subtotal;

    public OrderDetail() {
    }

    public OrderDetail(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        this.subtotal = quantity * product.getPrice();
    }

    public double getSubtotal() {
        return subtotal;
    }

    @Override
    public String toString() {
        return product + " x" + quantity + " = $" + subtotal;
    }
}
>>>>>>> 23a19a791ee45b0b7a40ad42c0dd08930a62491d
