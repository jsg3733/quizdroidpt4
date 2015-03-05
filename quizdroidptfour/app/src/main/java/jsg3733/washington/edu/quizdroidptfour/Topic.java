package jsg3733.washington.edu.quizdroidptfour;

import java.util.List;

/**
 * Created by jsg3733 on 2/16/2015.
 */
public class Topic {

    private String title;
    private String longDesc;
    private String shortDesc;
    private List<Question> questions;

    public Topic(){
        new Topic("", "", "", null);
    }

    public Topic(String T, String LD, String SD, List<Question> Qs) {
        title = T;
        longDesc = LD;
        shortDesc = SD;
        questions = Qs;
    }

    public String getTitle(){
        return title;
    }

    public String getLongDesc(){
        return longDesc;
    }

    public String getShortDesc(){
        return shortDesc;
    }

    public List<Question> getQuestions(){
        return questions;
    }

    public void updateTitle(String s) {title = s; }

    @Override
    public String toString(){
        return title;
    }

    /*public Topic getTopic(String s) {
        return topic;
    }*/
}
