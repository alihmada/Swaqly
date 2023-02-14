package com.swaqly.swaqly.services;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class SignUpService extends AsyncTask<String, Void, String> {

    private final WeakReference<Context> context;

    public SignUpService(Context context) {
        this.context = new WeakReference<>(context);
    }

    @Override
    protected String doInBackground(String... strings) {
//        URL url = null;
//        try {
//            url = new URL(String.format("%s/api/user/register", APIPlaceholder.baseUrl));
//        } catch (MalformedURLException ex) {
//            throw new RuntimeException(ex);
//        }
//        HttpURLConnection urlConnection = null;
//        try {
//            urlConnection = (HttpURLConnection) url.openConnection();
//        } catch (IOException ex) {
//            throw new RuntimeException(ex);
//        }
//        try {
//            urlConnection.setRequestMethod("POST");
//        } catch (ProtocolException ex) {
//            throw new RuntimeException(ex);
//        }
//
//        urlConnection.setDoOutput(true);
//
//        OutputStream outputStream = null;
//        try {
//            outputStream = urlConnection.getOutputStream();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//// write the body data to the output stream
//        String bodyData = String.format("name=%s&email=%s&phoneNumber=%s&Adress=%s&password=%s&password_confirmation=%s&api_password=ase1iXcLAxanvXLZcgh6tk",strings[0],strings[1],strings[2],strings[3],strings[4],strings[5]);
//        try {
//            outputStream.write(bodyData.getBytes());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        try {
//            outputStream.flush();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        try {
//            outputStream.close();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        try {
//            int responseCode = urlConnection.getResponseCode();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        try {
//            urlConnection.connect();
//        } catch (IOException ex) {
//            throw new RuntimeException(ex);
//        }
//
//        int responseCode = 0;
//        try {
//            responseCode = urlConnection.getResponseCode();
//        } catch (IOException ex) {
//            throw new RuntimeException(ex);
//        }
//        if (responseCode == HttpURLConnection.HTTP_OK) {
//            // Endpoint is reachable
//            Toast.makeText(context.get(), "ok", Toast.LENGTH_SHORT).show();
//        } else {
//            // Endpoint is not reachable
//        }


        try {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, String.format("%s/api/user/register", APIPlaceholder.baseUrl), response -> {
                try {
                    onResponse(response);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }, error -> Toast.makeText(context.get(), error.toString(), Toast.LENGTH_SHORT).show()) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("name", strings[0]);
                    params.put("email", strings[1]);
                    params.put("phoneNumber", strings[2]);
                    params.put("Adress", strings[3]);
                    params.put("password", strings[4]);
                    params.put("password_confirmation", strings[5]);
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
        Toast.makeText(context.get(), String.format("%s\n%s", jsonObject.getString("status"), jsonObject.getString("msg")), Toast.LENGTH_LONG).show();
    }
}
