package ec.edu.espe.petshopinventorycontrol.model;

import java.time.LocalDate;

public class Employee {

    private String id;
    private String username;
    private String password;
    private String role;      // Ej: "empleado", "gerente"
    private LocalDate createdAt;

    public Employee() {
    }

    public Employee(String id, String username, String password, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.createdAt = LocalDate.now();
    }

    public Employee(String id, String username, String password, String role, LocalDate createdAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "Empleado {" +
                "id='" + id + '\'' +
                ", usuario='" + username + '\'' +
                ", rol='" + role + '\'' +
                ", creadoEl=" + createdAt +
                '}';
    }
}
