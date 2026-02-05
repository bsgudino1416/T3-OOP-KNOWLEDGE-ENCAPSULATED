package ec.edu.espe.petshopinventorycontrol.model.services;

public record TwoFactorSetup(String secretBase32, String otpAuthUri) {
}
