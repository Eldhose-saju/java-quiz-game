package core;

import util.QuestionLoader;
import model.Question;
import data.Leaderboard;
import java.util.List;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("🎮 Welcome to the Java Quiz Game! 🎮");
        System.out.print("Enter your name: ");
        String username = scanner.nextLine();

        // Load questions
        QuestionLoader loader = new QuestionLoader();
        List<Question> questions = loader.loadQuestions();

        // Start quiz
        QuizEngine quiz = new QuizEngine(questions);

        while (quiz.isQuizActive()) {
            Question q = quiz.getCurrentQuestion();
            System.out.println("\n❓ Question " + (quiz.getCurrentQuestion() != null ? ":" : ""));
            System.out.println(q.getQuestionText());

            for (String option : q.getOptions()) {
                System.out.println("  " + option);
            }

            System.out.print("\n👉 Enter your answer (A/B/C/D): ");
            String answer = scanner.nextLine().trim().toUpperCase();

            if (answer.length() > 0) {
                quiz.submitAnswer(answer.charAt(0));
            } else {
                System.out.println("No answer provided.");
            }
        }

        quiz.endQuiz();

        
        Leaderboard leaderboard = new Leaderboard();
        leaderboard.addScore(username, quiz.getScore(), questions.size());
        leaderboard.displayTop();

        scanner.close();
    }
}