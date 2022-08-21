package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FlashcardSetTest {
    FlashcardSet testSet;

    @BeforeEach
    public void setup() {
        testSet = new FlashcardSet();
    }

    @Test
    public void testAddOne() {
        assertEquals(testSet.size(), 0);
        Card card = new Card("testq", "testa");
        testSet.add(card);
        assertTrue(testSet.get(0).equals(card));
        assertEquals(testSet.size(), 1);
        testSet.removeQuestion(0);
        assertEquals(testSet.size(), 0);
    }

    @Test
    public void testAddMultiple() {
        assertEquals(testSet.size(), 0);
        for (int i = 0; i < 10; i++) {
            testSet.add(new Card("testq" + i, "testa" + i));
            assertTrue(testSet.get(i).getQuestion().equals("testq" + i));
            assertTrue(testSet.get(i).getAnswer().equals("testa" + i));
            assertEquals(testSet.size(), i+1);
        }

        testSet.removeQuestion(7);
        testSet.removeQuestion(6);
        assertEquals(testSet.size(), 8);
        assertTrue(testSet.get(7).getQuestion().equals("testq9"));
        assertTrue(testSet.get(7).getAnswer().equals("testa9"));
    }

    @Test
    public void testClear() {
        for (int i = 0; i < 10; i++) {
            testSet.add(new Card("testq" + i, "testa" + i));
            assertEquals(testSet.size(), i+1);
        }

        testSet.clear();

        assertEquals(testSet.size(), 0);
    }

    @Test
    public void testArray() {
        testSet.add(new Card("Test", "ans"));
        assertEquals(testSet.toArray().length, 1);
    }
}
