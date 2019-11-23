package com.example.android_app_eksamen;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

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

    /**
     * Konstruktør metode for innhenting av JSONObjekter
     * Sender riktig json data til riktig klasse variabel
     *
     * @param jsonKP*/
    public Kravpunkt(JSONObject jsonKP) {
        this.dato               = jsonKP.optString(KOL_DATO);
        this.tilsynid           = jsonKP.optString(KOL_TILSYN_ID);
        this.tekst_no           = jsonKP.optString(KOL_TEKST_NO);
        this.karakter           = jsonKP.optInt(KOL_KARAKTER);
        this.ordningsverdi      = jsonKP.optDouble(KOL_ORDNINGS_VERDI);
        this.kravpunktnavn_no   = jsonKP.optString(KOL_KRAV_PUNKT_NAVN_NO);
    }

    /**
     * Metode for parsing av JSON data
     *
     * @param jsonKravpunkt
     *
     * @throws JSONException
     * @throws NullPointerException
     *
     * @return kravpunktList*/
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

    /**
     * Get metode for og hente karakter
     *
     * @return karakter*/
    public int getKarakter() {
        return karakter;
    }

    /**
     * Get metode for og hente ordningsverdi
     *
     * @return ordningsverdi*/
    public double getOrdningsverdi() {
        return ordningsverdi;
    }

    /**
     * Get metode for og hente dato
     *
     * @return dato*/
    public String getDato() {
        return dato;
    }

    /**
     * Get metode for og hente teskst_no
     *
     * @return teskst_no*/
    public String getTekst_no() {
        return tekst_no;
    }

    /**
     * Get metode for og hente kravpunktnavn_no
     *
     * @return kravpunktnavn_no*/
    public String getKravpunktnavn_no() {
        return kravpunktnavn_no;
    }

    @Override
    public String toString() {
        return  karakter            + " " +
                ordningsverdi       + " " +
                dato                + " " +
                tilsynid            + " " +
                tekst_no            + " " +
                kravpunktnavn_no;
    }
}/**SLUTT PÅ Kravpunkt KLASSEN*/
