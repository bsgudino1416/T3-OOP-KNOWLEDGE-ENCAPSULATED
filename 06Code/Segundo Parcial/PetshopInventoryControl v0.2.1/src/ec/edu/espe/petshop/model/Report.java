package ec.edu.espe.petshop.model;

import java.util.*;

public class Report {
    public void generateInventoryReport(Inventory inv) {
        System.out.println("\n=== INVENTORY REPORT ===");
        inv.showInventory();
    }
}
