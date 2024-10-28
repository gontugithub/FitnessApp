package com.example.fitnessapp;

import android.os.Bundle;
import android.view.View;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PruebaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.prueba);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void fillScrollViewDB(View view){

        LinearLayout layout = findViewById(R.id.userScrollDBLinear);
        layout.removeAllViews();

        FirebaseFirestore database = FirebaseFirestore.getInstance();
        // ACCEDER
        DocumentReference docref = database.collection("users").document("admin");
        Source src = Source.SERVER;
        // PARA ELIMINAR docref.delete();

        docref.get(src).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot result = task.getResult();
                Map<String, Object> info =  result.getData();
                Toast.makeText(PruebaActivity.this, "TODO OK",  Toast.LENGTH_LONG).show();
                TextView infoView = new TextView(PruebaActivity.this);
                infoView.setText(info.get("name").toString());
                layout.addView(infoView);

            }
        });





    }





    public void addToFireBase_DB(View view){


        FirebaseFirestore database = FirebaseFirestore.getInstance();
        Map<String, Object> values = new HashMap<>();


        TextView name = findViewById(R.id.nameInput);
        TextView password = findViewById(R.id.passwordInput);

        values.put("name", name.getText().toString());
        values.put("password", password.getText().toString());

        database.collection("users").document(name.getText().toString())
                .set(values)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(PruebaActivity.this, "TODO OK", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(PruebaActivity.this, "FAIL", Toast.LENGTH_LONG).show();
                    }
                });


    }



}