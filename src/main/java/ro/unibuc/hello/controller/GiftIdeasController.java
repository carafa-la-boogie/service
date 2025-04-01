package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ro.unibuc.hello.data.GiftIdeas;
import ro.unibuc.hello.service.GiftIdeasService;
import ro.unibuc.hello.exception.EntityNotFoundException;

import java.util.List;

@RestController
@RequestMapping("/gift-ideas")
public class GiftIdeasController {

    private final GiftIdeasService giftIdeaService;

    // Constructor-based Dependency Injection
    @Autowired
    public GiftIdeasController(GiftIdeasService giftIdeaService) {
        this.giftIdeaService = giftIdeaService;
    }

    // Get a gift idea by its ID
    @GetMapping("/{id}")
    public ResponseEntity<GiftIdeas> getGiftIdeaById(@PathVariable("id") String id) {
        try {
            GiftIdeas giftIdea = giftIdeaService.findById(id);
            return new ResponseEntity<>(giftIdea, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            // Handle the exception here and return a 404
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Get all gift ideas
    @GetMapping
    public ResponseEntity<List<GiftIdeas>> getAllGiftIdeas() {
        List<GiftIdeas> giftIdeas = giftIdeaService.findAll();
        return new ResponseEntity<>(giftIdeas, HttpStatus.OK);
    }

    // Create a new gift idea
    @PostMapping
    public ResponseEntity<String> createGiftIdea(@RequestBody GiftIdeas giftIdea) {
        giftIdeaService.createGiftIdeas(giftIdea);
        return new ResponseEntity<>("Gift idea created successfully", HttpStatus.CREATED);
    }

    // Update an existing gift idea
    @PutMapping("/{id}")
    public ResponseEntity<GiftIdeas> updateGiftIdea(@PathVariable("id") String id, @RequestBody GiftIdeas giftIdea) throws EntityNotFoundException {
        GiftIdeas updatedGiftIdea = giftIdeaService.updateGiftIdea(id, giftIdea);
        return new ResponseEntity<>(updatedGiftIdea, HttpStatus.OK);
    }

    // Patch an existing gift idea
    @PatchMapping("/{id}")
    public ResponseEntity<GiftIdeas> patchGiftIdea(@PathVariable("id") String id, @RequestBody GiftIdeas giftIdea) throws EntityNotFoundException {
        GiftIdeas patchedGiftIdea = giftIdeaService.patchGiftIdea(id, giftIdea);
        return new ResponseEntity<>(patchedGiftIdea, HttpStatus.OK);
    }

    // Delete a gift idea by its ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGiftIdea(@PathVariable("id") String id) throws EntityNotFoundException {
        giftIdeaService.deleteGiftIdea(id);
        return new ResponseEntity<>("Gift idea deleted successfully", HttpStatus.NO_CONTENT);
    }
}
