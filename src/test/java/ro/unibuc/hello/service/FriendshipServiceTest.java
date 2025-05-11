package ro.unibuc.hello.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;


import ro.unibuc.hello.data.Friendship;
import ro.unibuc.hello.data.FriendshipRepository;
import ro.unibuc.hello.exception.EntityNotFoundException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
class FriendshipServiceTest {

    @Mock
    private FriendshipRepository friendshipRepository;

    @Mock
    private Counter customCounter;

    @Mock
    private MeterRegistry meterRegistry;

    @InjectMocks
    private FriendshipService friendshipService;

    private Friendship friendship;

    @BeforeEach
    void setUp() {
        friendship = new Friendship();
        friendship.setId("1");
        friendship.setFirstFriend("FriendA");
        friendship.setSecondFriend("FriendB");

        when(meterRegistry.counter("custom_counter", "type", "friendship_created")).thenReturn(customCounter);
        when(meterRegistry.counter("custom_counter", "type", "friendship_deleted")).thenReturn(customCounter);
    }

    @Test
    void testFindFriendshipByFirstFriend() {
        when(friendshipRepository.findByFirstFriend(friendship.getFirstFriend())).thenReturn(Optional.of(friendship));
        Optional<Friendship> foundFriendship = friendshipService.findFriendshipByFirstFriend(friendship.getFirstFriend());
        assertTrue(foundFriendship.isPresent());
        assertEquals(friendship.getFirstFriend(), foundFriendship.get().getFirstFriend());
    }

    @Test
    void testFindFriendshipBySecondFriend() {
        when(friendshipRepository.findBySecondFriend(friendship.getSecondFriend())).thenReturn(Optional.of(friendship));
        Optional<Friendship> foundFriendship = friendshipService.findFriendshipBySecondFriend(friendship.getSecondFriend());
        assertTrue(foundFriendship.isPresent());
        assertEquals(friendship.getSecondFriend(), foundFriendship.get().getSecondFriend());
    }

    @Test
    void testFindAllFriendships() {
        List<Friendship> friendships = Arrays.asList(friendship, new Friendship("2", "FriendC", "FriendD", LocalDate.of(2023, 12, 11)));
        when(friendshipRepository.findAll()).thenReturn(friendships);
        List<Friendship> result = friendshipService.findAll();
        assertEquals(2, result.size());
    }

    @Test
    void testCreateFriendship() {
        when(friendshipRepository.save(friendship)).thenReturn(friendship);
        when(meterRegistry.counter("custom_counter", "type", "friendship_created")).thenReturn(customCounter);

        Friendship createdFriendship = friendshipService.createFriendship(friendship);
        assertNotNull(createdFriendship);
        assertEquals(friendship.getId(), createdFriendship.getId());
        verify(customCounter, times(1)).increment();
    }

    @Test
    void testUpdateFriendship_Success() throws EntityNotFoundException {
        when(friendshipRepository.existsById(friendship.getId())).thenReturn(true);
        when(friendshipRepository.save(friendship)).thenReturn(friendship);

        Friendship updatedFriendship = friendshipService.updateFriendship(friendship.getId(), friendship);
        assertEquals(friendship.getId(), updatedFriendship.getId());
    }

    @Test
    void testUpdateFriendship_NotFound() {
        when(friendshipRepository.existsById(friendship.getId())).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> friendshipService.updateFriendship(friendship.getId(), friendship));
    }

    @Test
    void testPatchFriendship_Success() throws EntityNotFoundException {
        when(friendshipRepository.existsById(friendship.getId())).thenReturn(true);
        when(friendshipRepository.save(friendship)).thenReturn(friendship);
        Friendship patchedFriendship = friendshipService.patchFriendship(friendship.getId(), friendship);
        assertEquals(friendship.getId(), patchedFriendship.getId());
    }

    @Test
    void testPatchFriendship_NotFound() {
        when(friendshipRepository.existsById(friendship.getId())).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> friendshipService.patchFriendship(friendship.getId(), friendship));
    }

    @Test
    void testDeleteFriendship_Success() throws EntityNotFoundException {
        when(friendshipRepository.existsById(friendship.getId())).thenReturn(true);

        doNothing().when(friendshipRepository).deleteById(friendship.getId());
        assertDoesNotThrow(() -> friendshipService.deleteFriendship(friendship.getId()));

    }

    @Test
    void testDeleteFriendship_NotFound() {
        when(friendshipRepository.existsById(friendship.getId())).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> friendshipService.deleteFriendship(friendship.getId()));
    }
}
