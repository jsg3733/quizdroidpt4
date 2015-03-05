package jsg3733.washington.edu.quizdroidptfour;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;


public class TopicOver extends ActionBarActivity {

    protected QuizApp app;
    private String topicName;
    private int QNum;
    private int QNumMax;
    private int howManyRight;
    private String answer;
    private String response;
    private Repo repo;
    private static final int SETTINGS_RESULT = 1;
    private PendingIntent pendingIntent;
    private boolean downloadTest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_over);

        Intent launchedMe = getIntent();
        app = (QuizApp) getApplication();
        topicName = launchedMe.getStringExtra("topic");
        repo = app.getRepo();
        Topic topic = app.getTopic(topicName, repo);
        QNum = 1;
        QNumMax = topic.getQuestions().size();
        answer = "";
        response = "4";
        howManyRight = 0;
        //Log.i("QuizAppTO", "" + QNumMax);



        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new TopicOverFragment())
                    .commit();
        }

        final Button begin = (Button) findViewById(R.id.btnNext);
        begin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(begin.getText().toString().equals("Begin")){
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, new QuestionFragment())
                            .commit();
                    begin.setText("Submit");
                }else if(begin.getText().toString().equals("Submit")) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, new AnswerFragment())
                            .commit();
                    QNum++;
                    if(QNum > QNumMax) {
                        begin.setText("Finish");
                    }else {
                        begin.setText("Next");
                    }
                }else if(begin.getText().toString().equals("Next")) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, new QuestionFragment())
                            .commit();
                    begin.setText("Submit");
                    //btnText = begin.getText().toString();
                }else {
                    Intent nextActivity = new Intent(TopicOver.this, TopicList.class);
                    startActivity(nextActivity);
                    finish();
                }

            }
        });
    }

    public String getTopic(){
        return topicName;
    }

    public int getQuestionNum(){
        return QNum;
    }

    public String getResponse(){
        return response;
    }

    public void setResponse(String res) {
        response = res;
    }

    public String getAnswer(){
        return answer;
    }

    public void setAnswer(String ans){
        answer = ans;
    }

    public int getHowManyRight(){
        return howManyRight;
    }

    public void addOneToAmountRight(){
        howManyRight++;
    }

    public QuizApp getApp(){
        return app;
    }

    public Repo getRepo(){
        return repo;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_topic_over, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            case R.id.action_preferences:
                //method()
                Intent nextActivity = new Intent(TopicOver.this, UserSettingActivity.class);
                startActivityForResult(nextActivity, SETTINGS_RESULT);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==SETTINGS_RESULT){
            if(resultCode == RESULT_OK) {
                //String result1 = data.getStringExtra("url");
                //int result2 = data.getIntExtra("mins", 20);
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                String resultURL = sharedPreferences.getString("prefDownloadURL", "");
                String resultMins = sharedPreferences.getString("prefDownloadMins", "20");
                int mins = Integer.parseInt(resultMins);
                Log.i("preferencestest", resultURL);
                Log.i("preferencetest", "" + resultMins);
                //displayUserSettings();
                //while(downloadTest == true) {

                //}
                downloadTest = false;
                if(downloadTest) {

                }else {
                    Intent alarmIntent = new Intent(TopicOver.this, AlarmReciever.class);
                    pendingIntent = PendingIntent.getBroadcast(TopicOver.this, 0, alarmIntent, 0);
                    cancel();
                    start(mins);
                }



            }
        }

    }

    public void start(int mins){
        Intent alarmIntent = new Intent(TopicOver.this, AlarmReciever.class);
        pendingIntent = PendingIntent.getBroadcast(TopicOver.this, 0, alarmIntent, 0);

        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        int interval = 1000 * 60 * mins;

        if(mins == 0) {
            cancel();
        } else {
            manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);
            Toast.makeText(this, "Download Alarm Set", Toast.LENGTH_SHORT).show();
        }
    }

    public void cancel() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);
        pendingIntent.cancel();
        Toast.makeText(this, "Download Alarm Canceled", Toast.LENGTH_SHORT).show();
    }



    /**
     * A placeholder fragment containing a simple view.
     */
    public static class TopicOverFragment extends Fragment {

        //private QuizApp app;

        public TopicOverFragment() {
            //app = appli;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View topicOverView = inflater.inflate(R.layout.fragment_topic_over, container, false);
            TopicOver activity = (TopicOver) getActivity();
            String topicName = activity.getTopic();
            QuizApp app = activity.getApp();
            Repo repo = activity.getRepo();
            Topic t = app.getTopic(topicName, repo);


            ImageView icon = (ImageView) topicOverView.findViewById(R.id.imgTopicIcon);
            icon.setImageResource(R.drawable.ic_launcher);
            TextView header = (TextView) topicOverView.findViewById(R.id.txtTopic);
            header.setText(topicName);
            TextView description = (TextView) topicOverView.findViewById(R.id.txtDesc);
            TextView questions = (TextView) topicOverView.findViewById(R.id.txtQuestions);
            String descr = t.getLongDesc();
            int questionNum = t.getQuestions().size();
            description.setText(descr);
            questions.setText("Questions: " + questionNum);
            /*if(topicName.equals("Math")) {
                description.setText("A topic based on the study of mathematics." +
                        "Includes the use of numbers and formulas.");
                questions.setText("Questions: 5");
            }else if(topicName.equals("Physics")) {
                description.setText("A topic based on the study of the natural science." +
                        "This includes topics such as motion and energy.");
                questions.setText("Questions: 5");
            }else if(topicName.equals("Marvel Super Heroes")) {
                description.setText("A topic based on the super heroes within the Marvel comics.");
                questions.setText("Questions: 5");
            }else if(topicName.equals("Hockey")){
                description.setText("A topic based on the sport hockey which uses a stick" +
                        " and puck to score goals.");
                questions.setText("Questions: 5");
            }else if(topicName.equals("Geography")) {
                description.setText("A topic based on where different cities and rivers are located.");
                questions.setText("Questions: 5");
            }*/

            return topicOverView;

        }
    }


    public static class QuestionFragment extends Fragment {
        private Button submit;
        private TopicOver activity;
        //private QuizApp app;

        public QuestionFragment() {
            //app = appli;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View questionView = inflater.inflate(R.layout.fragment_question, container, false);

            activity = (TopicOver)getActivity();
            String topicName = activity.getTopic();
            int QNum = activity.getQuestionNum();
            QuizApp app = activity.getApp();
            Repo repo = activity.getRepo();
            Topic t = app.getTopic(topicName, repo);
            String answer = "";
            TextView question = (TextView) questionView.findViewById(R.id.txtQuestion);
            RadioButton responseOne = (RadioButton) questionView.findViewById(R.id.btnResponseOne);
            RadioButton responseTwo = (RadioButton) questionView.findViewById(R.id.btnResponseTwo);
            RadioButton responseThree = (RadioButton) questionView.findViewById(R.id.btnResponseThree);
            RadioButton responseFour = (RadioButton) questionView.findViewById(R.id.btnResponseFour);

            int i = QNum - 1;
            //for(int i = 0; i < t.getQuestions().size(); i++){
                //Log.i("Quizapp", "Question num " + i);
                Question q = t.getQuestions().get(i);
                question.setText(q.getQuestiontxt());
                responseOne.setText(q.getAnswerOne());
                responseTwo.setText(q.getAnswerTwo());
                responseThree.setText(q.getAnswerThree());
                responseFour.setText(q.getAnswerFour());
                int an = q.getCorrectAnswer();
                if(an == 1) {
                    answer = q.getAnswerOne();
                }else if(an == 2) {
                    answer = q.getAnswerTwo();
                }else if(an == 3){
                    answer = q.getAnswerThree();
                }else {
                    answer = q.getAnswerFour();
                }
            //}


            submit = (Button) activity.findViewById(R.id.btnNext);
            submit.setEnabled(false);

            responseOne.setOnClickListener(btnListener);
            responseTwo.setOnClickListener(btnListener);
            responseThree.setOnClickListener(btnListener);
            responseFour.setOnClickListener(btnListener);
            //String response = "";

            activity.setAnswer(answer);
            //activity.setResponse(response);

            return questionView;
        }

        /*public String getQResponse(){
            return response;
        }*/

        private View.OnClickListener btnListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Log.i("QuestionPage", answer);
                boolean checked = ((RadioButton) v).isChecked();
                //TopicOverview activity = (TopicOverview)getActivity();
                String response;
                submit.setEnabled(true);
                switch(v.getId()) {
                    case R.id.btnResponseOne:
                        if(checked) {
                            response = ((RadioButton) getActivity().findViewById(R.id.btnResponseOne)).getText().toString();
                            activity.setResponse(response);
                        }
                        break;
                    case R.id.btnResponseTwo:
                        if(checked) {
                            response = ((RadioButton) getActivity().findViewById(R.id.btnResponseTwo)).getText().toString();
                            activity.setResponse(response);
                        }
                        break;
                    case R.id.btnResponseThree:
                        if(checked) {
                            response = ((RadioButton) getActivity().findViewById(R.id.btnResponseThree)).getText().toString();
                            activity.setResponse(response);
                        }
                        break;
                    case R.id.btnResponseFour:
                        if(checked) {
                            response = ((RadioButton) getActivity().findViewById(R.id.btnResponseFour)).getText().toString();
                            activity.setResponse(response);
                        }
                        break;
                }
            }
        };
    }

    public static class AnswerFragment extends Fragment {
        //private QuizApp app;

        public AnswerFragment() {
            //app = appli;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View answerView = inflater.inflate(R.layout.fragment_answer_summary, container, false);
            TopicOver activity = (TopicOver) getActivity();
            String topicName = activity.getTopic();
            String response = activity.getResponse();
            String answer = activity.getAnswer();
            QuizApp app = activity.getApp();
            Repo repo = activity.getRepo();
            Topic topic = app.getTopic(topicName, repo);
            int questionAmount = topic.getQuestions().size();

            TextView lastResponse = (TextView) answerView.findViewById(R.id.txtLastResponse);
            lastResponse.setText("Your response was: " + response);
            TextView correctAnswer = (TextView) answerView.findViewById(R.id.txtAnswer);
            correctAnswer.setText("The correct answer is: " + answer);
            TextView amountRight = (TextView) answerView.findViewById(R.id.txtTotalRight);

            if(response.equals(answer)) {
                activity.addOneToAmountRight();
            }
            int howManyRight = activity.getHowManyRight();
            amountRight.setText("You have " + howManyRight + " out of " + questionAmount + " correct!");

            return answerView;
        }
    }





}
