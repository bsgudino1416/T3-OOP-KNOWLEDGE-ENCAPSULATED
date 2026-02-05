
package ec.edu.espe.petshopinventorycontrol.model.services;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
/**
 *
 * @author Steven Loza @ESPE
 */
public final class AesGcmCryptoService implements CryptoService {

    private static final int IV_LENGTH = 12;
    private static final int TAG_BITS = 128;

    private final SecretKey key;
    private final SecureRandom random = new SecureRandom();

    public AesGcmCryptoService(SecretKey key) {
        this.key = key;
    }

    @Override
    public String encryptToBase64(String plainText) {
        if (plainText == null) throw new IllegalArgumentException("plainText cannot be null");
        try {
            byte[] iv = new byte[IV_LENGTH];
            random.nextBytes(iv);

            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, key, new GCMParameterSpec(TAG_BITS, iv));

            byte[] cipherBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

            ByteBuffer bb = ByteBuffer.allocate(iv.length + cipherBytes.length);
            bb.put(iv);
            bb.put(cipherBytes);

            return Base64.getEncoder().encodeToString(bb.array());
        } catch (Exception e) {
            throw new IllegalStateException("Unable to encrypt", e);
        }
    }

    @Override
    public String decryptFromBase64(String base64CipherText) {
        if (base64CipherText == null) throw new IllegalArgumentException("cipherText cannot be null");
        try {
            byte[] all = Base64.getDecoder().decode(base64CipherText);
            ByteBuffer bb = ByteBuffer.wrap(all);

            byte[] iv = new byte[IV_LENGTH];
            bb.get(iv);

            byte[] cipherBytes = new byte[bb.remaining()];
            bb.get(cipherBytes);

            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, key, new GCMParameterSpec(TAG_BITS, iv));

            byte[] plain = cipher.doFinal(cipherBytes);
            return new String(plain, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new IllegalStateException("Unable to decrypt", e);
        }
    }
}