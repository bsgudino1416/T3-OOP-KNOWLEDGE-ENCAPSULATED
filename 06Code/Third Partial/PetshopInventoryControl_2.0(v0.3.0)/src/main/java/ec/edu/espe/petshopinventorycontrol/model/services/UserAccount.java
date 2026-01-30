
package ec.edu.espe.petshopinventorycontrol.model.services;

import java.util.Objects;

/**
 *
 * @author Steven Loza @ESPE
 */
public final class UserAccount {

    private final String firstName;
    private final String lastName;
    private final String address;
    private final String email;
    private final String gender;

    // Seguridad:
    private final String usernameHash;       // Para buscar/login y unicidad
    private final String usernameEncrypted;  // cifrado como pediste (opcional pero incluido)
    private final String passwordHash;       // PBKDF2 (NO reversible)
    private final String passwordSalt;       // Base64
    private final int passwordIterations;

    public UserAccount(String firstName, String lastName, String address, String email, String gender,
            String usernameHash, String usernameEncrypted,
            String passwordHash, String passwordSalt, int passwordIterations) {

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
