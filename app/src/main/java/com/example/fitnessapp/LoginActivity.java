package com.example.fitnessapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {
    public static String username; // STATIC SOLO TIENE UNA VARIABLE EN TODO

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.glTopBtIniciarSesion), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getSupportActionBar().hide();




    }


    public void changePruebaActivity(View view){
        startActivity(new Intent(LoginActivity.this, PruebaActivity.class ));

    }

    public void changeHomeActivity(View view){
        startActivity(new Intent(LoginActivity.this, HomeActivity.class ));

    }

    public void changeRegisterActivty(View view){
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class ));

    }




































































































}