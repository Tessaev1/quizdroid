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

    private static final String ARG_PARAM1 = "selected answer";
    private static final String ARG_PARAM2 = "topic";

    private OnFragmentInteractionListener mListener;

    public QuestionReviewFragment() {}

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param selectedAnswer Parameter 1.
     * @param topic Parameter 2.
     * @return A new instance of fragment QuestionReviewFragment.
     */
    public static QuestionReviewFragment newInstance(String selectedAnswer, Topic topic) {
        QuestionReviewFragment fragment = new QuestionReviewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, selectedAnswer);
        args.putSerializable(ARG_PARAM2, topic);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            selectedAnswer = getArguments().getString(ARG_PARAM1);
            topic = (Topic) getArguments().getSerializable(ARG_PARAM2);
        }

        currentQuestion = topic.getQuestionAtIndex(topic.getCurrentQuestion());
        answerList = currentQuestion.getAnswers();
        lastQuestion = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_question_review, container, false);

        displayReview(view);

        nextQuestion = (Button) getActivity().findViewById(R.id.fragmentButton);
        nextQuestion.setText("Next");
        nextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    if (!lastQuestion) {
                        topic.nextQuestion();
                        mListener.onBeginQuizClick(topic);
                    }
                } else {
                    Log.i("QuestionReview", "listener is not null");
                    getActivity().finish();
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
        void onBeginQuizClick(Topic topic);
    }

    public void displayReview(View v) {
        TextView userAnswerField = (TextView) v.findViewById(R.id.userAnswer);
        TextView correctAnswerField = (TextView) v.findViewById(R.id.correctAnswer);
        TextView scoreField = (TextView) v.findViewById(R.id.score);
        TextView incorrectField = (TextView) v.findViewById(R.id.correctOrIncorrect);
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
