package ec.edu.espe.petshop.model;


public class Report {
    public void generateInventoryReport(Inventory inv) {
        System.out.println("\n=== REPORTE DE INVENTARIO ===");
        inv.showInventory();
    }
}
