package com.swaqly.swaqly.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.swaqly.swaqly.R;
import com.swaqly.swaqly.services.LoginService;

import java.util.Objects;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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
                    emailLayout.setError("please, enter a valid email address");
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
        LoginService loginService = new LoginService(this);
        loginService.execute(email, password);
    }
}