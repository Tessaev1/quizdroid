package edu.uw.tessaev1.quizdroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;

public class QuestionActivity extends AppCompatActivity {
    private Topic topic;
    public String selectedAnswer;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        topic= (Topic) getIntent().getSerializableExtra("topic");

        displayQuestion(topic.getCurrentQuestion());
        getSelectedRadioButton();

        submit = (Button) findViewById(R.id.submitButton);
        submit.setEnabled(false);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), QuestionReview.class);
                intent.putExtra("topic", topic);
                intent.putExtra("selectedAnswer", selectedAnswer);
                startActivity(intent);
            }
        });
    }

    public void getSelectedRadioButton() {
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton checkedRadioButton = (RadioButton)group.findViewById(checkedId);
                selectedAnswer = checkedRadioButton.getText().toString();
                submit.setEnabled(true);
            }
        });
    }

    public void displayQuestion(int index) {
        TextView question = (TextView) findViewById(R.id.question);
        question.setText(topic.getQuestionAtIndex(topic.getCurrentQuestion()).getQuestion());

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

    @Override
    public void onBackPressed() {
        if (topic.getCurrentQuestion() == 0) {
            Intent intent = new Intent(QuestionActivity.this, MainActivity.class);
            startActivity(intent);
        } else {
            finish();
        }
    }
}
