
package ec.edu.espe.petshopinventorycontrol.model.services;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
/**
 *
 * @author Steven Loza @ESPE
 */
public final class HashingService {

    public String sha256Base64(String input) {
        if (input == null) throw new IllegalArgumentException("La entrada no puede ser nula");
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(input.trim().toLowerCase().getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(digest);
        } catch (Exception e) {
            throw new IllegalStateException("No se pudo generar el hash", e);
        }
    }
}
