package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TestTest {

    model.Test test;
    FlashcardSet set;


    @BeforeEach
    public void setup() {
        set = new FlashcardSet();
        for (int i = 0; i < 5; i++) {
            set.add(new Card("question" + i, "answer" + i));
        }
        test = new model.Test(set);
    }

    @Test
    public void testSubmission() {
        assertEquals(test.getTotalQuestions(), 5);
        assertEquals(test.getCorrectAnswers(), 0);
        test.submitAnswer(0, "answer0");
        test.submitAnswer(1, "answer1");
        test.submitAnswer(2, "answer3");
        test.submitAnswer(3, "answer2");
        test.submitAnswer(4, "answer4");
        assertEquals(test.getTotalQuestions(), 5);
        assertEquals(test.getCorrectAnswers(), 3);
        assertTrue(test.getWrongAnswers().get(0).getQuestion().equals("question2"));
        assertTrue(test.getWrongAnswers().get(1).getQuestion().equals("question3"));
    }
}
