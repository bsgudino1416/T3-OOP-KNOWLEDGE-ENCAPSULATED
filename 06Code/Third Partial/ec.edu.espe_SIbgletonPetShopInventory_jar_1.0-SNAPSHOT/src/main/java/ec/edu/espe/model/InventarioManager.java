package ec.edu.espe.model;

import ec.edu.espe.utils.JsonFileManager; // Importamos tu nuevo util
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventarioManager {
    
    private static InventarioManager instancia;
    private List<Producto> productos;
    private Map<String, Integer> stockMinimoPorCategoria;

    private InventarioManager() {
        stockMinimoPorCategoria = new HashMap<>();
        stockMinimoPorCategoria.put("Alimento", 20);
        stockMinimoPorCategoria.put("Juguetes", 5);
        stockMinimoPorCategoria.put("Medicina", 10);
        stockMinimoPorCategoria.put("Accesorios", 3);
        
        // CAMBIO IMPORTANTE:
        // En lugar de iniciar una lista vacía, intentamos cargar del archivo JSON
        this.productos = JsonFileManager.leer();
    }

    public static InventarioManager getInstance() {
        if (instancia == null) {
            instancia = new InventarioManager();
        }
        return instancia;
    }

    public void agregarProducto(Producto p) {
        productos.add(p);
        // CAMBIO: Guardar inmediatamente después de agregar
        JsonFileManager.guardar(productos);
    }
    
    public boolean venderProducto(String nombre, int cantidadVenta) {
        for (Producto p : productos) {
            if (p.getNombre().equalsIgnoreCase(nombre)) {
                if (p.getCantidad() >= cantidadVenta) {
                    p.setCantidad(p.getCantidad() - cantidadVenta);
                    
                    // CAMBIO: Guardar inmediatamente después de vender
                    JsonFileManager.guardar(productos);
                    
                    return true;
                }
            }
        }
        return false;
    }

    // ... El resto de métodos (verificarStockCritico, getProductos) siguen igual ...
    public boolean verificarStockCritico(String nombreProducto) {
        // ... tu código anterior ...
        // (Copialo del chat anterior si no lo tienes a mano)
        for (Producto p : productos) {
            if (p.getNombre().equalsIgnoreCase(nombreProducto)) {
                String cat = p.getCategoria();
                int stockActual = p.getCantidad();
                int limite = stockMinimoPorCategoria.getOrDefault(cat, 5);
                return stockActual <= limite;
            }
        }
        return false;
    }

    public List<Producto> getProductos() { return productos; }
}