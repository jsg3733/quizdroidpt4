package jsg3733.washington.edu.quizdroidptfour;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by jsg3733 on 2/23/15.
 */
public class AlarmReciever extends BroadcastReceiver{


    @Override
    public void onReceive(Context context, Intent intent) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String resultURL = sharedPreferences.getString("prefDownloadURL", "");
        Log.i("QuizApptest", "In Alarm");
        Toast.makeText(context, "Downloading file from: " + resultURL, Toast.LENGTH_SHORT).show();
    }
}
