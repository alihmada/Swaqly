package com.swaqly.swaqly.services;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.swaqly.swaqly.ApiDataFetched.Products;
import com.swaqly.swaqly.ApiDataFetched.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class ProductService extends AsyncTask<String, Void, String> {

    private final WeakReference<Context> context;

    public ProductService(WeakReference<Context> context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, String.format("%s/api/user/product", APIPlaceholder.baseUrl), response -> {
                try {
                    onResponse(response);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }, error -> Toast.makeText(context.get(), error.toString(), Toast.LENGTH_SHORT).show()) {

                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("auth-token", User.getAccessToken());
                    return headers;
                }

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("api_password", APIPlaceholder.ApiPassword);
                    return params;
                }
            };

            // Add the request to the RequestQueue
            RequestQueue requestQueue = Volley.newRequestQueue(context.get());
            requestQueue.add(stringRequest);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private void onResponse(String response) throws JSONException {
        JSONObject jsonObject = new JSONObject(response);
        Products.setStatus(jsonObject.getString("status").equals("true"));
        Products.setMassege(jsonObject.getString("msg"));
        if (Products.isStatus() && Products.getMassege().equals("success")) {
            JSONArray jsonArray = jsonObject.getJSONArray("products");
            String[] strings = new String[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++) {
                strings[i] = jsonArray.getString(i);
            }
            Products.setProducts(strings);
        }
    }

}
