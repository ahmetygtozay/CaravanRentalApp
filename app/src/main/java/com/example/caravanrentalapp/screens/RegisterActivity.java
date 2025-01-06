package com.example.caravanrentalapp.screens;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.caravanrentalapp.model.User;

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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    // Declare UI elements
    private TextView haveAccount;  // TextView to navigate to login screen
    private EditText userName, userEmail, userPassword, confirmPassword;  // Input fields for user data
    private FirebaseAuth mAuth;  // Firebase authentication instance
    private AppCompatButton registerButton;  // Button to trigger registration process
    private DatabaseReference mRef;  // Firebase database reference

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize UI elements
        haveAccount = findViewById(R.id.have_account);
        userName = findViewById(R.id.user_name);
        userEmail = findViewById(R.id.user_email);
        userPassword = findViewById(R.id.user_password);
        confirmPassword = findViewById(R.id.user_confirm_password);
        registerButton = findViewById(R.id.register_button);

        // Initialize Firebase authentication and database
        mAuth = FirebaseAuth.getInstance();
        mRef = FirebaseDatabase.getInstance().getReference();

        // Set click listener on "Have an account?" text to navigate to login screen
        haveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class)); // Start LoginActivity
                finish();  // Finish current activity to prevent back navigation to this screen
            }
        });

        // Set click listener on the register button
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if the user has filled all required fields
                if(userName.getText().toString().trim().isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Enter Name", Toast.LENGTH_LONG).show();
                } else if(userEmail.getText().toString().trim().isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Enter Valid email", Toast.LENGTH_SHORT).show();
                } else if(userPassword.getText().toString().trim().isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Enter password", Toast.LENGTH_LONG).show();
                } else if(!userPassword.getText().toString().trim().equals(confirmPassword.getText().toString().trim())){
                    // Check if passwords match
                    Toast.makeText(RegisterActivity.this, "Enter Valid Password", Toast.LENGTH_LONG).show();
                } else {
                    // Check if email format is valid
                    if(emailChecker(userEmail.getText().toString().trim())){
                        // Create the user if validation passes
                        createUser(userEmail.getText().toString().trim(),
                                userPassword.getText().toString().trim(),
                                userName.getText().toString().trim());
                    } else {
                        // Show error if email format is invalid
                        Toast.makeText(RegisterActivity.this, "Enter Valid email", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    // Helper function to validate email format
    boolean emailChecker(String email){
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();  // Use Android's built-in email validation
    }

    // Function to create a user using Firebase Authentication
    void createUser(String email, String password, String name){
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                // Create a user object to store in Firebase Realtime Database
                User user = new User(name, email);

                if(task.isSuccessful()){
                    // If user creation is successful, store user data in Firebase Database
                    mRef.child("users").push().setValue(user);
                    // Navigate to HomeActivity after successful registration
                    startActivity(new Intent(RegisterActivity.this, HomeActivity.class));
                    finish();  // Finish the register activity to prevent returning to it
                    Toast.makeText(RegisterActivity.this, "Created user!", Toast.LENGTH_SHORT).show();
                } else {
                    // Show error message if user creation failed
                    Toast.makeText(RegisterActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Show error message if the Firebase operation fails
                Toast.makeText(RegisterActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
