package persistence;

import model.Card;
import model.FlashcardSet;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonReaderTest {

    @Test
    void testNonExistentCardsetRead() {
        JsonReader reader = new JsonReader("./data/nonExistentFile.json");
        try {
            FlashcardSet flashcardSet = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    void testEmptyCardsetRead() {
        JsonReader reader = new JsonReader("./data/emptyCardSet.json");
        try {
            FlashcardSet flashcardSet = reader.read();
            assertEquals(0, flashcardSet.size());
        } catch (IOException e) {
            fail("Couldn't read from specified file");
        }
    }

    @Test
    void generalCardsetReadTest() {
        JsonReader reader = new JsonReader("./data/testSet1.json");
        try {
            FlashcardSet flashcardSet = reader.read();
            assertEquals(2, flashcardSet.size());
            cardSetJsonValidityTest("tquest1", "tanswer1", flashcardSet.get(0));
            cardSetJsonValidityTest("tquest spaced", "tanswer spaced", flashcardSet.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

     private void cardSetJsonValidityTest(String question, String answer, Card card) {
        assertEquals(question, card.getQuestion());
        assertEquals(answer, card.getAnswer());
    }
}
