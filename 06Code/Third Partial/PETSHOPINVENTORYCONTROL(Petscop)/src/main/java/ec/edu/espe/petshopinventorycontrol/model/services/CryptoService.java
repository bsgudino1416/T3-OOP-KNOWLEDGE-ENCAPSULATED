package ec.edu.espe.petshopinventorycontrol.model.services;

public interface CryptoService {
    String encryptToBase64(String plainText);
    String decryptFromBase64(String base64CipherText);
}
