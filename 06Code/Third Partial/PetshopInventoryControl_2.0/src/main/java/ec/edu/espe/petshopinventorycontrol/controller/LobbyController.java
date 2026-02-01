package ec.edu.espe.petshopinventorycontrol.controller;

import java.text.DecimalFormat;
import java.time.Year;
import javax.swing.JOptionPane;

public final class LobbyController {

    private final LobbyService service;
    private final DecimalFormat moneyFormat = new DecimalFormat("$0.00");

    public LobbyController(LobbyService service) {
        this.service = service;
    }

    public void onInit(LobbyView view) {
        try {
            int year = Year.now().getValue();
            LobbyService.LobbyTotals totals = service.totalsForYear(year);
            view.setTotalInventoryText(moneyFormat.format(totals.totalInventory));
            view.setTotalBillText(moneyFormat.format(totals.totalBill));
        } catch (Exception ex) {
            view.showMessage(
                    "Error al cargar totales: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
