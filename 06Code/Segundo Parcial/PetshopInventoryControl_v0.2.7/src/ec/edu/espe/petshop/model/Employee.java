package ec.edu.espe.petshop.model;

public class Employee {
    private int id;
    private String name;
    private String role;

    public Employee(int id, String name, String role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }

    @Override
    public String toString() {
        return "Employee: " + name + " | Role: " + role;
    }
}
