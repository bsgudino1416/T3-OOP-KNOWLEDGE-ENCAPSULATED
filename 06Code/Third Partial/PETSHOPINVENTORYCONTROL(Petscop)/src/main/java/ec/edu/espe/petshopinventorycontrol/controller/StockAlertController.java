package ec.edu.espe.petshopinventorycontrol.controller;

import ec.edu.espe.petshopinventorycontrol.model.StockAlertManager;
import ec.edu.espe.petshopinventorycontrol.view.StockAlertView;

public class StockAlertController {

    private final StockAlertManager alertManager;
    private final StockAlertView alertView;

    public StockAlertController() {
        this.alertManager = StockAlertManager.getInstance();
        this.alertView = new StockAlertView();
    }

    public void verifyAndAlert(String productName, int quantity) {
        if (alertManager.requiresRestock(quantity)) {
            alertView.showLowStockWarning(productName, quantity);
        }
    }
}