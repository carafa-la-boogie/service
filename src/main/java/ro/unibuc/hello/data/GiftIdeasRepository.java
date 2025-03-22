package ro.unibuc.hello.data;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface GiftIdeasRepository extends MongoRepository<GiftIdeas, UUID> {

    // Custom query method to find gift ideas by gift giver's UUID
    Optional<GiftIdeas> findByGiftGiver(UUID giftGiver);

    // Custom query method to find gift ideas by gift receiver's UUID
    Optional<GiftIdeas> findByGiftReceiver(UUID giftReceiver);
}
