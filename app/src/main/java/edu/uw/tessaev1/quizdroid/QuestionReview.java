package edu.uw.tessaev1.quizdroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class QuestionReview extends AppCompatActivity {
    private Topic topic;
    private String selectedAnswer;
    private Question currentQuestion;
    private ArrayList<String> answerList;
    private boolean lastQuestion;
    private Button nextQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_review);

        Intent intent = getIntent();
        selectedAnswer = intent.getStringExtra("selectedAnswer");
        topic = (Topic) getIntent().getSerializableExtra("topic");

        currentQuestion = topic.getQuestionAtIndex(topic.getCurrentQuestion());
        answerList = currentQuestion.getAnswers();
        lastQuestion = false;
        nextQuestion = (Button) findViewById(R.id.nextQuestionButton);

        displayReview();

        nextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!lastQuestion) {
                    Intent intent = new Intent(v.getContext(), QuestionActivity.class);
                    topic.nextQuestion();
                    intent.putExtra("topic", topic);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(v.getContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    public void displayReview() {
        TextView userAnswerField = (TextView) findViewById(R.id.userAnswer);
        TextView correctAnswerField = (TextView) findViewById(R.id.correctAnswer);
        TextView scoreField = (TextView) findViewById(R.id.score);
        TextView incorrectField = (TextView) findViewById(R.id.correctOrIncorrect);
        String correctAnswer = answerList.get(currentQuestion.getCorrectAnswerIndex());

        userAnswerField.setText("Your Answer: " + selectedAnswer);
        correctAnswerField.setText("Correct Answer: " + correctAnswer);

        if (selectedAnswer.equals(correctAnswer)) {
            incorrectField.setText("Correct!");
            topic.incrementTotalCorrect();
        } else {
           incorrectField.setText("Oops, not quite!");
        }

        scoreField.setText("You have " + topic.getTotalCorrect() +
                " out of " + (topic.getCurrentQuestion() + 1) + " correct.");

        if (topic.getCurrentQuestion() == topic.size() - 1) {
            lastQuestion = true;
            nextQuestion.setText("Finish");
        }
    }
}
