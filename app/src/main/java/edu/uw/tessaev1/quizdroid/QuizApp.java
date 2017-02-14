package edu.uw.tessaev1.quizdroid;

import android.app.Application;
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
    public MyAsyncTask myAsyncTask = new MyAsyncTask();

    private static final String TAG = "QuizApp";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "QuizApp loaded");
        instance = this;
        myAsyncTask.execute();
    }

    public static QuizApp getInstance() {
        return instance;
    }

    public TopicRepository getTopicRepository() {
        return topicRepo;
    }

        /*
        String external = Environment.getExternalStorageDirectory().getAbsolutePath();
        Log.i(TAG, "external file path: " + external);

        Environment.getExternalStoragePublicDirectory(Environment.getExternalStorageDirectory(), "questions.json");

        String internalStorage = getFilesDir().getAbsolutePath();

        adb push questions.json /destination/path
         */

    public interface TaskDelegate {
        public void taskCompletionResult();
    }

    class MyAsyncTask extends AsyncTask<String, String, JSONArray> {
        private TaskDelegate delegate;

        public void setDelegate(TaskDelegate delegate) {
            this.delegate = delegate;
        }

        @Override
        protected JSONArray doInBackground(String... args) {
            String str = "http://tednewardsandbox.site44.com/questions.json";
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
            topicRepo.parseJSON(response);
            delegate.taskCompletionResult();
            if(response != null) {
                try {
                    Log.e(TAG, "Success: " + response.get(0));
                } catch (JSONException ex) {
                    Log.e(TAG, "Failure", ex);
                }
            }
        }
    }
}
