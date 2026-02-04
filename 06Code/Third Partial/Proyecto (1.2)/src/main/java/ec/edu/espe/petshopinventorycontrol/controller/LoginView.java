package ec.edu.espe.petshopinventorycontrol.controller;

public interface LoginView {
    String getUsername();
    String getPassword();
    void showWarning(String message);
    void showInfo(String message);
    void showError(String message);
    boolean confirmEnableTwoFactor();
    String promptTwoFactorCode(String message);
    void showTwoFactorSetup(java.awt.image.BufferedImage qrImage, String secret, String uri);
    void openLobby();
    void openRegister();
    void close();
}
