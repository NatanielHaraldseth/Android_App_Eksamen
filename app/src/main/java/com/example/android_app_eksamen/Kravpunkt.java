package com.example.android_app_eksamen;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Kravpunkt implements Serializable {

    //Klasse variabler
    private int karakter;
    private double ordningsverdi;
    private String dato, tilsynid, tekst_no, kravpunktnavn_no;

    //Json spesifikke variabler
    private static final String KOL_KARAKTER            = "karakter";
    private static final String KOL_DATO                = "dato";
    private static final String KOL_TILSYN_ID           = "tilsynid";
    private static final String KOL_TEKST_NO            = "tekst_no";
    private static final String KOL_KRAV_PUNKT_NAVN_NO  = "kravpunktnavn_no";
    private static final String KOL_ORDNINGS_VERDI      = "ordningsverdi";

    //Tabell navn
    private static final String TABLE_NAME              = "entries";

    //Hmmmmm, kan funke
    private Date date;

    public Kravpunkt(int karakter, String dato, String tilsynid, String tekst_no, String kravpunktnavn_no, double ordningsverdi) {
        this.dato               = dato;
        this.tilsynid           = tilsynid;
        this.tekst_no           = tekst_no;
        this.karakter           = karakter;
        this.ordningsverdi      = ordningsverdi;
        this.kravpunktnavn_no   = kravpunktnavn_no;
    }

    public Kravpunkt(JSONObject jsonKP) {
        this.dato               = jsonKP.optString(KOL_DATO);
        this.tilsynid           = jsonKP.optString(KOL_TILSYN_ID);
        this.tekst_no           = jsonKP.optString(KOL_TEKST_NO);
        this.karakter           = jsonKP.optInt(KOL_KARAKTER);
        this.ordningsverdi      = jsonKP.optDouble(KOL_ORDNINGS_VERDI);
        this.kravpunktnavn_no   = jsonKP.optString(KOL_KRAV_PUNKT_NAVN_NO);
    }

    public static ArrayList<Kravpunkt> leggTilKravpunktListe(String jsonKravpunkt) throws JSONException, NullPointerException{
        ArrayList<Kravpunkt> kravpunktList= new ArrayList<>();

        JSONObject jsonObject = new JSONObject(jsonKravpunkt);

        JSONArray kravpunktTabell = jsonObject.optJSONArray(TABLE_NAME);

        for(int i = 0; i < kravpunktTabell.length(); i++) {
            JSONObject spisestedObject = (JSONObject) kravpunktTabell.get(i);
            Kravpunkt kravpunkt = new Kravpunkt(spisestedObject);
            kravpunktList.add(kravpunkt);
        }

        return kravpunktList;

    }

    public int getKarakter() {
        return karakter;
    }

    public double getOrdningsverdi() {
        return ordningsverdi;
    }

    public String getDato() {
        return dato;
    }

    public String getTilsynid() {
        return tilsynid;
    }

    public String getTekst_no() {
        return tekst_no;
    }

    public String getKravpunktnavn_no() {
        return kravpunktnavn_no;
    }

    @Override
    public String toString() {
        return  karakter            + ' ' +
                ordningsverdi       + ' ' +
                dato                + ' ' +
                tilsynid            + ' ' +
                tekst_no            + ' ' +
                kravpunktnavn_no    + ' ' +
                date;
    }
}/**SLUTT PÅ Kravpunkt KLASSEN*/
