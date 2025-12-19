package ec.edu.espe.petshopinventorycontrol.employee.model;


/**
 *
 * @author Bryan Gudino, KNOWLEDGE ENCAPSULATE, @ESPE
 */
public class PurchaseProduct {
    
    
    private String id;
    private String product;
    private String animal;
    private String size;
    private String unit;
    private int quantity;
    private int stock;
    private double subtotal;

    public PurchaseProduct(String id, String product, String animal,
                           String size, String unit,
                           int quantity, int stock, double subtotal) {
        this.id = id;
        this.product = product;
        this.animal = animal;
        this.size = size;
        this.unit = unit;
        this.quantity = quantity;
        this.stock = stock;
        this.subtotal = subtotal;
    }


    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getProduct() { return product; }
    public void setProduct(String product) { this.product = product; }

    public String getAnimal() { return animal; }
    public void setAnimal(String animal) { this.animal = animal; }

    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public double getSubtotal() { return subtotal; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }
    
}
