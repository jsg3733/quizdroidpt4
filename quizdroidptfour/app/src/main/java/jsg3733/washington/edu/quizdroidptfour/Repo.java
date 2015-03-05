package jsg3733.washington.edu.quizdroidptfour;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jsg3733 on 2/16/2015.
 */
public class Repo implements TopicRepository{

    private List<Topic> topics;

    public Repo(Context con){
        topics = new ArrayList<>();
        try{

            AssetManager am = con.getAssets();
            InputStream is = am.open("quizs.txt");

            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = br.readLine();

            List<Question> questions = new ArrayList<Question>();
            while(line != null) {
                if(line.equals("::")) {
                    String topic = br.readLine();
                    String longDesc = br.readLine();
                    String shortDesc = br.readLine();

                    Topic t = new Topic(topic, longDesc, shortDesc, questions);
                    topics.add(t);
                    //Log.i("QuizappQ" , "" + t.getQuestions().size());
                    questions = new ArrayList<>();
                    //questions.clear();
                    //Log.i("QuizappQ" , "" + t.getQuestions().size());
                } else {
                    String questiontxt = line;
                    String responseOne = br.readLine();
                    String responseTwo = br.readLine();
                    String responseThree = br.readLine();
                    String responseFour = br.readLine();
                    int answer = Integer.parseInt(br.readLine());
                    Question q = new Question(questiontxt, responseOne, responseTwo, responseThree, responseFour, answer);
                    questions.add(q);
                    //Log.i("QuizappQ", "" + questions.size());
                    //Log.i("quizapp", questiontxt);
                    //Log.i("quizapp", "" + answer);
                }
                line = br.readLine();
                //Log.i("Quizapp", "Reading File");

            }
        }catch (IOException e) {
            Log.i("Quizapp", "File is not being found");
        }
    }

    @Override
    public Topic getTopic(String s) {
        Topic t = new Topic();
        //Log.i.
        for(int i = 0; i < topics.size(); i++) {
            if(topics.get(i).getTitle().equals(s)) {
                t = topics.get(i);
                String tString = t.toString();
                //Log.i("Quizapprepo", tString);
                //Log.i("QuizappQ", "" + t.getQuestions().size());
            }
        }

        return t;
    }

    public List<Topic> getTopics(){
        return topics;
    }

    @Override
    public void addTopic(Topic t) {
        topics.add(t);
    }

    @Override
    public void updateTopic(Topic t) {
        t.updateTitle("hello");
    }

    @Override
    public void deleteTopic(Topic t) {
        for(int i=0; i < topics.size(); i++) {
            if(topics.get(i).toString().equals(t.toString())) {
                topics.remove(i);
            }
        }
    }
}
