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

    void showMessage(String message, String title, int messageType);
    void openLogin();
    void close();
}
