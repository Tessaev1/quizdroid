package edu.uw.tessaev1.quizdroid;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Tessa on 1/29/17.
 */

public class Topic implements Serializable {
    private String topic;
    private String descr;
    private ArrayList<Question> questions;
    private int currentQuestion = 0;
    private int totalCorrect = 0;

    public Topic(String name) {
        this(name, "", new ArrayList<Question>());
    }

    public Topic(String name, String descr, ArrayList<Question> questions) {
        this.topic = name;
        this.descr = descr;
        this.questions = questions;
    }

    public String getTopicName() {
        return this.topic;
    }

    public String getDescription() {
        return this.descr;
    }

    public Question getQuestionAtIndex(int index) {
        return this.questions.get(index);
    }

    public int getCurrentQuestion() {
        return this.currentQuestion;
    }

    public int getTotalCorrect() {
        return this.totalCorrect;
    }

    public int size() {
        return this.questions.size();
    }

    public void incrementTotalCorrect() {
        this.totalCorrect++;
    }

    public void nextQuestion() {
        this.currentQuestion++;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setDescr(String description) {
        this.descr = description;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }
}
