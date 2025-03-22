package ro.unibuc.hello.service;

import ro.unibuc.hello.data.GiftIdeas;
import ro.unibuc.hello.data.GiftIdeasRepository;
import ro.unibuc.hello.exception.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class GiftIdeasService {

    private final GiftIdeasRepository giftIdeasRepository;

    @Autowired
    public GiftIdeasService(GiftIdeasRepository giftIdeasRepository) {
        this.giftIdeasRepository = giftIdeasRepository;
    }

    // Find gift ideas by gift giver UUID
    public Optional<GiftIdeas> findGiftIdeasByGiver(String giftGiver) {
        return giftIdeasRepository.findByGiftGiver(giftGiver);
    }

    // Find gift ideas by gift receiver UUID
    public Optional<GiftIdeas> findGiftIdeasByReceiver(String giftReceiver) {
        return giftIdeasRepository.findByGiftReceiver(giftReceiver);
    }

    // Save a gift idea (can be used for both create and update operations)
    public GiftIdeas saveGiftIdeas(GiftIdeas giftIdeas) {
        return giftIdeasRepository.save(giftIdeas);
    }

    // Delete gift idea by ID
    public void deleteGiftIdeasById(String id) {
        giftIdeasRepository.deleteById(id);
    }

    // Find GiftIdea by ID
    public GiftIdeas findById(String id) throws EntityNotFoundException {
        return giftIdeasRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Gift idea not found"));
    }

    // Get all gift ideas
    public List<GiftIdeas> findAll() {
        return giftIdeasRepository.findAll();
    }

    // Create a new Gift Idea
    public GiftIdeas createGiftIdeas(GiftIdeas giftIdea) {
        return giftIdeasRepository.save(giftIdea);
    }

    // Update an existing Gift Idea
    public GiftIdeas updateGiftIdea(String id, GiftIdeas giftIdea) throws EntityNotFoundException {
        if (!giftIdeasRepository.existsById(id)) {
            throw new EntityNotFoundException("Gift idea not found");
        }
        giftIdea.setId(id); // Update the ID
        return giftIdeasRepository.save(giftIdea);
    }

    // Patch a gift idea (usually partial update)
    public GiftIdeas patchGiftIdea(String id, GiftIdeas giftIdea) throws EntityNotFoundException {
        if (!giftIdeasRepository.existsById(id)) {
            throw new EntityNotFoundException("Gift idea not found");
        }
        // Here you can apply logic to patch the gift idea (e.g., partial update)
        giftIdea.setId(id); // Set the correct ID
        return giftIdeasRepository.save(giftIdea);
    }

    // Delete gift idea by ID
    public void deleteGiftIdea(String id) throws EntityNotFoundException {
        if (!giftIdeasRepository.existsById(id)) {
            throw new EntityNotFoundException("Gift idea not found");
        }
        giftIdeasRepository.deleteById(id);
    }
}
