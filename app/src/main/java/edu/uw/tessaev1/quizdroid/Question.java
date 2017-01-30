package edu.uw.tessaev1.quizdroid;

import java.io.Serializable;
import java.util.ArrayList;

public class Question implements Serializable {
    private String question;
    private ArrayList<String> answers;
    private int correct;

    public Question() {
        this("", new ArrayList<String>(), 0);
    }

    public Question(String question, ArrayList<String> answers, int correct) {
        if (answers.size() != 4) {
            throw new IllegalArgumentException("Must have exactly 4 answers");
        }
        this.question = question;
        this.answers = answers;
        this.correct = correct;
    }

    public String getQuestion() {
        return this.question;
    }

    public ArrayList<String> getAnswers() {
        return this.answers;
    }

    public int getCorrectAnswerIndex() {
        return this.correct;
   }
}
