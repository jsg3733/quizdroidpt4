package jsg3733.washington.edu.quizdroidptfour;

/**
 * Created by jsg3733 on 2/16/2015.
 */
public interface TopicRepository {
    Topic getTopic(String s);
    void addTopic(Topic t);
    void updateTopic(Topic t);
    void deleteTopic(Topic t);
}
