package ec.edu.espe.petshop.model;

public class Supplier {
    private int id;
    private String name;
    private String contact;
    private String phone;

    public Supplier(int id, String name, String contact, String phone) {
        this.id = id;
        this.name = name;
        this.contact = contact;
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Supplier: " + name + " | Contact: " + contact + " | Phone: " + phone;
    }
}
