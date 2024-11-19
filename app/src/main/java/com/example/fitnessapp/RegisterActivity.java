package com.example.fitnessapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
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

    public void changeHomeActivity(View view){
        startActivity(new Intent(RegisterActivity.this, HomeActivity.class ));

    }

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().isEmpty();
    }

    public void checkEmptyInputsRegister(View view){
        TextInputEditText name = findViewById(R.id.r_inpt_nombre);
        TextInputEditText email = findViewById(R.id.r_inpt_email);
        TextInputEditText password = findViewById(R.id.r_input_password);


        if (isEmpty(name) || isEmpty(password) || isEmpty(email)){
            Toast.makeText(RegisterActivity.this, "CAMPOS VACIOS", Toast.LENGTH_SHORT).show();
        } else {
            checkIfNameEmailExist();
        }}


    public void checkIfNameEmailExist(){

        TextInputEditText name = findViewById(R.id.r_inpt_nombre);
        TextInputEditText email = findViewById(R.id.r_inpt_email);
        TextInputEditText password = findViewById(R.id.r_input_password);




        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                boolean flag = false;
                ArrayList<User> users = new ArrayList<>();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Map<String, Object> documentObj = document.getData();
                    users.add(new User(
                            documentObj.get("name").toString(),
                            documentObj.get("email").toString(),
                            documentObj.get("password").toString()));
                }

                for (User user : users){

                    if (user.getName().equals(name.getText().toString())|| user.getEmail().equals(email.getText().toString())){
                        flag = true;
                        break;
                    }
                }
                if (!flag){
                    createNewUser(name.getText().toString(),email.getText().toString(),password.getText().toString());
                } else {

                    Toast.makeText(RegisterActivity.this, "NOMBRE O CORREO EXISTENTE", Toast.LENGTH_LONG).show();
                }

            }


        });



    }

    public void createNewUser(String name, String email, String password){



        FirebaseFirestore database = FirebaseFirestore.getInstance();

        Map<String, Object> userdata = new HashMap<>();

        userdata.put("name", name);
        userdata.put("email", email);
        userdata.put("password", password);
        userdata.put("exercises", new ArrayList<>());

        database.collection("users").document(name)
                .set(userdata)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class );

                        intent.putExtra("email",email);
                        intent.putExtra("password", password);

                        startActivity(intent);

                        Toast.makeText(RegisterActivity.this, "USUARIO " + name.toUpperCase(Locale.ROOT) + " CREADO", Toast.LENGTH_LONG).show();
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