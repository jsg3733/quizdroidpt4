package jsg3733.washington.edu.quizdroidptfour;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class TopicList extends ActionBarActivity {

    protected QuizApp app;
    ListView list;
    private static final int SETTINGS_RESULT = 1;
    private PendingIntent pendingIntent;
    private boolean downloadTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_list);


        app = (QuizApp)getApplication();
        Repo repo = app.getRepo();
        final List<Topic> topics = app.getTopics(repo);

        list = (ListView) findViewById(R.id.lstTopics);

        List<String> values = new ArrayList<>();
        List<TopicRow> topicRow_data = new ArrayList<>();

        for(int i = 0; i < topics.size(); i++) {
            Topic t = topics.get(i);
            values.add(t.getTitle());
            topicRow_data.add(new TopicRow(R.drawable.ic_launcher, t.getTitle(), t.getShortDesc()));
        }

        TopicRowAdapter adapter = new TopicRowAdapter(this,
                R.layout.listview_item_row, topicRow_data);
        list.setAdapter(adapter);


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int itemPosition = position;
                //String itemValue = (String) list.getItemAtPosition(itemPosition);
                String itemValue = (String) topics.get(itemPosition).getTitle();

                Intent nextActivity = new Intent(TopicList.this, TopicOver.class);
                nextActivity.putExtra("topic", itemValue);

                startActivity(nextActivity);
                //finish();


            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_topic_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        /*int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
        */

        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            case R.id.action_preferences:
                //method()
                Intent nextActivity = new Intent(TopicList.this, UserSettingActivity.class);
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
                        Intent alarmIntent = new Intent(TopicList.this, AlarmReciever.class);
                        pendingIntent = PendingIntent.getBroadcast(TopicList.this, 0, alarmIntent, 0);
                        cancel();
                        start(mins);
                    }



                }
        }

    }

    public void start(int mins){
        Intent alarmIntent = new Intent(TopicList.this, AlarmReciever.class);
        pendingIntent = PendingIntent.getBroadcast(TopicList.this, 0, alarmIntent, 0);

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




    /*private void displayUserSettings()
    {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        String  settings = "";

        settings=settings+"Password: " + sharedPrefs.getString("prefUserPassword", "NOPASSWORD");

        settings=settings+"\nRemind For Update:"+ sharedPrefs.getBoolean("prefLockScreen", false);

        settings=settings+"\nUpdate Frequency: "
                + sharedPrefs.getString("prefUpdateFrequency", "NOUPDATE");

        //TextView textViewSetting = (TextView) findViewById(R.id.textViewSettings);

        //textViewSetting.setText(settings);
    }*/

}
