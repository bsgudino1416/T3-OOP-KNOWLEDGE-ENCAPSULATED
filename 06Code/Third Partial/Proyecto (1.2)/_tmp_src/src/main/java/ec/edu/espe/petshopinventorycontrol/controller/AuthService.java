package ec.edu.espe.petshopinventorycontrol.controller;

import ec.edu.espe.petshopinventorycontrol.model.services.CryptoService;
import ec.edu.espe.petshopinventorycontrol.model.services.HashingService;
import ec.edu.espe.petshopinventorycontrol.model.services.LoginRequest;
import ec.edu.espe.petshopinventorycontrol.model.services.PasswordHasher;
import ec.edu.espe.petshopinventorycontrol.model.services.RegistrationRequest;
import ec.edu.espe.petshopinventorycontrol.model.services.UserAccount;
import ec.edu.espe.petshopinventorycontrol.model.services.UserRepository;
import java.util.Optional;

public final class AuthService {

    private final UserRepository userRepository;
    private final RegistrationValidator validator;
    private final HashingService hashingService;
    private final CryptoService cryptoService;
    private final PasswordHasher passwordHasher;

    public AuthService(UserRepository userRepository,
                       RegistrationValidator validator,
                       HashingService hashingService,
                       CryptoService cryptoService,
                       PasswordHasher passwordHasher) {
        this.userRepository = userRepository;
        this.validator = validator;
        this.hashingService = hashingService;
        this.cryptoService = cryptoService;
        this.passwordHasher = passwordHasher;
    }

    public void register(RegistrationRequest request) {
        validator.validate(request);

        String usernameHash = hashingService.sha256Base64(request.getUsername());

        if (userRepository.existsByUsernameHash(usernameHash)) {
            throw new IllegalStateException("Username already exists");
        }

        String usernameEncrypted = cryptoService.encryptToBase64(request.getUsername().trim());
        PasswordHasher.HashResult hr = passwordHasher.hash(request.getPassword().toCharArray());

        UserAccount user = new UserAccount(
                request.getFirstName(),
                request.getLastName(),
                request.getAddress(),
                request.getEmail(),
                request.getGender(),
                usernameHash,
                usernameEncrypted,
                hr.hashBase64(),
                hr.saltBase64(),
                hr.iterations()
        );

        userRepository.save(user);
    }

  public Optional<UserAccount> login(LoginRequest request) {
    if (request.getUsername() == null || request.getUsername().trim().isEmpty()) {
        return Optional.empty();
    }
    if (request.getPassword() == null || request.getPassword().isEmpty()) {
        return Optional.empty();
    }

    String usernameHash = hashingService.sha256Base64(request.getUsername());

    return userRepository.findByUsernameHash(usernameHash)
            .filter(u -> passwordHasher.verify(
                    request.getPassword().toCharArray(),
                    u.getPasswordSalt(),
                    u.getPasswordIterations(),
                    u.getPasswordHash()
            ));
}


}

