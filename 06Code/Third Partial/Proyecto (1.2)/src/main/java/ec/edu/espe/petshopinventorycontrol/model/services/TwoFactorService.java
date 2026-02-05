package ec.edu.espe.petshopinventorycontrol.model.services;

import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public final class TwoFactorService {

    private static final int SECRET_BYTES = 20;
    private static final int DIGITS = 6;
    private static final int TIME_STEP_SECONDS = 30;
    private static final int WINDOW = 1;

    private final CryptoService cryptoService;
    private final SecureRandom random = new SecureRandom();
    private final String issuer;

    public TwoFactorService(CryptoService cryptoService, String issuer) {
        this.cryptoService = cryptoService;
        this.issuer = issuer == null || issuer.isBlank() ? "PetshopInventoryControl" : issuer.trim();
    }

    public TwoFactorSetup createSetup(String username) {
        String secret = generateSecret();
        String uri = buildOtpAuthUri(username, secret);
        return new TwoFactorSetup(secret, uri);
    }

    public boolean verifyCode(String secretBase32, String code) {
        if (secretBase32 == null || secretBase32.isBlank()) {
            return false;
        }
        String normalized = normalizeCode(code);
        if (normalized == null) {
            return false;
        }
        byte[] key = Base32Codec.decode(secretBase32);
        long timeStep = System.currentTimeMillis() / 1000L / TIME_STEP_SECONDS;
        for (int i = -WINDOW; i <= WINDOW; i++) {
            String expected = generateTotp(key, timeStep + i);
            if (normalized.equals(expected)) {
                return true;
            }
        }
        return false;
    }

    public boolean verifyEncrypted(String encryptedSecret, String code) {
        if (encryptedSecret == null || encryptedSecret.isBlank()) {
            return false;
        }
        String secret = cryptoService.decryptFromBase64(encryptedSecret);
        return verifyCode(secret, code);
    }

    public String encryptSecret(String secretBase32) {
        return cryptoService.encryptToBase64(secretBase32);
    }

    public String buildOtpAuthUri(String username, String secretBase32) {
        String user = username == null ? "" : username.trim();
        String label = issuer + ":" + user;
        String encodedLabel = urlEncode(label);
        String encodedIssuer = urlEncode(issuer);
        return "otpauth://totp/" + encodedLabel
                + "?secret=" + secretBase32
                + "&issuer=" + encodedIssuer
                + "&algorithm=SHA1"
                + "&digits=" + DIGITS
                + "&period=" + TIME_STEP_SECONDS;
    }

    private String generateSecret() {
        byte[] bytes = new byte[SECRET_BYTES];
        random.nextBytes(bytes);
        return Base32Codec.encode(bytes);
    }

    private String generateTotp(byte[] key, long timestep) {
        try {
            byte[] data = ByteBuffer.allocate(8).putLong(timestep).array();
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(new SecretKeySpec(key, "HmacSHA1"));
            byte[] hash = mac.doFinal(data);
            int offset = hash[hash.length - 1] & 0x0f;
            int binary = ((hash[offset] & 0x7f) << 24)
                    | ((hash[offset + 1] & 0xff) << 16)
                    | ((hash[offset + 2] & 0xff) << 8)
                    | (hash[offset + 3] & 0xff);
            int otp = binary % (int) Math.pow(10, DIGITS);
            return String.format("%0" + DIGITS + "d", otp);
        } catch (Exception ex) {
            throw new IllegalStateException("No se pudo generar el TOTP", ex);
        }
    }

    private String normalizeCode(String code) {
        if (code == null) {
            return null;
        }
        String cleaned = code.replaceAll("\\s+", "");
        if (!cleaned.matches("\\d{" + DIGITS + "}")) {
            return null;
        }
        return cleaned;
    }

    private String urlEncode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }
}
