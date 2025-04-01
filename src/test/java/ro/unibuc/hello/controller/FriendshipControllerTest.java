package ro.unibuc.hello.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ro.unibuc.hello.data.Friendship;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.service.FriendshipService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class FriendshipControllerTest {

    @Mock
    private FriendshipService friendshipService;

    @InjectMocks
    private FriendshipController friendshipController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(friendshipController).build();
    }

    @Test
    void test_getFriendshipById() throws Exception {
        Friendship friendship = new Friendship("1", "Alice", "Bob", LocalDate.of(2023, 12, 11));
        when(friendshipService.findById("1")).thenReturn(friendship);

        mockMvc.perform(get("/friendships/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.firstFriend").value("Alice"))
                .andExpect(jsonPath("$.secondFriend").value("Bob"))
                .andExpect(jsonPath("$.friendshipAniversary").isArray());
    }

    @Test
    void test_getAllFriendships() throws Exception {
        List<Friendship> friendships = Arrays.asList(new Friendship("1", "Alice", "Bob", LocalDate.of(2023, 12, 11)), new Friendship("2", "Charlie", "Dave", LocalDate.of(2023, 12, 11)));
        when(friendshipService.findAll()).thenReturn(friendships);

        mockMvc.perform(get("/friendships"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[1].id").value("2"));
    }

    @Test
    void test_createFriendship() throws Exception {
        mockMvc.perform(post("/friendships")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"firstFriend\":\"Alice\",\"secondFriend\":\"Bob\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    void test_updateFriendship() throws Exception {
        Friendship updatedFriendship = new Friendship("1", "Alice", "Charlie", LocalDate.of(2023, 12, 11));
        when(friendshipService.updateFriendship(eq("1"), any(Friendship.class))).thenReturn(updatedFriendship);

        mockMvc.perform(put("/friendships/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\": \"1\", \"firstFriend\":\"Alice\",\"secondFriend\":\"Charlie\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstFriend").value("Alice"))
                .andExpect(jsonPath("$.secondFriend").value("Charlie"))
                .andExpect(jsonPath("$.friendshipAniversary").isArray());
    }

    @Test
    void test_patchFriendship() throws Exception {
        Friendship patchedFriendship = new Friendship("1", "Alice", "Charlie", LocalDate.of(2023, 12, 11));
        when(friendshipService.patchFriendship(eq("1"), any(Friendship.class))).thenReturn(patchedFriendship);

        mockMvc.perform(patch("/friendships/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"secondFriend\":\"Charlie\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.secondFriend").value("Charlie"));
    }

    @Test
    void test_deleteFriendship() throws Exception {
        doNothing().when(friendshipService).deleteFriendship("1");

        mockMvc.perform(delete("/friendships/1"))
                .andExpect(status().isNoContent())
                .andExpect(content().string("Friendship deleted successfully"));
    }
}
