package com.example.caravanrentalapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.caravanrentalapp.R;
import com.example.caravanrentalapp.model.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;

// Fragment responsible for displaying and managing the user's account details.
// It fetches user information from Firebase Realtime Database and displays it in UI components.
public class AccountFragment extends Fragment {

    // UI components
    private CircleImageView userProfile; // Profile image view
    private EditText userName; // Text field for displaying user's name
    private EditText userEmail; // Text field for displaying user's email
    private AppCompatButton updateButton; // Button for updating user details

    // Firebase Database reference
    private DatabaseReference ref;

    // Called to create and return the view hierarchy associated with the fragment.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment from XML
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    // Called immediately after the view is created.
    // Used to initialize UI components and set up Firebase listeners.
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize UI components
        userProfile = view.findViewById(R.id.profile_image); // Profile image
        userName = view.findViewById(R.id.user_name); // Username EditText
        userEmail = view.findViewById(R.id.user_email); // User email EditText
        updateButton = view.findViewById(R.id.update_button); // Update button

        // Initialize Firebase database reference to 'users' node
        ref = FirebaseDatabase.getInstance().getReference().child("users");

        // Add a listener to fetch user data from the database
        ref.addChildEventListener(new ChildEventListener() {
            // Triggered when a new child node is added to the 'users' node.
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // Retrieve user object from the snapshot
                User user = snapshot.getValue(User.class);

                // Update UI fields if user data is valid
                if (user != null){
                    userName.setText(user.getName()); // Set user name
                    userEmail.setText(user.getEmail()); // Set user email
                }
            }

            // Triggered when an existing child node is updated.
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // Handle updates to existing child nodes if needed
            }

            // Triggered when a child node is removed.
            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                // Handle the removal of child nodes if needed
            }

            // Triggered when a child node is moved.
            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // Handle when a child node changes position in the database
            }

            // Triggered when the listener is canceled.
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database errors, such as permission denied
            }
        });
    }
}
