
package ec.edu.espe.petshopinventorycontrol.model.services;

/**
 *
 * @author Steven Loza @ESPE
 */
public final class LoginRequest {

    private final String username;
    private final String password;

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
