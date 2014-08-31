package com.hackbitstudios.taekwondo_time_tracker_android;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.hackbitstudios.taekwondo_time_tracker_android.R;
import com.hackbitstudios.taekwondo_time_tracker_android.api.taekwondo.ApiDownloaderStat;
import com.hackbitstudios.taekwondo_time_tracker_android.api.taekwondo.ApiModelStat;
import com.hackbitstudios.taekwondo_time_tracker_android.api.taekwondo.ApiObjectStat;

import java.util.ArrayList;

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


    /*@Override
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
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    */
    //endregion

    //region PRIVATE FUNCTIONS

    // Function to launch an async task and populate the listview
    private void populateStatList() {

        // Create an APIModel
        //ApiModelStat apiModelStat = new ApiModelStat("http://192.168.1.6:3000", "472Pb1g_1YLXTGNZs7v9"); // DEV
        ApiModelStat apiModelStat = new ApiModelStat("http://taekwondo-time-tracker.herokuapp.com", "s19o4zoUsC6FEgsyk-yj");  // HEROKU

        // Create the downloader
        ApiDownloaderStat apiDownloaderStat = new ApiDownloaderStat(apiModelStat) {

            @Override
            public void onResponse(ArrayList<ApiObjectStat> stats, boolean isError, String error) {

                // Throw an error if needed
                if (isError) {
                    // Show a toast
                    Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();

                    return;
                }

                // Not an error, display in listview
                populateStatListHelper(stats);

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
