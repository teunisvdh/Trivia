package com.example.teunis.trivia;

import java.util.ArrayList;
import java.util.List;

public class Question {

    public String question, correctAnswer;
    public List answers;

    public Question(String question, String correctAnswer, List answers) {
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.answers = answers;
    }

    public String getQuestion() {
        return question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public List getAnswers() {
        return answers;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public void setAnswers(List answers) {
        this.answers = answers;
    }
}
