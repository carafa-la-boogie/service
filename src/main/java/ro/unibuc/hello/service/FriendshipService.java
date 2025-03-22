package ro.unibuc.hello.service;

import ro.unibuc.hello.data.Friendship;
import ro.unibuc.hello.data.FriendshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.UUID;

@Service
public class FriendshipService {

    @Autowired
    private FriendshipRepository friendshipRepository;

    @Autowired
    public FriendshipService (FriendshipRepository friendshipRepository){
        this.friendshipRepository = friendshipRepository;
    }

    // Find friendship by first friend UUID
    public Optional<Friendship> findFriendshipByFirstFriend(UUID firstFriend) {
        return friendshipRepository.findByFirstFriend(firstFriend);
    }

    // Find friendship by second friend UUID
    public Optional<Friendship> findFriendshipBySecondFriend(UUID secondFriend) {
        return friendshipRepository.findBySecondFriend(secondFriend);
    }

    // Save a friendship (can be used for both create and update operations)
    public Friendship saveFriendship(Friendship friendship) {
        return friendshipRepository.save(friendship);
    }

    // Delete friendship by ID
    public void deleteFriendshipById(UUID id) {
        friendshipRepository.deleteById(id);
    }
}
