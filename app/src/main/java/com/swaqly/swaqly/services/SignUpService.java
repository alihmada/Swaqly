package com.swaqly.swaqly.services;

import com.swaqly.swaqly.ApiDataFetched.User;

import org.json.JSONException;
import org.json.JSONObject;

public class SignUpService {

    private static final String signupUrl = String.format("%s/api/user/register", APIPlaceholder.baseUrl);

    public static String getSignupUrl() {
        return signupUrl;
    }

    public static void onResponse(String response) throws JSONException {
        JSONObject jsonObject = new JSONObject(response);
        User.setStatus(jsonObject.getString("status").equals("true"));
        User.setMassege(jsonObject.getString("msg"));
        if (User.isStatus() && User.getMassege().contains("successfully")) {

            JSONObject user = jsonObject.getJSONObject("user");
            User.setId(Integer.parseInt(user.getString("id")));
            User.setName(user.getString("name"));
            User.setEmail(user.getString("email"));
            User.setAddress(user.getString("Adress"));
        }
    }
}
