package model;

import java.util.ArrayList;
import java.util.List;

// This represents a test conducted with a given flashcard set with all its associated information.
public class Test {

    private FlashcardSet flashcardSet;
    private int totalQuestions;
    private int correctAnswers;
    private List<Card> wrongAnswers;

    // REQUIRES: the flashcard set that the test is being conducted with should have at least 1 card
    // EFFECTS: instantiates a new test with the given flashcard set
    public Test(FlashcardSet flashcardSet) {
        this.flashcardSet = flashcardSet;
        totalQuestions = this.flashcardSet.size();
        correctAnswers = 0;
        wrongAnswers = new ArrayList<>();
    }

    // REQUIRES: the index is a valid index position in the flashcard set used by this test
    // MODIFIES: this
    // EFFECTS: submits an answer for the question in this test's flashcard set at the given index's Card,
    //          if it is correct, then add one to correctAnswers, otherwise add the card to the list of wrongAnswers
    public void submitAnswer(int index, String submission) {
        if (flashcardSet.get(index).getAnswer().equals(submission)) {
            correctAnswers += 1;
        } else {
            wrongAnswers.add(flashcardSet.get(index));
        }
    }

    public List<Card> getWrongAnswers() {
        return wrongAnswers;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }
}
