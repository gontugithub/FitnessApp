package com.example.fitnessapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getSupportActionBar().hide();
        setName();
    }

    public void changeAllexerciseActivty(View view){
        startActivity(new Intent(HomeActivity.this, AllExercises.class ));

    }

    public void changeUserProfile(View view){
        startActivity(new Intent(HomeActivity.this, UserProfile.class ));

    }



    public void setName(){
        String name = RegisterUser.getInstance().getUser().getName();

        TextView tx_name = findViewById(R.id.home_user_name);

        tx_name.setText(name);


    }


}