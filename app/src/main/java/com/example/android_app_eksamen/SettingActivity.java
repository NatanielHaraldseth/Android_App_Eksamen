package com.example.android_app_eksamen;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SettingActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private static final String PREFS_NAME = "key_enable_arstall";
    private TextView favoritt_sted_input_postnr, favoritt_sted_input_poststed;
    private Spinner arstall_spinner;
    private Switch bryter;

    private ArrayAdapter<String> adapter;

    private String arstall;

    private DatabaseAccess aksess;

    private static final String TAG = "SettingActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        //
        arstall_spinner                 = findViewById(R.id.årstall_spinner);
        bryter                          = findViewById(R.id.switch_årstall_lagring);
        favoritt_sted_input_postnr      = findViewById(R.id.favoritt_sted_input_postnr);
        favoritt_sted_input_poststed    = findViewById(R.id.favoritt_sted_input_poststed);
        //
        aksess = new DatabaseAccess(this);
        //
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        boolean silent = settings.getBoolean("switchkey", false);
        bryter.setChecked(silent);
        //
        aksess.open();
        arstall = aksess.getArstall();
        aksess.close();
        //
        String[] arstallArray = new String[]{"2015", "2016", "2017", "2018", "2019", "Alle"};
        //
        int arstallIndex = getArstallIndex(arstall, arstallArray);
        Log.d(TAG, "onCreate: " + getArstallIndex(arstall, arstallArray));
        //
        arstall_spinner.setSelection(arstallIndex);
        if (bryter != null) {
            bryter.setOnCheckedChangeListener(this);
        }
        //
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, arstallArray);
        //
        arstall_spinner.setAdapter(adapter);
    }

    public int getArstallIndex(String arstall, String[] arstallArray) {

        for (int i = 0; i < arstallArray.length; i++) {
            String arstallSammenligner = arstallArray[i];
            if (arstallSammenligner.equals(arstall)) return i;
        }
        return 0;
    }

    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Toast.makeText(this, "The Switch is " + (isChecked ? "on" : "off"), Toast.LENGTH_SHORT).show();
        if(isChecked) {
            String arstall = arstall_spinner.getSelectedItem().toString();
            aksess.open();
            aksess.settInnArstall(arstall);
            aksess.close();
            Log.d(TAG, "onCheckedChanged: " + arstall);
        }
        aksess.open();
        aksess.settInnBryterTilstand(String.valueOf(isChecked));
        aksess.close();
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("switchkey", isChecked);
        editor.apply();
    }

    public void favorittsted_lagring_poststed(View view) {
        String poststed = String.valueOf(favoritt_sted_input_poststed.getText());
        aksess.open();
        aksess.settInnPoststed(poststed);
        favoritt_sted_input_poststed.setText("");
        aksess.close();
        Toast.makeText(this, "SATT INN SUKSESSFULLT", Toast.LENGTH_LONG).show();
    }

    public void favorittsted_lagring_postnr(View view) {
        String postnr = String.valueOf(favoritt_sted_input_postnr.getText());
        aksess.open();
        aksess.settInnPostnr(postnr);
        favoritt_sted_input_postnr.setText("");
        aksess.close();
        Toast.makeText(this, "SATT INN SUKSESSFULLT", Toast.LENGTH_LONG).show();
    }
}
