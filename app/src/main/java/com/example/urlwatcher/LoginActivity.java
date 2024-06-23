package com.example.urlwatcher;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.Console;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            addButtonEvent();
            return insets;
        });
    }

    private void addButtonEvent(){
        Button authButton = findViewById(R.id.button_auth);
        authButton.setOnClickListener(v -> {
            View label = findViewById(R.id.user_login_auth);
            if(label.getContext().toString().isEmpty())
                Toast.makeText(getApplicationContext(), "Полe не может быть пустым",Toast.LENGTH_SHORT).show();
            else{
                DBHelper dbHelper = new DBHelper(LoginActivity.this,null);
                dbHelper.addUser(label.getContext().toString());
                startActivity(new Intent(LoginActivity.this,MainActivity.class));
            }
        });
    }
}