
package ec.edu.espe.petshopinventorycontrol.model.services;

/**
 *
 * @author Steven Loza @ESPE
 */
public final class RegistrationRequest {

    private final String firstName;
    private final String lastName;
    private final String address;
    private final String email;
    private final String gender;
    private final String username;
    private final String password;
    private final String confirmPassword;

    public RegistrationRequest(String firstName, String lastName, String address, String email,
            String gender, String username, String password, String confirmPassword) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.email = email;
        this.gender = gender;
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
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

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }
}
