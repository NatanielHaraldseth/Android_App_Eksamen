package com.example.android_app_eksamen;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class KartverkData {

    //Klasse Variabler
    private int postnr;

    //JSON spesifikke variabler
    private static final String KOL_POST_NR = "postnummer";

    //JSON tabell
    private static final String TABLE_NAME  = "adresser";

    public KartverkData(int postnr) {
        this.postnr = postnr;
    }

    public KartverkData(JSONObject jsonKart) {
        this.postnr = jsonKart.optInt(KOL_POST_NR);
    }

    public static ArrayList<KartverkData> getKartPostnr(String jsonKart) throws JSONException, NullPointerException{
        ArrayList<KartverkData> kartverkList = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(jsonKart);

        JSONArray kartTabell = jsonObject.optJSONArray(TABLE_NAME);

        for(int i = 0; i < kartTabell.length(); i++) {
            JSONObject kartObject = (JSONObject) kartTabell.get(i);
            KartverkData kartData = new KartverkData(kartObject);
            kartverkList.add(kartData);
        }
        return kartverkList;
    }

    public int getPostnr() {
        return postnr;
    }
}
