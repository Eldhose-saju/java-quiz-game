// util/QuestionLoader.java
package util;

import data.DatabaseManager;
import model.Question;
import model.Difficulty;
import java.util.List;

public class QuestionLoader {
    private DatabaseManager db = new DatabaseManager();

    // Load questions by difficulty
    public List<Question> loadQuestions(Difficulty difficulty) {
        return db.loadQuestions(difficulty);
    }

    // Optional: Add a new question via code
    public void addQuestion(Question q) {
        db.addQuestion(q);
    }
}