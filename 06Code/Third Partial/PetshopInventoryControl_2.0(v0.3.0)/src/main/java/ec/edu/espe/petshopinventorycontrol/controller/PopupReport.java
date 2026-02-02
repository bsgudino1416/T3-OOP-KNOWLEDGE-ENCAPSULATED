package ec.edu.espe.petshopinventorycontrol.controller;


import ec.edu.espe.petshopinventorycontrol.model.Product;
import ec.edu.espe.petshopinventorycontrol.model.ReportGenerator;
import java.util.List;
import javax.swing.JOptionPane;

public class PopupReport extends ReportGenerator {

    private final StringBuilder sb = new StringBuilder();

    @Override
    protected void printHeader() {
        sb.setLength(0);
        sb.append("=== REPORTE RÁPIDO ===\n\n");
    }

    @Override
    protected void printBody(List<Product> products) {
        for (Product p : products) {
            if (p.getStock() < 10) { 
                sb.append("⚠️ BAJO STOCK: ").append(formatProduct(p)).append("\n");
            } else {
                sb.append("✅ ").append(formatProduct(p)).append("\n");
            }
        }
    }

    @Override
    protected void printFooter(int totalItems) {
        sb.append("\nTotal items registrados: ").append(totalItems);
        JOptionPane.showMessageDialog(null, sb.toString(), "Reporte de Inventario", JOptionPane.INFORMATION_MESSAGE);
    }
}