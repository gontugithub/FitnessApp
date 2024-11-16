package com.example.fitnessapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ExtendExercise extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // EdgeToEdge.enable(this);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_extend_exercise);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



        fillActivity();
    }



    public void changeAllExercises(View view){
        startActivity(new Intent(ExtendExercise.this, AllExercises.class ));

    }


    public void fillActivity(){
        TextView name, weigth, reps;

        name = findViewById(R.id.name);
        weigth = findViewById(R.id.weigth);
        reps = findViewById(R.id.reps);

        // Establecer el texto para el nombre, repeticiones y peso
        name.setText(getIntent().getStringExtra("name").toUpperCase());

        // Convertir los valores numéricos a String antes de pasarlos a setText()
        reps.setText("x" + String.valueOf(getIntent().getLongExtra("repetition", 0)));
        weigth.setText(String.valueOf(getIntent().getDoubleExtra("weight", 0.0)) + "kg");
    }






}