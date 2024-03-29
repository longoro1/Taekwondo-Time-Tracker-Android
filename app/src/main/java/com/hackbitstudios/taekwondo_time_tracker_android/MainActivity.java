package com.hackbitstudios.taekwondo_time_tracker_android;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.hackbitstudios.taekwondo_time_tracker_android.api.taekwondo.stat.ApiDownloaderStat;
import com.hackbitstudios.taekwondo_time_tracker_android.api.taekwondo.stat.ApiModelStat;
import com.hackbitstudios.taekwondo_time_tracker_android.api.taekwondo.stat.ApiObjectStat;

import java.util.ArrayList;
import android.preference.PreferenceManager;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends Activity {

    //region Views
    @InjectView(R.id.main_listview) ListView statsList;
    //endregion

    //region OVERRIDE FUNCTIONS
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.inject(this); // So ButterKnife works

        // Main Code
        populateStatList();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Main Code
        populateStatList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // Launch settings menu
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        // Refresh
        else if (id == R.id.action_refresh) {
            populateStatList();
            return true;
        }

        // Add a new punchcard
        else if (id == R.id.action_new) {
            try {
                Intent intent = new Intent(this, PunchcardActivity.class);
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //endregion

    //region PRIVATE FUNCTIONS

    // Function to launch an async task and populate the listview
    private void populateStatList() {

        // Show a toast
        Toast.makeText(getApplicationContext(), "Attempting to get stats", Toast.LENGTH_SHORT).show();

        // Get the preferences
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String url = sharedPreferences.getString("server_url_pref", "");
        String access_token = sharedPreferences.getString("access_token_pref", "");

        // Create the ApiModel
        ApiModelStat apiModelStat = new ApiModelStat(url, access_token);

        // Create the downloader
        ApiDownloaderStat apiDownloaderStat = new ApiDownloaderStat(apiModelStat) {

            @Override
            public void onResponse(ArrayList<ApiObjectStat> stats, boolean wasSuccessful, String error) {

                // Throw an error if needed
                if (!wasSuccessful) {
                    // Show a toast
                    Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();

                    return;
                }

                // Not an error, display in listview
                populateStatListHelper(stats);

                // Show a toast
                Toast.makeText(getApplicationContext(), "Finished getting stats", Toast.LENGTH_SHORT).show();

            }
        };

        // Run the async task
        apiDownloaderStat.execute();

    }

    // Function that will actually fill list view
    private void populateStatListHelper(ArrayList<ApiObjectStat> _stats) {

        // First make an array of strings
        String values[] = new String[_stats.size()];
        for (int i = 0; i < _stats.size(); i++)
            values[i] = _stats.get(i).getStat();

        // Create an array adapter that uses the values array
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, values);

        // Add the adapter to the statList
        statsList.setAdapter(adapter);

    }

    //endregion


}
