package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;



public class QuestionFactory {
    private final String dbPath;
    private List<TriviaQuestion> questions;

    // Constructor, takes database path
    public QuestionFactory(String dbPath) {
        this.dbPath = dbPath;
        this.questions = new ArrayList<>();
        loadQuestions();
    }

    // Load questions from database
    private void loadQuestions() {
        String sql = "SELECT question, option_a, option_b, option_c, option_d, correct_answer FROM trivia_questions";
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String question = rs.getString("question");
                String optionA = rs.getString("option_a");
                String optionB = rs.getString("option_b");
                String optionC = rs.getString("option_c");
                String optionD = rs.getString("option_d");
                String correctAnswer = rs.getString("correct_answer");

                String[] options = { optionA, optionB, optionC, optionD };
                TriviaQuestion triviaQuestion = new TriviaQuestion(question, options, correctAnswer);
                questions.add(triviaQuestion);
            }
        } catch (SQLException e) {
            System.err.println("Error loading questions from database: " + e.getMessage());
        }
    }

    // Get the next question (for simplicity, just return the first question or one from the list)
    public TriviaQuestion getNextQuestion() {
        if (!questions.isEmpty()) {
            return questions.get(0); // Or use an index to go through the questions
        }
        return null;
    }

    // Method to get all questions, useful if you need them for something else
    public List<TriviaQuestion> getAllQuestions() {
        return questions;
    }

    // Get the number of questions
    public int size() {
        return questions.size();
    }
}
