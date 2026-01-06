package ec.edu.espe.petshop.model;

public class AnimalCategory {
    private int id;
    private String type;
    private String description;

    public AnimalCategory(int id, String type, String description) {
        this.id = id;
        this.type = type;
        this.description = description;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return id + " - " + type + " (" + description + ")";
    }
}
