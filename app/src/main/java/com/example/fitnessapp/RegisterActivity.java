package com.example.fitnessapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.fitnessapp.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.Firebase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getSupportActionBar().hide();
    }


    public void changeLoginActivity(View view){
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class ));

    }

    public void checkIfNameEmailExist(View view, String name, String email){
        boolean flag = false;
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                ArrayList<User> users = new ArrayList<>();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Map<String, Object> documentObj = document.getData();
                    users.add(new User(
                            documentObj.get("name").toString(),
                            documentObj.get("email").toString(),
                            documentObj.get("password").toString()));
                }

                for (User user : users){

                    if (user.getName().equals(name) || user.getEmail().equals(email)){

                    }


                }

            }


        });

        return flag;

    }

    public void createNewUser(View view){



        FirebaseFirestore database = FirebaseFirestore.getInstance();

        Map<String, Object> userdata = new HashMap<>();

        TextInputEditText name = findViewById(R.id.r_inpt_nombre);
        TextInputEditText email = findViewById(R.id.r_inpt_email);
        TextInputEditText password = findViewById(R.id.r_input_password);


        userdata.put("name", name.getText().toString());
        userdata.put("email", email.getText().toString());
        userdata.put("password", password.getText().toString());

        database.collection("users").document(name.getText().toString())
                .set(userdata)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        Toast.makeText(RegisterActivity.this, "USUARIO " + name.getText().toString() + "\nCREADO", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegisterActivity.this, "ERROR", Toast.LENGTH_LONG).show();
                    }
                });









    }


}