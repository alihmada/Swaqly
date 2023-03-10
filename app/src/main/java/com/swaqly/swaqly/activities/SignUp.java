package com.swaqly.swaqly.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.swaqly.swaqly.R;
import com.swaqly.swaqly.alertDialog.AlertDialog;
import com.swaqly.swaqly.services.APIPlaceholder;
import com.swaqly.swaqly.services.Network;
import com.swaqly.swaqly.services.SignUpService;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SignUp extends AppCompatActivity {

    SignupTask signupTask;
    boolean[] inputIsCorrect;
    boolean has8Chars, hasUppercase, hasNumber, hasSymbol;

    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signupTask = new SignupTask();

        inputIsCorrect = new boolean[5];

        alertDialog = new AlertDialog(this);

        TextInputLayout nameLayout = findViewById(R.id.name_layout);
        TextInputEditText eTextName = findViewById(R.id.name_text);

        eTextName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkOnText(nameLayout, !charSequence.toString().matches("^(?:[a-zA-Zء-ي]{3,10}(?:\\s|$)){1,6}$") && charSequence.toString().length() > 0, "Please, enter a valid name", 0);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        TextInputLayout emailLayout = findViewById(R.id.email_layout);
        TextInputEditText eTextEmail = findViewById(R.id.email_text);

        eTextEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkOnText(emailLayout, !charSequence.toString().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$") && charSequence.toString().length() > 0, "please, enter a valid email address", 1);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        TextInputLayout mobileLayout = findViewById(R.id.mobile_layout);
        TextInputEditText eTextMobile = findViewById(R.id.mobile_text);

        eTextMobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkOnText(mobileLayout, !charSequence.toString().matches("^1[0125][0-9]{8}$") && charSequence.toString().length() > 0, "Please, enter a valid mobile no.", 2);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        TextInputEditText eTextAddress = findViewById(R.id.address_text);

        TextInputLayout passwordLayout = findViewById(R.id.password_layout);
        TextInputEditText eTextPassword = findViewById(R.id.password_text);

        eTextPassword.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) {
                findViewById(R.id.pass_verification).setVisibility(View.VISIBLE);
            } else {
                findViewById(R.id.pass_verification).setVisibility(View.GONE);
            }
        });

        eTextPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                has8Chars = cardViewColorChanger(findViewById(R.id.chars), charSequence.toString().length() >= 8);
                hasUppercase = cardViewColorChanger(findViewById(R.id.uppercase), charSequence.toString().matches(".*[A-Z].*"));
                hasNumber = cardViewColorChanger(findViewById(R.id.numbers), charSequence.toString().matches(".*[0-9].*"));
                hasSymbol = cardViewColorChanger(findViewById(R.id.symbols), charSequence.toString().matches(".*[~`!@#$%^&*()\\-_=+\\[\\]{}\\\\|;:'\",.<>/?].*"));

                checkOnText(passwordLayout, !((has8Chars && hasUppercase && hasNumber & hasSymbol) || charSequence.toString().length() == 0), "Invalid password", 3);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        TextInputLayout confirmPasswordLayout = findViewById(R.id.confirm_pass_layout);
        TextInputEditText eTextConfirmPassword = findViewById(R.id.confirm_pass_text);

        eTextConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                checkOnText(confirmPasswordLayout, !editable.toString().equals(Objects.requireNonNull(eTextPassword.getText()).toString()) && editable.toString().length() > 0, "Please, there is no match", 4);
            }
        });

        findViewById(R.id.login).setOnClickListener(view -> {
            Intent intent = new Intent(getBaseContext(), Login.class);
            startActivity(intent);
        });

        findViewById(R.id.sign_up).setOnClickListener(view -> {
            try {
                if (Network.isNetworkAvailable(this)) {
                    if (allIsGood()) {
                        alertDialog.progressDialogShow();
                        signupTask.execute(Objects.requireNonNull(eTextName.getText()).toString(), Objects.requireNonNull(eTextEmail.getText()).toString(), Objects.requireNonNull(eTextMobile.getText()).toString(), Objects.requireNonNull(eTextAddress.getText()).toString(), Objects.requireNonNull(eTextPassword.getText()).toString(), Objects.requireNonNull(eTextConfirmPassword.getText()).toString());
                    } else {
                        AlertDialog.createDialog(this, getString(R.string.check_your_inputs_title), getString(R.string.check_your_inputs_msg));
                    }
                } else {
                    AlertDialog.showErrorDialog(this, false);
                }
            } catch (Exception exception) {
                Toast.makeText(this, "DB Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean cardViewColorChanger(CardView cardView, boolean condition) {
        if (condition) {
            cardView.setCardBackgroundColor(getColor(R.color.green));
            return true;
        } else {
            cardView.setCardBackgroundColor(getColor(R.color.platinum));
            return false;
        }
    }

    private void checkOnText(TextInputLayout textInputLayout, boolean condition, String message, int index) {
        if (condition) {
            textInputLayout.setError(message);
            inputIsCorrect[index] = false;
        } else {
            textInputLayout.setErrorEnabled(false);
            inputIsCorrect[index] = true;
        }
    }

    private boolean allIsGood() {
        boolean isGoode = true;
        for (boolean b : inputIsCorrect) {
            isGoode = isGoode & b;
        }
        return isGoode;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        signupTask.cancel(true);
    }

    private class SignupTask extends AsyncTask<String, Void, String> {

        String response;

        @Override
        protected String doInBackground(String... strings) {
            try {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, SignUpService.getSignupUrl(), response -> SignupTask.this.response = response, error -> {
                    alertDialog.progressDialogDismiss();
                    AlertDialog.showErrorDialog(SignUp.this, Network.isNetworkAvailable(SignUp.this));
                }) {
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
                RequestQueue requestQueue = Volley.newRequestQueue(SignUp.this);
                requestQueue.add(stringRequest);

            } catch (Exception e) {
                alertDialog.progressDialogDismiss();
                throw new RuntimeException(e);
            }

            return SignupTask.this.response;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            alertDialog.progressDialogShow();
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            alertDialog.progressDialogDismiss();

            try {
                if (response != null) {
                    SignUpService.onResponse(response);
                    Intent home = new Intent(SignUp.this, Home.class);
                    startActivity(home);
                    finish();
                } else {
                    AlertDialog.createDialog(SignUp.this, "Email address is already exist", "Please enter another email address.");
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }

}