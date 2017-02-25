package edu.uw.tessaev1.quizdroid;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.*;
import android.util.Log;
import android.view.View;
import android.widget.*;

public class PreferencesActivity extends AppCompatActivity {
    private SharedPreferences prefs;


    public static final String TAG = "PreferencesActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        this.prefs = this.getSharedPreferences(getString(R.string.preference_file_key),
                Context.MODE_PRIVATE);

        String defaultURL = getString(R.string.default_url);
        String defaultInterval = getString(R.string.default_interval);

        String url = this.prefs.getString(getString(R.string.url_key), defaultURL);
        String interval = this.prefs.getString(getString(R.string.interval_key), defaultInterval);

        final EditText urlText = (EditText) findViewById(R.id.configuredURL);
        final EditText intervalText = (EditText) findViewById(R.id.downloadMinutes);

        urlText.setText(url);
        intervalText.setText(interval);

        final Button savePrefs = (Button) findViewById(R.id.savePreferences);
        savePrefs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newURL = urlText.getText().toString();
                String newInterval = intervalText.getText().toString();

                if (Integer.parseInt(newInterval) > 0) {
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString(getString(R.string.url_key), newURL);
                    editor.putString(getString(R.string.interval_key), newInterval);
                    editor.commit();

                } else {
                    Toast.makeText(PreferencesActivity.this,
                            "Please input a number above 0 for an interval.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
