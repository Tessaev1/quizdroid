package edu.uw.tessaev1.quizdroid;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

/**
 * Created by Tessa on 2/9/17.
 */

public class QuizApp extends Application {
    private static QuizApp instance;
    private static TopicRepository topicRepo;

    private static final String TAG = "QuizApp";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "QuizApp loaded");
        instance = this;
    }

    public static QuizApp getInstance() {
        return instance;
    }

    public TopicRepository getTopicRepository() {
        topicRepo = TopicRepository.getInstance();
        return topicRepo;
    }
}
