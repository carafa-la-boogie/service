package ro.unibuc.hello.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ro.unibuc.hello.data.GiftIdeas;
import ro.unibuc.hello.service.GiftIdeasService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@Tag("IntegrationTest")
public class GiftIdeasControllerIntegrationTest {

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
    private GiftIdeasService giftIdeasService;

    @BeforeEach
    public void cleanUpAndAddTestData() {
        // Clean up existing data and add sample gift ideas
        giftIdeasService.deleteAll();
        
        GiftIdeas giftIdea1 = new GiftIdeas("1", "iPhone", "https://flip.ro/magazin/apple/telefon-mobil-apple-iphone-15-128gb-black/75267921/", "John", "Mary");
        GiftIdeas giftIdea2 = new GiftIdeas("2", "Laptop", "https://flip.ro/magazin/apple/macbook-pro-16-inch/75267922/", "Alice", "Bob");

        giftIdeasService.saveGiftIdeas(giftIdea1);
        giftIdeasService.saveGiftIdeas(giftIdea2);
    }

    @Test
    public void testGetAllGiftIdeas() throws Exception {
        mockMvc.perform(get("/gift-ideas"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].giftName").value("iPhone"))
            .andExpect(jsonPath("$[1].giftName").value("Laptop"));
    }

    @Test
    public void testCreateGiftIdea() throws Exception {
        GiftIdeas newGiftIdea = new GiftIdeas("3", "Watch", "https://flip.ro/magazin/apple/apple-watch/75267923/", "David", "Sarah");

        mockMvc.perform(post("/gift-ideas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(newGiftIdea)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("3"))
                .andExpect(jsonPath("$.giftName").value("Watch"))
                .andExpect(jsonPath("$.giftReceiver").value("Sarah"));

        mockMvc.perform(get("/gift-ideas"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(3)); // Verify the new gift idea is added
    }

    @Test
    public void testUpdateGiftIdea() throws Exception {
        GiftIdeas updatedGiftIdea = new GiftIdeas("1", "Smartphone", "https://flip.ro/magazin/apple/smartphone-iphone/75267924/", "John", "Mary");

        mockMvc.perform(put("/gift-ideas/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(updatedGiftIdea)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.giftName").value("Smartphone"))
                .andExpect(jsonPath("$.giftReceiver").value("Mary"));
    }

    @Test
    public void testDeleteGiftIdea() throws Exception {
        mockMvc.perform(delete("/gift-ideas/1"))
            .andExpect(status().isOk());

        mockMvc.perform(get("/gift-ideas"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].giftName").value("Laptop"));
    }
}
