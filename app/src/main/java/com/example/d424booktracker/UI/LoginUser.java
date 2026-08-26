package com.example.d424booktracker.UI;

import android.content.Intent;
import android.os.Bundle;
import android.se.omapi.Session;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.d424booktracker.R;
import com.example.d424booktracker.dao.UserDAO;
import com.example.d424booktracker.database.Repository;
import com.example.d424booktracker.entities.User;

public class LoginUser extends AppCompatActivity {
    private EditText un, pw;
    private Button loginBtn;
    Repository repository;
    User currentUser;
    int userId = -1;
    String hashedPW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        un = findViewById(R.id.loginUsername);
        pw = findViewById(R.id.loginPassword);
        loginBtn = findViewById(R.id.loginButton);

        repository = new Repository(getApplication());

        loginBtn.setOnClickListener(v -> {
            String username = un.getText().toString().trim();
            String password = pw.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Both fields are required", Toast.LENGTH_LONG).show();
                return;
            }

            java.util.concurrent.Executors.newSingleThreadExecutor().execute(() -> {
                hashedPW = repository.returnHash(username);

                boolean validUN = repository.validateName(username);
                boolean validPW = repository.validatePassword(password, hashedPW);


                if (validUN && validPW) {
                    userId = (repository.loginUser(username));

                    runOnUiThread(() -> {
                        currentUser = new User(userId, username, password);

                        SessionManager sessionManager = new SessionManager(LoginUser.this);
                        sessionManager.createSession(userId);
                        System.out.println("UserID: " + userId);

                        Toast.makeText(this, "Login successful", Toast.LENGTH_LONG).show();

                        startActivity(new Intent(LoginUser.this, BookList.class));
                        finish();
                    });
                } else {
                    runOnUiThread(() -> {
                        Toast.makeText(this, "Login failed", Toast.LENGTH_LONG).show();
                    });
                }
            });
        });
    }
}
