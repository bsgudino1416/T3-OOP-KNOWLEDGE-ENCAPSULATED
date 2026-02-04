package ec.edu.espe.petshopinventorycontrol.controller;

import ec.edu.espe.petshopinventorycontrol.model.services.CryptoService;
import ec.edu.espe.petshopinventorycontrol.model.services.HashingService;
import ec.edu.espe.petshopinventorycontrol.model.services.LoginRequest;
import ec.edu.espe.petshopinventorycontrol.model.services.PasswordHasher;
import ec.edu.espe.petshopinventorycontrol.model.services.RegistrationRequest;
import ec.edu.espe.petshopinventorycontrol.model.services.TwoFactorService;
import ec.edu.espe.petshopinventorycontrol.model.services.TwoFactorSetup;
import ec.edu.espe.petshopinventorycontrol.model.services.UserAccount;
import ec.edu.espe.petshopinventorycontrol.model.services.UserRepository;
import java.util.Optional;

public final class AuthService {

    private final UserRepository userRepository;
    private final RegistrationValidator validator;
    private final HashingService hashingService;
    private final CryptoService cryptoService;
    private final PasswordHasher passwordHasher;
    private final TwoFactorService twoFactorService;

    public AuthService(UserRepository userRepository,
                       RegistrationValidator validator,
                       HashingService hashingService,
                       CryptoService cryptoService,
                       PasswordHasher passwordHasher,
                       TwoFactorService twoFactorService) {
        this.userRepository = userRepository;
        this.validator = validator;
        this.hashingService = hashingService;
        this.cryptoService = cryptoService;
        this.passwordHasher = passwordHasher;
        this.twoFactorService = twoFactorService;
    }

    public void register(RegistrationRequest request) {
        validator.validate(request);

        String usernameHash = hashingService.sha256Base64(request.getUsername());

        if (userRepository.existsByUsernameHash(usernameHash)) {
            throw new IllegalStateException("El usuario ya existe");
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
                hr.iterations(),
                false,
                null
        );

        userRepository.save(user);
    }

    public Optional<UserAccount> login(LoginRequest request) {
        AuthResult result = authenticate(request, null);
        if (result.getStatus() == AuthResult.Status.SUCCESS) {
            return Optional.ofNullable(result.getUser());
        }
        return Optional.empty();
    }

    public AuthResult authenticate(LoginRequest request, String totpCode) {
        if (request.getUsername() == null || request.getUsername().trim().isEmpty()) {
            return AuthResult.invalid();
        }
        if (request.getPassword() == null || request.getPassword().isEmpty()) {
            return AuthResult.invalid();
        }

        String usernameHash = hashingService.sha256Base64(request.getUsername());
        Optional<UserAccount> userOpt = userRepository.findByUsernameHash(usernameHash)
                .filter(u -> passwordHasher.verify(
                        request.getPassword().toCharArray(),
                        u.getPasswordSalt(),
                        u.getPasswordIterations(),
                        u.getPasswordHash()
                ));

        if (userOpt.isEmpty()) {
            return AuthResult.invalid();
        }

        UserAccount user = userOpt.get();
        if (!user.isTwoFactorEnabled()) {
            return AuthResult.success(user);
        }

        if (totpCode == null || totpCode.trim().isEmpty()) {
            return AuthResult.twoFactorRequired(user);
        }

        boolean valid = twoFactorService.verifyEncrypted(user.getTwoFactorSecretEncrypted(), totpCode);
        return valid ? AuthResult.success(user) : AuthResult.twoFactorInvalid(user);
    }

    public TwoFactorSetup beginTwoFactorSetup(String username) {
        return twoFactorService.createSetup(username);
    }

    public boolean enableTwoFactor(UserAccount user, String secretBase32, String totpCode) {
        if (user == null || secretBase32 == null || secretBase32.isBlank()) {
            return false;
        }
        if (!twoFactorService.verifyCode(secretBase32, totpCode)) {
            return false;
        }
        String encrypted = twoFactorService.encryptSecret(secretBase32);
        userRepository.updateTwoFactor(user.getUsernameHash(), true, encrypted);
        return true;
    }


}
