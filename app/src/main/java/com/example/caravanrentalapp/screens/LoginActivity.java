package com.example.caravanrentalapp.screens;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.caravanrentalapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    // Declare UI elements: TextView for account creation prompt, EditTexts for email and password input, and a Button for login
    private TextView createAccount;
    private EditText email, password;
    private AppCompatButton loginButton;

    // Declare FirebaseAuth instance to manage authentication
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the layout for the activity using the 'activity_login' layout XML
        setContentView(R.layout.activity_login);

        // Initialize the UI elements by finding them by their respective IDs
        createAccount = findViewById(R.id.not_exist_account);
        email = findViewById(R.id.user_email);
        password = findViewById(R.id.user_password);
        loginButton = findViewById(R.id.login_button);

        // Initialize Firebase Authentication instance
        mAuth = FirebaseAuth.getInstance();

        // Set an OnClickListener for the 'createAccount' TextView
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // If the user clicks on the "Create Account" prompt, navigate to the RegisterActivity
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        // Set an OnClickListener for the login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if the email input is not empty and if the email format is valid
                if(!email.getText().toString().trim().isEmpty() && emailChecker(email.getText().toString().trim())){
                    // Check if the password input is not empty
                    if(!password.getText().toString().isEmpty()){
                        // If both email and password are valid, proceed with logging in the user
                        loginUser(email.getText().toString().trim(), password.getText().toString().trim());
                    }else{
                        // If password is empty, show a Toast indicating the user should enter a valid password
                        Toast.makeText(LoginActivity.this, "Enter Valid password", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    // If the email is invalid or empty, show a Toast indicating the user should enter a valid email
                    Toast.makeText(LoginActivity.this, "Enter Valid email", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Helper method to check if the entered email matches a valid email pattern using Android's built-in Patterns class
    boolean emailChecker(String email){
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // Method to authenticate the user by signing in using the provided email and password
    void loginUser(String email, String password){
        // Use Firebase Authentication to sign in with email and password
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If login is successful
                        if(task.isSuccessful()){
                            // Show a success message
                            Toast.makeText(LoginActivity.this, "Successfully Login", Toast.LENGTH_SHORT).show();
                            // Navigate to HomeActivity after successful login
                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                            // Finish the LoginActivity to remove it from the back stack
                            finish();
                        }else{
                            // If login fails, show a failure message
                            Toast.makeText(LoginActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                // Add failure listener to handle potential errors during the sign-in process
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // If there is a failure (e.g., network issues, invalid credentials), show a failure message
                        Toast.makeText(LoginActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
