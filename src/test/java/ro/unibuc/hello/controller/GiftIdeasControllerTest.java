package ro.unibuc.hello.controller;

import ro.unibuc.hello.data.GiftIdeas;
import ro.unibuc.hello.service.GiftIdeasService;
import ro.unibuc.hello.exception.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class GiftIdeasControllerTest {

    @Mock
    private GiftIdeasService giftIdeasService;

    @InjectMocks
    private GiftIdeasController giftIdeasController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(giftIdeasController).build();
    }

    @Test
    void test_getGiftIdeaById() throws Exception {
        GiftIdeas giftIdea = new GiftIdeas("1", "iPhone", "https://flip.ro/magazin/apple/telefon-mobil-apple-iphone-15-128gb-black/75267921/", "John", "Mary");
        when(giftIdeasService.findById("1")).thenReturn(giftIdea);

        mockMvc.perform(get("/gift-ideas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.giftName").value("iPhone"))
                .andExpect(jsonPath("$.giftReceiver").value("Mary"))
                .andExpect(jsonPath("$.giftGiver").value("John"));
    }

    @Test
    void test_getGiftIdeaById_NotFound() throws Exception {
        when(giftIdeasService.findById("unknown")).thenThrow(new EntityNotFoundException("Gift idea not found"));

        mockMvc.perform(get("/gift-ideas/unknown"))
                .andExpect(status().isNotFound());
    }

    @Test
    void test_getAllGiftIdeas() throws Exception {
        List<GiftIdeas> giftIdeas = Arrays.asList(
            new GiftIdeas("1", "iPhone", "https://flip.ro/magazin/apple/telefon-mobil-apple-iphone-15-128gb-black/75267921/", "John", "Mary"),
                new GiftIdeas("2", "Samsung", "https://flip.ro/magazin/samsung/telefon-mobil-samsung-galaxy-s21-fe-5g-dual-sim-128gb-graphite/73327529/", "Sean", "Paul")
        );
        when(giftIdeasService.findAll()).thenReturn(giftIdeas);

        mockMvc.perform(get("/gift-ideas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].giftName").value("iPhone"))
                .andExpect(jsonPath("$[1].id").value("2"))
                .andExpect(jsonPath("$[1].giftName").value("Samsung"));
    }

    @Test
    void test_createGiftIdea() throws Exception {
        GiftIdeas newGiftIdea = new GiftIdeas("1", "John", "Mary", "Gift 1", "Description of Gift 1");
        when(giftIdeasService.createGiftIdeas(any(GiftIdeas.class))).thenReturn(newGiftIdea);

        mockMvc.perform(post("/gift-ideas")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"giftName\":\"Gift 1\", \"giftReceiver\":\"Mary\", \"giftGiver\":\"John\", \"description\":\"Description of Gift 1\"}"))
            .andExpect(status().isCreated())
            .andExpect(content().string("Gift idea created successfully"));
    }

    @Test
    void test_updateGiftIdea() throws Exception {
        GiftIdeas updatedGiftIdea = new GiftIdeas("1", "iPhone", "https://flip.ro/magazin/apple/telefon-mobil-apple-iphone-15-128gb-black/75267921/", "John", "Mary");
        when(giftIdeasService.updateGiftIdea(eq("1"), any(GiftIdeas.class))).thenReturn(updatedGiftIdea);

        mockMvc.perform(put("/gift-ideas/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":\"1\", \"giftName\":\"iPhone\", \"giftReceiver\":\"Mary\", \"giftGiver\":\"John\", \"giftLink\":\"https://flip.ro/magazin/apple/telefon-mobil-apple-iphone-15-128gb-black/75267921/\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.giftName").value("iPhone"))
                .andExpect(jsonPath("$.giftReceiver").value("Mary"))
                .andExpect(jsonPath("$.giftGiver").value("John"))
                .andExpect(jsonPath("$.giftLink").value("https://flip.ro/magazin/apple/telefon-mobil-apple-iphone-15-128gb-black/75267921/"));
    }

    @Test
    void test_patchGiftIdea() throws Exception {
        GiftIdeas patchedGiftIdea = new GiftIdeas("1", "iPhone", "https://flip.ro/magazin/apple/telefon-mobil-apple-iphone-15-128gb-black/75267921/", "John", "Mary");
        when(giftIdeasService.patchGiftIdea(eq("1"), any(GiftIdeas.class))).thenReturn(patchedGiftIdea);

        mockMvc.perform(patch("/gift-ideas/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"giftName\":\"iPhone\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.giftName").value("iPhone"));
    }

    @Test
    void test_deleteGiftIdea() throws Exception {
        doNothing().when(giftIdeasService).deleteGiftIdea("1");

        mockMvc.perform(delete("/gift-ideas/1"))
                .andExpect(status().isNoContent());

        verify(giftIdeasService, times(1)).deleteGiftIdea("1");
    }
}