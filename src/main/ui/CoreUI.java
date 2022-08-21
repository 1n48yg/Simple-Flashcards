package ui;

import model.Card;
import model.Event;
import model.EventLog;
import model.FlashcardSet;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

// The graphical user interface of this program
public class CoreUI {
    private JFrame window;

    private ImageIcon addIcon;
    private ImageIcon removeIcon;
    private ImageIcon saveIcon;
    private ImageIcon loadIcon;

    private JPanel leftPanel;
    private JPanel rightPanel;
    private JPanel centerPanel;

    private JPanel addPanel;
    private JPanel editPanel;

    JButton addButton;
    JButton removeButton;
    JButton saveButton;
    JButton loadButton;

    JList flashcardsListGUI;
    FlashcardSet cardset;

    private static final String JSON_FILE_LOCATION = "./data/flashcards.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // REQUIRES: only one of these per instance of program
    // EFFECTS: constructor to run setup
    public CoreUI() {
        run();
    }

    // EFFECTS: conducts the necessary procedures to setup the UI for use, main setup function, also creates a window
    //  listener to output logged events when the main window is closed
    public void run() {
        cardset = new FlashcardSet();

        //initializing everything
        initializeIcons();
        initializeFrame();
        setupLeftPanel();
        setRightPanel();
        setupCenterPanel();
        setupMainButtons();
        setupListeners();

        leftPanel.setBackground(Color.gray);
        rightPanel.setBackground(Color.lightGray);

        jsonWriter = new JsonWriter(JSON_FILE_LOCATION);
        jsonReader = new JsonReader(JSON_FILE_LOCATION);

        window.setVisible(true);

        window.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                for (Event next : EventLog.getInstance()) {
                    System.out.println(next.toString() + "\n");
                }
            }

        });
    }

    // REQUIRES: cardset != null
    // MODIFIES: this, rightPanel, flashcardsListGUI
    // EFFECTS: updates the right panel and the list of cards to match what the current flashcardset contains
    private void updateList() {
        if (cardset.size() != 0) {
            flashcardsListGUI = new JList(cardset.toArray());
            flashcardsListGUI.setVisibleRowCount(40);
            flashcardsListGUI.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            rightPanel.removeAll();
            rightPanel.add(new JScrollPane(flashcardsListGUI));

            flashcardsListGUI.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    showViewMenu("Question: " + cardset.get(flashcardsListGUI.getSelectedIndex()).getQuestion(),
                            "Answer: " + cardset.get(flashcardsListGUI.getSelectedIndex()).getAnswer());
                }
            });
        } else {
            rightPanel.removeAll();
            rightPanel.repaint();
        }
    }

    // MODIFIES: this, centerPanel
    // EFFECTS: changes the central panel to display the screen and information necessary for adding new cards
    private void showAddMenu() {
        centerPanel.removeAll();
        JPanel addPanel = new JPanel();
        addPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        centerPanel.add(addPanel);

        JLabel currentQuestionBox = new JLabel("New Question: ");
        JTextField questionInput = new JTextField(80);
        JLabel currentAnswerBox = new JLabel("Answer to Question: ");
        JTextField answerInput = new JTextField(80);
        JButton cardSaveButton = new JButton("Add Card");

        addPanel.add(currentQuestionBox);
        addPanel.add(questionInput);
        addPanel.add(currentAnswerBox);
        addPanel.add(answerInput);
        addPanel.add(cardSaveButton);

        cardSaveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guiPromptAddCard(questionInput.getText(), answerInput.getText());
                questionInput.setText("");
                answerInput.setText("");
            }
        });

        window.setVisible(true);
    }

    // MODIFIES: cardset
    // EFFECTS: intermediate method for connecting the add card button from the add menu to all the necessary processes
    //  such as adding the card internally within out flashcard set, and updating the visual list of cards on the right
    //  panel
    public void guiPromptAddCard(String question, String answer) {
        cardset.add(new Card(question, answer));
        updateList();
        window.setVisible(true);
    }

    // MODIFIES: centralPanel
    // EFFECTS: displays the selected card's question and answer on the central panel
    private void showViewMenu(String question, String answer) {
        centerPanel.removeAll();
        JPanel viewPanel = new JPanel();
        viewPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        centerPanel.add(viewPanel);

        JLabel currentQuestionBox = new JLabel(question);
        JLabel currentAnswerBox = new JLabel(answer);
        viewPanel.add(currentQuestionBox);
        viewPanel.add(currentAnswerBox);

        window.setVisible(true);
    }

    // EFFECTS: helper function for what happens when the add button on the left is clicked
    private void onAddClick() {
        showAddMenu();
    }

    // MODIFIES: this, cardset
    // EFFECTS: when remove button is clicked, removes the selected card from the list and updates the list and GUI
    private void onRemoveClick() {
        if (!(flashcardsListGUI.getSelectedIndex() == -1)) {
            cardset.removeQuestion(flashcardsListGUI.getSelectedIndex());
            updateList();
            centerPanel.removeAll();
            centerPanel.repaint();
            window.setVisible(true);
        }
    }

    // EFFECTS: saves the flashcard set to file
    private void onSaveClick() {
        try {
            jsonWriter.open();
            jsonWriter.write(cardset);
            jsonWriter.close();
            centerPanel.removeAll();
            centerPanel.repaint();
            centerPanel.add(new JLabel("Successfully wrote flashcardset to " + JSON_FILE_LOCATION));
            window.setVisible(true);
        } catch (FileNotFoundException e) {
            centerPanel.removeAll();
            centerPanel.repaint();
            centerPanel.add(new JLabel("Unable to write to file: " + JSON_FILE_LOCATION));
            window.setVisible(true);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads flashcard set from file
    private void onLoadClick() {
        try {
            cardset = jsonReader.read();
            centerPanel.removeAll();
            centerPanel.repaint();
            centerPanel.add(new JLabel("Loaded flashcard set from " + JSON_FILE_LOCATION));
            updateList();
            window.setVisible(true);
        } catch (IOException e) {
            centerPanel.removeAll();
            centerPanel.repaint();
            centerPanel.add(new JLabel("Unable to read from file: " + JSON_FILE_LOCATION));
            window.setVisible(true);
        }
    }

    // REQUIRES: buttons have already been setup
    // MODIFIES: this
    // EFFECTS: creates listeners for main menu buttons
    private void setupListeners() {
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onAddClick();
            }
        });

        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onRemoveClick();
            }
        });

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onSaveClick();
            }
        });

        loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onLoadClick();
            }
        });
    }

    // REQUIRES: left Panel has already been setup
    // MODIFIES: leftPanel
    // EFFECTS: handles all graphical tasks of creating main buttons
    private void setupMainButtons() {
        addButton = new JButton("Add");
        addButton.setPreferredSize(new Dimension(200, 40));
        addButton.setFont(new Font("Arial", Font.PLAIN, 30));
        addButton.setIcon(addIcon);
        leftPanel.add(addButton);

        removeButton = new JButton("Remove");
        removeButton.setPreferredSize(new Dimension(200, 40));
        removeButton.setFont(new Font("Arial", Font.PLAIN, 30));
        removeButton.setIcon(removeIcon);
        leftPanel.add(removeButton);

        saveButton = new JButton("Save");
        saveButton.setPreferredSize(new Dimension(200, 40));
        saveButton.setFont(new Font("Arial", Font.PLAIN, 30));
        saveButton.setIcon(saveIcon);
        leftPanel.add(saveButton);

        loadButton = new JButton("Load");
        loadButton.setPreferredSize(new Dimension(200, 40));
        loadButton.setFont(new Font("Arial", Font.PLAIN, 30));
        loadButton.setIcon(loadIcon);
        leftPanel.add(loadButton);
    }

    // REQUIRES: the necessary files exist in the data folder
    // MODIFIES: this
    // EFFECTS: sets up all the icons
    private void initializeIcons() {
        int iconSize = 40;
        Image addImage = new ImageIcon("data/add.png").getImage().getScaledInstance(
                iconSize, iconSize, java.awt.Image.SCALE_SMOOTH);
        Image removeImage = new ImageIcon("data/remove.png").getImage().getScaledInstance(
                iconSize, iconSize, java.awt.Image.SCALE_SMOOTH);
        Image saveImage = new ImageIcon("data/save.png").getImage().getScaledInstance(
                iconSize, iconSize, java.awt.Image.SCALE_SMOOTH);
        Image loadImage = new ImageIcon("data/load.png").getImage().getScaledInstance(
                iconSize, iconSize, java.awt.Image.SCALE_SMOOTH);
        addIcon = new ImageIcon(addImage);
        removeIcon = new ImageIcon(removeImage);
        saveIcon = new ImageIcon(saveImage);
        loadIcon = new ImageIcon(loadImage);
    }

    // MODIFIES: this
    // EFFECTS: sets up the main frame of the window for the application
    private void initializeFrame() {
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setSize(1600, 900);
        window.setTitle("Simple Flashcards");
        window.setLocationRelativeTo(null);
        window.setLayout(new BorderLayout(5, 5));
    }

    // REQUIRES: main window has already been setup
    // MODIFIES: this, leftPanel
    // EFFECTS: sets up the left panel
    private void setupLeftPanel() {
        leftPanel = new JPanel();
        leftPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        leftPanel.setPreferredSize(new Dimension(250, 250));
        window.add(leftPanel, BorderLayout.WEST);
    }

    // REQUIRES: main window has already been setup
    // MODIFIES: this, leftPanel
    // EFFECTS: sets up the right panel
    private void setRightPanel() {
        rightPanel = new JPanel();
        rightPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        rightPanel.setPreferredSize(new Dimension(250, 250));
        window.add(rightPanel, BorderLayout.EAST);
    }

    // REQUIRES: main window has already been setup
    // MODIFIES: this, leftPanel
    // EFFECTS: sets up the center panel
    private void setupCenterPanel() {
        centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout());
        centerPanel.setPreferredSize(new Dimension(250, 250));
        window.add(centerPanel, BorderLayout.CENTER);
    }
}
