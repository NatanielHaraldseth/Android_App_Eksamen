package com.example.android_app_eksamen;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    //Variabler for RecyclerViewet
    private LinkedList<String> mOrdListe = new LinkedList<>();
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;

    //Variabler for toolbar
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //
        toolbar         = findViewById(R.id.toolbar);
        mRecyclerView   = findViewById(R.id.recycler_view);

        //
        setSupportActionBar(toolbar);

        //
        populerListe();
        mAdapter = new RecyclerViewAdapter(this, mOrdListe);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }/**SLUTT PÅ OnCreate*/

    private void populerListe() {
        for (int i = 0; i <= 20; i++) {
            mOrdListe.add("Numero: " + i);
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

}/**SLUTT PÅ KLASSE MainActivity*/
