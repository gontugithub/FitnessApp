package com.example.fitnessapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

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
        fillinputsIfRegister();
        checkLocalDatabase();
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

    public void changeEditActivyty(View view){
        startActivity(new Intent(LoginActivity.this, ExtendExercise.class ));
    }

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    private boolean checkLocalDatabase() {
        DataBaseAux dataBaseAux = new DataBaseAux(this);
        SQLiteDatabase db = dataBaseAux.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users", null);
        if(cursor.moveToFirst()){
            String email = cursor.getString(1);
            String pass = cursor.getString(2);
            checkUserPassword(email, pass);
            return true;
        }

        return false;
    }

    private void checkUserPassword( String email, String pass) {
        Toast.makeText(this, "aaaa", Toast.LENGTH_SHORT).show();
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                boolean flag = false;
                User correctUser = null;
                ArrayList<User> users = new ArrayList<>();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Map<String, Object> documentObj = document.getData();
                    users.add(new User(
                            documentObj.get("name").toString(),
                            documentObj.get("email").toString(),
                            documentObj.get("password").toString()));
                }

                for (User user : users) {
                    if (user.getPassword().equals(pass) && user.getEmail().equals(email)) {
                        flag = true;
                        correctUser = user;
                        break;
                    }
                }

                if (flag){
                     RegisterUser registerUser = RegisterUser.getInstance();
                     registerUser.setUser(correctUser);
                     createLocalRegistry(correctUser);
                     login(registerUser.getUser().getName());
                } else {

                    Toast.makeText(LoginActivity.this, "EMAIL O CONTRSEÑA NO REGISTRADO", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void checkEmptyInputsLogin(View view){
        TextInputEditText email = findViewById(R.id.l_inpt_email);
        TextInputEditText password = findViewById(R.id.l_inpt_password);


        if (isEmpty(email) || isEmpty(password)){
            Toast.makeText(LoginActivity.this, "CAMPOS VACIOS", Toast.LENGTH_SHORT).show();
        } else {


       checkUserPassword(email.getText().toString(), password.getText().toString());
    }}

    public void login(String username){

        changeHomeActivity(null);


    }

    private void createLocalRegistry(User user) {
        if(user == null)
            return;

        DataBaseAux dbaux = new DataBaseAux(this);
        SQLiteDatabase db = dbaux.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("email", user.getEmail());
        values.put("password", user.getPassword());
        db.insert("users", null, values);
    }


    private void fillinputsIfRegister(){

        if(getIntent().hasExtra("email")){

            TextInputEditText inp_email = findViewById(R.id.l_inpt_email);
            TextInputEditText password = findViewById(R.id.l_inpt_password);

            String email = getIntent().getStringExtra("email");
            String pass = getIntent().getStringExtra("password");

            inp_email.setText(email);
            password.setText(pass);

        }






    }
}