package com.example.fitnessapp;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

public class EditExerciseActivity extends AppCompatActivity {

    Dialog dialog;
    Button btnyes, btncancel;

    Button btnedit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_exercise_activity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getSupportActionBar().hide();
        fillInputs();

        btnedit = findViewById(R.id.e_btn_edit);

        dialog = new Dialog(EditExerciseActivity.this);
        dialog.setContentView(R.layout.custom_dialog_edit_exercise);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.custom_bg));
        dialog.setCancelable(false); // para que no se cancele si pinchas fuera


        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });

        btnyes = dialog.findViewById(R.id.btn_yes);
        btncancel = dialog.findViewById(R.id.btn_cancel);

        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(EditExerciseActivity.this, "CANCEL", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        btnyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // METODOD PARA EDITAR
            }
        });

    }

    public void changeExtendActivity(View view){

        startActivity(new Intent(EditExerciseActivity.this, AllExercises.class));


    }

    public void fillInputs(){
        TextInputEditText name, weigth, reps;

        name = findViewById(R.id.e_inpt_name);
        weigth = findViewById(R.id.e_inpt_weigth);
        reps = findViewById(R.id.e_inpt_reps);

        // Establecer el texto para el nombre, repeticiones y peso
        name.setText(getIntent().getStringExtra("name").toLowerCase());
        weigth.setText(getIntent().getStringExtra("weight"));
        reps.setText(getIntent().getStringExtra("repetition"));

    }

    public void editExercise(){

    }



}