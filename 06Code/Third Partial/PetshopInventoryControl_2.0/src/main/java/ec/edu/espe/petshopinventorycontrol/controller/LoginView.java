package ec.edu.espe.petshopinventorycontrol.controller;

public interface LoginView {
    String getUsername();
    String getPassword();
    void showWarning(String message);
    void showInfo(String message);
    void showError(String message);
    void openLobby();
    void openRegister();
    void close();
}
