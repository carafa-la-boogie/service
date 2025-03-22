package ro.unibuc.hello.data;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GiftIdeasRepository extends MongoRepository<GiftIdeas, String> {

    // Custom query method to find gift ideas by gift giver's UUID
    Optional<GiftIdeas> findByGiftGiver(String giftGiver);

    // Custom query method to find gift ideas by gift receiver's UUID
    Optional<GiftIdeas> findByGiftReceiver(String giftReceiver);
}
