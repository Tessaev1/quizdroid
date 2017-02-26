package edu.uw.tessaev1.quizdroid;

import android.content.*;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Tessa on 2/26/17.
 */

public class AlarmReceiver extends BroadcastReceiver {
    private static AlarmReceiver instance;
    private static TopicRepository topicRepo = TopicRepository.getInstance();
    private Context context;
    private Intent intent;
    private SharedPreferences prefs;
    private String url;
    public static MyAsyncTask myAsyncTask;

    public static final String TAG = "AlarmReceiver";

    public AlarmReceiver() {
        instance = this;
    }

    public static AlarmReceiver getInstance() {
        return instance;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        this.intent = intent;
        this.prefs = this.context.getSharedPreferences(this.context.getString(R.string.preference_file_key),
                Context.MODE_PRIVATE);
        this.url = this.context.getString(R.string.default_url);

        if (this.checkInternetConnection(this.context)) {
            new MyAsyncTask().execute();
        }
    }

    public interface TaskDelegate {
        public void taskCompletionResult();
    }

    class MyAsyncTask extends AsyncTask<String, String, JSONArray> {
        private AlarmReceiver.TaskDelegate delegate;

        public MyAsyncTask() {
            Toast.makeText(context, "Download in progress...", Toast.LENGTH_LONG).show();
        }

        public void setDelegate(AlarmReceiver.TaskDelegate delegate) {
            this.delegate = delegate;
        }

        @Override
        protected JSONArray doInBackground(String... args) {
            String str = AlarmReceiver.this.url;
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
         * Use the data downloaded from given url, write it to a file, and parse its JSON
         */
        @Override
        protected void onPostExecute(JSONArray response) {
            if(response != null) {
                try {
                    Log.e(TAG, "Success: " + response.get(0));
                    Toast.makeText(context, "Download successful!", Toast.LENGTH_LONG).show();
                    this.writeToFile(response.toString());
                    topicRepo.parseJSON(response);
//                    delegate.taskCompletionResult();
                } catch (JSONException ex) {
                    Log.e(TAG, "Failure", ex);
                }
            }
        }

        private void writeToFile(String data) {
            try {
                File file = new File(AlarmReceiver.this.context.getFilesDir().getPath().toString() + "/question.json");
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

    public boolean checkInternetConnection(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if (isAirplaneModeOn(context)) {
            Toast.makeText(context, "Airplane mode is on. Please turn Airplane mode off" +
                    " and try again.", Toast.LENGTH_LONG).show();
            return false;
        } else if (activeNetwork == null || (!activeNetwork.isAvailable()) ||
                (!activeNetwork.isConnected())) {
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
}
