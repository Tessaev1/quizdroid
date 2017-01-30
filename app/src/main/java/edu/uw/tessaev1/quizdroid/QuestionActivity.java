package edu.uw.tessaev1.quizdroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

public class QuestionActivity extends AppCompatActivity {
    private Topic topic;
    private int currentQuestion = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        topic= (Topic) getIntent().getSerializableExtra("topic");

        displayQuestion(currentQuestion);

        Button submit = (Button) findViewById(R.id.submitButton);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), QuestionReview.class);
                startActivity(intent);
            }
        });
    }

    public void displayQuestion(int index) {
        TextView question = (TextView) findViewById(R.id.question);
        question.setText(topic.getQuestionAtIndex(currentQuestion).getQuestion());

        ArrayList<String> answers = topic.getQuestionAtIndex(index).getAnswers();
        Button q1 = (RadioButton) findViewById(R.id.radioButton1);
        q1.setText(answers.get(0));

        Button q2 = (RadioButton) findViewById(R.id.radioButton2);
        q2.setText(answers.get(1));

        Button q3 = (RadioButton) findViewById(R.id.radioButton3);
        q3.setText(answers.get(2));

        Button q4 = (RadioButton) findViewById(R.id.radioButton4);
        q4.setText(answers.get(3));
    }
}
