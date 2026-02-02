package ec.edu.espe.petshopinventorycontrol.model;

import java.util.List;

public class ConsoleReport extends ReportGenerator {

    @Override
    protected void printHeader() {
        System.out.println("=========================================");
        System.out.println("       REPORTE DE INVENTARIO (CONSOLA)   ");
        System.out.println("=========================================");
    }

    @Override
    protected void printBody(List<Product> products) {
        if (products.isEmpty()) {
            System.out.println("  No hay productos.");
            return;
        }
        for (Product p : products) {
            System.out.println(formatProduct(p));
        }
    }

    @Override
    protected void printFooter(int totalItems) {
        System.out.println("-----------------------------------------");
        System.out.println(" Total Productos: " + totalItems);
        System.out.println("=========================================\n");
    }
}
