package edu.uw.tessaev1.quizdroid;

import android.content.*;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.*;
import android.widget.*;

import java.util.*;

public class TopicOverviewFragment extends Fragment {
    public Topic topic;

    private static final String FRAGMENT = "topic overview";

    private OnFragmentInteractionListener mListener;

    public TopicOverviewFragment() {}

    public static TopicOverviewFragment newInstance() {
        TopicOverviewFragment fragment = new TopicOverviewFragment();
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

        View view = inflater.inflate(R.layout.fragment_topic_overview, container, false);

        displayTopicOverview(view);

        Button begin = (Button) getActivity().findViewById(R.id.fragmentButton);
        begin.setText("Begin");
        begin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onBeginQuizClick();
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

    public void displayTopicOverview(View v) {
        TextView topicField = (TextView) v.findViewById(R.id.topic);
        TextView overview = (TextView) v.findViewById(R.id.topicOverview);
        TextView total = (TextView) v.findViewById(R.id.totalQuestions);

        topicField.setText(this.topic.getTopicName());
        overview.setText(this.topic.getLongDescription());
        total.setText("This quiz has " + this.topic.size() + " total questions");
    }
}
