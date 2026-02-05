package ec.edu.espe.petshopinventorycontrol.controller;

import ec.edu.espe.petshopinventorycontrol.model.RegisterRecord;
import ec.edu.espe.petshopinventorycontrol.model.mongo.MongoUserRepository;
import ec.edu.espe.petshopinventorycontrol.model.services.AesGcmCryptoService;
import ec.edu.espe.petshopinventorycontrol.model.services.CryptoService;
import ec.edu.espe.petshopinventorycontrol.model.services.SecretKeyProvider;
import ec.edu.espe.petshopinventorycontrol.model.services.UserAccount;
import ec.edu.espe.petshopinventorycontrol.model.services.UserRepository;
import java.util.ArrayList;
import java.util.List;

public final class RegisterGraphicService {

    private final UserRepository repository;
    private final CryptoService cryptoService;

    public RegisterGraphicService(UserRepository repository, CryptoService cryptoService) {
        this.repository = repository;
        this.cryptoService = cryptoService;
    }

    public static RegisterGraphicService defaultService() {
        String mongoUri = System.getenv("MONGO_URI");
        String dbName = System.getenv().getOrDefault("MONGO_DB", "PetshopInventoryControlBD");
        if (mongoUri == null || mongoUri.isBlank()) {
            mongoUri = "mongodb+srv://Steven:Steven2001@cluster0.mp8muds.mongodb.net/?appName=Cluster0t";
        }

        UserRepository repo = new MongoUserRepository(mongoUri, dbName);
        CryptoService crypto = new AesGcmCryptoService(new SecretKeyProvider().getAesKey());
        return new RegisterGraphicService(repo, crypto);
    }

    public List<RegisterRecord> fetchAll() {
        List<UserAccount> users = repository.findAllOrdered();
        List<RegisterRecord> records = new ArrayList<>();

        for (UserAccount user : users) {
            RegisterRecord record = new RegisterRecord();
            record.setId(user.getUsernameHash());
            record.setFirstName(user.getFirstName());
            record.setLastName(user.getLastName());
            record.setAddress(user.getAddress());
            record.setEmail(user.getEmail());
            record.setGender(user.getGender());
            record.setUsername(decryptUsername(user.getUsernameEncrypted()));
            records.add(record);
        }

        return records;
    }

    private String decryptUsername(String encrypted) {
        if (encrypted == null || encrypted.trim().isEmpty()) {
            return "";
        }
        try {
            return cryptoService.decryptFromBase64(encrypted.trim());
        } catch (Exception ex) {
            return encrypted;
        }
    }
}
