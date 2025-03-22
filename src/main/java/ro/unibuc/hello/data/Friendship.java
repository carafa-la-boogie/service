package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.util.UUID;
import java.time.LocalDate;

@Document(collection = "friendships") // MongoDB collection name
public class Friendship {

    @Id
    private UUID id; // Unique identifier for the friendship

    @Field("firstFriend")
    private UUID firstFriend; // UUID of the first friend

    @Field("secondFriend")
    private UUID secondFriend; // UUID of the second friend

    @Field("friendshipAniversary")
    private LocalDate friendshipAniversary; // Date of the friendship anniversary

    // Getter and Setter for 'id'
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    // Getter and Setter for 'firstFriend'
    public UUID getFirstFriend() {
        return firstFriend;
    }

    public void setFirstFriend(UUID firstFriend) {
        this.firstFriend = firstFriend;
    }

    // Getter and Setter for 'secondFriend'
    public UUID getSecondFriend() {
        return secondFriend;
    }

    public void setSecondFriend(UUID secondFriend) {
        this.secondFriend = secondFriend;
    }

    // Getter and Setter for 'friendshipAniversary'
    public LocalDate getFriendshipAniversary() {
        return friendshipAniversary;
    }

    public void setFriendshipAniversary(LocalDate friendshipAniversary) {
        this.friendshipAniversary = friendshipAniversary;
    }

    @Override
    public String toString() {
        return "Friendship{" +
                "id=" + id +
                ", firstFriend=" + firstFriend +
                ", secondFriend=" + secondFriend +
                ", friendshipAniversary=" + friendshipAniversary +
                '}';
    }
}
