package ec.edu.espe.petshop.model;

public class OrderDetail {
    private Product product;
    private int quantity;

    public OrderDetail(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    // Ya no hay precio, pero dejamos el método para evitar errores
    public double calculateSubtotal() {
        return 0.0; // sin cálculo por ahora
    }

    @Override
    public String toString() {
        return product.toString() + " | Quantity: " + quantity;
    }
}

