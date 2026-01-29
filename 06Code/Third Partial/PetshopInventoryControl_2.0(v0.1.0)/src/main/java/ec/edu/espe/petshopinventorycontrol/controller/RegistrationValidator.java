package ec.edu.espe.petshopinventorycontrol.controller;

import ec.edu.espe.petshopinventorycontrol.model.services.RegistrationRequest;

public final class RegistrationValidator {

    public void validate(RegistrationRequest r) {
        requireFilled(r.getFirstName(), "El nombre es obligatorio");
        requireFilled(r.getLastName(), "El apellido es obligatorio");
        requireFilled(r.getAddress(), "La direccion es obligatoria");
        requireFilled(r.getEmail(), "El correo es obligatorio");
        requireFilled(r.getGender(), "El genero es obligatorio");
        requireFilled(r.getUsername(), "El usuario es obligatorio");
        requireFilled(r.getPassword(), "La contrasena es obligatoria");
        requireFilled(r.getConfirmPassword(), "La confirmacion de contrasena es obligatoria");

        if (!r.getPassword().equals(r.getConfirmPassword())) {
            throw new IllegalArgumentException("Las contrasenas no coinciden");
        }

        // Tu regla minima:
        if (r.getPassword().length() < 1) {
            throw new IllegalArgumentException("La contrasena debe tener al menos 1 caracter");
        }
    }

    private void requireFilled(String value, String message) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }
}
