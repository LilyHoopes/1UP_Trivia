/*
 * Responsible for loading trivia questions from an SQLite database and
 * serving them to the game in randomized order. This class maintains
 * a static question bank and provides sequential access.
 */

package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Factory class responsible for loading trivia questions from a database
 * and providing access to them in a randomized order.
 *
 * @author Lily Hoopes
 * @author Komalpreet Dhaliwal
 * @author Christiannel Maningat
 * @version 5/20/2025
 */

public class QuestionFactory {

    /** The file path to the SQLite database. */
    private final String myDBPath;

    /** The list of loaded trivia questions. */
    private static final ArrayList<TriviaQuestion> myQuestions = new ArrayList<>();

    /** The current index of the question to return. */
    private int currentIndex = 0;

    /**
     * Constructs a QuestionFactory and loads trivia questions from the specified database.
     *
     * @param theDBPath the file path to the SQLite database.
     */
    public QuestionFactory(final String theDBPath) {
        this.myDBPath = theDBPath;
        loadQuestionsFromDatabase();
    }

    /**
     * Loads trivia questions from the SQLite database and stores them in a shuffled list.
     */
    private void loadQuestionsFromDatabase() {
        final String query = """
            SELECT question, option_a, option_b, option_c, option_d, correct_answer FROM TriviaQuestions;
        """;

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                TriviaQuestion question = mapRowToQuestion(rs);
                if (question != null) {
                    myQuestions.add(question);
                }
            }

        } catch (SQLException theError) {
            System.err.printf("Failed to load trivia questions: %s%n", theError.getMessage());
            // Consider logging or rethrowing as a runtime exception depending on your use case
        }
        Collections.shuffle(myQuestions);
    }

    /**
     * Establishes and returns a connection to the SQLite database.
     *
     * @return the database connection.
     * @throws SQLException if the connection fails.
     */
    private Connection connect() throws SQLException {
        String jdbcUrl = String.format("jdbc:sqlite:%s", myDBPath);
        return DriverManager.getConnection(jdbcUrl);
    }

    /**
     * Maps a result set row to a TriviaQuestion object.
     *
     * @param theRS the result set row.
     * @return a TriviaQuestion object or null if mapping fails.
     */
    private TriviaQuestion mapRowToQuestion(final ResultSet theRS) {
        try {
            String questionText = theRS.getString("question");
            String[] options = {
                    theRS.getString("option_a"),
                    theRS.getString("option_b"),
                    theRS.getString("option_c"),
                    theRS.getString("option_d")
            };
            String correctAnswer = theRS.getString("correct_answer");

            return new TriviaQuestion(questionText, options, correctAnswer);
        } catch (SQLException e) {
            System.err.printf("Invalid row data: %s%n", e.getMessage());
            return null;
        }
    }

    /**
     * Returns the list of loaded trivia questions.
     *
     * @return the list of TriviaQuestion objects.
     */
    public static ArrayList<TriviaQuestion> getQuestions() {
        return myQuestions;
    }

    /**
     * Prints all loaded trivia questions to the console.
     * Used for testing and debugging.
     */
    public static void printQuestions() {
        //FOR TESTING
        ArrayList<TriviaQuestion> questions = getQuestions();

        if (questions.isEmpty()) {
            System.out.println("Question list is EMPTY.");
        } else {
            for (int i = 0; i < questions.size(); i++) {
                System.out.println("Q" + (i + 1) + ": " + questions.get(i));
            }
        }
    }

    /**
     * Returns the next trivia question in the sequence, or null if all questions have been returned.
     *
     * @return the next TriviaQuestion or null if none remain.
     */
    public TriviaQuestion getNextQuestion() {
        if (currentIndex < myQuestions.size()) {
            return myQuestions.get(currentIndex++);
        } else {
            return null; // or reset index to loop: currentIndex = 0;
        }
    }

    /**
     * Returns the number of trivia questions loaded.
     *
     * @return the number of questions.
     */
    public int size() {
        return myQuestions.size();
    }
}