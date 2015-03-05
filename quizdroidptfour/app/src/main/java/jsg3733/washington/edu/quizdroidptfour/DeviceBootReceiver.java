package jsg3733.washington.edu.quizdroidptfour;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

/**
 * Created by iguest on 2/26/15.
 */
public class DeviceBootReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            /* Setting the alarm here */

            Intent alarmIntent = new Intent(context, AlarmReciever.class);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, 0);

            AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            String resultURL = sharedPreferences.getString("prefDownloadURL", "");
            String resultMins = sharedPreferences.getString("prefDownloadMins", "20");
            int mins = Integer.parseInt(resultMins);

            int interval = 1000 * 60 * mins;

            manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);

            Toast.makeText(context, "Downloading file from: " + resultURL, Toast.LENGTH_SHORT).show();
        }
    }
}
