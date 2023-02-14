package com.swaqly.swaqly.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.swaqly.swaqly.R;
import com.swaqly.swaqly.services.SignUpService;

import java.util.Objects;

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
                if (!charSequence.toString().matches("^(?:[a-zA-Zء-ي]{3,10}(?:\\s|$)){1,6}$") && charSequence.toString().length() > 0) {
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
                } else {
                    mobileLayout.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        TextInputEditText eTextAddress = findViewById(R.id.address_text);

        TextInputEditText eTextPassword = findViewById(R.id.password_text);

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
                if (!editable.toString().equals(Objects.requireNonNull(eTextPassword.getText()).toString()) && editable.toString().length() > 0) {
                    confirmPasswordLayout.setError("Please, there is no match");
                } else {
                    confirmPasswordLayout.setErrorEnabled(false);
                }
            }
        });

        findViewById(R.id.login).setOnClickListener(view -> {
            Intent intent = new Intent(getBaseContext(), Login.class);
            startActivity(intent);
        });

        findViewById(R.id.sign_up).setOnClickListener(view -> {
            SignUpService signUpService = new SignUpService(this);
            signUpService.execute
            (
                Objects.requireNonNull(eTextName.getText()).toString(),
                Objects.requireNonNull(eTextEmail.getText()).toString(),
                Objects.requireNonNull(eTextMobile.getText()).toString(),
                Objects.requireNonNull(eTextAddress.getText()).toString(),
                Objects.requireNonNull(eTextPassword.getText()).toString(),
                Objects.requireNonNull(eTextConfirmPassword.getText()).toString()
            );
        });
    }

}