package edu.uw.tessaev1.quizdroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.*;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        final ListView listView = (ListView) findViewById(R.id.listView);

        List<String> topicList = QuizApp.getInstance().getTopicRepository().getTopicList();
        ArrayAdapter<String> adapter = new CustomListAdapter(this.getApplicationContext(), topicList);
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
}
