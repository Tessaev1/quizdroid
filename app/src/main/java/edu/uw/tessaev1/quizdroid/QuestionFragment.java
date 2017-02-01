package edu.uw.tessaev1.quizdroid;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.*;
import android.widget.*;

import java.util.ArrayList;

public class QuestionFragment extends Fragment {
    private static final String ARG_PARAM1 = "topic";

    private Topic topic;
    public String selectedAnswer;
    public Button submit;

    private OnFragmentInteractionListener mListener;

    public QuestionFragment() {}

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param topic Parameter 1.
     * @return A new instance of fragment QuestionFragment.
     */
    public static QuestionFragment newInstance(Topic topic) {
        QuestionFragment fragment = new QuestionFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, topic);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            topic = (Topic) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question, container, false);

        displayQuestion(view, topic.getCurrentQuestion());
        getSelectedRadioButton(view);

        submit = (Button) getActivity().findViewById(R.id.fragmentButton);
        submit.setText("Submit");
        submit.setEnabled(false);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.showQuestionSummary(selectedAnswer, topic);
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void showQuestionSummary(String string, Topic topic);
    }

    public void getSelectedRadioButton(View v) {
        RadioGroup radioGroup = (RadioGroup) v.findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton checkedRadioButton = (RadioButton)group.findViewById(checkedId);
                selectedAnswer = checkedRadioButton.getText().toString();
                submit.setEnabled(true);
            }
        });
    }

    public void displayQuestion(View v, int index) {
        TextView question = (TextView) v.findViewById(R.id.question);
        question.setText(topic.getQuestionAtIndex(topic.getCurrentQuestion()).getQuestion());

        ArrayList<String> answers = topic.getQuestionAtIndex(index).getAnswers();
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
