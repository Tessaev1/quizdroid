package edu.uw.tessaev1.quizdroid;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class QuizActivity extends AppCompatActivity
    implements TopicOverviewFragment.OnFragmentInteractionListener,
        QuestionFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // get intent from Main and instantiate TopicOverviewFragment
        // set onClick listener on Overview button to instantiate QuestionFragment
        // do the same to instantiate QuestionReviewFragment
        // back to QuestionFragment

        Intent intent = getIntent();
        String quizName = intent.getStringExtra(MainActivity.EXTRA_QUIZ_NAME);

        Fragment topicOverview = TopicOverviewFragment.newInstance(quizName);
        startFragmentTransaction(topicOverview);
    }

    @Override
    public void onBeginQuizClick(Topic topic) {
        Fragment question = QuestionFragment.newInstance(topic);
        startFragmentTransaction(question);
    }

    @Override
    public void showQuestionSummary(String selectedAnswer, Topic topic) {
        Fragment questionReview = QuestionReviewFragment.newInstance(null, null);
        startFragmentTransaction(questionReview);
    }

    public void startFragmentTransaction(Fragment name) {
        FragmentTransaction tx = getFragmentManager().beginTransaction();
        tx.replace(R.id.fragmentPlaceholder, name);
        tx.commit();
    }
}
