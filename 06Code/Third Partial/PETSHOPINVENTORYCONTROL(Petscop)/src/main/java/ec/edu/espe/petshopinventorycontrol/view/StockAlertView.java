package ec.edu.espe.petshopinventorycontrol.view;

import javax.swing.JOptionPane;

public class StockAlertView {
    
    public void showLowStockWarning(String productName, int currentQuantity) {
        String message = "Low stock warning for: " + productName + "\n" +
                         "Current quantity: " + currentQuantity + "\n" +
                         "Please restock immediately.";
        
        JOptionPane.showMessageDialog(null, message, "Stock Alert System", JOptionPane.WARNING_MESSAGE);
    }
}