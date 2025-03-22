package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ro.unibuc.hello.model.Friendship;
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

    @GetMapping("/{id}")
    public ResponseEntity<Friendship> getFriendshipById(@PathVariable String id) throws EntityNotFoundException {
        Friendship friendship = friendshipService.findById(id);
        return new ResponseEntity<>(friendship, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Friendship>> getAllFriendships() {
        List<Friendship> friendships = friendshipService.findAll();
        return new ResponseEntity<>(friendships, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createFriendship(@RequestBody Friendship friendship) {
        friendshipService.createFriendship(friendship);
        return new ResponseEntity<>("Friendship created successfully", HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Friendship> updateFriendship(@PathVariable String id, @RequestBody Friendship friendship) throws EntityNotFoundException {
        Friendship updatedFriendship = friendshipService.updateFriendship(id, friendship);
        return new ResponseEntity<>(updatedFriendship, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Friendship> patchFriendship(@PathVariable String id, @RequestBody Friendship friendship) throws EntityNotFoundException {
        Friendship patchedFriendship = friendshipService.patchFriendship(id, friendship);
        return new ResponseEntity<>(patchedFriendship, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFriendship(@PathVariable String id) throws EntityNotFoundException {
        friendshipService.deleteFriendship(id);
        return new ResponseEntity<>("Friendship deleted successfully", HttpStatus.NO_CONTENT);
    }
}
