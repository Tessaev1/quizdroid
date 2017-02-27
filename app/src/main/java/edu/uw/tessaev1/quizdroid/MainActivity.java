package edu.uw.tessaev1.quizdroid;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;
import android.widget.AdapterView;
import android.widget.*;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static Activity activity;
    public final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        activity = this;
        taskCompletionResult();
    }

    public void taskCompletionResult() {
        final ListView listView = (ListView) findViewById(R.id.listView);

        List<String> topicList = QuizApp.getInstance().getTopicRepository().getTopicList();
        ArrayAdapter<String> adapter = new CustomListAdapter(this, topicList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                String topic = (String) (listView.getItemAtPosition(position));
                QuizApp.getInstance().getTopicRepository().setCurrentTopic(topic);
                Intent intent = new Intent(MainActivity.this, QuizActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar, menu);
        return true;
    }

    public void getPreferences(MenuItem item) {
        Intent intent = new Intent(MainActivity.this, PreferencesActivity.class);
        startActivity(intent);
    }

    public static void showNoticeDialog() {
        DialogFragment dialog = new RetryDownloadDialogFragment();
        dialog.show(activity.getFragmentManager(), "RetryDownloadDialogFragment");
    }
}
