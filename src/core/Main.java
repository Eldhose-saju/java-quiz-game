// Main.java
package core;

import util.QuestionLoader;
import model.Question;
import model.Difficulty;
import data.Leaderboard;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("🎮 Welcome to the Java Quiz Game! 🎮");

        // Difficulty choice
        System.out.println("Choose difficulty:");
        System.out.println("1. Easy (30s per question)");
        System.out.println("2. Normal (25s)");
        System.out.println("3. Hard (20s)");
        System.out.print("Enter 1, 2, or 3: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        Difficulty difficulty;
        switch (choice) {
            case 1:
                difficulty = Difficulty.EASY;
                break;
            case 2:
                difficulty = Difficulty.NORMAL;
                break;
            case 3:
                difficulty = Difficulty.HARD;
                break;
            default:
                System.out.println("Invalid choice. Defaulting to Easy.");
                difficulty = Difficulty.EASY;
                break;
        }

        System.out.print("Enter your name: ");
        String username = scanner.nextLine();

        QuestionLoader loader = new QuestionLoader();
        List<Question> questions = loader.loadQuestions(difficulty);

        QuizEngine quiz = new QuizEngine(questions);

        System.out.println("\n🔥 Quiz Started! You have 3 lives.");
        System.out.println("Available Lifelines: 50:50, Skip, Hint (each once)");

        while (quiz.isQuizActive()) {
            Question q = quiz.getCurrentQuestion();
            System.out.println("\n❤️  Lives: " + quiz.getLives());
            System.out.println("❓ Question:");
            System.out.println(q.getQuestionText());

            for (String option : q.getOptions()) {
                System.out.println("  " + option);
            }

            // Show available lifelines using public getters
            System.out.println("\n💡 Lifelines: ");
            if (quiz.canUseFifty()) System.out.print("F - 50:50  ");
            if (quiz.canUseSkip()) System.out.print("S - Skip  ");
            if (quiz.canUseHint()) System.out.print("H - Hint  ");
            if (!quiz.canUseFifty() && !quiz.canUseSkip() && !quiz.canUseHint())
                System.out.print("None left");
            System.out.println("\n");

            // Start timer for this question
            quiz.startTimer(q.getTimeLimit());
            System.out.printf("⏳ You have %d seconds to answer...\n", q.getTimeLimit());

            System.out.print("👉 Enter answer (A/B/C/D), or F/S/H to use lifeline: ");
            String input = scanner.nextLine().trim().toUpperCase();

            // Handle lifeline usage with safe getter checks
            if (input.equals("F") && quiz.canUseFifty()) {
                quiz.useLifeline("fifty");
                continue; // skip to next question
            } else if (input.equals("S") && quiz.canUseSkip()) {
                quiz.useLifeline("skip");
                continue;
            } else if (input.equals("H") && quiz.canUseHint()) {
                quiz.useLifeline("hint");
                continue;
            }

            // Handle regular answer
            if (input.length() > 0 && "ABCD".contains(input)) {
                quiz.submitAnswer(input.charAt(0));
            } else {
                System.out.println("❌ Invalid input. No answer recorded.");
                quiz.cancelTimer();
                quiz.submitAnswer('X'); // counts as wrong
            }
        }

        // Quiz finished
        quiz.endQuiz();

        // Update and display leaderboard
        Leaderboard leaderboard = new Leaderboard();
        leaderboard.addScore(username, quiz.getScore(), questions.size());
        leaderboard.displayTop();

        scanner.close();
    }
}