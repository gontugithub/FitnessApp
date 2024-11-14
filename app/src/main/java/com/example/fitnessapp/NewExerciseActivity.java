package com.example.fitnessapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.fitnessapp.models.Exercise;
import com.example.fitnessapp.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NewExerciseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_new_exercise);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getSupportActionBar().hide();
    }

    public void changeAllExercise(View view){
        startActivity(new Intent(NewExerciseActivity.this, AllExercises.class ));

    }

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    public void checkIfExist(View view){

       RegisterUser registerUser = RegisterUser.getInstance();
       String userId = registerUser.getUser().getName();

        TextInputEditText exercisename = findViewById(R.id.ne_inpt_nombre);
        TextInputEditText weight = findViewById(R.id.ne_inpt_peso);
        TextInputEditText reps = findViewById(R.id.ne_inpt_repeticiones);

        if (isEmpty(exercisename) || isEmpty(weight) || isEmpty(reps)){

            Toast.makeText(NewExerciseActivity.this, "RELLENA TODOS LOS CAMPOS", Toast.LENGTH_SHORT).show();

        } else {

            FirebaseFirestore database = FirebaseFirestore.getInstance();
            Source src = Source.SERVER;



            database.collection("users").document(userId)
                    .get(src).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            DocumentSnapshot result = task.getResult();
                            Map<String, Object> data =  result.getData();

                            ArrayList<HashMap> exerciseArray = (ArrayList<HashMap>) data.get("exercises");

                            if(exerciseArray.isEmpty()){

                                HashMap<Object, Object> nuevoejercicio = new HashMap<>();
                                nuevoejercicio.put("name", exercisename.getText().toString());
                                nuevoejercicio.put("repetition", Integer.parseInt(reps.getText().toString()));
                                nuevoejercicio.put("weight" ,Double.parseDouble(weight.getText().toString()));

                                exerciseArray.add(nuevoejercicio);

                                addNewExercise(exerciseArray, database, userId);

                            } else {

                                Toast.makeText(NewExerciseActivity.this, "SIZE" + exerciseArray.size(), Toast.LENGTH_SHORT).show();
                                boolean flag = true;


                                for (HashMap<Object,Object> exe : exerciseArray){

                                    String name = (String) exe.get("name");

                                    if(name.toUpperCase().equals(exercisename.getText().toString().toUpperCase())){

                                        flag = false;
                                        break;

                                    }
                                }

                                if(flag){

                                    Toast.makeText(NewExerciseActivity.this, "NUEVO EJERCICO", Toast.LENGTH_SHORT).show();

                                    HashMap<Object, Object> nuevoejercicio = new HashMap<>();
                                    nuevoejercicio.put("name", exercisename.getText().toString());
                                    nuevoejercicio.put("repetition", Integer.parseInt(reps.getText().toString()));
                                    nuevoejercicio.put("weight" ,Double.parseDouble(weight.getText().toString()));

                                    exerciseArray.add(nuevoejercicio);

                                    addNewExercise(exerciseArray, database, userId);


                                } else {
                                    Toast.makeText(NewExerciseActivity.this, "EJERCICIO YA EXISTE", Toast.LENGTH_SHORT).show();
                                }



                            }




                        }
                    });







        }



    }

    public void addNewExercise(ArrayList<HashMap> allexercises, FirebaseFirestore database, String userId){

        Map<String, Object> update = new HashMap<>();
        update.put("exercises", allexercises);

        database.collection("users").document(userId)
                .update(update).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(NewExerciseActivity.this, "NUEVO EJERCICIO CREADO", Toast.LENGTH_SHORT).show();
                    }
                });












    }


}