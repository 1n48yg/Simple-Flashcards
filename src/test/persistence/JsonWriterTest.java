package persistence;

import model.Card;
import model.FlashcardSet;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonWriterTest {
    @Test
    void testWritingToIllegalFileName() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException expected");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    void testWritingEmptyCardset() {
        try {
            FlashcardSet flashcardSet = new FlashcardSet();
            JsonWriter writer = new JsonWriter("./data/writerEmptyTest.json");
            writer.open();
            writer.write(flashcardSet);
            writer.close();

            JsonReader reader = new JsonReader("./data/writerEmptyTest.json");
            flashcardSet = reader.read();
            assertEquals(0, flashcardSet.size());
        } catch (IOException e) {
            fail("Unexpected IOException");
        }
    }

    @Test
    void testWritingGeneralCardset() {
        try {
            FlashcardSet flashcardSet = new FlashcardSet();
            flashcardSet.add(new Card("tquest1", "tanswer1"));
            flashcardSet.add(new Card("tquest2", "tanswer2"));
            JsonWriter writer = new JsonWriter("./data/writerGeneralTest.json");
            writer.open();
            writer.write(flashcardSet);
            writer.close();

            JsonReader reader = new JsonReader("./data/writerGeneralTest.json");
            flashcardSet = reader.read();
            assertEquals(2, flashcardSet.size());
            cardSetJsonValidityTest("tquest1", "tanswer1", flashcardSet.get(0));
            cardSetJsonValidityTest("tquest2", "tanswer2", flashcardSet.get(1));
        } catch (IOException e) {
            fail("Unexpected IOException");
        }
    }

    private void cardSetJsonValidityTest(String question, String answer, Card card) {
        assertEquals(question, card.getQuestion());
        assertEquals(answer, card.getAnswer());
    }
}