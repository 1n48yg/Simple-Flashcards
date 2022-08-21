package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CardTest {

    Card testCard;

    @BeforeEach
    public void setup() {
        testCard = new Card("What is 1 + 1?", "2!");
    }

    @Test
    public void testChangeAnswer() {
        assertTrue(testCard.getAnswer().equals("2!"));
        testCard.editAnswer("not 3!");
        assertTrue(testCard.getAnswer().equals("not 3!"));
        assertFalse(testCard.getAnswer().equals("2!"));
    }

    @Test
    public void testChangeQuestion() {
        assertTrue(testCard.getQuestion().equals("What is 1 + 1?"));
        testCard.editQuestion("What is 2 + 0?");
        assertTrue(testCard.getQuestion().equals("What is 2 + 0?"));
        assertFalse(testCard.getQuestion().equals("What is 1 + 1?"));
    }
}
