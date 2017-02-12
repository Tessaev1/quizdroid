package edu.uw.tessaev1.quizdroid;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.*;
import android.widget.*;

import java.util.ArrayList;

public class QuestionFragment extends Fragment {
    private Topic topic;
    public Button submit;

    private OnFragmentInteractionListener mListener;

    public QuestionFragment() {}

    public static QuestionFragment newInstance() {
        QuestionFragment fragment = new QuestionFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.topic = QuizApp.getInstance().getTopicRepository().getCurrentTopic();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question, container, false);

        displayQuestion(view, this.topic.getCurrentQuestion());
        getSelectedRadioButton(view);

        submit = (Button) getActivity().findViewById(R.id.fragmentButton);
        submit.setText("Submit");
        submit.setEnabled(false);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.showQuestionSummary();
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
        void showQuestionSummary();
    }

    public void getSelectedRadioButton(View v) {
        RadioGroup radioGroup = (RadioGroup) v.findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton checkedRadioButton = (RadioButton)group.findViewById(checkedId);
                QuizApp.getInstance().getTopicRepository().setUserAnswer(checkedRadioButton.getText().toString());
                submit.setEnabled(true);
            }
        });
    }

    public void displayQuestion(View v, int index) {
        TextView question = (TextView) v.findViewById(R.id.question);
        question.setText(this.topic.getQuestionAtIndex(this.topic.getCurrentQuestion()).getQuestion());

        ArrayList<String> answers = this.topic.getQuestionAtIndex(index).getAnswers();
        Button q1 = (RadioButton) v.findViewById(R.id.radioButton1);
        q1.setText(answers.get(0));

        Button q2 = (RadioButton) v.findViewById(R.id.radioButton2);
        q2.setText(answers.get(1));

        Button q3 = (RadioButton) v.findViewById(R.id.radioButton3);
        q3.setText(answers.get(2));

        Button q4 = (RadioButton) v.findViewById(R.id.radioButton4);
        q4.setText(answers.get(3));
    }
}
