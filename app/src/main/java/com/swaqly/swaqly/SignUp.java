package com.swaqly.swaqly;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        TextInputLayout nameLayout = findViewById(R.id.name_layout);
        TextInputEditText eTextName = findViewById(R.id.name_text);

        eTextName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

                @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().matches("^(?:(?:[a-zA-Zء-ي]{3,10})(?:\\s|$)){1,6}$") && charSequence.toString().length() > 0) {
                    nameLayout.setError("Please, enter a valid name");
                } else {
                    nameLayout.setErrorEnabled(false);
                }
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
                if (!charSequence.toString().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$") && charSequence.toString().length() > 0) {
                    emailLayout.setError("please, enter a valid email address");
                } else {
                    emailLayout.setErrorEnabled(false);
                }
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
                if (!charSequence.toString().matches("^1[0125][0-9]{8}$") && charSequence.toString().length() > 0) {
                    mobileLayout.setError("Please, enter a valid mobile no.");
                }else{
                    mobileLayout.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        findViewById(R.id.login).setOnClickListener(view -> {
            Intent intent = new Intent(getBaseContext(), Login.class);
            startActivity(intent);
        });
    }
}