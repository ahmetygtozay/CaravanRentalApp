package com.example.caravanrentalapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.caravanrentalapp.screens.HomeActivity;
import com.example.caravanrentalapp.screens.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the layout for the splash screen activity
        setContentView(R.layout.activity_splash);

        // Get the current user from Firebase Authentication
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        // Create a handler to delay the next action by 3 seconds (3000 milliseconds)
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                // Check if a user is logged in
                if(user != null){
                    // If the user is logged in, navigate to the HomeActivity
                    startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                    finish();  // Finish the SplashActivity so the user cannot return to it
                } else {
                    // If no user is logged in, navigate to the LoginActivity
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();  // Finish the SplashActivity so the user cannot return to it
                }

            }

        }, 3000);  // The delay is set to 3 seconds (3000 milliseconds)
    }
}
