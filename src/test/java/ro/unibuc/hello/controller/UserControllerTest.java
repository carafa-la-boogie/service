package ro.unibuc.hello.controller;
 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ro.unibuc.hello.data.User;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.service.UserService;

import java.util.Arrays;
import java.util.List;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void test_getUserByEmail() throws Exception {
        User user = new User("1", "John", "Doe", LocalDate.of(1990, 5, 15), "test@example.com");
        when(userService.findUserByEmail("test@example.com")).thenReturn(Optional.of(user));

        mockMvc.perform(get("/users/test@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"));
    }

    @Test
    void test_getUserByEmail_NotFound() {
        when(userService.findUserByEmail("unknown@example.com")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userController.getUserByEmail("unknown@example.com"));
    }

    @Test
    void test_getAllUsers() throws Exception {
        List<User> users = Arrays.asList(
                new User("1", "John", "Doe", LocalDate.of(1990, 5, 15), "test1@example.com"),
                new User("2", "Jane", "Doe", LocalDate.of(1992, 8, 25), "test2@example.com")
        );
        when(userService.findAll()).thenReturn(users);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].email").value("test1@example.com"))
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[1].id").value("2"))
                .andExpect(jsonPath("$[1].email").value("test2@example.com"))
                .andExpect(jsonPath("$[1].firstName").value("Jane"));
    }

    @Test
    void test_createUser() throws Exception {
        User user = new User("1", "New", "User", LocalDate.of(2000, 1, 1), "new@example.com");
        when(userService.createUser(any(User.class))).thenReturn(user); // Fix

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"firstName\":\"New\", \"lastName\":\"User\", \"birthday\":\"2000-01-01\", \"email\":\"new@example.com\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    void test_updateUser() throws Exception {
        User updatedUser = new User("1", "Updated", "User", LocalDate.of(1995, 7, 20), "updated@example.com");
        when(userService.updateUser(eq("1"), any(User.class))).thenReturn(updatedUser);

        mockMvc.perform(put("/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"firstName\":\"Updated\", \"lastName\":\"User\", \"birthday\":\"1995-07-20\", \"email\":\"updated@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.email").value("updated@example.com"))
                .andExpect(jsonPath("$.firstName").value("Updated"))
                .andExpect(jsonPath("$.lastName").value("User"));
    }

    @Test
    void test_patchUser() throws Exception {
        User patchedUser = new User("1", "Patched", "User", LocalDate.of(1995, 7, 20), "patched@example.com");
        when(userService.patchUser(eq("1"), any(User.class))).thenReturn(patchedUser);

        mockMvc.perform(patch("/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"patched@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.email").value("patched@example.com"))
                .andExpect(jsonPath("$.firstName").value("Patched"))
                .andExpect(jsonPath("$.lastName").value("User"));
    }

    @Test
    void test_deleteUser() throws Exception {
        doNothing().when(userService).deleteUser("1");

        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isNoContent());
    }
}
