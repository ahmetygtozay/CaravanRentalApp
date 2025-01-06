package com.example.caravanrentalapp.screens;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.caravanrentalapp.R;
import com.example.caravanrentalapp.fragments.AccountFragment;
import com.example.caravanrentalapp.fragments.ChatFragment;
import com.example.caravanrentalapp.fragments.FavoriteFragment;
import com.example.caravanrentalapp.fragments.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomeActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    // Declare a BottomNavigationView to be used for navigation in the app
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the content view to the layout 'activity_home'
        setContentView(R.layout.activity_home);

        // Find the BottomNavigationView from the layout by its ID
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set the item selected listener for the BottomNavigationView
        bottomNavigationView.setOnItemSelectedListener(this);

        // Load the HomeFragment by default when the activity is created
        loadFragment(new HomeFragment());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Declare a fragment variable to hold the selected fragment
        Fragment fragment = null;

        // Use a switch statement to check which item is selected from the BottomNavigationView
        switch(item.getItemId()){
            case R.id.menu_home:
                // If the Home item is selected, create and load HomeFragment
                fragment = new HomeFragment();
                break;
            case R.id.menu_fav:
                // If the Favorite item is selected, create and load FavoriteFragment
                fragment = new FavoriteFragment();
                break;
            case R.id.menu_chat:
                // If the Chat item is selected, create and load ChatFragment
                fragment = new ChatFragment();
                break;
            case R.id.menu_account:
                // If the Account item is selected, create and load AccountFragment
                fragment = new AccountFragment();
                break;
        }

        // Call the 'loadFragment' method to replace the current fragment with the selected one
        return loadFragment(fragment);
    }

    // Method to replace the current fragment with the new one
    boolean loadFragment (Fragment fragment){
        // Check if the fragment is not null before proceeding
        if(fragment != null){
            // Begin a transaction to replace the existing fragment with the new one
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            // Return true to indicate that the fragment was loaded successfully
            return true;
        }
        // Return false if the fragment is null, indicating no fragment was loaded
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
        // The method can be overridden to handle pointer capture changes, if needed
    }
}
