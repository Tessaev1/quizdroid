package edu.uw.tessaev1.quizdroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.*;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity implements QuizApp.TaskDelegate {
    public final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        taskCompletionResult();
        QuizApp.getInstance().myAsyncTask.setDelegate(this);
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
}
