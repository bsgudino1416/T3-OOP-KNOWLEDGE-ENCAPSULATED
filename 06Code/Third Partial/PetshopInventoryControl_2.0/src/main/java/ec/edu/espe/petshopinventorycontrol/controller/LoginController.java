package ec.edu.espe.petshopinventorycontrol.controller;

import ec.edu.espe.petshopinventorycontrol.model.services.LoginRequest;
import ec.edu.espe.petshopinventorycontrol.model.services.UserAccount;
import java.util.Optional;

public final class LoginController {

    private final LoginView view;

    public LoginController(LoginView view) {
        this.view = view;
    }

    public void onSignIn() {
        try {
            String username = view.getUsername();
            String password = view.getPassword();

            if (username == null || username.trim().isEmpty()
                    || password == null || password.isEmpty()) {
                view.showWarning("Ingresa usuario y contrasena.");
                return;
            }

            LoginRequest request = new LoginRequest(username, password);
            Optional<UserAccount> userOpt = AuthDependencies
                    .getAuthService()
                    .login(request);

            if (userOpt.isEmpty()) {
                view.showWarning("Usuario o contrasena incorrectos.");
                return;
            }

            UserAccount user = userOpt.get();
            String fullName = user.getFirstName() + " " + user.getLastName();
            view.showInfo("Inicio de sesion correcto. Bienvenido, " + fullName + "!");
            view.openLobby();
            view.close();
        } catch (Exception ex) {
            view.showError("Error inesperado: " + ex.getMessage());
        }
    }

    public void onCreateAccount() {
        view.openRegister();
        view.close();
    }
}
