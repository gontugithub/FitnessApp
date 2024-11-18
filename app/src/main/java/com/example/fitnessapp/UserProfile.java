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

import com.example.fitnessapp.models.User;

public class UserProfile extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



        Dialog dialog;
        Button btnyes, btncancel;

        Button btn_log_out = findViewById(R.id.up_log_out);

        fillData();
        getSupportActionBar().hide();

        dialog = new Dialog(UserProfile.this);
        dialog.setContentView(R.layout.custom_dialog_log_out);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.custom_bg));
        dialog.setCancelable(false); // para que no se cancele si pinchas fuera
        
        btn_log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.show();
                
            }
        });

        btncancel = dialog.findViewById(R.id.up_btn_cancel);
        btnyes = dialog.findViewById(R.id.up_btn_yes);
        
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(UserProfile.this, "CANCEL", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        
        btnyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                log_out();
                Toast.makeText(UserProfile.this, "CERRANDO SESION", Toast.LENGTH_SHORT).show();
            }
        });
        
    }


    public void log_out(){
        RegisterUser registerUser = RegisterUser.getInstance();
        registerUser.setUser(null);
        startActivity(new Intent(UserProfile.this, LoginActivity.class));
    }

    public void fillData(){

        TextView name,email, password;

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        RegisterUser registerUser = RegisterUser.getInstance();
        User user = registerUser.getUser();

        name.setText(user.getName());
        email.setText(user.getEmail());


    }

}