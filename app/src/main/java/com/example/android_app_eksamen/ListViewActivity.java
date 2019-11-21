package com.example.android_app_eksamen;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
    private ArrayList<Spisested> spisestedArrayList;
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
        Intent intent = getIntent();
        String tilsynId = intent.getStringExtra(MainActivity.MIN_ID);
        Log.d(TAG, "onCreate: " + tilsynId);
        //
        kravpunktArrayList = new ArrayList<>();
        fyllListViewKravpunkt(tilsynId);
        //
        listViewAdapter = new ListViewAdapter(this, R.layout.listview_item_layout, kravpunktArrayList);
        list.setAdapter(listViewAdapter);
    }

    private void fyllListViewSpisested(String tilsynId) {
        String url = "https://hotell.difi.no/api/json/mattilsynet/smilefjes/tilsyn?tilsynid=" + tilsynId;

        StringRequest request =  new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    ArrayList<Spisested> spisestedList = Spisested.leggTilSpisestedListe(response);

                    for (Spisested s: spisestedList) {
                        spisestedArrayList.add(s);
                    }

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
