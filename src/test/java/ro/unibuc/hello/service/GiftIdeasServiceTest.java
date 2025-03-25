package ro.unibuc.hello.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ro.unibuc.hello.data.GiftIdeas;
import ro.unibuc.hello.data.GiftIdeasRepository;
import ro.unibuc.hello.exception.EntityNotFoundException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class GiftIdeasServiceTest {

    @Mock
    private GiftIdeasRepository giftIdeasRepository;

    @InjectMocks
    private GiftIdeasService giftIdeasService;

    private GiftIdeas giftIdea;

    @BeforeEach
    void setUp() {
        giftIdea = new GiftIdeas("1", "iPHONE", "http://emag.ro", "1", "4");
    }

    @Test
    void testFindGiftIdeasByGiver() {
        when(giftIdeasRepository.findByGiftGiver(giftIdea.getGiftGiver())).thenReturn(Optional.of(giftIdea));
        Optional<GiftIdeas> foundIdea = giftIdeasService.findGiftIdeasByGiver(giftIdea.getGiftGiver());
        assertTrue(foundIdea.isPresent());
        assertEquals(giftIdea.getGiftGiver(), foundIdea.get().getGiftGiver());
    }

    @Test
    void testFindGiftIdeasByReceiver() {
        when(giftIdeasRepository.findByGiftReceiver(giftIdea.getGiftReceiver())).thenReturn(Optional.of(giftIdea));
        Optional<GiftIdeas> foundIdea = giftIdeasService.findGiftIdeasByReceiver(giftIdea.getGiftReceiver());
        assertTrue(foundIdea.isPresent());
        assertEquals(giftIdea.getGiftReceiver(), foundIdea.get().getGiftReceiver());
    }

    @Test
    void testFindAllGiftIdeas() {
        List<GiftIdeas> ideas = Arrays.asList(giftIdea, new GiftIdeas("69", "ipod", "emag.ro", "2", "3"));
        when(giftIdeasRepository.findAll()).thenReturn(ideas);
        List<GiftIdeas> result = giftIdeasService.findAll();
        assertEquals(2, result.size());
    }

    @Test
    void testCreateGiftIdeas() {
        when(giftIdeasRepository.save(giftIdea)).thenReturn(giftIdea);
        GiftIdeas createdIdea = giftIdeasService.createGiftIdeas(giftIdea);
        assertNotNull(createdIdea);
        assertEquals(giftIdea.getId(), createdIdea.getId());
    }

    @Test
    void testUpdateGiftIdea_Success() throws EntityNotFoundException {
        when(giftIdeasRepository.existsById(giftIdea.getId())).thenReturn(true);
        when(giftIdeasRepository.save(giftIdea)).thenReturn(giftIdea);
        GiftIdeas updatedIdea = giftIdeasService.updateGiftIdea(giftIdea.getId(), giftIdea);
        assertEquals(giftIdea.getId(), updatedIdea.getId());
    }

    @Test
    void testUpdateGiftIdea_NotFound() {
        when(giftIdeasRepository.existsById(giftIdea.getId())).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> giftIdeasService.updateGiftIdea(giftIdea.getId(), giftIdea));
    }

    @Test
    void testPatchGiftIdea_Success() throws EntityNotFoundException {
        when(giftIdeasRepository.existsById(giftIdea.getId())).thenReturn(true);
        when(giftIdeasRepository.save(giftIdea)).thenReturn(giftIdea);
        GiftIdeas patchedIdea = giftIdeasService.patchGiftIdea(giftIdea.getId(), giftIdea);
        assertEquals(giftIdea.getId(), patchedIdea.getId());
    }

    @Test
    void testPatchGiftIdea_NotFound() {
        when(giftIdeasRepository.existsById(giftIdea.getId())).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> giftIdeasService.patchGiftIdea(giftIdea.getId(), giftIdea));
    }

    @Test
    void testDeleteGiftIdea_Success() throws EntityNotFoundException {
        when(giftIdeasRepository.existsById(giftIdea.getId())).thenReturn(true);
        doNothing().when(giftIdeasRepository).deleteById(giftIdea.getId());
        assertDoesNotThrow(() -> giftIdeasService.deleteGiftIdea(giftIdea.getId()));
    }

    @Test
    void testDeleteGiftIdea_NotFound() {
        when(giftIdeasRepository.existsById(giftIdea.getId())).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> giftIdeasService.deleteGiftIdea(giftIdea.getId()));
    }
}

