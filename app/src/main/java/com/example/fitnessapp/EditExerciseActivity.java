package com.example.fitnessapp;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import kotlin._Assertions;

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
                Toast.makeText(EditExerciseActivity.this, "CANCELANDO ...", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        btnyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                editExercise();


            }
        });

    }

    public void changeExtendActivity(View view){

        startActivity(new Intent(EditExerciseActivity.this, AllExercises.class));


    }

    public void changeAllExercises(View view){

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

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    public void editExercise(ArrayList<HashMap> allexercises, FirebaseFirestore database, String userId){

        Map<String, Object> update = new HashMap<>();
        update.put("exercises", allexercises);

        database.collection("users").document(userId)
                .update(update).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(EditExerciseActivity.this, "EDITADO CON EXITO", Toast.LENGTH_SHORT).show();
                        changeAllExercises(null);
                    }
                });

    }

    public void editExercise(){

        String actual_name;
        TextInputEditText name, weigth, reps;
        RegisterUser registerUser = RegisterUser.getInstance();
        String userId = registerUser.getUser().getName();

        // VALORES

        actual_name = getIntent().getStringExtra("name").toLowerCase();


        name = findViewById(R.id.e_inpt_name);
        weigth = findViewById(R.id.e_inpt_weigth);
        reps = findViewById(R.id.e_inpt_reps);


        if (isEmpty(name) || isEmpty(weigth) || isEmpty(reps)){

            Toast.makeText(EditExerciseActivity.this, "RELLENA TODOS LOS CAMPOS", Toast.LENGTH_SHORT).show();

        } else{


            FirebaseFirestore database = FirebaseFirestore.getInstance();
            Source src = Source.SERVER;

            database.collection("users").document(userId)
                    .get(src).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            DocumentSnapshot result = task.getResult();
                            Map<String, Object> data =  result.getData();

                            ArrayList<HashMap> exerciseArray = (ArrayList<HashMap>) data.get("exercises");

                            boolean flag = true;

                            for(HashMap<Object,Object> exercise : exerciseArray){

                                String name_string = (String) exercise.get("name");

                                if(name_string.toUpperCase().equals(name.getText().toString().toUpperCase())){

                                    flag = false;
                                    break;

                                }

                            }

                            if(flag || actual_name.toLowerCase().equals(name.getText().toString().toLowerCase())){

                                for(HashMap<Object,Object> exercise : exerciseArray){

                                    if(exercise.get("name").equals(actual_name)){

                                        exercise.put("name", name.getText().toString());
                                        exercise.put("repetition", Integer.parseInt(reps.getText().toString()));
                                        exercise.put("weight" ,Double.parseDouble(weigth.getText().toString()));
                                        break;

                                    }

                                }

                                editExercise(exerciseArray,database,userId);



                            } else{
                                Toast.makeText(EditExerciseActivity.this, "ESE NOMBRE YA EXISTE", Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                            }

                        }
                    });




        }
















    }



}