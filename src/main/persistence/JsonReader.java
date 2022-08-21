package persistence;

import model.Card;
import model.FlashcardSet;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads saved flashcard set from file and returns it
    // throws IOException if an error occurs reading data from file
    public FlashcardSet read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseCardSet(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder fileContent = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(fileContent::append);
        }

        return fileContent.toString();
    }

    // EFFECTS: parses flashcard set from JSON object and returns it
    private FlashcardSet parseCardSet(JSONObject jsonObject) {
        FlashcardSet cardSet = new FlashcardSet();
        addCards(cardSet, jsonObject);
        return cardSet;
    }

    // MODIFIES: cardSet
    // EFFECTS: parses cards from JSON object and adds them to the current flashcard set
    private void addCards(FlashcardSet cardSet, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("cards");
        for (Object json : jsonArray) {
            JSONObject nextThingy = (JSONObject) json;
            addCard(cardSet, nextThingy);
        }
    }

    // MODIFIES: cardSet
    // EFFECTS: parses card from JSON object and adds it to the current flashcard set
    private void addCard(FlashcardSet cardSet, JSONObject jsonObject) {
        String question = jsonObject.getString("question");
        String answer = jsonObject.getString("answer");
        cardSet.add(new Card(question, answer));
    }
}
