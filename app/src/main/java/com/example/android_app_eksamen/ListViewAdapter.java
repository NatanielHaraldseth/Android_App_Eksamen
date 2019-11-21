package com.example.android_app_eksamen;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

class ListViewAdapter extends ArrayAdapter {

    private Context context;
    private int resource;
    private ArrayList<Kravpunkt> kravpunktArrayList;

    //Logging
    private static final String TAG = "ListViewAdapter";

    public ListViewAdapter(Context context, int resource, ArrayList<Kravpunkt> kravpunktArrayList) {
        super(context, resource, kravpunktArrayList);
        this.context            = context;
        this.resource           = resource;
        this.kravpunktArrayList = kravpunktArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String dato             = kravpunktArrayList.get(position).getDato();
        int karakter            = kravpunktArrayList.get(position).getKarakter();
        String tekst_no         = kravpunktArrayList.get(position).getTekst_no();
        String tilsynid         = kravpunktArrayList.get(position).getTilsynid();
        double ordningsverdi    = kravpunktArrayList.get(position).getOrdningsverdi();
        String kravpunktnavn_no = kravpunktArrayList.get(position).getKravpunktnavn_no();

        Log.d(TAG, "Dato: " + dato);
        Log.d(TAG, "Karakter: " + karakter);
        Log.d(TAG, "Tekst: " + tekst_no);
        Log.d(TAG, "Tilsynid: " + tilsynid);
        Log.d(TAG, "Ordningsverdi: " + ordningsverdi);
        Log.d(TAG, "Kravpunktnavn: " + kravpunktnavn_no);

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(resource, parent, false);

        TextView datoTV          = (TextView) convertView.findViewById(R.id.textListView1);
        TextView tekstNoTV       = (TextView) convertView.findViewById(R.id.textListView3);
        TextView tilsynIdTV      = (TextView) convertView.findViewById(R.id.textListView4);
        TextView karakterTV      = (TextView) convertView.findViewById(R.id.textListView2);
        TextView ordningsVerdiTV = (TextView) convertView.findViewById(R.id.textListView5);
        TextView kravpunktNavnTV = (TextView) convertView.findViewById(R.id.textListView6);

        datoTV.setText(dato);
        tekstNoTV.setText(tekst_no);
        tilsynIdTV.setText(tilsynid);
        karakterTV.setText("" + karakter);
        kravpunktNavnTV.setText(kravpunktnavn_no);
        ordningsVerdiTV.setText("" + ordningsverdi);

        return convertView;
    }
}
