package ec.edu.espe.petshopinventorycontrol.controller;

public interface LoginView {
    String getUsername();
    String getPassword();
    void showMessage(String message, String title, int messageType);
    void openRegister();
    void close();
}
