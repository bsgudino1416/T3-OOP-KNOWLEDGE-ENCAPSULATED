package ec.edu.espe.controller;

import ec.edu.espe.model.InventarioManager;
import ec.edu.espe.model.Producto;
import ec.edu.espe.view.PetShopView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class PetShopController {

    private PetShopView vista;

    public PetShopController(PetShopView vista) {
        this.vista = vista;
        
        
        this.vista.addAgregarListener(new AgregarListener());
        this.vista.addActualizarListener(new ActualizarListener());
        this.vista.addVenderListener(new VenderListener());
        
        // Carga inicial de la lista
        actualizarVista();
    }

    
    class AgregarListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                // Pedimos datos a la vista
                String nombre = vista.getNombre();
                String categoria = vista.getCategoria();
                int cantidad = vista.getCantidad(); // Esto puede fallar si está vacío

                if(nombre.isEmpty()) {
                    vista.mostrarError("El nombre no puede estar vacío.");
                    return;
                }

                // Creamos el producto y lo guardamos en el Singleton
                Producto nuevo = new Producto(nombre, categoria, cantidad);
                InventarioManager.getInstance().agregarProducto(nuevo);
                
                // Limpiamos y refrescamos
                vista.limpiarCampos();
                actualizarVista();
                
            } catch (NumberFormatException ex) {
                vista.mostrarError("Por favor ingresa una cantidad numérica válida.");
            }
        }
    }

    // ==========================================
    // 2. LÓGICA PARA EL BOTÓN "REFRESCAR"
    // ==========================================
    class ActualizarListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            actualizarVista();
        }
    }
    
    // ==========================================
    // 3. LÓGICA AVANZADA: VENDER + ALARMA
    // ==========================================
    class VenderListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String nombre = vista.getNombre();
                int cantidadDeseada = vista.getCantidad();
                
                // Llamamos al Singleton una sola vez
                InventarioManager manager = InventarioManager.getInstance();
                
                // A) VALIDACIÓN MANUAL: ¿Existe el producto?
                Producto productoEncontrado = buscarProductoInterno(nombre);
                
                if (productoEncontrado == null) {
                    vista.mostrarError("El producto '" + nombre + "' no existe.\nVerifique mayúsculas y minúsculas.");
                    return;
                }

                // B) VALIDACIÓN DE STOCK: ¿Alcanza?
                if (productoEncontrado.getCantidad() < cantidadDeseada) {
                    vista.mostrarError("No puedes vender esa cantidad.\n" + 
                                       "Stock actual: " + productoEncontrado.getCantidad());
                    return;
                }

                // C) EJECUTAR VENTA
                manager.venderProducto(nombre, cantidadDeseada);
                actualizarVista();
                vista.limpiarCampos();
                
               
                if (manager.verificarStockCritico(nombre)) {
                    vista.mostrarAlertaStock(
                        "¡AVISO DE REABASTECIMIENTO!\n" +
                        "El producto '" + nombre + "' tiene poco stock (" + productoEncontrado.getCantidad() + ").\n" +
                        "Categoría: " + productoEncontrado.getCantidad()
                    );
                }

            } catch (NumberFormatException ex) {
                vista.mostrarError("Ingrese un número válido para vender.");
            }
        }
    }
    
    // ==========================================
    // MÉTODOS AUXILIARES
    // ==========================================
    
    // Pinta la lista de productos en el área blanca de la ventana
    private void actualizarVista() {
        List<Producto> lista = InventarioManager.getInstance().getProductos();
        StringBuilder sb = new StringBuilder();
        sb.append("--- INVENTARIO ACTUALIZADO ---\n");
        
        if (lista.isEmpty()) {
            sb.append("(Inventario vacío)");
        } else {
            for (Producto p : lista) {
                sb.append(p.toString()).append("\n");
            }
        }
        vista.setDisplay(sb.toString());
    }
    
    // Ayuda a buscar un producto sin llamar a lógica de negocio compleja
    private Producto buscarProductoInterno(String nombre) {
        for (Producto p : InventarioManager.getInstance().getProductos()) {
            if (p.getNombre().equalsIgnoreCase(nombre)) {
                return p;
            }
        }
        return null;
    }
}