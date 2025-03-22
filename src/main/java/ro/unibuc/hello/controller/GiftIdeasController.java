package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ro.unibuc.hello.model.GiftIdea;
import ro.unibuc.hello.service.GiftIdeaService;
import ro.unibuc.hello.exception.EntityNotFoundException;

import java.util.List;

@RestController
@RequestMapping("/gift-ideas")
public class GiftIdeasController {

    private final GiftIdeaService giftIdeaService;

    // Constructor-based Dependency Injection
    @Autowired
    public GiftIdeasController(GiftIdeaService giftIdeaService) {
        this.giftIdeaService = giftIdeaService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<GiftIdea> getGiftIdeaById(@PathVariable String id) throws EntityNotFoundException {
        GiftIdea giftIdea = giftIdeaService.findById(id);
        return new ResponseEntity<>(giftIdea, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<GiftIdea>> getAllGiftIdeas() {
        List<GiftIdea> giftIdeas = giftIdeaService.findAll();
        return new ResponseEntity<>(giftIdeas, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createGiftIdea(@RequestBody GiftIdea giftIdea) {
        giftIdeaService.createGiftIdea(giftIdea);
        return new ResponseEntity<>("Gift idea created successfully", HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GiftIdea> updateGiftIdea(@PathVariable String id, @RequestBody GiftIdea giftIdea) throws EntityNotFoundException {
        GiftIdea updatedGiftIdea = giftIdeaService.updateGiftIdea(id, giftIdea);
        return new ResponseEntity<>(updatedGiftIdea, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<GiftIdea> patchGiftIdea(@PathVariable String id, @RequestBody GiftIdea giftIdea) throws EntityNotFoundException {
        GiftIdea patchedGiftIdea = giftIdeaService.patchGiftIdea(id, giftIdea);
        return new ResponseEntity<>(patchedGiftIdea, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGiftIdea(@PathVariable String id) throws EntityNotFoundException {
        giftIdeaService.deleteGiftIdea(id);
        return new ResponseEntity<>("Gift idea deleted successfully", HttpStatus.NO_CONTENT);
    }
}
