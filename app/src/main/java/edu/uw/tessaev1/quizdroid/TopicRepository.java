package edu.uw.tessaev1.quizdroid;

/**
 * Created by Tessa on 2/9/17.
 */

public class TopicRepository {
    private static TopicRepository instance = new TopicRepository();

    public static TopicRepository getInstance() {
        return instance;
    }
}
