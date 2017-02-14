package edu.uw.tessaev1.quizdroid;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

/**
 * Created by Tessa on 2/9/17.
 */

public class TopicRepository {
    private static TopicRepository instance = new TopicRepository();
    private Map<String, Topic> topicList = new TreeMap<String, Topic>();
    private Topic currentTopic;
    private String userAnswer;

    private static final String TAG = "TopicRepository";

    protected TopicRepository() {}

    public static TopicRepository getInstance() {
        return instance;
    }

    public List<String> getTopicList() {
        return new ArrayList<String>(this.topicList.keySet());
    }

    public Topic getCurrentTopic() {
        return this.currentTopic;
    }

    public void setCurrentTopic(String topic) {
        if (this.topicList.containsKey(topic)) {
            this.currentTopic = this.topicList.get(topic);
        } else {
            Log.e(TAG, "Failure: topic does not exist");
        }
    }

    public void setUserAnswer(String answer) {
        this.userAnswer = answer;
    }

    public String getUserAnswer() {
        return this.userAnswer;
    }

    public void parseJSON(JSONArray json) {
        try {
            for (int i = 0; i < json.length(); i++) {
                JSONObject jsonObject = json.getJSONObject(i);
                String title = jsonObject.getString("title");
                Topic topic = new Topic(title);
                topic.setShortDescription("");
                String desc = jsonObject.getString("desc");
                topic.setLongDescription(desc);
                JSONArray questions = jsonObject.getJSONArray("questions");

                ArrayList<Question> questionList = new ArrayList<Question>();
                for (int j = 0; j < questions.length(); j++) {
                    JSONObject q = questions.getJSONObject(j);
                    String text = q.getString("text");
                    int correct = q.getInt("answer") - 1;
                    ArrayList<String> options = new ArrayList<String>();
                    JSONArray answers = q.getJSONArray("answers");

                    for (int k = 0; k < answers.length(); k++) {
                        options.add(answers.getString(k));
                    }

                    questionList.add(new Question(text, options, correct));
                }

                topic.setQuestions(questionList);
                this.topicList.put(title, topic);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
