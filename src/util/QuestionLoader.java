package util;

import model.Question;
import java.util.*;

public class QuestionLoader {

    public List<Question> loadQuestions() {
        List<Question> questions = new ArrayList<>();

        questions.add(new Question(
            "What is the capital of France?",
            new String[]{"A. Berlin", "B. Madrid", "C. Paris", "D. Rome"},
            'C',
            15
        ));

        questions.add(new Question(
            "Which planet is known as the Red Planet?",
            new String[]{"A. Venus", "B. Mars", "C. Jupiter", "D. Saturn"},
            'B',
            15
        ));

        questions.add(new Question(
            "What is 5 + 7?",
            new String[]{"A. 10", "B. 11", "C. 12", "D. 13"},
            'C',
            10
        ));

        questions.add(new Question(
            "Who wrote 'Romeo and Juliet'?",
            new String[]{"A. Charles Dickens", "B. J.K. Rowling", "C. William Shakespeare", "D. Mark Twain"},
            'C',
            20
        ));

        return questions;
    }
}