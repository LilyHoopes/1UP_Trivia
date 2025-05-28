package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;

public class QuestionFactory {
    private final String dbPath;
    private final ArrayList<TriviaQuestion> questions = new ArrayList<>();

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
                    questions.add(question);
                }
            }

        } catch (SQLException e) {
            System.err.printf("Failed to load trivia questions: %s%n", e.getMessage());
            // Consider logging or rethrowing as a runtime exception depending on your use case
        }
        Collections.shuffle(questions);
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

    public ArrayList<TriviaQuestion> getAllQuestions() {
        return questions;
    }

    public TriviaQuestion getNextQuestion() {
        if (currentIndex < questions.size()) {
            return questions.get(currentIndex++);
        } else {
            return null; // or reset index to loop: currentIndex = 0;
        }
    }

    public int size() {
        return questions.size();
    }
}