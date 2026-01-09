package ec.edu.espe.petshopinventorycontrol.model;

public class Report {

    public void generateInventoryReport(Inventory inv) {
        System.out.println("\n=== REPORTE DE INVENTARIO ===");
        inv.showInventory();
    }
}
