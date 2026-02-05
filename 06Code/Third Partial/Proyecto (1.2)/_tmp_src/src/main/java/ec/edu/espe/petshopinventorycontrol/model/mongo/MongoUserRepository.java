package ec.edu.espe.petshopinventorycontrol.model.mongo;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import ec.edu.espe.petshopinventorycontrol.model.services.UserAccount;
import ec.edu.espe.petshopinventorycontrol.model.services.UserRepository;
import java.util.Optional;
import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;

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
                .append("passwordIterations", user.getPasswordIterations());

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
                doc.getInteger("passwordIterations", 0)
        );
        return Optional.of(user);
    }
}

