package ec.edu.espe.petshop.model;

public class Product {
    private String id;             // Ejemplo: CPP01
    private String category;       // FOOD, MEDICINE, SNACK, ACCESORIES
    private String animalType;     // perro pequeño, gato grande, etc.
    private String supplier;       // DogChown, Perro, etc.

    public Product(String id, String category, String animalType, String supplier) {
        this.id = id;
        this.category = category;
        this.animalType = animalType;
        this.supplier = supplier;
    }

    public String getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public String getAnimalType() {
        return animalType;
    }

    public String getSupplier() {
        return supplier;
    }

    @Override
    public String toString() {
        return "Product ID: " + id +
               " | Category: " + category +
               " | Type: " + animalType +
               " | Supplier: " + supplier;
    }
}

