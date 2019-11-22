package com.example.android_app_eksamen;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.OnNoteListener {

    //Variabler for RecyclerViewet
    ArrayList<Spisested> spisestedArrayList;
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    //Variabler for toolbar
    private Toolbar toolbar;

    //Variabler for Volley
    private RequestQueue requestQueue;
    private RestAdapter restAdapter;

    //Variabler for søkebar
    private EditText søkeBar;
    private String spørringNavn = "";
    private String spørringPostSted = "";

    //Variabler for aktivitets "transport"
    public final static String MIN_ID = "android_app_eksamen";

    //Logging
    private static final String TAG = "MainActivity";

    //Database aksess
    private DatabaseAccess dbAksess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //
        toolbar         = findViewById(R.id.toolbar);
        mRecyclerView   = findViewById(R.id.recycler_view);
        søkeBar         = findViewById(R.id.spise_sted_søkebar);

        //
        setSupportActionBar(toolbar);

        //
        restAdapter = new RestAdapter(this);
        requestQueue = Volley.newRequestQueue(this);

        //
        spisestedArrayList = new ArrayList<>();

        //
        dbAksess = new DatabaseAccess(this);
        dbAksess.open();
        boolean tilstand = Boolean.parseBoolean(dbAksess.getBryterTilstand());
        String arstall = dbAksess.getArstall();
        Log.d(TAG, "onCreate: " + tilstand + " " + arstall);
        dbAksess.close();

        //
        fyllRecyclerView(spørringNavn, spørringPostSted, tilstand, arstall);

        //
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new RecyclerViewAdapter(spisestedArrayList, this);

        //
        mRecyclerView.setLayoutManager(mLayoutManager);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeForÅSlette(mAdapter));
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);

    }/**SLUTT PÅ OnCreate*/

    public void søke_knapp(View view) {
        spisestedArrayList.clear();
        spørringNavn = String.valueOf(søkeBar.getText());
        spørringPostSted = String.valueOf(søkeBar.getText());
        fyllRecyclerView(spørringNavn, spørringPostSted, false, "");
    }

    private void fyllRecyclerView(String spørringNavn, String spørringPostSted, boolean tilstand, String arstall) {
        String url          = "https://hotell.difi.no/api/json/mattilsynet/smilefjes/tilsyn?navn=" + spørringNavn + "&poststed=" + spørringPostSted;
        String urlOnCreate  = "https://hotell.difi.no/api/json/mattilsynet/smilefjes/tilsyn?sakref=" + arstall;

        if (tilstand && !arstall.equals("Alle")) {
            StringRequest requestArstall =  new StringRequest(Request.Method.GET, urlOnCreate, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        ArrayList<Spisested> spisestedList = Spisested.leggTilSpisestedListe(response);

                        for (Spisested s: spisestedList) {
                            spisestedArrayList.add(s);
                        }
                        mAdapter.notifyDataSetChanged();

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

            MySingleton.getInstance(this).addToRequestQueue(requestArstall);

        }else {
            StringRequest request =  new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        ArrayList<Spisested> spisestedList = Spisested.leggTilSpisestedListe(response);

                        for (Spisested s: spisestedList) {
                            spisestedArrayList.add(s);
                        }
                        mAdapter.notifyDataSetChanged();

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent startIntent = new Intent(MainActivity.this, SettingActivity.class);
            startActivity(startIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onNoteClick(int posisjon) {
        Intent intent = new Intent(this, ListViewActivity.class);
        Spisested spisested = new Spisested(spisestedArrayList.get(posisjon).getOrgnummer(),
                                            spisestedArrayList.get(posisjon).getPostnr(),
                                            spisestedArrayList.get(posisjon).getTotal_karakter(),
                                            spisestedArrayList.get(posisjon).getNavn(),
                                            spisestedArrayList.get(posisjon).getAdrlinje1(),
                                            spisestedArrayList.get(posisjon).getPoststed(),
                                            spisestedArrayList.get(posisjon).getTilsynid());
        intent.putExtra(MIN_ID, spisested);
        startActivity(intent);
    }
}/**SLUTT PÅ KLASSE MainActivity*/
