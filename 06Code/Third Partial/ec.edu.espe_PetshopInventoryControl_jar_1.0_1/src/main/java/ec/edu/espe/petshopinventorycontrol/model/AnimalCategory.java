package ec.edu.espe.petshopinventorycontrol.model;

public class AnimalCategory {

    private int id;
    private String type;
    private String description;

    public AnimalCategory(int id, String type, String description) {
        this.id = id;
        this.type = type;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Category: " + type + " - " + description;
    }
}
