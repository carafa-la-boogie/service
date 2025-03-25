package ro.unibuc.hello.data;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GiftIdeasTest {

    private GiftIdeas giftIdeas;

    @BeforeEach
    void setUp() {
        giftIdeas = new GiftIdeas("1", "Smartwatch", "http://example.com/smartwatch", "1", "2");
    }

    @Test
    void testGetId() {
        assertEquals("1", giftIdeas.getId());
    }

    @Test
    void testSetId() {
        giftIdeas.setId("2");
        assertEquals("2", giftIdeas.getId());
    }

    @Test
    void testGetGiftName() {
        assertEquals("Smartwatch", giftIdeas.getGiftName());
    }

    @Test
    void testSetGiftName() {
        giftIdeas.setGiftName("Laptop");
        assertEquals("Laptop", giftIdeas.getGiftName());
    }

    @Test
    void testGetGiftLink() {
        assertEquals("http://example.com/smartwatch", giftIdeas.getGiftLink());
    }

    @Test
    void testSetGiftLink() {
        giftIdeas.setGiftLink("http://example.com/laptop");
        assertEquals("http://example.com/laptop", giftIdeas.getGiftLink());
    }

    @Test
    void testGetGiftGiver() {
        assertEquals("1", giftIdeas.getGiftGiver());
    }

    @Test
    void testSetGiftGiver() {
        giftIdeas.setGiftGiver("1");
        assertEquals("1", giftIdeas.getGiftGiver());
    }

    @Test
    void testGetGiftReceiver() {
        assertEquals("2", giftIdeas.getGiftReceiver());
    }

    @Test
    void testSetGiftReceiver() {
        giftIdeas.setGiftReceiver("2");
        assertEquals("2", giftIdeas.getGiftReceiver());
    }

    @Test
    void testToString() {
        String expected = "GiftIdeas{" +
                "id=1, giftName='Smartwatch', giftLink='http://example.com/smartwatch', " +
                "giftGiver=1, giftReceiver=2}";
        assertEquals(expected, giftIdeas.toString());
    }
}