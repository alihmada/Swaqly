package com.swaqly.swaqly.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.swaqly.swaqly.R;

public class LoginCreate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_create);

        findViewById(R.id.login).setOnClickListener(view -> {
            Intent intent = new Intent(getBaseContext(), Login.class);
            startActivity(intent);
        });

        findViewById(R.id.create_an_account).setOnClickListener(view -> {
            Intent intent = new Intent(getBaseContext(), SignUp.class);
            startActivity(intent);
        });
    }
}