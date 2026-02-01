package ec.edu.espe.petshopinventorycontrol.controller;

import ec.edu.espe.petshopinventorycontrol.model.services.LoginRequest;
import ec.edu.espe.petshopinventorycontrol.model.services.TwoFactorSetup;
import ec.edu.espe.petshopinventorycontrol.model.services.UserAccount;

public final class LoginController {

    private final LoginView view;
    private final QrCodeService qrCodeService = new QrCodeService();

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
            AuthService authService = AuthDependencies.getAuthService();
            AuthResult result = authService.authenticate(request, null);

            if (result.getStatus() == AuthResult.Status.INVALID_CREDENTIALS) {
                view.showWarning("Usuario o contrasena incorrectos.");
                return;
            }

            if (result.getStatus() == AuthResult.Status.TWO_FACTOR_REQUIRED) {
                String code = view.promptTwoFactorCode("Ingresa el codigo de Google Authenticator:");
                AuthResult totpResult = authService.authenticate(request, code);
                if (totpResult.getStatus() == AuthResult.Status.TWO_FACTOR_INVALID) {
                    view.showWarning("Codigo 2FA invalido.");
                    return;
                }
                if (totpResult.getStatus() != AuthResult.Status.SUCCESS) {
                    view.showWarning("No se pudo validar el segundo factor.");
                    return;
                }
                result = totpResult;
            }

            if (result.getStatus() != AuthResult.Status.SUCCESS) {
                view.showWarning("No se pudo iniciar sesion.");
                return;
            }

            UserAccount user = result.getUser();
            if (user != null && !user.isTwoFactorEnabled() && view.confirmEnableTwoFactor()) {
                TwoFactorSetup setup = authService.beginTwoFactorSetup(username);
                try {
                    view.showTwoFactorSetup(
                            qrCodeService.generate(setup.otpAuthUri(), 220),
                            setup.secretBase32(),
                            setup.otpAuthUri()
                    );
                } catch (Exception ex) {
                    view.showError("No se pudo generar el QR: " + ex.getMessage());
                }
                String verifyCode = view.promptTwoFactorCode("Ingresa el codigo para activar 2FA:");
                boolean enabled = authService.enableTwoFactor(user, setup.secretBase32(), verifyCode);
                if (enabled) {
                    view.showInfo("2FA activado correctamente.");
                } else {
                    view.showWarning("No se pudo activar 2FA. Codigo invalido.");
                }
            }

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
