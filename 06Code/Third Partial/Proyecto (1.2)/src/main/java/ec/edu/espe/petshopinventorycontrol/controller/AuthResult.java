package ec.edu.espe.petshopinventorycontrol.controller;

import ec.edu.espe.petshopinventorycontrol.model.services.UserAccount;

public final class AuthResult {

    public enum Status {
        SUCCESS,
        INVALID_CREDENTIALS,
        TWO_FACTOR_REQUIRED,
        TWO_FACTOR_INVALID
    }

    private final Status status;
    private final UserAccount user;

    private AuthResult(Status status, UserAccount user) {
        this.status = status;
        this.user = user;
    }

    public static AuthResult success(UserAccount user) {
        return new AuthResult(Status.SUCCESS, user);
    }

    public static AuthResult invalid() {
        return new AuthResult(Status.INVALID_CREDENTIALS, null);
    }

    public static AuthResult twoFactorRequired(UserAccount user) {
        return new AuthResult(Status.TWO_FACTOR_REQUIRED, user);
    }

    public static AuthResult twoFactorInvalid(UserAccount user) {
        return new AuthResult(Status.TWO_FACTOR_INVALID, user);
    }

    public Status getStatus() {
        return status;
    }

    public UserAccount getUser() {
        return user;
    }
}
