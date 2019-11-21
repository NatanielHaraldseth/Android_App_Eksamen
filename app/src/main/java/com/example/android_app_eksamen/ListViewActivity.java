package com.example.android_app_eksamen;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;

import java.util.ArrayList;
public class ListViewActivity extends AppCompatActivity {

    //ListView variabler
    private ListView list;
    private ArrayList<Kravpunkt> kravpunktArrayList;
    private ListViewAdapter listViewAdapter;

    //Logging
    private static final String TAG = "ListViewActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        //
        list = findViewById(R.id.list);
        //
        TextView navnTV     = findViewById(R.id.textViewNavn);
        TextView adresseTV  = findViewById(R.id.textViewAdresse);
        TextView poststedTV = findViewById(R.id.textViewPoststed);
        //
        Intent intent = getIntent();
        Spisested spisested = (Spisested) intent.getSerializableExtra(MainActivity.MIN_ID);
        String tilsynId = spisested.getTilsynid();
        Log.d(TAG, "onCreate: " + tilsynId);
        //
        navnTV.setText(spisested.getNavn());
        adresseTV.setText(spisested.getAdrlinje1());
        poststedTV.setText(spisested.getPoststed());
        //
        kravpunktArrayList = new ArrayList<>();
        //
        fyllListViewKravpunkt(tilsynId);
        //
        listViewAdapter = new ListViewAdapter(this, R.layout.listview_item_layout, kravpunktArrayList);
        list.setAdapter(listViewAdapter);
    }

    private void fyllListViewKravpunkt(String tilsynId) {
        String url = "https://hotell.difi.no/api/json/mattilsynet/smilefjes/kravpunkter?tilsynid=" + tilsynId;

        StringRequest request =  new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    ArrayList<Kravpunkt> kravpunktList = Kravpunkt.leggTilKravpunktListe(response);

                    for (Kravpunkt k: kravpunktList) {
                        kravpunktArrayList.add(k);
                    }
                    listViewAdapter.notifyDataSetChanged();

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        MySingleton.getInstance(this).addToRequestQueue(request);
    }
}
