package ec.edu.espe.petshopinventorycontrol.model.services;

import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public final class PasswordHasher {

    private static final int SALT_BYTES = 16;
    private static final int KEY_BITS = 256;
    private static final int DEFAULT_ITERATIONS = 120_000;

    private final SecureRandom random = new SecureRandom();

    public record HashResult(String hashBase64, String saltBase64, int iterations) {}

    public HashResult hash(char[] password) {
        if (password == null || password.length == 0) {
            throw new IllegalArgumentException("La contrasena no puede estar vacia");
        }

        byte[] salt = new byte[SALT_BYTES];
        random.nextBytes(salt);

        int iterations = DEFAULT_ITERATIONS;
        byte[] hash = pbkdf2(password, salt, iterations);

        return new HashResult(
                Base64.getEncoder().encodeToString(hash),
                Base64.getEncoder().encodeToString(salt),
                iterations
        );
    }

    public boolean verify(char[] password, String saltBase64, int iterations, String expectedHashBase64) {
        if (password == null) return false;
        byte[] salt = Base64.getDecoder().decode(saltBase64);
        byte[] expected = Base64.getDecoder().decode(expectedHashBase64);

        byte[] actual = pbkdf2(password, salt, iterations);

       
        if (actual.length != expected.length) return false;
        int diff = 0;
        for (int i = 0; i < actual.length; i++) diff |= (actual[i] ^ expected[i]);
        return diff == 0;
    }

    private byte[] pbkdf2(char[] password, byte[] salt, int iterations) {
        try {
            PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, KEY_BITS);
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            return skf.generateSecret(spec).getEncoded();
        } catch (Exception e) {
            throw new IllegalStateException("No se pudo generar el hash de la contrasena", e);
        }
    }
}
