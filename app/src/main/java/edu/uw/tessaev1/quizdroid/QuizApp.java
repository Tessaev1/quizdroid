package edu.uw.tessaev1.quizdroid;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by Tessa on 2/9/17.
 */

public class QuizApp extends Application {

    @Override
    public void onCreate() {
        Log.d("QuizApp", "QuizApp loaded");
    }

//    public void getTopicRepository() {
//        TopicRepository topicRepository = getApplication().getInstance(TopicRepository);
//    }
}
