// model/Question.java
package model;

public class Question {
    private String questionText;
    private String[] options;
    private char correctAnswer;
    private int timeLimit; // seconds
    private Difficulty difficulty;

    public Question(String questionText, String[] options, char correctAnswer, int timeLimit, Difficulty difficulty) {
        this.questionText = questionText;
        this.options = options;
        this.correctAnswer = correctAnswer;
        this.timeLimit = timeLimit;
        this.difficulty = difficulty;
    }

    // Getters
    public String getQuestionText() { return questionText; }
    public String[] getOptions() { return options; }
    public char getCorrectAnswer() { return correctAnswer; }
    public int getTimeLimit() { return timeLimit; }
    public Difficulty getDifficulty() { return difficulty; }

    @Override
    public String toString() {
        return questionText + " [" + difficulty + ", " + timeLimit + "s]";
    }
}