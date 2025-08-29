// data/DatabaseManager.java
package data;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Question;
import model.Difficulty;

public class DatabaseManager {
    private Connection conn;

    public DatabaseManager() {
        try {
            // Load SQLite JDBC driver
            Class.forName("org.sqlite.JDBC");
            // Connect to database (creates quizgame.db in project folder)
            conn = DriverManager.getConnection("jdbc:sqlite:quizgame.db");
            createTables();
            insertSampleQuestions();
        } catch (ClassNotFoundException e) {
            System.err.println("❌ SQLite driver not found! Add sqlite-jdbc.jar to build path.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("❌ Database connection failed!");
            e.printStackTrace();
        }
    }

    // Create the questions table if it doesn't exist
    private void createTables() throws SQLException {
        String sql = """
            CREATE TABLE IF NOT EXISTS questions (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                question TEXT NOT NULL,
                option_a TEXT NOT NULL,
                option_b TEXT NOT NULL,
                option_c TEXT NOT NULL,
                option_d TEXT NOT NULL,
                correct_answer CHAR(1) NOT NULL,
                difficulty TEXT NOT NULL,
                time_limit INTEGER NOT NULL
            );
            """;
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    // Add a question to the database (reads difficulty from Question object)
    public void addQuestion(Question q) {
        String sql = """
            INSERT INTO questions (question, option_a, option_b, option_c, option_d, correct_answer, difficulty, time_limit)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            """;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, q.getQuestionText());
            pstmt.setString(2, parseOption(q.getOptions()[0])); // "A. Option" → "Option"
            pstmt.setString(3, parseOption(q.getOptions()[1]));
            pstmt.setString(4, parseOption(q.getOptions()[2]));
            pstmt.setString(5, parseOption(q.getOptions()[3]));
            pstmt.setString(6, String.valueOf(q.getCorrectAnswer()));
            pstmt.setString(7, q.getDifficulty().name());
            pstmt.setInt(8, q.getTimeLimit());

            pstmt.executeUpdate();
            System.out.println("✅ Added: " + q.getQuestionText());
        } catch (SQLException e) {
            System.err.println("❌ Failed to add question!");
            e.printStackTrace();
        }
    }

    // Helper: Extract text after "A. ", "B. ", etc.
    private String parseOption(String option) {
        if (option == null || option.length() < 3) return "Unknown";
        if (option.charAt(1) == '.') {
            return option.substring(3).trim();
        }
        return option.trim();
    }

    // Load questions from DB by difficulty
    public List<Question> loadQuestions(Difficulty difficulty) {
        List<Question> list = new ArrayList<>();
        String sql = "SELECT * FROM questions WHERE difficulty = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, difficulty.name());

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String qText = rs.getString("question");
                    String a = "A. " + rs.getString("option_a");
                    String b = "B. " + rs.getString("option_b");
                    String c = "C. " + rs.getString("option_c");
                    String d = "D. " + rs.getString("option_d");
                    String[] options = {a, b, c, d};
                    char correct = rs.getString("correct_answer").charAt(0);
                    int timeLimit = rs.getInt("time_limit");

                    Question q = new Question(qText, options, correct, timeLimit, difficulty);
                    list.add(q);
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Error loading questions!");
            e.printStackTrace();
        }

        return list;
    }

    // Add sample questions (only once)
    private void insertSampleQuestions() {
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM questions")) {

            if (rs.next() && rs.getInt(1) > 0) {
                return; // Already has data
            }
        } catch (SQLException e) {
            // Ignore — table may be new
        }

        // Add 6 sample questions
        addQuestion(new Question(
            "What is the capital of France?",
            new String[]{"A. Berlin", "B. Madrid", "C. Paris", "D. Rome"},
            'C', 30, Difficulty.EASY));

        addQuestion(new Question(
            "Which planet is known as the Red Planet?",
            new String[]{"A. Venus", "B. Mars", "C. Jupiter", "D. Saturn"},
            'B', 30, Difficulty.EASY));

        addQuestion(new Question(
            "What is 5 + 7?",
            new String[]{"A. 10", "B. 11", "C. 12", "D. 13"},
            'C', 25, Difficulty.EASY));

        addQuestion(new Question(
            "Who wrote 'Romeo and Juliet'?",
            new String[]{"A. Charles Dickens", "B. J.K. Rowling", "C. William Shakespeare", "D. Mark Twain"},
            'C', 30, Difficulty.NORMAL));

        addQuestion(new Question(
            "What is the chemical symbol for gold?",
            new String[]{"A. Go", "B. Gd", "C. Au", "D. Ag"},
            'C', 25, Difficulty.NORMAL));

        addQuestion(new Question(
            "Which data structure uses FIFO?",
            new String[]{"A. Stack", "B. Queue", "C. Tree", "D. Array"},
            'B', 20, Difficulty.HARD));

        System.out.println("📊 Sample questions added to database.");
    }
}