package edu.uw.tessaev1.quizdroid;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
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

    public static final String MY_URL = "http://tednewardsandbox.site44.com/questions.json";
    private static final String TAG = "QuizApp";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "QuizApp loaded");

        instance = this;
        myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute();
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
            String str = MY_URL;
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
                FileOutputStream fileOutput = new FileOutputStream(file);
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
