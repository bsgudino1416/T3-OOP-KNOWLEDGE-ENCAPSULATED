package ec.edu.espe.petshopinventorycontrol.controller;

import ec.edu.espe.petshopinventorycontrol.model.services.RegistrationRequest;

public final class RegistrationValidator {

    public void validate(RegistrationRequest r) {
        requireFilled(r.getFirstName(), "First name is required");
        requireFilled(r.getLastName(), "Last name is required");
        requireFilled(r.getAddress(), "Address is required");
        requireFilled(r.getEmail(), "Email is required");
        requireFilled(r.getGender(), "Gender is required");
        requireFilled(r.getUsername(), "Username is required");
        requireFilled(r.getPassword(), "Password is required");
        requireFilled(r.getConfirmPassword(), "Password confirmation is required");

        if (!r.getPassword().equals(r.getConfirmPassword())) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        // Tu regla mínima:
        if (r.getPassword().length() < 1) {
            throw new IllegalArgumentException("Password must contain at least 1 character");
        }
    }

    private void requireFilled(String value, String message) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }
}

