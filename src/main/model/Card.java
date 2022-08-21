package model;

import org.json.JSONObject;
import persistence.Writable;

// This the class for individual cards within the set that the user interacts with, each contains a question and a
// corresponding answer, along with functions on how to interact with those two properties.
public class Card implements Writable {
    private String question;
    private String answer;

    // MODIFIES: this
    // EFFECTS: creates a new Card object initialized with the given question along with its answer
    public Card(String question, String answer) {
        editQuestion(question);
        editAnswer(answer);
    }

    // MODIFIES: this
    // EFFECTS: changes the Card object's question to the given parameter
    public void editQuestion(String question) {
        this.question = question;
    }

    // MODIFIES: this
    // EFFECTS: changes the Card object's answer to the given parameter
    public void editAnswer(String answer) {
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("question", question);
        json.put("answer", answer);
        return json;
    }
}
