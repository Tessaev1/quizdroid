package edu.uw.tessaev1.quizdroid;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class QuizActivity extends AppCompatActivity
    implements TopicOverviewFragment.OnFragmentInteractionListener,
        QuestionFragment.OnFragmentInteractionListener,
        QuestionReviewFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        Fragment topicOverview = TopicOverviewFragment.newInstance();
        startFragmentTransaction(topicOverview);
    }

    @Override
    public void onBeginQuizClick() {
        Fragment question = QuestionFragment.newInstance();
        startFragmentTransaction(question);
    }

    @Override
    public void showQuestionSummary() {
        Fragment questionReview = QuestionReviewFragment.newInstance();
        startFragmentTransaction(questionReview);
    }

    public void startFragmentTransaction(Fragment name) {
        FragmentTransaction tx = getFragmentManager().beginTransaction();
        tx.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right);
        tx.replace(R.id.fragmentPlaceholder, name);
        tx.commit();
    }
}
