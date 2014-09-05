package com.hackbitstudios.taekwondo_time_tracker_android;

import android.app.ActionBar;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.hackbitstudios.taekwondo_time_tracker_android.api.taekwondo.post.ApiDownloaderPost;
import com.hackbitstudios.taekwondo_time_tracker_android.api.taekwondo.post.ApiModelPost;
import com.hackbitstudios.taekwondo_time_tracker_android.api.taekwondo.purpose.ApiDownloaderPurpose;
import com.hackbitstudios.taekwondo_time_tracker_android.api.taekwondo.purpose.ApiModelPurpose;
import com.hackbitstudios.taekwondo_time_tracker_android.api.taekwondo.purpose.ApiObjectPurpose;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class PunchcardActivity extends Activity {

    //region VIEWS
    @InjectView(R.id.punchcard_purpose) Spinner purpose;
    @InjectView(R.id.punchcard_date) DatePicker date;
    @InjectView(R.id.punchcard_start_time) TimePicker startTime;
    @InjectView(R.id.punchcard_end_time) TimePicker endTime;
    @InjectView(R.id.punchcard_submit) Button submit;

    // Onclick for the submit button
    @OnClick(R.id.punchcard_submit) void submit() {

        // Show a toast
        Toast.makeText(getApplicationContext(), "Attempting to submit punchcard", Toast.LENGTH_SHORT).show();

        // Construct the post model
        ApiModelPost apiModelPost = constructApiModelPost();

        // Quit if it failed to construct
        if (apiModelPost == null) return;

        // Create and Launch an async task to post to the web
        ApiDownloaderPost apiDownloaderPost = new ApiDownloaderPost(apiModelPost) {

            @Override
            public void onResponse(boolean wasSuccessful, String error) {

                // Throw an error if needed
                if (!wasSuccessful) {
                    // Show a toast
                    Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();

                    return;
                }

                // Was successful, so exit this page
                // Show a toast
                Toast.makeText(getApplicationContext(), "Submission Successful", Toast.LENGTH_SHORT).show();
                finish();

            }
        };

        // Run the async task
        apiDownloaderPost.execute();

    }

    //endregion

    //region MEMBERS

    // Store the array list from the async task
    ArrayList<ApiObjectPurpose> purposeArrayList = null;

    //endregion

    //region OVERRIDDEN METHODS
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_punchcard);
        ButterKnife.inject(this); // So ButterKnife works

        // Main Code
        setSpinnerMenu(null); // Set to warning message
        getSpinnerMenuItems(); // Get real items

        // Add back button to action bar
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.punchcard, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //endregion

    //region PRIVATE METHODS

    // Gets the spinner option from api
    private void getSpinnerMenuItems() {

        // Get the preferences
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String url = sharedPreferences.getString("server_url_pref", "");
        String access_token = sharedPreferences.getString("access_token_pref", "");

        // Create the ApiModel
        ApiModelPurpose apiModelStat = new ApiModelPurpose(url, access_token);

        // Get the array
        ApiDownloaderPurpose apiDownloaderPurpose = new ApiDownloaderPurpose(apiModelStat) {

            @Override
            public void onResponse(ArrayList<ApiObjectPurpose> purpose, boolean wasSuccessful, String error) {

                // purpose was empty, quit
                if (purpose == null || purpose.isEmpty())
                    return ;

                // Construct and populate the array of strings
                String[] names = new String[purpose.size()];
                for (int i = 0; i < names.length; i++)
                    names[i] = purpose.get(i).getName();

                // Finally, update the spinner menu and store the array list
                setSpinnerMenu(names);
                purposeArrayList = purpose;
            }
        };

        // Run the async task
        apiDownloaderPurpose.execute();

    }

    // Sets the options for the spinner
    private void setSpinnerMenu(String []_names) {

        String names[] = (_names != null && _names.length > 0)
                ? _names
                : new String[] {"Getting Values from Database..."}; // Fill the spinner menu with the strings array or a message

        // Create the array adapter
        ArrayAdapter<CharSequence> arrayAdapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, names );
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        purpose.setAdapter(arrayAdapter);

    }

    // This function will validate the input in all of the fields and then return an ApiModelPost to send to teh database
    private ApiModelPost constructApiModelPost() {

        // First, get the id of the training purpose
        String purposeName  = purpose.getSelectedItem().toString();

        int purposeId = -1;

        // Try to match the string
        if (purposeArrayList != null && !purposeArrayList.isEmpty()) {
            for (ApiObjectPurpose purpose : purposeArrayList) {
                if (purpose.getName() == purposeName) {
                    purposeId = purpose.getId();
                    break;
                }
            }
        }

        // Throw and error if purpose never found
        if(purposeId == -1) {
            Toast.makeText(getApplicationContext(), "Invalid Purpose", Toast.LENGTH_LONG).show();
            return null;
        }


        // Get the date in format YYYY-MM-DD
        int year = date.getYear();
        int month = date.getMonth();
        int day = date.getDayOfMonth();

        // Get the month as a string
        String monthString = new Integer(month + 1).toString();
        if (month < 10) // Must make the string two digits wide
            monthString = "0" + monthString;

        // Get the day as a string
        String dayString = new Integer(day).toString();
        if (day < 10) // Must make the string two digits wide
            dayString = "0" + dayString;

        // Get date string
        String dateString = new Integer(year).toString() + '-' + monthString + '-' + dayString;


        // Get the time in format "HH:MM"
        int minute = startTime.getCurrentMinute();
        int hour = startTime.getCurrentHour();

        // Get the hour as a string
        String hourString = new Integer(hour).toString();
        if (hour < 10) // Must make the string two digits wide
            hourString = "0" + hourString;

        // Get the minute as a string
        String minuteString = new Integer(minute).toString();
        if (minute < 10) // Must make the string two digits wide
            minuteString = "0" + minuteString;

        // Get the start_time
        String startTimeString = hourString + ':' + minuteString;


        // Get the time in format "HH:MM"
        minute = endTime.getCurrentMinute();
        hour = endTime.getCurrentHour();

        // Get the hour as a string
        hourString = new Integer(hour).toString();
        if (hour < 10) // Must make the string two digits wide
            hourString = "0" + hourString;

        // Get the minute as a string
        minuteString = new Integer(minute).toString();
        if (minute < 10) // Must make the string two digits wide
            minuteString = "0" + minuteString;

        // Get the end_time
        String endTimeString = hourString + ':' + minuteString;


        // Get the preferences
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String url = sharedPreferences.getString("server_url_pref", "");
        String access_token = sharedPreferences.getString("access_token_pref", "");

        // Everything worked out, construct the object
        return new ApiModelPost(url, access_token, purposeId, dateString, startTimeString, endTimeString);
    }

    //endregion

}
