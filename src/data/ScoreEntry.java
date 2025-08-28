package data;

public class ScoreEntry {
    private String name;
    private int score;
    private int streak;

    public ScoreEntry(String name, int score, int streak) {
        this.name = name;
        this.score = score;
        this.streak = streak;
    }

    // Getters
    public String getName() { return name; }
    public int getScore() { return score; }
    public int getStreak() { return streak; }
}