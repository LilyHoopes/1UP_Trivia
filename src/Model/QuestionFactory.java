package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class QuestionFactory {
    private final List<TriviaQuestion> myQuestions = new ArrayList<>();

    private int myCurrentIndex = 0;

    private final Random myRandom = new Random();

    public QuestionFactory(String dbFilePath) {
        loadQuestions(dbFilePath);
    }

    private void loadQuestions(String dbFilePath) {
        // Form the JDBC URL using the provided file path
        String url = "jdbc:sqlite:" + dbFilePath;

        // Use try-with-resources to ensure database resources are closed properly
        try (Connection conn = DriverManager.getConnection(url);  // Connect to the SQLite database
             Statement stmt = conn.createStatement();              // Create a statement for executing SQL
             ResultSet rs = stmt.executeQuery("SELECT * FROM questions")) {  // Execute SQL to get all questions

            // Loop through each row in the result set
            while (rs.next()) {
                // Retrieve the question and all four answer options from the current row
                String questionText = rs.getString("question");
                String optionA = rs.getString("option_a");
                String optionB = rs.getString("option_b");
                String optionC = rs.getString("option_c");
                String optionD = rs.getString("option_d");

                // Get the correct answer (should match one of the options)
                String correctAnswer = rs.getString("correct_answer");

                // Create an array to hold the answer choices
                String[] choices = {optionA, optionB, optionC, optionD};

                // Create a new TriviaQuestion object using the extracted data
                TriviaQuestion question = new TriviaQuestion(questionText, choices, correctAnswer);

                // Add the newly created question to the list
                myQuestions.add(question);
            }

        } catch (SQLException e) {
            // If an SQL error occurs, print the error message and stack trace
            System.err.println("Error loading questions from database:");
            e.printStackTrace();
        }
    }


    public TriviaQuestion getNextQuestion() {
        if (myQuestions.isEmpty()) return null;

        if (myCurrentIndex >= myQuestions.size()) {
            myCurrentIndex = 0;
        }

        return myQuestions.get(myCurrentIndex++);
    }

    public TriviaQuestion getRandomQuestion() {
        if (myQuestions.isEmpty()) return null;

        int randomIndex = myRandom.nextInt(myQuestions.size());
        return myQuestions.get(randomIndex);
    }

    public List<TriviaQuestion> getAllQuestions() {
        return new ArrayList<>(myQuestions); // Defensive copy
    }

    public void shuffleQuestions() {
        Collections.shuffle(myQuestions, myRandom);
        myCurrentIndex = 0;
    }

    public boolean hasQuestions() {
        return !myQuestions.isEmpty();
    }

    public int size() {
        return myQuestions.size();
    }
}
