package ro.unibuc.hello.data;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface FriendshipRepository extends MongoRepository<Friendship, UUID> {

    // Custom query method to find friendship by the first friend
    Optional<Friendship> findByFirstFriend(UUID firstFriend);

    // Custom query method to find friendship by the second friend
    Optional<Friendship> findBySecondFriend(UUID secondFriend);
}
