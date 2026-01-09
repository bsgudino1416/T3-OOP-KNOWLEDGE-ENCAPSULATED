
package ec.edu.espe.petshop.model;

public class OrderDetail {
    private Product product;
    private int quantity;
    private double subtotal;

    public OrderDetail(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        // corregido: usar el getter getPrice()
        this.subtotal = quantity * product.getPrice();
    }

    public double getSubtotal() { return subtotal; }

    @Override
    public String toString() {
        return product + " x" + quantity + " = $" + subtotal;
    }
}
