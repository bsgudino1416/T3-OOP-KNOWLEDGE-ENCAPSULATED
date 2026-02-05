package ec.edu.espe.petshopinventorycontrol.controller;

import ec.edu.espe.petshopinventorycontrol.model.services.RegistrationRequest;

public final class RegisterController {

    private final RegisterView view;

    public RegisterController(RegisterView view) {
        this.view = view;
    }

    public void onRegister() {
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
            view.showInfo("Cuenta creada correctamente.");
            view.openLogin();
            view.close();
        } catch (IllegalArgumentException ex) {
            view.showWarning(ex.getMessage());
        } catch (Exception ex) {
            view.showError("Error inesperado: " + ex.getMessage());
        }
    }

    public void onExit() {
        view.openLogin();
        view.close();
    }
}
