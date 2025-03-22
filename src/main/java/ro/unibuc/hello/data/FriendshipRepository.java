package ro.unibuc.hello.data;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FriendshipRepository extends MongoRepository<Friendship, String> {

    // Custom query method to find friendships by first friend's UUID
    Optional<Friendship> findByFirstFriend(String firstFriend);

    // Custom query method to find friendships by second friend's UUID
    Optional<Friendship> findBySecondFriend(String secondFriend);
}
