package com.swaqly.swaqly.services;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.swaqly.swaqly.ApiDataFetched.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class LoginService extends AsyncTask<String, Void, String> {
    private final WeakReference<Context> context;

    public LoginService(Context context) {
        this.context = new WeakReference<>(context);
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, String.format("%s/api/user/login", APIPlaceholder.baseUrl), response -> {
                try {
                    onResponse(response);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }, error -> Toast.makeText(context.get(), error.toString(), Toast.LENGTH_SHORT).show()) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("email", strings[0]);
                    params.put("password", strings[1]);
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
        User.setStatus(jsonObject.getString("status").equals("true"));
        User.setMassege(jsonObject.getString("msg"));
        if (User.isStatus() && User.getMassege().equals("successfully")) {
            User.setAccessToken(jsonObject.getJSONObject("user_token").getString("access_token"));
            User.setId(Integer.parseInt(((jsonObject.getJSONObject("user_token")).getJSONObject("user")).getString("id")));
            User.setName(((jsonObject.getJSONObject("user_token")).getJSONObject("user")).getString("name"));
            User.setAddress(((jsonObject.getJSONObject("user_token")).getJSONObject("user")).getString("Adress"));
            User.setEmail(((jsonObject.getJSONObject("user_token")).getJSONObject("user")).getString("email"));
            User.setEmailVerifiedAt(((jsonObject.getJSONObject("user_token")).getJSONObject("user")).getString("email_verified_at"));
        }
    }
}
