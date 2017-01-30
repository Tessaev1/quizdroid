package edu.uw.tessaev1.quizdroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

public class QuestionReview extends AppCompatActivity {
    private Topic topic;
    private String selectedAnswer;
    private Question currentQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_review);

        Intent intent = getIntent();
        selectedAnswer = intent.getStringExtra("selectedAnswer");
        topic = (Topic) getIntent().getSerializableExtra("topic");

        currentQuestion = topic.getQuestionAtIndex(topic.getCurrentQuestion());

        displayReview();
    }

    public void displayReview() {
        TextView userAnswer = (TextView) findViewById(R.id.userAnswer);
        TextView correctAnswer = (TextView) findViewById(R.id.correctAnswer);
        ArrayList<String> answerList = currentQuestion.getAnswers();

        userAnswer.setText("Your Answer: " + selectedAnswer);
        correctAnswer.setText("Correct Answer: " +
                answerList.get(currentQuestion.getCorrectAnswerIndex()));
    }
}
