package ec.edu.espe.petshopinventorycontrol.model.services;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    boolean existsByUsernameHash(String usernameHash);
    void save(UserAccount user);
    Optional<UserAccount> findByUsernameHash(String usernameHash);
    List<UserAccount> findAllOrdered();
    void updateTwoFactor(String usernameHash, boolean enabled, String secretEncrypted);
}
