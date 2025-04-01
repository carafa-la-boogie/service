package ro.unibuc.hello.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ro.unibuc.hello.data.Friendship;
import ro.unibuc.hello.service.FriendshipService;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@Tag("IntegrationTest")
public class FriendshipControllerIntegrationTest {

    @Container
    public static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:6.0.20")
            .withExposedPorts(27017)
            .withEnv("MONGO_INITDB_ROOT_USERNAME", "root")
            .withEnv("MONGO_INITDB_ROOT_PASSWORD", "example")
            .withEnv("MONGO_INITDB_DATABASE", "testdb")
            .withCommand("--auth");

    @BeforeAll
    public static void setUp() {
        mongoDBContainer.start();
    }

    @AfterAll
    public static void tearDown() {
        mongoDBContainer.stop();
    }

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        final String MONGO_URL = "mongodb://root:example@localhost:";
        final String PORT = String.valueOf(mongoDBContainer.getMappedPort(27017));

        registry.add("mongodb.connection.url", () -> MONGO_URL + PORT);
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FriendshipService friendshipService;

    @BeforeEach
    public void cleanUpAndAddTestData() {        
        Friendship friendship1 = new Friendship("1", "userA", "userB", LocalDate.of(2023, 12, 11));
        Friendship friendship2 = new Friendship("2", "userC", "userD", LocalDate.of(2024, 1, 1));

        friendshipService.saveFriendship(friendship1);
        friendshipService.saveFriendship(friendship2);
    }

    @Test
    public void testGetAllFriendships() throws Exception {
        mockMvc.perform(get("/friendships"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].firstFriend").value("userA"))
            .andExpect(jsonPath("$[1].firstFriend").value("userC"));
    }

    @Test
    public void testCreateFriendship() throws Exception {
        Friendship friendship = new Friendship("3", "userE", "userF", LocalDate.of(2025, 5, 20));

        mockMvc.perform(post("/friendships")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(friendship)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("3"))
                .andExpect(jsonPath("$.firstFriend").value("userE"));

        mockMvc.perform(get("/friendships"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(3));
    }

    @Test
    public void testUpdateFriendship() throws Exception {
        Friendship friendship = new Friendship("1", "userA", "userB", LocalDate.of(2026, 6, 15));

        mockMvc.perform(put("/friendships/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(friendship)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.friendshipAniversary").value("2026-06-15"));
    }

    @Test
    public void testDeleteFriendship() throws Exception {
        mockMvc.perform(delete("/friendships/1"))
            .andExpect(status().isOk());

        mockMvc.perform(get("/friendships"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].firstFriend").value("userC"));
    }
}