package ec.edu.espe.petshopinventorycontrol.model;

public class Employee {

    private int id;
    private String name;
    private String role;
    private String username;
    private String password;

    public Employee(int id, String name, String role, String username, String password) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.username = username;
        this.password = password;
    }

    Employee(int i, String luis, String clave123, String vendedor) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean login(String user, String pass) {
        return this.username.equals(user) && this.password.equals(pass);
    }

    @Override
    public String toString() {
        return "Empleado: " + name + " | Rol: " + role;
    }

    public boolean validatePassword(String string) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
