package model;

public class Question {
    private String questionText;
    private String[] options;
    private char correctAnswer;
    private int timeLimit; // seconds

    public Question(String questionText, String[] options, char correctAnswer, int timeLimit) {
        this.questionText = questionText;
        this.options = options;
        this.correctAnswer = correctAnswer;
        this.timeLimit = timeLimit;
    }

    // Getters
    public String getQuestionText() {
        return questionText;
    }

    public String[] getOptions() {
        return options;
    }

    public char getCorrectAnswer() {
        return correctAnswer;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    @Override
    public String toString() {
        return questionText + " (Answer: " + correctAnswer + ")";
    }
}