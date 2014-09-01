package com.hackbitstudios.taekwondo_time_tracker_android;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.hackbitstudios.taekwondo_time_tracker_android.R;

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
        // DO STUFF
    }

    //endregion

    //region OVERRIDDEN METHODS
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_punchcard);
        ButterKnife.inject(this); // So ButterKnife works

        // Main Code
        fillSpinnerMenu();

    }


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.punchcard, menu);
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
    }*/
    //endregion

    //region PRIVATE METHODS

    // Gets the options for the spinner from db
    private void fillSpinnerMenu() {

        String names[] = new String[] {"A", "B", "C"};

        // Create the array adapter
        ArrayAdapter<CharSequence> arrayAdapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, names );
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        purpose.setAdapter(arrayAdapter);

    }

    //endregion

}
