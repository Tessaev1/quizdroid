package edu.uw.tessaev1.quizdroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class TopicOverviewActivity extends AppCompatActivity implements Serializable {
    public Topic topic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_overview);

        Intent intent = getIntent();
        String quizName = intent.getStringExtra(MainActivity.EXTRA_QUIZ_NAME);
        if (quizName.toString().toLowerCase().equals("math")) {
            createMathTopic();
        } else if (quizName.toString().toLowerCase().equals("physics")) {
            createPhysicsTopic();
        } else {
            createSuperHeroTopic();
        }

        displayTopicOverview();

        Button begin = (Button) findViewById(R.id.beginQuizButton);
        begin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), QuestionActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("topic", (Serializable) topic);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    public void displayTopicOverview() {
        TextView topicField = (TextView) findViewById(R.id.topic);
        TextView overview = (TextView) findViewById(R.id.topicOverview);
        TextView total = (TextView) findViewById(R.id.totalQuestions);

        topicField.setText(this.topic.getTopicName());
        overview.setText(this.topic.getDescription());
        total.setText("This quiz has " + this.topic.size() + " total questions");
    }

    public void createMathTopic() {
        topic = new Topic("Math");
        ArrayList<Question> questionList = new ArrayList<Question>();
        questionList.add(new Question("2 + 2 = ", new ArrayList<String>(Arrays.asList("2", "5", "4", "10")),
                2));
        questionList.add(new Question("5 * 5 = ", new ArrayList<String>(Arrays.asList("25", "5", "4", "10")),
                0));
        questionList.add(new Question("7 + 6 = ", new ArrayList<String>(Arrays.asList("2", "13", "14", "12")),
                1));
        questionList.add(new Question("30 / 5 = ", new ArrayList<String>(Arrays.asList("5", "7", "14", "6")),
                3));

        topic.setDescr("The fundamentals of mathematics");
        topic.setQuestions(questionList);
    }

    public void createPhysicsTopic() {
        topic = new Topic("Physics");
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

        topic.setDescr("The science of the natural world");
        topic.setQuestions(questionList);
    }

    public void createSuperHeroTopic() {
        topic = new Topic("Marvel Super Heroes");
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

        topic.setDescr("How well do you know your Marvel Super Heroes?");
        topic.setQuestions(questionList);
    }
}
