package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;

public class QuestionFactory {
    private final String dbPath;
    private static final ArrayList<TriviaQuestion> myQuestions = new ArrayList<>();

    private int currentIndex = 0;

    public QuestionFactory(String dbPath) {
        this.dbPath = dbPath;
        loadQuestionsFromDatabase();
    }

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

        } catch (SQLException e) {
            System.err.printf("Failed to load trivia questions: %s%n", e.getMessage());
            // Consider logging or rethrowing as a runtime exception depending on your use case
        }
        Collections.shuffle(myQuestions);
    }

    private Connection connect() throws SQLException {
        String jdbcUrl = String.format("jdbc:sqlite:%s", dbPath);
        return DriverManager.getConnection(jdbcUrl);
    }

    private TriviaQuestion mapRowToQuestion(ResultSet rs) {
        try {
            String questionText = rs.getString("question");
            String[] options = {
                    rs.getString("option_a"),
                    rs.getString("option_b"),
                    rs.getString("option_c"),
                    rs.getString("option_d")
            };
            String correctAnswer = rs.getString("correct_answer");

            return new TriviaQuestion(questionText, options, correctAnswer);
        } catch (SQLException e) {
            System.err.printf("Invalid row data: %s%n", e.getMessage());
            return null;
        }
    }

    public static ArrayList<TriviaQuestion> getQuestions() {
        return myQuestions;
    }

    //for testing
    public static void printQuestions() {
        ArrayList<TriviaQuestion> questions = getQuestions();

        if (questions.isEmpty()) {
            System.out.println("Question list is EMPTY.");
        } else {
            for (int i = 0; i < questions.size(); i++) {
                System.out.println("Q" + (i + 1) + ": " + questions.get(i));
            }
        }
    }


    public TriviaQuestion getNextQuestion() {
        if (currentIndex < myQuestions.size()) {
            return myQuestions.get(currentIndex++);
        } else {
            return null; // or reset index to loop: currentIndex = 0;
        }
    }

    public int size() {
        return myQuestions.size();
    }
}