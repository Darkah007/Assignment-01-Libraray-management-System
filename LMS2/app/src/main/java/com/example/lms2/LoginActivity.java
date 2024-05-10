package com.example.lms2;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextUsername, editTextPassword;
    private Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Here you can implement your login logic
                // For simplicity, let's assume login is successful
                String username = editTextUsername.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                if (isValidLogin(username, password)) {
                    // Show a toast message indicating successful login
                    Toast.makeText(LoginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();

                    // Once login is successful, start the main menu activity
                    Intent intent = new Intent(LoginActivity.this, MainMenuActivity.class);
                    startActivity(intent);
                    finish(); // Finish current activity to prevent user from going back to login screen
                } else {
                    // Show a toast message indicating login failure
                    Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Dummy method to validate login credentials
    private boolean isValidLogin(String username, String password) {
        // Replace these dummy username and password with your actual user credentials
        String validUsername = "user123";
        String validPassword = "password123";

        // Check if the entered username and password match the valid credentials
        return username.equals(validUsername) && password.equals(validPassword);
    }
}
