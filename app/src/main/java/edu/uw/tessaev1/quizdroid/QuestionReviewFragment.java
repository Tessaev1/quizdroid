package edu.uw.tessaev1.quizdroid;

import android.content.*;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.*;
import android.widget.*;

import java.util.ArrayList;

public class QuestionReviewFragment extends Fragment {
    private String selectedAnswer;
    private Topic topic;
    private Question currentQuestion;
    private ArrayList<String> answerList;
    private boolean lastQuestion;
    private Button nextQuestion;

    private OnFragmentInteractionListener mListener;

    public QuestionReviewFragment() {}

    public static QuestionReviewFragment newInstance() {
        QuestionReviewFragment fragment = new QuestionReviewFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.topic = QuizApp.getInstance().getTopicRepository().getCurrentTopic();
        this.selectedAnswer = QuizApp.getInstance().getTopicRepository().getUserAnswer();
        this.currentQuestion = this.topic.getQuestionAtIndex(this.topic.getCurrentQuestion());
        this.answerList = this.currentQuestion.getAnswers();
        this.lastQuestion = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_question_review, container, false);

        this.nextQuestion = (Button) getActivity().findViewById(R.id.fragmentButton);
        this.nextQuestion.setText("Next");

        displayReview(view);

        this.nextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    if (!lastQuestion) {
                        topic.nextQuestion();
                        mListener.onBeginQuizClick();
                    } else {
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                }
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onBeginQuizClick();
    }

    public void displayReview(View v) {
        TextView userAnswerField = (TextView) v.findViewById(R.id.userAnswer);
        TextView correctAnswerField = (TextView) v.findViewById(R.id.correctAnswer);
        TextView scoreField = (TextView) v.findViewById(R.id.score);
        TextView incorrectField = (TextView) v.findViewById(R.id.correctOrIncorrect);
        String correctAnswer = this.answerList.get(this.currentQuestion.getCorrectAnswerIndex());

        userAnswerField.setText("Your Answer: " + this.selectedAnswer);
        correctAnswerField.setText("Correct Answer: " + correctAnswer);

        if (this.selectedAnswer.equals(correctAnswer)) {
            incorrectField.setText("Correct!");
            this.topic.incrementTotalCorrect();
        } else {
            incorrectField.setText("Oops, not quite!");
        }

        scoreField.setText("You have " + this.topic.getTotalCorrect() +
                " out of " + (this.topic.getCurrentQuestion() + 1) + " correct.");

        if (this.topic.getCurrentQuestion() == this.topic.size() - 1) {
            this.lastQuestion = true;
            this.nextQuestion.setText("Finish");
        }
    }
}
