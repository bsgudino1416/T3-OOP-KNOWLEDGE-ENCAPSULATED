package ec.edu.espe.petshopinventorycontrol.model;

public class StockAlertManager {
    
    private static StockAlertManager instance;
    private final int MINIMUM_STOCK_THRESHOLD = 5;

    private StockAlertManager() {
    }

    public static StockAlertManager getInstance() {
        if (instance == null) {
            instance = new StockAlertManager();
        }
        return instance;
    }

    public boolean requiresRestock(int quantity) {
        return quantity <= MINIMUM_STOCK_THRESHOLD;
    }
}