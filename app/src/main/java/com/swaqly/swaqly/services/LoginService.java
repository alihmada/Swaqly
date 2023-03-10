package com.swaqly.swaqly.services;

import com.swaqly.swaqly.ApiDataFetched.User;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginService {

    private static final String loginUrl = String.format("%s/api/user/login", APIPlaceholder.baseUrl);

    public static String getLoginUrl() {
        return loginUrl;
    }

    public static void onResponse(String response) throws JSONException {

        JSONObject jsonObject = new JSONObject(response);

        User.setStatus(jsonObject.getString("status").equals("true"));
        User.setMassege(jsonObject.getString("msg"));

        //login validation
        if (User.isStatus() && User.getMassege().contains("successfully")) {

            //token data
            JSONObject userToken = jsonObject.getJSONObject("user_token");
            User.setAccessToken(userToken.getString("access_token"));

            //user data
            JSONObject user = userToken.getJSONObject("user");
            User.setId(Integer.parseInt(user.getString("id")));
            User.setName(user.getString("name"));
            User.setAddress(user.getString("Adress"));
            User.setEmail(user.getString("email"));
            User.setEmailVerifiedAt(user.getString("email_verified_at"));
        }
    }
}
