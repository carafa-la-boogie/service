package ro.unibuc.hello.service;

import ro.unibuc.hello.data.GiftIdeas;
import ro.unibuc.hello.data.GiftIdeasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.UUID;

@Service
public class GiftIdeasService {

    private GiftIdeasRepository giftIdeasRepository;

    @Autowired
    public GiftIdeasService (GiftIdeasRepository giftIdeasRepository){
        this.giftIdeasRepository = giftIdeasRepository;
    }

    // Find gift ideas by gift giver UUID
    public Optional<GiftIdeas> findGiftIdeasByGiver(UUID giftGiver) {
        return giftIdeasRepository.findByGiftGiver(giftGiver);
    }

    // Find gift ideas by gift receiver UUID
    public Optional<GiftIdeas> findGiftIdeasByReceiver(UUID giftReceiver) {
        return giftIdeasRepository.findByGiftReceiver(giftReceiver);
    }

    // Save a gift idea (can be used for both create and update operations)
    public GiftIdeas saveGiftIdeas(GiftIdeas giftIdeas) {
        return giftIdeasRepository.save(giftIdeas);
    }

    // Delete gift idea by ID
    public void deleteGiftIdeasById(UUID id) {
        giftIdeasRepository.deleteById(id);
    }
}
