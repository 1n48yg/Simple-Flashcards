package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents a set of flashcards, this will be the main object that the user interacts with, adding, removing, editing,
// and ultimately self-quizzing with using the cards stored within an object of this class.
public class FlashcardSet implements Writable {
    private List<Card> cardSet;

    // EFFECTS: Instantiates the flashcard set
    public FlashcardSet() {
        cardSet = new ArrayList<>();
    }

    // EFFECTS: returns the internal arraylist as an array to allow it to be used in a JList
    public Object[] toArray() {
        return cardSet.toArray();
    }

    // MODIFIES: this
    // EFFECTS: adds the given Card to this set and logs the action
    public void add(Card c) {
        cardSet.add(c);
        EventLog.getInstance().logEvent(new Event("New flashcard added to set."));
    }

    // MODIFIES: this
    // EFFECTS: resets/clears the entire set
    public void clear() {
        cardSet.clear();
    }

    // REQUIRES: the index position of 'i' in cardSet must be defined
    // MODIFIES: this
    // EFFECTS: removes the card at index i and logs the action
    public void removeQuestion(int i) {
        cardSet.remove(i);
        EventLog.getInstance().logEvent(new Event("Flashcard removed from set."));
    }

    // REQUIRES: the index position of 'i' in cardSet must be defined
    // EFFECTS: returns the card in the set at index 'i'
    public Card get(int i) {
        return cardSet.get(i);
    }

    // EFFECTS: returns the total number of elements in this flashcard set
    public int size() {
        return cardSet.size();
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("cards", processCardsForJsonFormat());
        return json;
    }

    // EFFECTS: returns things in this flashcard set as a JSON array
    private JSONArray processCardsForJsonFormat() {
        JSONArray jsonArray = new JSONArray();

        for (Card c : cardSet) {
            jsonArray.put(c.toJson());
        }

        return jsonArray;
    }
}
