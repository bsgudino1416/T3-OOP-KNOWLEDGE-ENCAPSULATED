package ec.edu.espe.petshopinventorycontrol.controller;

import ec.edu.espe.petshopinventorycontrol.model.services.RegistrationRequest;

public final class RegisterController {

    public void onRegister(RegisterView view) {
        try {
            RegistrationRequest request = new RegistrationRequest(
                    view.getFirstName(),
                    view.getLastName(),
                    view.getAddress(),
                    view.getEmail(),
                    view.getGender(),
                    view.getUsername(),
                    view.getPassword(),
                    view.getConfirmPassword()
            );

            AuthDependencies.getAuthService().register(request);

            view.showMessage(
                    "Cuenta creada correctamente.",
                    "Exito",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE
            );

            view.openLogin();
            view.close();
        } catch (IllegalArgumentException ex) {
            view.showMessage(
                    ex.getMessage(),
                    "Error de validacion",
                    javax.swing.JOptionPane.WARNING_MESSAGE
            );
        } catch (IllegalStateException ex) {
            view.showMessage(
                    ex.getMessage(),
                    "Error de registro",
                    javax.swing.JOptionPane.ERROR_MESSAGE
            );
        } catch (Exception ex) {
            view.showMessage(
                    "Error inesperado: " + ex.getMessage(),
                    "Error",
                    javax.swing.JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public void onExit(RegisterView view) {
        view.openLogin();
        view.close();
    }
}
