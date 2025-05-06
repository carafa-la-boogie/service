package ro.unibuc.hello.service;

import io.micrometer.core.instrument.Counter;
import ro.unibuc.hello.data.Friendship;
import ro.unibuc.hello.data.FriendshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.unibuc.hello.exception.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class FriendshipService {

    private final FriendshipRepository friendshipRepository;
    private final Counter customFrienshipCounter;

    @Autowired
    public FriendshipService(FriendshipRepository friendshipRepository, Counter customFrienshipCounter) {
        this.friendshipRepository = friendshipRepository;
        this.customFrienshipCounter = customFrienshipCounter;
    }

    // Find friendship by first friend UUID
    public Optional<Friendship> findFriendshipByFirstFriend(String firstFriend) {
        return friendshipRepository.findByFirstFriend(firstFriend);
    }

    // Find friendship by second friend UUID
    public Optional<Friendship> findFriendshipBySecondFriend(String secondFriend) {
        return friendshipRepository.findBySecondFriend(secondFriend);
    }

    // Save a friendship (can be used for both create and update operations)
    public Friendship saveFriendship(Friendship friendship) {
        // Increment the custom counter whenever a friendship is saved
        customFrienshipCounter.increment();  // Increment the counter
        return friendshipRepository.save(friendship);
    }

    // Delete friendship by ID
    public void deleteFriendshipById(String id) {
        friendshipRepository.deleteById(id);
    }

    // Find Friendship by ID
    public Friendship findById(String id) throws EntityNotFoundException {
        return friendshipRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Friendship not found"));
    }

    // Get all friendships
    public List<Friendship> findAll() {
        return friendshipRepository.findAll();
    }

    // Create a new Friendship
    public Friendship createFriendship(Friendship friendship) {
        // Increment the custom counter whenever a new friendship is created
        customFrienshipCounter.increment();  // Increment the counter
        return friendshipRepository.save(friendship);
    }

    // Update an existing Friendship
    public Friendship updateFriendship(String id, Friendship friendship) throws EntityNotFoundException {
        if (!friendshipRepository.existsById(id)) {
            throw new EntityNotFoundException("Friendship not found");
        }
        friendship.setId(id); // Ensure the ID is set for the update
        return friendshipRepository.save(friendship);
    }

    // Patch a friendship (partial update)
    public Friendship patchFriendship(String id, Friendship friendship) throws EntityNotFoundException {
        if (!friendshipRepository.existsById(id)) {
            throw new EntityNotFoundException("Friendship not found");
        }
        friendship.setId(id); // Ensure the ID is set for the patch
        return friendshipRepository.save(friendship);
    }

    // Delete a friendship by ID
    public void deleteFriendship(String id) throws EntityNotFoundException {
        if (!friendshipRepository.existsById(id)) {
            throw new EntityNotFoundException("Friendship not found");
        }
        friendshipRepository.deleteById(id);
    }
}
