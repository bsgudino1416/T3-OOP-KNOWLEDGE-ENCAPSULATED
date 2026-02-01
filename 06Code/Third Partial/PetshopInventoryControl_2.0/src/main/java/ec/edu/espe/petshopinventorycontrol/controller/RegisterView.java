package ec.edu.espe.petshopinventorycontrol.controller;

public interface RegisterView {
    String getFirstName();
    String getLastName();
    String getAddress();
    String getEmail();
    String getGender();
    String getUsername();
    String getPassword();
    String getConfirmPassword();
    void showInfo(String message);
    void showWarning(String message);
    void showError(String message);
    void openLogin();
    void close();
}
