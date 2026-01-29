
package ec.edu.espe.petshopinventorycontrol.model.services;

/**
 *
 * @author Steven Loza @ESPE
 */

public interface CryptoService {
    String encryptToBase64(String plainText);
    String decryptFromBase64(String base64CipherText);
}
