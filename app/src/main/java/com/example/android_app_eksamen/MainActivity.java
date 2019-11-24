package com.example.android_app_eksamen;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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
    ArrayList<KartverkData> kartverkDataArrayList;
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    //Variabler for toolbar
    private Toolbar toolbar;

    //Variabler for Volley
    private RequestQueue requestQueue;

    //Variabler for søkebar
    private EditText søkeBar;
    private String spørring = "";

    //Variabler for aktivitets "transport"
    public final static String MIN_ID = "android_app_eksamen";

    //Database aksess
    private DatabaseAccess dbAksess;

    //GPS lokasjon
    private final static int MY_REQUEST_LOCATION = 1;
    private final static int radius = 300;
    private int permissionCheck;
    private double lat;
    private double lon;
    private LocationManager locationManager;
    private String locationProvider = LocationManager.GPS_PROVIDER;
    private Location myLocation;

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
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        //Utfører metodekall som sjekker om bruker har godkjent bruk av GPS koordinater
        gpsDetaljer();

        //
        requestQueue = Volley.newRequestQueue(this);

        //
        spisestedArrayList = new ArrayList<>();
        kartverkDataArrayList = new ArrayList<>();

        //
        dbAksess = new DatabaseAccess(this);
        dbAksess.open();
        boolean tilstand = Boolean.parseBoolean(dbAksess.getBryterTilstand());
        String arstall = dbAksess.getArstall();
        dbAksess.close();

        //Sender med radius, lengde og breddegrad
        //for og kunne bruke det til og få postNr fra Kartverkets API
        postnrFraKartData(radius, lat, lon);
        fyllRecyclerView(spørring, tilstand, arstall, "");

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

    /**
     * Metode for og søke etter spisesteder
     * basert på det den henter fra søkebar
     *
     * @param view*/
    public void søke_knapp(View view) {
        spisestedArrayList.clear();
        spørring = String.valueOf(søkeBar.getText());
        fyllRecyclerView(spørring, false, "", "");
    }

    /**
     * Metode for og fylle recyclerview.
     * Filtrering blir også lagt til om bruker har det på
     * Baser på 3 "kriterier".
     * Kriterie 1: Standard lasting ved onCreate/Oppstart
     * Kriterie 2: Spørring i form av tekst fra søkebar
     * Kriterie 3: postNr uthentet fra gps lokasjon
     *
     * @param spørring
     * @param tilstand
     * @param arstall
     * @param postnr*/
    private void fyllRecyclerView(String spørring, boolean tilstand, String arstall, String postnr) {
        String url          = "https://hotell.difi.no/api/json/mattilsynet/smilefjes/tilsyn?query=" + spørring;
        String urlOnCreate  = "https://hotell.difi.no/api/json/mattilsynet/smilefjes/tilsyn?sakref=" + arstall;
        String urlPostnr    = "https://hotell.difi.no/api/json/mattilsynet/smilefjes/tilsyn?query=" + postnr;

        if (postnr != null && !postnr.equals("")) {
            StringRequest requestArstall =  new StringRequest(Request.Method.GET, urlPostnr, new Response.Listener<String>() {
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

        }else if (tilstand && !arstall.equals("Alle")) {
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

    /**
     * Metode for og hente data fra parset JSON
     *
     * @param radius
     * @param lat
     * @param lon*/
    private void postnrFraKartData(int radius, double lat, double lon) {

        String kartDataUrl = "https://ws.geonorge.no/adresser/v1/punktsok?radius=" + radius + "&lat=" + lat +"&lon=" + lon;

        StringRequest request=  new StringRequest(Request.Method.GET, kartDataUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    ArrayList<KartverkData> kartverkList = KartverkData.getKartPostnr(response);

                    for (KartverkData s: kartverkList) {
                        kartverkDataArrayList.add(s);
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
        }else if (id == R.id.action_location) {
            //Populerer recyclerviewet basert på postNr fra brukers koordinater.
            //3801 Bø gir ingen resultater.
            spisestedArrayList.clear();
            if(kartverkDataArrayList!= null && kartverkDataArrayList.size() !=0) {
                String postnr = String.valueOf(kartverkDataArrayList.get(0).getPostnr());
                fyllRecyclerView("", false, "", postnr);
            }else {
                Toast.makeText(this, "Permission not granted", Toast.LENGTH_SHORT).show();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Metode for og sende spisested objekt til ListViewActivity.
     * Posisjon blir brukt for og vite hvilket objekt som skal sendes
     *
     * @param posisjon */
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

    /**
     * Metode for og be bruker om tillatelse til og bruke
     * enhetens gps koordinater.
     *
     * @param requestCode
     * @param permissions
     * @param grantResults */
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == MY_REQUEST_LOCATION) {
            //hvis bruker avviser tillatelsen vil arrayet være tomt
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //tillatelse er gitt, ok og starte bruk av gps
            }else {
                //tillatelse ikke gitt, drep all funksjon som krever tillatelse
            }
        }
    }

    /**
     * Metode for sjekk om bruker har godkjent bruk av gps.
     * Tar i bruk godkjennelsen for og hente lengde og breddegrad*/
    public void gpsDetaljer() {
        if (!locationManager.isProviderEnabled(locationProvider)) {
            Toast.makeText(this, "Aktiver " + locationProvider + " under Location i settings", Toast.LENGTH_SHORT).show();
        }else {
            int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, MY_REQUEST_LOCATION );
            }else {
                //Appen har allerede fått tillatelse
                myLocation = locationManager.getLastKnownLocation(locationProvider);
                lat = myLocation.getLatitude();
                lon = myLocation.getLongitude();
            }
        }
    }

}/**SLUTT PÅ KLASSE MainActivity*/
