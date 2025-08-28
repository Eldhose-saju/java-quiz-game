package core;

import model.Question;
import java.util.*;

public class QuizEngine {
    private List<Question> questions;
    private int currentIdx;
    private int score;
    private boolean isActive;
    private Scanner scanner; // for console input in test

    public QuizEngine(List<Question> questions) {
        this.questions = questions;
        this.currentIdx = 0;
        this.score = 0;
        this.isActive = true;
        this.scanner = new Scanner(System.in);
    }

    public boolean isQuizActive() {
        return isActive && currentIdx < questions.size();
    }

    public Question getCurrentQuestion() {
        if (currentIdx < questions.size()) {
            return questions.get(currentIdx);
        }
        return null;
    }

    public boolean submitAnswer(char answer) {
        if (currentIdx >= questions.size()) return false;

        Question q = questions.get(currentIdx);
        boolean correct = Character.toUpperCase(answer) == q.getCorrectAnswer();
        if (correct) {
            score += 10;
            System.out.println("✅ Correct!");
        } else {
            System.out.println("❌ Wrong! Correct answer was: " + q.getCorrectAnswer());
        }
        currentIdx++;
        return correct;
    }

    public void endQuiz() {
        isActive = false;
        System.out.println("\n🎉 Quiz Finished!");
        System.out.println("Final Score: " + score + "/" + (questions.size() * 10));
    }

    public int getScore() {
        return score;
    }
}