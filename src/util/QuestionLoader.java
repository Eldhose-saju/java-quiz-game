// util/QuestionLoader.java
package util;

import model.Question;
import model.Difficulty;
import java.util.*;

public class QuestionLoader {
    public List<Question> loadQuestions(Difficulty difficulty) {
        List<Question> allQuestions = new ArrayList<>();

        // All questions
        allQuestions.add(new Question(
            "What is the capital of France?",
            new String[]{"A. Berlin", "B. Madrid", "C. Paris", "D. Rome"},
            'C', 30, Difficulty.EASY
        ));

        allQuestions.add(new Question(
            "Which planet is known as the Red Planet?",
            new String[]{"A. Venus", "B. Mars", "C. Jupiter", "D. Saturn"},
            'B', 30, Difficulty.EASY
        ));

        allQuestions.add(new Question(
            "What is 5 + 7?",
            new String[]{"A. 10", "B. 11", "C. 12", "D. 13"},
            'C', 25, Difficulty.EASY
        ));

        allQuestions.add(new Question(
            "Who wrote 'Romeo and Juliet'?",
            new String[]{"A. Charles Dickens", "B. J.K. Rowling", "C. William Shakespeare", "D. Mark Twain"},
            'C', 30, Difficulty.NORMAL
        ));

        allQuestions.add(new Question(
            "What is the chemical symbol for gold?",
            new String[]{"A. Go", "B. Gd", "C. Au", "D. Ag"},
            'C', 25, Difficulty.NORMAL
        ));

        allQuestions.add(new Question(
            "Which data structure uses FIFO?",
            new String[]{"A. Stack", "B. Queue", "C. Tree", "D. Array"},
            'B', 20, Difficulty.HARD
        ));

        allQuestions.add(new Question(
            "In Java, which keyword is used to inherit a class?",
            new String[]{"A. implements", "B. extends", "C. inherits", "D. parent"},
            'B', 20, Difficulty.HARD
        ));

        // Filter by difficulty
        List<Question> filtered = new ArrayList<>();
        for (Question q : allQuestions) {
            if (q.getDifficulty() == difficulty) {
                filtered.add(q);
            }
        }

        // Shuffle and return 5 random questions
        Collections.shuffle(filtered);
        return filtered.subList(0, Math.min(5, filtered.size()));
    }
}