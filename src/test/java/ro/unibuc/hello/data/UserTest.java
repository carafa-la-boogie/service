package ro.unibuc.hello.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User user;
    private String id;
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private String email;

    @BeforeEach
    void setUp() {
        id = "1";
        firstName = "John";
        lastName = "Doe";
        birthday = LocalDate.of(1990, 5, 15);
        email = "john.doe@example.com";

        user = new User();
        user.setId(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setBirthday(birthday);
        user.setEmail(email);
    }

    @Test
    void testConstructor() {
    
        assertEquals(id, user.getId());
        assertEquals(firstName, user.getFirstName());
        assertEquals(lastName, user.getLastName());
        assertEquals(birthday, user.getBirthday());
        assertEquals(email, user.getEmail());
    }

    @Test
    void testSettersAndGetters() {
        user.setId("2");
        user.setFirstName("Jane");
        user.setLastName("Smith");
        user.setBirthday(LocalDate.of(1992, 7, 20));
        user.setEmail("jane.smith@example.com");

        assertEquals("2", user.getId());
        assertEquals("Jane", user.getFirstName());
        assertEquals("Smith", user.getLastName());
        assertEquals(LocalDate.of(1992, 7, 20), user.getBirthday());
        assertEquals("jane.smith@example.com", user.getEmail());
    }

    @Test
    void testToString() {
        String expectedString = "User{id='1', firstName='John', lastName='Doe', birthday=1990-05-15, email='john.doe@example.com'}";

        assertEquals(expectedString, user.toString());
    }

    @Test
    void testSetAndGetId() {
        user.setId("3");
        assertEquals("3", user.getId());
    }

    @Test
    void testSetAndGetFirstName() {
        user.setFirstName("Alice");
        assertEquals("Alice", user.getFirstName());
    }

    @Test
    void testSetAndGetLastName() {
        user.setLastName("Wonderland");
        assertEquals("Wonderland", user.getLastName());
    }

    @Test
    void testSetAndGetBirthday() {
        LocalDate newBirthday = LocalDate.of(1995, 10, 10);
        user.setBirthday(newBirthday);
        assertEquals(newBirthday, user.getBirthday());
    }

    @Test
    void testSetAndGetEmail() {
        user.setEmail("alice.wonderland@example.com");
        assertEquals("alice.wonderland@example.com", user.getEmail());
    }
}
