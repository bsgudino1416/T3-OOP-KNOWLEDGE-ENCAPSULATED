package ec.edu.espe.petshopinventorycontrol.controller;

import ec.edu.espe.petshopinventorycontrol.model.mongo.MongoUserRepository;
import ec.edu.espe.petshopinventorycontrol.model.services.*;

public final class AuthDependencies {

    private static AuthService authService;

    private AuthDependencies() {}

    public static AuthService getAuthService() {
    if (authService == null) {

        String mongoUri = System.getenv("MONGO_URI" );
            
        String dbName = System.getenv().getOrDefault("MONGO_DB", "PetshopInventoryControlBD");

        if (mongoUri == null || mongoUri.isBlank()) {
            mongoUri = "mongodb+srv://Steven:Steven2001@cluster0.mp8muds.mongodb.net/?appName=Cluster0t";
        }

        UserRepository repo = new MongoUserRepository(mongoUri, dbName);

        SecretKeyProvider keyProvider = new SecretKeyProvider();
        CryptoService crypto = new AesGcmCryptoService(keyProvider.getAesKey());

        authService = new AuthService(
                repo,
                new RegistrationValidator(),
                new HashingService(),
                crypto,
                new PasswordHasher()
        );
    }
    return authService;
}

}

