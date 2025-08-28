package data;

import java.util.*;

public class Leaderboard {
    private List<ScoreEntry> scores;

    public Leaderboard() {
        scores = new ArrayList<>();
        // Add dummy entries
        scores.add(new ScoreEntry("Alice", 40, 4));
        scores.add(new ScoreEntry("Bob", 30, 3));
    }

    public void addScore(String name, int score, int questions) {
        int streak = score / 10; // Simplified
        scores.add(new ScoreEntry(name, score, streak));
        // Sort by score (descending)
        scores.sort((a, b) -> b.getScore() - a.getScore());
    }

    public void displayTop() {
        System.out.println("\n🏆 LEADERBOARD 🏆");
        System.out.println("------------------------");
        for (int i = 0; i < Math.min(5, scores.size()); i++) {
            ScoreEntry se = scores.get(i);
            System.out.println((i+1) + ". " + se.getName() + " - " + se.getScore() + " pts (Streak: " + se.getStreak() + ")");
        }
    }
}