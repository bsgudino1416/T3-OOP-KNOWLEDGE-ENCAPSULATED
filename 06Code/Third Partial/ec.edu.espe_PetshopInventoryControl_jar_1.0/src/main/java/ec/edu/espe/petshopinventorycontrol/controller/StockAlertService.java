package ec.edu.espe.petshopinventorycontrol.controller;

import ec.edu.espe.petshopinventorycontrol.model.Product;
import java.awt.Component;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * Clase encargada de monitorear niveles de stock y notificar alertas.
 */
public class StockAlertService {

    // Define el límite mínimo aquí (ej: 5 unidades)
    private static final int STOCK_MINIMO = 5;

    /**
     * Verifica un producto individual y lanza alerta si es necesario.
     * Úsalo después de realizar una venta.
     */
    public void checkAndAlert(Component parentComponent, Product product) {
        if (product != null && product.getStock() <= STOCK_MINIMO) {
            String message = "¡ALERTA DE STOCK BAJO!\n"
                           + "Producto: " + product.getName() + "\n"
                           + "ID: " + product.getId() + "\n"
                           + "Stock restante: " + product.getStock() + " unidades.\n"
                           + "Por favor, contacte al proveedor para reabastecer.";
            
            JOptionPane.showMessageDialog(parentComponent, message, "Advertencia de Inventario", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Devuelve una lista de todos los productos que están bajos de stock.
     * Útil para generar reportes o ver en el Lobby.
     */
    public String getLowStockSummary(List<Product> inventoryList) {
        StringBuilder report = new StringBuilder();
        boolean found = false;

        for (Product p : inventoryList) {
            if (p.getStock() <= STOCK_MINIMO) {
                report.append("- ").append(p.getName())
                      .append(" (Stock: ").append(p.getStock()).append(")\n");
                found = true;
            }
        }

        if (found) {
            return "PRODUCTOS POR AGOTARSE:\n" + report.toString();
        } else {
            return "Todo el inventario está en niveles óptimos.";
        }
    }
}