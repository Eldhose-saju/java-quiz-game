// core/QuizEngine.java
package core;

import model.Question;
import model.Difficulty;
import java.util.*;
import java.util.Timer;
import java.util.TimerTask; // ✅ Required import

public class QuizEngine {
    private List<Question> questions;
    private int currentIdx;
    private int score;
    private boolean isActive;
    private Scanner scanner;
    private int lives = 3;
    private Map<String, Boolean> lifelines; // 50:50, Skip, Hint
    private Timer questionTimer;
    private boolean answered = false;

    public QuizEngine(List<Question> questions) {
        this.questions = questions;
        this.currentIdx = 0;
        this.score = 0;
        this.isActive = true;
        this.scanner = new Scanner(System.in);
        this.lifelines = new HashMap<>();
        lifelines.put("fifty", true);
        lifelines.put("skip", true);
        lifelines.put("hint", true);
    }

    public boolean isQuizActive() {
        return isActive && currentIdx < questions.size() && lives > 0;
    }

    public Question getCurrentQuestion() {
        if (currentIdx >= questions.size()) return null;
        return questions.get(currentIdx);
    }

    public int getLives() {
        return lives;
    }

    public boolean useLifeline(String type) {
        if (!lifelines.get(type)) {
            System.out.println("❌ You already used this lifeline!");
            return false;
        }

        lifelines.put(type, false);
        Question q = getCurrentQuestion();

        switch (type.toLowerCase()) {
            case "fifty":
                useFiftyFifty(q);
                break;

            case "skip":
                System.out.println("⏭️  Skipped question.");
                currentIdx++;
                break;

            case "hint":
                System.out.println("🔍 Hint: The correct answer is '" + q.getCorrectAnswer() + "'.");
                break;

            default:
                System.out.println("Unknown lifeline.");
                return false;
        }
        return true;
    }

    private void useFiftyFifty(Question q) {
        List<String> validOptions = new ArrayList<>();
        char correct = q.getCorrectAnswer();
        int correctIndex = correct - 'A';

        validOptions.add(q.getOptions()[correctIndex]);

        Random rand = new Random();
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            if (i != correctIndex) {
                indices.add(i);
            }
        }
        Collections.shuffle(indices);
        int wrongIndex = indices.get(0);

        validOptions.add(q.getOptions()[wrongIndex]);

        Collections.shuffle(validOptions);

        System.out.println("💡 50:50: Two options remain:");
        for (String opt : validOptions) {
            System.out.println("  " + opt);
        }
    }

    public void startTimer(int seconds) {
        answered = false;
        questionTimer = new Timer();
        questionTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!answered) {
                    System.out.println("\n⏰ Time's up!");
                    lives--;
                    System.out.println("💔 Lives left: " + lives);
                    currentIdx++;
                    answered = true;
                }
            }
        }, seconds * 1000);
    }

    public void cancelTimer() {
        if (questionTimer != null) {
            questionTimer.cancel();
            questionTimer = null;
        }
    }

    public boolean submitAnswer(char answer) {
        if (answered) return false;
        answered = true;
        cancelTimer();

        Question q = getCurrentQuestion();
        if (q == null) return false;

        boolean correct = Character.toUpperCase(answer) == q.getCorrectAnswer();

        if (correct) {
            score += 10;
            System.out.println("✅ Correct!");
        } else {
            lives--;
            System.out.println("❌ Wrong! Correct answer was: " + q.getCorrectAnswer());
            if (lives > 0) {
                System.out.println("💔 Lives left: " + lives);
            }
        }

        currentIdx++;
        return correct;
    }

    public void endQuiz() {
        isActive = false;
        System.out.println("\n🎉 Quiz Finished!");
        if (lives == 0) {
            System.out.println("💀 You ran out of lives!");
        }
        System.out.println("Final Score: " + score + "/" + (questions.size() * 10));

        System.out.print("🛡️  Unused Lifelines: ");
        boolean anyLeft = false;
        if (lifelines.get("fifty")) { System.out.print("50:50 "); anyLeft = true; }
        if (lifelines.get("skip")) { System.out.print("Skip "); anyLeft = true; }
        if (lifelines.get("hint")) { System.out.print("Hint "); anyLeft = true; }
        if (!anyLeft) System.out.print("None");
        System.out.println();
    }

    public int getScore() {
        return score;
    }

    public boolean canUseFifty() {
        return lifelines.get("fifty");
    }

    public boolean canUseSkip() {
        return lifelines.get("skip");
    }

    public boolean canUseHint() {
        return lifelines.get("hint");
    }
}