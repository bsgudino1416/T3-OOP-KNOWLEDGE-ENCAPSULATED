package ec.edu.espe.petshopinventorycontrol.controller;

import ec.edu.espe.petshopinventorycontrol.model.services.LoginRequest;
import ec.edu.espe.petshopinventorycontrol.model.services.UserAccount;
import java.util.Optional;

public final class LoginController {

    public void onCreateAccount(LoginView view) {
        view.openRegister();
        view.close();
    }

    public void onSignIn(LoginView view) {
        try {
            String username = view.getUsername();
            String password = view.getPassword();

            if (username == null || username.trim().isEmpty()
                    || password == null || password.isEmpty()) {
                view.showMessage(
                        "Ingresa usuario y contrasena.",
                        "Error de validacion",
                        javax.swing.JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            LoginRequest request = new LoginRequest(username, password);
            Optional<UserAccount> userOpt = AuthDependencies.getAuthService().login(request);

            if (userOpt.isEmpty()) {
                view.showMessage(
                        "Usuario o contrasena incorrectos.",
                        "Acceso denegado",
                        javax.swing.JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            UserAccount user = userOpt.get();
            String fullName = user.getFirstName() + " " + user.getLastName();
            view.showMessage(
                    "Inicio de sesion correcto. Bienvenido, " + fullName + "!",
                    "Bienvenido",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE
            );
        } catch (Exception ex) {
            view.showMessage(
                    "Error inesperado: " + ex.getMessage(),
                    "Error",
                    javax.swing.JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
