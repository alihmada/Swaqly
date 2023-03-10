package com.swaqly.swaqly.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.swaqly.swaqly.R;
import com.swaqly.swaqly.alertDialog.AlertDialog;
import com.swaqly.swaqly.services.APIPlaceholder;
import com.swaqly.swaqly.services.LoginService;
import com.swaqly.swaqly.services.Network;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Login extends AppCompatActivity {

    LoginTask loginTask;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginTask = new LoginTask();
        progressDialog = new ProgressDialog(this);

        TextInputLayout emailLayout = findViewById(R.id.email_layout);
        TextInputEditText eTextEmail = findViewById(R.id.email_text);

        TextInputEditText eTextPassword = findViewById(R.id.password_text);

        eTextEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$") && charSequence.toString().length() > 0) {
                    emailLayout.setError(getString(R.string.email_address_error));
                } else {
                    emailLayout.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        findViewById(R.id.sign_up).setOnClickListener(view -> {
            Intent intent = new Intent(getBaseContext(), SignUp.class);
            startActivity(intent);
        });

        findViewById(R.id.forget_password).setOnClickListener(view -> {
            Intent intent = new Intent(getBaseContext(), ForgetPassword.class);
            startActivity(intent);
        });

        findViewById(R.id.login).setOnClickListener(view -> LoginService(Objects.requireNonNull(eTextEmail.getText()).toString(), Objects.requireNonNull(eTextPassword.getText()).toString()));
    }

    private void LoginService(String email, String password) {
        try {
            if (Network.isNetworkAvailable(this)) {
                progressDialogShow();
                loginTask.execute(email, password);
            } else {
                AlertDialog.showErrorDialog(this, false);
            }
        } catch (Exception exception) {
            Toast.makeText(this, "DB Error", Toast.LENGTH_SHORT).show();
            progressDialogDismiss();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        loginTask.cancel(true);
    }

    private class LoginTask extends AsyncTask<String, Void, String> {

        String response = null;

        @Override
        protected String doInBackground(String... strings) {
            try {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, LoginService.getLoginUrl(), response -> LoginTask.this.response = response, error -> {
                    progressDialogDismiss();
                    AlertDialog.showErrorDialog(Login.this, Network.isNetworkAvailable(Login.this));
                }) {
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
                RequestQueue requestQueue = Volley.newRequestQueue(Login.this);
                requestQueue.add(stringRequest);

            } catch (Exception e) {
                progressDialogDismiss();
                throw new RuntimeException(e);
            }
            return response;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialogShow();
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            progressDialogDismiss();
            try {
                if (response != null) {
                    LoginService.onResponse(response);
                    Intent home = new Intent(Login.this, Home.class);
                    startActivity(home);
                    finish();
                } else {
                    progressDialogDismiss();
                    AlertDialog.createDialog(Login.this, "Invalid email or password", "Please check your inputs.");
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void progressDialogShow() {
        //Initialize Progress Dialog
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }

    public void progressDialogDismiss() {
        progressDialog.dismiss();
    }
}