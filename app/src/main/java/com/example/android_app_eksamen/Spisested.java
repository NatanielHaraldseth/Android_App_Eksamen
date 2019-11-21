package com.example.android_app_eksamen;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class Spisested implements Serializable {
    //Klasse variabler
    private int orgnummer, postnr, total_karakter;
    private String navn, adrlinje1, poststed, tilsynid;

    //Json spesifikke variabler
    private static final String KOL_NAVN            = "navn";
    private static final String KOL_ADR_LINJE       = "adrlinje1";
    private static final String KOL_POST_STED       = "poststed";
    private static final String KOL_ORG_NUMMER      = "orgnummer";
    private static final String KOL_POST_NR         = "postnr";
    private static final String KOL_TOTAL_KARAKTER  = "total_karakter";
    private static final String KOL_TILSYN_ID       = "tilsynid";

    //Json tabell
    private static final String TABLE_NAME          = "entries";

    public Spisested(int orgnummer, int postnr, int total_karakter, String navn, String adrlinje1, String poststed, String tilsynid) {
        this.orgnummer          = orgnummer;
        this.postnr             = postnr;
        this.total_karakter     = total_karakter;
        this.navn               = navn;
        this.adrlinje1          = adrlinje1;
        this.poststed           = poststed;
        this.tilsynid           = tilsynid;
    }

    //jsonST = jsonSpiseSted.
    public Spisested(JSONObject jsonST) {
        this.orgnummer          = jsonST.optInt(KOL_ORG_NUMMER);
        this.postnr             = jsonST.optInt(KOL_POST_NR);
        this.total_karakter     = jsonST.optInt(KOL_TOTAL_KARAKTER);
        this.navn               = jsonST.optString(KOL_NAVN);
        this.adrlinje1          = jsonST.optString(KOL_ADR_LINJE);
        this.poststed           = jsonST.optString(KOL_POST_STED);
        this.tilsynid           = jsonST.optString(KOL_TILSYN_ID);
    }

    public Spisested() {}

    public static ArrayList<Spisested> leggTilSpisestedListe(String jsonSpisested) throws JSONException, NullPointerException{
        ArrayList<Spisested> spisestedList = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(jsonSpisested);

        JSONArray spisestedTabell = jsonObject.optJSONArray(TABLE_NAME);

        for(int i = 0; i < spisestedTabell.length(); i++) {
            JSONObject spisestedObject = (JSONObject) spisestedTabell.get(i);
            Spisested spisested = new Spisested(spisestedObject);
            spisestedList.add(spisested);
        }

        return spisestedList;

    }

    public int getOrgnummer() {
        return orgnummer;
    }

    public int getPostnr() {
        return postnr;
    }

    public int getTotal_karakter() {
        return total_karakter;
    }

    public String getNavn() {
        return navn;
    }

    public String getAdrlinje1() {
        return adrlinje1;
    }

    public String getPoststed() {
        return poststed;
    }

    public String getTilsynid() {
        return tilsynid;
    }

    @Override
    public String toString() {
        return  orgnummer         + ' ' +
                postnr            + ' ' +
                total_karakter    + ' ' +
                navn              + ' ' +
                adrlinje1         + ' ' +
                poststed          + ' ' +
                tilsynid;
    }
}
