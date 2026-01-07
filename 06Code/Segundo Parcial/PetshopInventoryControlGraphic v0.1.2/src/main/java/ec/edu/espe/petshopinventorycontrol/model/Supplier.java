<<<<<<< HEAD
package ec.edu.espe.petshopinventorycontrol.model;

public class Supplier {

    private int id;
    private String name;
    private String contact;
    private String phone;

    public Supplier() {
    }

    public Supplier(int id, String name, String contact, String phone) {
        this.id = id;
        this.name = name;
        this.contact = contact;
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Proveedor: " + name + " | Contacto: " + contact + " | Teléfono: " + phone;
    }
}
=======
package ec.edu.espe.petshopinventorycontrol.model;

public class Supplier {

    private int id;
    private String name;
    private String contact;
    private String phone;

    public Supplier() {
    }

    public Supplier(int id, String name, String contact, String phone) {
        this.id = id;
        this.name = name;
        this.contact = contact;
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Proveedor: " + name + " | Contacto: " + contact + " | Teléfono: " + phone;
    }
}
>>>>>>> 23a19a791ee45b0b7a40ad42c0dd08930a62491d
