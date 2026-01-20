package ec.edu.espe.model;

public class Producto {
    private String nombre;
    private String categoria;
    private int cantidad;

    public Producto(String nombre, String categoria, int cantidad) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.cantidad = cantidad;
    }

    // --- GETTERS (Para leer los datos) ---
    public String getNombre() { 
        return nombre; 
    }
    
    public String getCategoria() { 
        return categoria; 
    }
    
    public int getCantidad() { 
        return cantidad; 
    }

    // --- SETTERS (Para modificar los datos, como restar stock) ---
    public void setCantidad(int cantidad) { 
        this.cantidad = cantidad; 
    }

    @Override
    public String toString() {
        return String.format("%s (%s) - Stock: %d", nombre, categoria, cantidad);
    }
}