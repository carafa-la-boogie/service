package ro.unibuc.hello.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import io.micrometer.core.instrument.Counter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ro.unibuc.hello.data.User;
import ro.unibuc.hello.data.UserRepository;
import ro.unibuc.hello.exception.EntityNotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private Counter customCounter;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId("1");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setBirthday(LocalDate.of(1990, 1, 1));
    }

    @Test
    void testFindUserByEmail() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        Optional<User> foundUser = userService.findUserByEmail(user.getEmail());
        assertTrue(foundUser.isPresent());
        assertEquals(user.getEmail(), foundUser.get().getEmail());
    }

    @Test
    void testFindUserById() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        Optional<User> foundUser = userService.findUserById(user.getId());
        assertTrue(foundUser.isPresent());
        assertEquals(user.getId(), foundUser.get().getId());
    }

    @Test
    void testFindAllUsers() {
        User user2 = new User();
        user2.setId("2");
        user2.setFirstName("Jane");
        user2.setLastName("Smith");
        user2.setEmail("jane.smith@example.com");

        when(userRepository.findAll()).thenReturn(List.of(user, user2));
        List<User> users = userService.findAll();
        assertEquals(2, users.size());
    }

    @Test
    void testCreateUser() {
        when(userRepository.save(user)).thenReturn(user);
        User createdUser = userService.createUser(user);
        assertNotNull(createdUser);
        assertEquals(user.getId(), createdUser.getId());
    }

    @Test
    void testUpdateUser_Success() throws EntityNotFoundException {
        when(userRepository.existsById(user.getId())).thenReturn(true);
        when(userRepository.save(user)).thenReturn(user);
        User updatedUser = userService.updateUser(user.getId(), user);
        assertEquals(user.getId(), updatedUser.getId());
    }

    @Test
    void testUpdateUser_NotFound() {
        when(userRepository.existsById(user.getId())).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> userService.updateUser(user.getId(), user));
    }

    @Test
    void testPatchUser_Success() throws EntityNotFoundException {
        when(userRepository.existsById(user.getId())).thenReturn(true);
        when(userRepository.save(user)).thenReturn(user);
        User patchedUser = userService.patchUser(user.getId(), user);
        assertEquals(user.getId(), patchedUser.getId());
    }

    @Test
    void testPatchUser_NotFound() {
        when(userRepository.existsById(user.getId())).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> userService.patchUser(user.getId(), user));
    }

    @Test
    void testDeleteUser_Success() throws EntityNotFoundException {
        when(userRepository.existsById(user.getId())).thenReturn(true);
        doNothing().when(userRepository).deleteById(user.getId());
        assertDoesNotThrow(() -> userService.deleteUser(user.getId()));
    }

    @Test
    void testDeleteUser_NotFound() {
        when(userRepository.existsById(user.getId())).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> userService.deleteUser(user.getId()));
    }
}
