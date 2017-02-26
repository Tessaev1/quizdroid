package edu.uw.tessaev1.quizdroid;

import android.app.*;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

import org.json.*;
import java.io.*;

/**
 * Created by Tessa on 2/9/17.
 */

public class QuizApp extends Application {
    private static QuizApp instance;
    private static TopicRepository topicRepo = TopicRepository.getInstance();

    private static final String TAG = "QuizApp";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "QuizApp loaded");

        instance = this;

        File file = new File(this.getFilesDir().getPath().toString() + "/question.json");
        if (file.exists()) {
            try {
                String json = readFile();
                JSONArray jsonArray = new JSONArray(json);
                topicRepo.parseJSON(jsonArray);
            } catch (JSONException ex) {
                Log.e(TAG, "Failure", ex);
            }
        }

        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime(), Integer.parseInt(getString(R.string.default_interval)), pendingIntent);

    }

    public static QuizApp getInstance() {
        return instance;
    }

    public TopicRepository getTopicRepository() {
        return topicRepo;
    }

    public String readFile() {
        String ret = "";

        try {
            InputStream inputStream = this.openFileInput("question.json");
            if (inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }
}
