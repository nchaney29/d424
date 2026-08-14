package com.example.d424booktracker.UI;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.d424booktracker.R;
import com.example.d424booktracker.database.Repository;
import com.example.d424booktracker.entities.User;

public class RegisterUser extends AppCompatActivity {
    private EditText un, pw;
    private Button registerBtn;
    Repository repository;
    User currentUser;
    int userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        un = findViewById(R.id.registerUsername);
        pw = findViewById(R.id.registerPassword);
        registerBtn = findViewById(R.id.registerButton);

        repository = new Repository(getApplication());


        registerBtn.setOnClickListener(v -> {
            String username = un.getText().toString().toLowerCase().trim();
            String password = pw.getText().toString().toLowerCase().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_LONG).show();
            }

            if (repository.getmAllUsers().isEmpty()) userID = 1;
            else
                userID = repository.getmAllUsers().get(repository.getmAllUsers().size() - 1).getUserId() + 1;

            currentUser = new User (userID, username, password);

            try {
                repository.registerUser(currentUser);
                Toast.makeText(this, "Registration successful", Toast.LENGTH_LONG).show();
                this.finish();
            } catch (RuntimeException e) {
                Toast.makeText(this, "Account with username already exists", Toast.LENGTH_LONG).show();
                System.err.println("Error code: " + e.getMessage());
            }
        });

    }
}
