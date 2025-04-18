package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ro.unibuc.hello.data.Friendship;
import ro.unibuc.hello.service.FriendshipService;
import ro.unibuc.hello.exception.EntityNotFoundException;

import java.util.List;

@RestController
@RequestMapping("/friendships")
public class FriendshipController {

    private final FriendshipService friendshipService;

    // Constructor-based Dependency Injection
    @Autowired
    public FriendshipController(FriendshipService friendshipService) {
        this.friendshipService = friendshipService;
    }

    // Get a friendship by its ID
    @GetMapping("/{id}")
    public ResponseEntity<Friendship> getFriendshipById(@PathVariable String id) throws EntityNotFoundException {
        Friendship friendship = friendshipService.findById(id);
        return new ResponseEntity<>(friendship, HttpStatus.OK);
    }

    // Get all friendships
    @GetMapping
    public ResponseEntity<List<Friendship>> getAllFriendships() {
        List<Friendship> friendships = friendshipService.findAll();
        return new ResponseEntity<>(friendships, HttpStatus.OK);
    }

    // Create a new friendship
    @PostMapping
    public ResponseEntity<String> createFriendship(@RequestBody Friendship friendship) {
        friendshipService.createFriendship(friendship);
        return new ResponseEntity<>("Friendship created successfully", HttpStatus.CREATED);
    }

    // Update an existing friendship
    @PutMapping("/{id}")
    public ResponseEntity<Friendship> updateFriendship(@PathVariable String id, @RequestBody Friendship friendship) throws EntityNotFoundException {
        Friendship updatedFriendship = friendshipService.updateFriendship(id, friendship);
        return new ResponseEntity<>(updatedFriendship, HttpStatus.OK);
    }

    // Patch an existing friendship
    @PatchMapping("/{id}")
    public ResponseEntity<Friendship> patchFriendship(@PathVariable String id, @RequestBody Friendship friendship) throws EntityNotFoundException {
        Friendship patchedFriendship = friendshipService.patchFriendship(id, friendship);
        return new ResponseEntity<>(patchedFriendship, HttpStatus.OK);
    }

    // Delete a friendship by its ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFriendship(@PathVariable String id) throws EntityNotFoundException {
        friendshipService.deleteFriendship(id);
        return new ResponseEntity<>("Friendship deleted successfully", HttpStatus.NO_CONTENT);
    }
}
