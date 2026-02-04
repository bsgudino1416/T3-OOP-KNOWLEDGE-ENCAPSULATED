
package ec.edu.espe.petshopinventorycontrol.model.services;

import java.util.Objects;


public final class UserAccount {

    private final String firstName;
    private final String lastName;
    private final String address;
    private final String email;
    private final String gender;

    // Seguridad:
    private final String usernameHash;       
    private final String usernameEncrypted; 
    private final String passwordHash;       
    private final String passwordSalt;       
    private final int passwordIterations;
    private final boolean twoFactorEnabled;
    private final String twoFactorSecretEncrypted;

    public UserAccount(String firstName, String lastName, String address, String email, String gender,
            String usernameHash, String usernameEncrypted,
            String passwordHash, String passwordSalt, int passwordIterations) {
        this(firstName, lastName, address, email, gender,
                usernameHash, usernameEncrypted,
                passwordHash, passwordSalt, passwordIterations,
                false, null);
    }

    public UserAccount(String firstName, String lastName, String address, String email, String gender,
            String usernameHash, String usernameEncrypted,
            String passwordHash, String passwordSalt, int passwordIterations,
            boolean twoFactorEnabled, String twoFactorSecretEncrypted) {

        this.firstName = requireNonBlank(firstName, "firstName");
        this.lastName = requireNonBlank(lastName, "lastName");
        this.address = requireNonBlank(address, "address");
        this.email = requireNonBlank(email, "email");
        this.gender = requireNonBlank(gender, "gender");

        this.usernameHash = requireNonBlank(usernameHash, "usernameHash");
        this.usernameEncrypted = requireNonBlank(usernameEncrypted, "usernameEncrypted");

        this.passwordHash = requireNonBlank(passwordHash, "passwordHash");
        this.passwordSalt = requireNonBlank(passwordSalt, "passwordSalt");
        this.passwordIterations = passwordIterations;
        this.twoFactorEnabled = twoFactorEnabled;
        this.twoFactorSecretEncrypted = twoFactorSecretEncrypted;
    }

    private static String requireNonBlank(String v, String name) {
        if (v == null || v.trim().isEmpty()) {
            throw new IllegalArgumentException(name + " no puede estar vacio");
        }
        return v.trim();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public String getUsernameHash() {
        return usernameHash;
    }

    public String getUsernameEncrypted() {
        return usernameEncrypted;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getPasswordSalt() {
        return passwordSalt;
    }

    public int getPasswordIterations() {
        return passwordIterations;
    }

    public boolean isTwoFactorEnabled() {
        return twoFactorEnabled;
    }

    public String getTwoFactorSecretEncrypted() {
        return twoFactorSecretEncrypted;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof UserAccount other)) {
            return false;
        }
        return Objects.equals(usernameHash, other.usernameHash);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usernameHash);
    }
}
