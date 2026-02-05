package ec.edu.espe.petshopinventorycontrol.controller;

public interface LobbyView {

    void setTotalInventoryText(String value);

    void setTotalBillText(String value);

    void showMessage(String message, String title, int messageType);
}
