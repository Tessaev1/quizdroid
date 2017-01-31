package edu.uw.tessaev1.quizdroid;

import android.content.*;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.*;
import android.widget.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TopicOverviewFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TopicOverviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TopicOverviewFragment extends Fragment {
    public Topic topic;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "quiz";
    private static final String FRAGMENT = "topic overview";

    private String quizName;

    private OnFragmentInteractionListener mListener;

    public TopicOverviewFragment() {}

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param quiz Parameter 1.
     * @return A new instance of fragment TopicOverviewFragment.
     */
    public static TopicOverviewFragment newInstance(String quiz) {
        TopicOverviewFragment fragment = new TopicOverviewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, quiz);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            quizName = getArguments().getString(ARG_PARAM1);
        }

        if (quizName.toString().toLowerCase().equals("math")) {
            createMathTopic();
        } else if (quizName.toString().toLowerCase().equals("physics")) {
            createPhysicsTopic();
        } else {
            createSuperHeroTopic();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_topic_overview, container, false);

        displayTopicOverview(view);

        Button begin = (Button) getActivity().findViewById(R.id.fragmentButton);
        begin.setText("Begin");
        begin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onBeginQuizClick(topic);
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
        void onBeginQuizClick(Topic topic);
    }

    public void displayTopicOverview(View v) {
        TextView topicField = (TextView) v.findViewById(R.id.topic);
        TextView overview = (TextView) v.findViewById(R.id.topicOverview);
        TextView total = (TextView) v.findViewById(R.id.totalQuestions);

        topicField.setText(this.topic.getTopicName());
        overview.setText(this.topic.getDescription());
        total.setText("This quiz has " + this.topic.size() + " total questions");
    }

    public void createMathTopic() {
        topic = new Topic("Math");
        ArrayList<Question> questionList = new ArrayList<Question>();
        questionList.add(new Question("2 + 2 = ", new ArrayList<String>(Arrays.asList("2", "5", "4", "10")),
                2));
        questionList.add(new Question("5 * 5 = ", new ArrayList<String>(Arrays.asList("25", "5", "4", "10")),
                0));
        questionList.add(new Question("7 + 6 = ", new ArrayList<String>(Arrays.asList("2", "13", "14", "12")),
                1));
        questionList.add(new Question("30 / 5 = ", new ArrayList<String>(Arrays.asList("5", "7", "14", "6")),
                3));

        topic.setDescr("The fundamentals of mathematics");
        topic.setQuestions(questionList);
    }

    public void createPhysicsTopic() {
        topic = new Topic("Physics");
        ArrayList<Question> questionList = new ArrayList<Question>();
        questionList.add(new Question("Physics is the study of",
                new ArrayList<String>(Arrays.asList("Matter and Energy", "Earth", "Stars", "Anatomy")), 0));
        questionList.add(new Question("Which of the following is a unit of Work?",
                new ArrayList<String>(Arrays.asList("Watt", "Joule", "Inches", "Newton")), 1));
        questionList.add(new Question("Light Year is a unit of...",
                new ArrayList<String>(Arrays.asList("Time", "Distance", "Light", "Intensity")), 1));
        questionList.add(new Question("Sound waves in the air are",
                new ArrayList<String>(Arrays.asList("Electromagnetic", "Polarized", "Transverse", "Longitudinal")),
                3));

        topic.setDescr("The science of the natural world");
        topic.setQuestions(questionList);
    }

    public void createSuperHeroTopic() {
        topic = new Topic("Marvel Super Heroes");
        ArrayList<Question> questionList = new ArrayList<Question>();
        questionList.add(new Question("Which of following is not a Marvel movie?",
                new ArrayList<String>(Arrays.asList("The Incredibles", "Wonder Woman", "Batman", "Suicide Squad")), 0));
        questionList.add(new Question("Which Super Hero Team Does Johnny Storm Belong To?",
                new ArrayList<String>(Arrays.asList("Avengers", "Justice League", "X-Men", "Fantastic 4")), 3));
        questionList.add(new Question("Saber tooth/Victor Creed and Wolverine are...",
                new ArrayList<String>(Arrays.asList("Brothers", "Cousins", "Friends", "Strangers")), 0));
        questionList.add(new Question("What Is Tony Stark's Super Hero Name?",
                new ArrayList<String>(Arrays.asList("Spiderman", "Iron Man", "Deadpool", "Superman")),
                1));

        topic.setDescr("How well do you know your Marvel Super Heroes?");
        topic.setQuestions(questionList);
    }
}
