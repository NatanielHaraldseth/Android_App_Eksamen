package com.example.android_app_eksamen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

class ListViewAdapter extends ArrayAdapter {

    //Klasse variabler
    private Context context;
    private int ressurs;
    private ArrayList<Kravpunkt> kravpunktArrayList;

    /**
     * Konstruktør metode for ListViewAdapter.
     * Som et "Custom" adapter så trenger den og vite hvor den skal være.
     * hvordan utseendet til det den skal bygge er, og datasett som skal brukes.
     *
     * @param context
     * @param ressurs
     * @param kravpunktArrayList*/
    public ListViewAdapter(Context context, int ressurs, ArrayList<Kravpunkt> kravpunktArrayList) {
        super(context, ressurs, kravpunktArrayList);
        this.context            = context;
        this.ressurs            = ressurs;
        this.kravpunktArrayList = kravpunktArrayList;
    }

    /**
     * Metode som tar i bruk informasjon innhentet av konstruktør metoden
     *
     * @param posisjon
     * @param convertView
     * @param foreldre
     *
     * @return convertView*/
    @Override
    public View getView(int posisjon, View convertView, ViewGroup foreldre) {
        String dato             = kravpunktArrayList.get(posisjon).getDato();
        int karakter            = kravpunktArrayList.get(posisjon).getKarakter();
        String tekst_no         = kravpunktArrayList.get(posisjon).getTekst_no();
        double ordningsverdi    = kravpunktArrayList.get(posisjon).getOrdningsverdi();
        String kravpunktnavn_no = kravpunktArrayList.get(posisjon).getKravpunktnavn_no();

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(ressurs, foreldre, false);

        TextView datoTV          = (TextView)  convertView.findViewById(R.id.list_view_dato);
        TextView tekstNoTV       = (TextView)  convertView.findViewById(R.id.list_view_tekst_no);
        TextView karakterTV      = (TextView)  convertView.findViewById(R.id.list_view_total_karakter);
        TextView ordningsVerdiTV = (TextView)  convertView.findViewById(R.id.list_view_ordnings_verdi);
        TextView kravpunktNavnTV = (TextView)  convertView.findViewById(R.id.list_view_kravpunkt_navn);

        datoTV.setText(dato);
        tekstNoTV.setText(tekst_no);
        karakterTV.setText("" + karakter);
        kravpunktNavnTV.setText(kravpunktnavn_no);
        ordningsVerdiTV.setText("" + ordningsverdi);
        return convertView;
    }
}
