package edu.uw.tessaev1.quizdroid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Tessa on 2/9/17.
 */

public class TopicRepository {
    private static TopicRepository instance = new TopicRepository();
    private List<Topic> topics;
    private Topic currentTopic;

    public TopicRepository() {
        topics = new ArrayList<Topic>();
    }

    public static TopicRepository getInstance() {
        return instance;
    }

    public static String[] getTopicList() {
        return new String[] {"Math", "Physics", "Marvel Super Heroes"};
    }

    public Topic getCurrentTopic() {
        return this.currentTopic;
    }

    public void setCurrentTopic(String topic) {
        if (topic.toString().toLowerCase().equals("math")) {
            createMathTopic();
        } else if (topic.toString().toLowerCase().equals("physics")) {
            createPhysicsTopic();
        } else {
            createSuperHeroTopic();
        }
    }

    public void createMathTopic() {
        this.currentTopic = new Topic("Math");
        ArrayList<Question> questionList = new ArrayList<Question>();
        questionList.add(new Question("2 + 2 = ", new ArrayList<String>(Arrays.asList("2", "5", "4", "10")),
                2));
        questionList.add(new Question("5 * 5 = ", new ArrayList<String>(Arrays.asList("25", "5", "4", "10")),
                0));
        questionList.add(new Question("7 + 6 = ", new ArrayList<String>(Arrays.asList("2", "13", "14", "12")),
                1));
        questionList.add(new Question("30 / 5 = ", new ArrayList<String>(Arrays.asList("5", "7", "14", "6")),
                3));

        this.currentTopic.setShortDescription("The fundamentals of mathematics");
        this.currentTopic.setLongDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus velit mi, " +
                "vehicula non aliquam quis, tempor ac ipsum. Fusce tortor erat, mollis a tincidunt sed, efficitur a mi.");
        this.currentTopic.setQuestions(questionList);
    }

    public void createPhysicsTopic() {
        this.currentTopic = new Topic("Physics");
        ArrayList<Question> questionList = new ArrayList<Question>();
        questionList.add(new Question("Physics is the study of",
                new ArrayList<String>(Arrays.asList("Matter and Energy", "Earth", "Stars", "Anatomy")), 0));
        questionList.add(new Question("Which of the following is a unit of Work?",
                new ArrayList<String>(Arrays.asList("Watt", "Joule", "Inches", "Newton")), 1));
        questionList.add(new Question("Light Year is a unit of...",
                new ArrayList<String>(Arrays.asList("Time", "Distance", "Light", "Intensity")), 1));
        questionList.add(new Question("Sound waves in the air are",
                new ArrayList<String>(Arrays.asList("Electromagnetic", "Polarized", "Transverse", "Longitudinal")),
                3));

        this.currentTopic.setShortDescription("The science of the natural world");
        this.currentTopic.setLongDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus velit mi, " +
                "vehicula non aliquam quis, tempor ac ipsum. Fusce tortor erat, mollis a tincidunt sed, efficitur a mi.");
        this.currentTopic.setQuestions(questionList);
    }

    public void createSuperHeroTopic() {
        this.currentTopic = new Topic("Marvel Super Heroes");
        ArrayList<Question> questionList = new ArrayList<Question>();
        questionList.add(new Question("Which of following is not a Marvel movie?",
                new ArrayList<String>(Arrays.asList("The Incredibles", "Wonder Woman", "Batman", "Suicide Squad")), 0));
        questionList.add(new Question("Which Super Hero Team Does Johnny Storm Belong To?",
                new ArrayList<String>(Arrays.asList("Avengers", "Justice League", "X-Men", "Fantastic 4")), 3));
        questionList.add(new Question("Saber tooth/Victor Creed and Wolverine are...",
                new ArrayList<String>(Arrays.asList("Brothers", "Cousins", "Friends", "Strangers")), 0));
        questionList.add(new Question("What Is Tony Stark's Super Hero Name?",
                new ArrayList<String>(Arrays.asList("Spiderman", "Iron Man", "Deadpool", "Superman")),
                1));

        this.currentTopic.setShortDescription("How well do you know your Marvel Super Heroes?");
        this.currentTopic.setLongDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus velit mi, " +
                "vehicula non aliquam quis, tempor ac ipsum. Fusce tortor erat, mollis a tincidunt sed, efficitur a mi.");
        this.currentTopic.setQuestions(questionList);
    }
}
