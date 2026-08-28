package com.example.d424booktracker.UI;

import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.mindrot.jbcrypt.BCrypt;

import androidx.appcompat.app.AppCompatActivity;

import com.example.d424booktracker.R;
import com.example.d424booktracker.database.Repository;
import com.example.d424booktracker.entities.User;

import java.sql.SQLException;

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
            String password = pw.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_LONG).show();
            }

            if (repository.getmAllUsers().isEmpty()) userID = 1;
            else
                userID = repository.getmAllUsers().get(repository.getmAllUsers().size() - 1).getUserId() + 1;

            String hashedPassword = repository.hashPassword(password);

            currentUser = new User(userID, username, hashedPassword);


            java.util.concurrent.Executors.newSingleThreadExecutor().execute(() -> {
                try {
                    repository.registerUser(currentUser);

                    runOnUiThread(() -> {
                        Toast.makeText(this, "Registration successful", Toast.LENGTH_LONG).show();
                        this.finish();
                    });
                } catch (android.database.sqlite.SQLiteConstraintException e) {
                    runOnUiThread(() -> {
                        Toast.makeText(this, "Account with username already exists", Toast.LENGTH_LONG).show();
                    });
                    System.err.println("Error code: " + e.getMessage());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
        });

    }
}
