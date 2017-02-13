package edu.uw.tessaev1.quizdroid;

import android.content.Context;
import android.util.Log;
import android.view.*;
import android.widget.*;

import java.util.List;

/**
 * Created by Tessa on 2/12/17.
 */

public class CustomListAdapter extends ArrayAdapter<String> {
    private List<String> topicList;
    private Context context;

    public CustomListAdapter(Context context, List<String> topicList) {
        super(context, R.layout.custom_list, topicList);

        this.topicList = topicList;
        this.context = context;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.custom_list, parent, false);

        TextView topic = (TextView) rowView.findViewById(R.id.topic);
        TextView descr = (TextView) rowView.findViewById(R.id.shortDescr);

        topic.setText(topicList.get(position));
        QuizApp.getInstance().getTopicRepository().setCurrentTopic(topicList.get(position));
        descr.setText(QuizApp.getInstance().getTopicRepository().getCurrentTopic().getShortDescription());

        return rowView;

    }
}
