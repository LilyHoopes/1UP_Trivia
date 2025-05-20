package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/*
 * Factory class to load trivia questions from an SQLite database
 * and provide access to questions via next, random, or shuffled order.
 */
public class QuestionFactory {
    // List holding all loaded TriviaQuestion objects
    private final List<TriviaQuestion> myQuestions = new ArrayList<>();
    // Index tracking the current question for sequential access
    private int myCurrentIndex = 0;
    // Random instance for random question selection and shuffling
    private final Random myRandom = new Random();

    /*
     * Constructor - loads all questions from the specified SQLite DB file
     */
    public QuestionFactory(String dbFilePath) {
        loadQuestions(dbFilePath);
    }

    /*
     * Loads all questions from the database into the myQuestions list.
     * Each row in the "questions" table should have columns:
     * QUESTION, OPTION_A, OPTION_B, OPTION_C, OPTION_D, CORRECT_ANSWER.
     */

    /*
    * import java.sql.*;

public class SQLiteExample {
    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:school.db")) {
            Statement stmt = conn.createStatement();
            stmt.execute("CREATE TABLE IF NOT EXISTS students (id INTEGER PRIMARY KEY, name TEXT, grade INTEGER)");
            stmt.execute("INSERT INTO students (name, grade) VALUES ('Charlie', 88)");

            ResultSet rs = stmt.executeQuery("SELECT * FROM students");
            while (rs.next()) {
                System.out.println(rs.getInt("id") + " | " + rs.getString("name") + " | " + rs.getInt("grade"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
* */
    private void loadQuestions(String dbFilePath) {
        String url = "jdbc:sqlite:" + dbFilePath;

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:trivia_questions.db");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM questions")) {

            // Iterate over all result rows and create TriviaQuestion objects
            while (rs.next()) {
                String questionText = rs.getString("QUESTION");
                String optionA = rs.getString("OPTION_A");
                String optionB = rs.getString("OPTION_B");
                String optionC = rs.getString("OPTION_C");
                String optionD = rs.getString("OPTION_D");
                String correctAnswer = rs.getString("CORRECT_ANSWER");

                // Store multiple choice options in an array
                String[] choices = {optionA, optionB, optionC, optionD};

                // Add the new TriviaQuestion to the list
                myQuestions.add(new TriviaQuestion(questionText, choices, correctAnswer));
            }

        } catch (SQLException e) {
            System.err.println("Error loading questions from database:");
            e.printStackTrace();
        }
    }

    /*
     * Returns the next question in the list.
     * If the end is reached, it cycles back to the start.
     *
     * return Next TriviaQuestion or null if no questions loaded
     */
    public TriviaQuestion getNextQuestion() {
        if (myQuestions.isEmpty()) return null;

        if (myCurrentIndex >= myQuestions.size()) {
            myCurrentIndex = 0; // Reset index to cycle questions
        }

        return myQuestions.get(myCurrentIndex++);
    }

    /*
     * Returns a random question from the list.
     *
     * return Random TriviaQuestion or null if no questions loaded
     */
    public TriviaQuestion getRandomQuestion() {
        if (myQuestions.isEmpty()) return null;

        int randomIndex = myRandom.nextInt(myQuestions.size());
        return myQuestions.get(randomIndex);
    }

    /*
     * Returns a copy of the entire list of loaded questions.
     *
     * return List of all TriviaQuestion objects
     */
    public List<TriviaQuestion> getAllQuestions() {
        return new ArrayList<>(myQuestions); // Return copy to avoid external modification
    }

    /*
     * Randomly shuffles the list of questions and resets current index.
     * Useful to randomize question order for quizzes.
     */
    public void shuffleQuestions() {
        Collections.shuffle(myQuestions, myRandom);
        myCurrentIndex = 0;
    }

    /*
     * Checks if there are any questions loaded.
     *
     * return true if at least one question is loaded, false otherwise
     */
    public boolean hasQuestions() {
        return !myQuestions.isEmpty();
    }

    /*
     * Returns the total number of questions loaded.
     *
     * return Number of TriviaQuestion objects
     */
    public int size() {
        return myQuestions.size();
    }
}


