package ro.unibuc.hello.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FriendshipTest {

    private Friendship friendship;
    private String id;
    private String firstFriend;
    private String secondFriend;
    private LocalDate friendshipAniversary;

    @BeforeEach
    void setUp() {
        // Initialize test data
        id = "1";
        firstFriend = "friend1";
        secondFriend = "friend2";
        friendshipAniversary = LocalDate.of(2023, 3, 25);

        // Create a new Friendship instance for each test
        friendship = new Friendship(id, firstFriend, secondFriend, friendshipAniversary);
    }

    @Test
    void testConstructor() {
        // Test that the constructor properly initializes the fields
        assertEquals(id, friendship.getId());
        assertEquals(firstFriend, friendship.getFirstFriend());
        assertEquals(secondFriend, friendship.getSecondFriend());
        assertEquals(friendshipAniversary, friendship.getFriendshipAniversary());
    }

    @Test
    void testSettersAndGetters() {
        // Set new values
        friendship.setId("2");
        friendship.setFirstFriend("friend3");
        friendship.setSecondFriend("friend4");
        friendship.setFriendshipAniversary(LocalDate.of(2024, 4, 10));

        // Test that the setters correctly update the values
        assertEquals("2", friendship.getId());
        assertEquals("friend3", friendship.getFirstFriend());
        assertEquals("friend4", friendship.getSecondFriend());
        assertEquals(LocalDate.of(2024, 4, 10), friendship.getFriendshipAniversary());
    }

    @Test
    void testToString() {
        // Expected string representation
        String expectedString = "Friendship{id=1, firstFriend=friend1, secondFriend=friend2, friendshipAniversary=2023-03-25}";

        // Test if the toString method returns the expected string
        assertEquals(expectedString, friendship.toString());
    }

    @Test
    void testSetAndGetId() {
        friendship.setId("3");
        assertEquals("3", friendship.getId());
    }

    @Test
    void testSetAndGetFirstFriend() {
        friendship.setFirstFriend("friend5");
        assertEquals("friend5", friendship.getFirstFriend());
    }

    @Test
    void testSetAndGetSecondFriend() {
        friendship.setSecondFriend("friend6");
        assertEquals("friend6", friendship.getSecondFriend());
    }

    @Test
    void testSetAndGetFriendshipAniversary() {
        LocalDate newDate = LocalDate.of(2025, 5, 15);
        friendship.setFriendshipAniversary(newDate);
        assertEquals(newDate, friendship.getFriendshipAniversary());
    }
}
