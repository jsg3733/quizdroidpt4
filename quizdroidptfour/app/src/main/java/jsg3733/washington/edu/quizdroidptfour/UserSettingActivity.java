package jsg3733.washington.edu.quizdroidptfour;

import android.content.Intent;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by jsg3733 on 2/24/15.
 */
public class UserSettingActivity extends PreferenceActivity {

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.user_settings);
        addPreferencesFromResource(R.xml.user_settings);
        // add the xml resource                     addPreferencesFromResource(R.xml.user_settings);
        Preference button = (Preference)findPreference("button");
        button.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent= new Intent();
               /*EditTextPreference txtURL = (EditTextPreference) findPreference("prefDownloadURL");
                EditTextPreference txtMins = (EditTextPreference) findPreference("prefDownloadMins");
                String url = txtURL.getText().toString();
                int mins = Integer.parseInt(txtMins.getText().toString());
                intent.putExtra("url", "hello");
                intent.putExtra("mins", 10);
                intent.putExtra("url", url);
                intent.putExtra("mins", mins);*/
                setResult(RESULT_OK, intent);
                finish();
                return false;
            }
        });

    }





    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_preferences, menu);
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
        //

        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            case R.id.action_preferences:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }*/


}
