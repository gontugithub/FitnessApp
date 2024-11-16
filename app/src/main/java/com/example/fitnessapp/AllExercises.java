package com.example.fitnessapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.fitnessapp.models.Exercise;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AllExercises extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_all_exercises);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getSupportActionBar().hide();
        mostrarEjercicios();

    }

    public void changeHomeActivity(View view){
        startActivity(new Intent(AllExercises.this, HomeActivity.class ));

    }

    public void changeNewExercise(View view){
        startActivity(new Intent(AllExercises.this, NewExerciseActivity.class ));

    }





    public void mostrarEjercicios(){

        FirebaseFirestore database = FirebaseFirestore.getInstance();
        RegisterUser registerUser = RegisterUser.getInstance();
        // ACCEDER
        DocumentReference docref = database.collection("users").document(registerUser.getUser().getName());
        Source src = Source.SERVER;

        docref.get(src).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                ArrayList<Exercise> exercises = new ArrayList<>();
                ArrayList<HashMap> exerciseArray = new ArrayList<>();
                DocumentSnapshot result = task.getResult();
                Map<String, Object> documentObj = result.getData();

               exerciseArray = (ArrayList<HashMap>) documentObj.get("exercises");

               for (HashMap<Object,Object> exe : exerciseArray){

                   String name = (String) exe.get("name");
                   Long rep = (Long)exe.get("repetition");
                   Double weigth = (Double) exe.get("weight");

                   exercises.add(new Exercise(name,rep,weigth));


                }


               GridView tabla = findViewById(R.id.tabla);
               Adapter adapter = new Adapter(AllExercises.this,R.layout.element, exercises);
               tabla.setAdapter(adapter);

               tabla.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                   @Override
                   public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                      Intent intent = new Intent(AllExercises.this, ExtendExercise.class);

                      Exercise exercise = exercises.get(i);

                     intent.putExtra("name",exercise.getName());
                     intent.putExtra("repetition",exercise.getRepetition());
                     intent.putExtra("weight",exercise.getWeight());
                     startActivity(intent);


                   }
               });


                }

        });


    }




}