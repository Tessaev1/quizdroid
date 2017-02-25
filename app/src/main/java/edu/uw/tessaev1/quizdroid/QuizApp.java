package edu.uw.tessaev1.quizdroid;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import org.json.*;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Tessa on 2/9/17.
 */

public class QuizApp extends Application {
    private static QuizApp instance;
    private static TopicRepository topicRepo = TopicRepository.getInstance();
    public MyAsyncTask myAsyncTask;

    private SharedPreferences prefs;
    private String url;
//    private String url = "https://api.myjson.com/bins/11n579";

    private static final String TAG = "QuizApp";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "QuizApp loaded");

        instance = this;
        myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute();

        this.prefs = this.getSharedPreferences(getString(R.string.preference_file_key),
                Context.MODE_PRIVATE);
        this.url = getString(R.string.default_url);

        checkInternetConnection(this);
    }

    public static QuizApp getInstance() {
        return instance;
    }

    public TopicRepository getTopicRepository() {
        return topicRepo;
    }

    public interface TaskDelegate {
        public void taskCompletionResult();
    }

    public boolean checkInternetConnection(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (isAirplaneModeOn(context)) {
            Toast.makeText(context, "Airplane mode is on. Please turn Airplane mode off" +
                    " and try again.", Toast.LENGTH_LONG).show();
            return false;
        } else if (cm.getActiveNetworkInfo() == null || !cm.getActiveNetworkInfo().isAvailable() ||
                !cm.getActiveNetworkInfo().isConnected()) {
            Toast.makeText(context, "No signal. Try again later.", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }

    @SuppressWarnings("deprecation")
    public static boolean isAirplaneModeOn(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return Settings.System.getInt(context.getContentResolver(),
                    Settings.System.AIRPLANE_MODE_ON, 0) != 0;
        } else {
            return Settings.Global.getInt(context.getContentResolver(),
                    Settings.Global.AIRPLANE_MODE_ON, 0) != 0;
        }
    }

    class MyAsyncTask extends AsyncTask<String, String, JSONArray> {
        private TaskDelegate delegate;

        // if the questions.json file exists locally, parse its JSON
        public MyAsyncTask() {
            File file = new File(getApplicationContext().getFilesDir().getPath().toString() + "question.json");
            if (file.exists()) {
                try {
                    String json = topicRepo.readFile(QuizApp.this);
                    JSONArray jsonArray = new JSONArray(json);
                    topicRepo.parseJSON(jsonArray);
                } catch (JSONException ex) {
                    Log.e(TAG, "Failure", ex);
                }
            }
        }

        public void setDelegate(TaskDelegate delegate) {
            this.delegate = delegate;
        }

        @Override
        protected JSONArray doInBackground(String... args) {
            String str = QuizApp.this.url;
            URLConnection urlConn = null;
            BufferedReader bufferedReader = null;
            try {
                URL url = new URL(str);
                urlConn = url.openConnection();
                bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));

                StringBuffer stringBuffer = new StringBuffer();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuffer.append(line);
                }

                return new JSONArray(stringBuffer.toString());
            }
            catch(Exception ex) {
                Log.e(TAG, "json object", ex);
                return null;
            } finally {
                if(bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        /*
         * Use the data downloaded from myUrl, write it to a file, and parse its JSON
         */
        @Override
        protected void onPostExecute(JSONArray response) {
            if(response != null) {
                try {
                    Log.e(TAG, "Success: " + response.get(0));
                    this.writeToFile(response.toString());
                    topicRepo.parseJSON(response);
                    delegate.taskCompletionResult();
                } catch (JSONException ex) {
                    Log.e(TAG, "Failure", ex);
                }
            }
        }

        private void writeToFile(String data) {
            try {
                Log.i(TAG, "context: " + getApplicationContext());
                File file = new File(getApplicationContext().getFilesDir().getPath().toString() + "question.json");
                FileOutputStream fileOutput = new FileOutputStream(file, false);
                OutputStreamWriter output = new OutputStreamWriter(fileOutput);
                output.write(data);
                output.close();
            }
            catch (IOException e) {
                Log.e("Exception", "File write failed: " + e.toString());
            }
        }
    }
}
