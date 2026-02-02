package ec.edu.espe.petshopinventorycontrol.model.mongo;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import ec.edu.espe.petshopinventorycontrol.model.services.UserAccount;
import ec.edu.espe.petshopinventorycontrol.model.services.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Sorts.ascending;

public final class MongoUserRepository implements UserRepository {

    private final MongoCollection<Document> users;

    public MongoUserRepository(String connectionString, String dbName) {
        MongoClient client = MongoClients.create(connectionString);
        MongoDatabase db = client.getDatabase(dbName);
        this.users = db.getCollection("users");
    }

    @Override
    public boolean existsByUsernameHash(String usernameHash) {
        return users.find(eq("usernameHash", usernameHash)).first() != null;
    }

    @Override
    public void save(UserAccount user) {
        Document doc = new Document()
                .append("firstName", user.getFirstName())
                .append("lastName", user.getLastName())
                .append("address", user.getAddress())
                .append("email", user.getEmail())
                .append("gender", user.getGender())
                .append("usernameHash", user.getUsernameHash())
                .append("usernameEncrypted", user.getUsernameEncrypted())
                .append("passwordHash", user.getPasswordHash())
                .append("passwordSalt", user.getPasswordSalt())
                .append("passwordIterations", user.getPasswordIterations())
                .append("twoFactorEnabled", user.isTwoFactorEnabled())
                .append("twoFactorSecretEncrypted", user.getTwoFactorSecretEncrypted());

        users.insertOne(doc);
    }

    @Override
    public Optional<UserAccount> findByUsernameHash(String usernameHash) {
        Document doc = users.find(eq("usernameHash", usernameHash)).first();
        if (doc == null) return Optional.empty();

        UserAccount user = new UserAccount(
                doc.getString("firstName"),
                doc.getString("lastName"),
                doc.getString("address"),
                doc.getString("email"),
                doc.getString("gender"),
                doc.getString("usernameHash"),
                doc.getString("usernameEncrypted"),
                doc.getString("passwordHash"),
                doc.getString("passwordSalt"),
                doc.getInteger("passwordIterations", 0),
                doc.getBoolean("twoFactorEnabled", false),
                doc.getString("twoFactorSecretEncrypted")
        );
        return Optional.of(user);
    }

    @Override
    public List<UserAccount> findAllOrdered() {
        List<UserAccount> result = new ArrayList<>();
        for (Document doc : users.find().sort(ascending("_id"))) {
            if (doc == null) {
                continue;
            }
            UserAccount user = new UserAccount(
                    doc.getString("firstName"),
                    doc.getString("lastName"),
                    doc.getString("address"),
                    doc.getString("email"),
                    doc.getString("gender"),
                    doc.getString("usernameHash"),
                    doc.getString("usernameEncrypted"),
                    doc.getString("passwordHash"),
                    doc.getString("passwordSalt"),
                    doc.getInteger("passwordIterations", 0),
                    doc.getBoolean("twoFactorEnabled", false),
                    doc.getString("twoFactorSecretEncrypted")
            );
            result.add(user);
        }
        return result;
    }

    @Override
    public void updateTwoFactor(String usernameHash, boolean enabled, String secretEncrypted) {
        users.updateOne(
                eq("usernameHash", usernameHash),
                new Document("$set", new Document()
                        .append("twoFactorEnabled", enabled)
                        .append("twoFactorSecretEncrypted", secretEncrypted))
        );
    }
}
