package edu.uw.tessaev1.quizdroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.*;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    public final static String EXTRA_QUIZ_NAME = "edu.uw.tessaev1.quizdroid.QUIZNAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        String[] quizList = QuizApp.getInstance().getTopicRepository().getTopicList();

        final ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, quizList));

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
