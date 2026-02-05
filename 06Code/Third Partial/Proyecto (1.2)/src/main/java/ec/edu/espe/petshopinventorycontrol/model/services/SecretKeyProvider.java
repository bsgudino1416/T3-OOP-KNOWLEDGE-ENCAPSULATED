package ec.edu.espe.petshopinventorycontrol.model.services;

import java.util.Base64;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public final class SecretKeyProvider {

    
    public SecretKey getAesKey() {
        String b64 = System.getenv("PETSHOP_SECRET_KEY_B64");

      
        if (b64 == null || b64.isBlank()) {
            byte[] devKey = new byte[32];
            for (int i = 0; i < devKey.length; i++) devKey[i] = (byte) (i + 1);
            return new SecretKeySpec(devKey, "AES");
        }

        byte[] raw = Base64.getDecoder().decode(b64.trim());
        if (raw.length != 32) {
            throw new IllegalStateException("PETSHOP_SECRET_KEY_B64 debe decodificar a 32 bytes (AES-256).");
        }
        return new SecretKeySpec(raw, "AES");
    }
}
