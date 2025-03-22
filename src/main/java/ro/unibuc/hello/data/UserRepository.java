package ro.unibuc.hello.data;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends MongoRepository<User, UUID> {

    // Custom query method to find a user by their email
    Optional<User> findByEmail(String email);

    // The default findById method is inherited from MongoRepository, no need to define it.
}
