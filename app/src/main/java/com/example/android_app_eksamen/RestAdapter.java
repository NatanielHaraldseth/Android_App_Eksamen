package com.example.android_app_eksamen;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class RestAdapter {

    private Context context;
    private RequestQueue requestQueue;

    public RestAdapter(Context context) {
        this.context = context;
        requestQueue = Volley.newRequestQueue(context);
    }

    private void addJSONRequest(int requestMethod, String url, JSONObject data) {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(requestMethod, url, data, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Toast.makeText(context, response.toString(), Toast.LENGTH_LONG).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            });
            requestQueue.add(jsonObjectRequest);
    }
}
