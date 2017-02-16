package edu.uw.tessaev1.quizdroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.*;
import android.util.Log;
import android.widget.*;

public class PreferencesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        final EditText activeURL = (EditText) findViewById(R.id.configuredURL);
        activeURL.setText(QuizApp.getInstance().myUrl);
    }
}
