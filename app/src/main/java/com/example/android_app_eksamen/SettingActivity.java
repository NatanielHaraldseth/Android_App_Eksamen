package com.example.android_app_eksamen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SettingActivity extends AppCompatActivity {

    private DatabaseHjelper mDb;

    private static final String TAG = "SettingActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        DatabaseAccess aksess = new DatabaseAccess(this);
        aksess.open();
        Log.d(TAG, "onCreate: " + "REEEEEEEEEEEEEEEE");
        aksess.close();

        //get the spinner from the xml.
        Spinner dropdown = findViewById(R.id.årstall_spinner);
        //create a list of items for the spinner.
        String[] items = new String[]{"2015", "2016", "2017", "2018", "2019"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);
    }
}
