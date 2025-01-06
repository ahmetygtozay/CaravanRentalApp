package com.example.caravanrentalapp.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.caravanrentalapp.R;
import com.example.caravanrentalapp.adapters.HomeAdapter;
import com.example.caravanrentalapp.listeners.ItemListener;
import com.example.caravanrentalapp.model.Item;
import com.example.caravanrentalapp.screens.DetailsActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// Fragment to display a list of caravans with Firebase integration
public class HomeFragment extends Fragment implements ItemListener {

    // RecyclerView for displaying caravan offers
    private RecyclerView newOffersRV;

    // Adapter to bind data to RecyclerView
    private HomeAdapter adapter;

    // List to store caravan items
    private List<Item> itemList;

    // Inflate the fragment layout
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    // Called immediately after the view is created
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize RecyclerView
        newOffersRV = view.findViewById(R.id.new_offers_RV);

        // Initialize item list
        itemList = new ArrayList<>();

        // Fetch caravan data from Firebase
        FirebaseDatabase.getInstance().getReference().child("caravans")
                .addListenerForSingleValueEvent(new ValueEventListener() {

                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        // Loop through each child node in the database
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            // Add each caravan item to the list
                            itemList.add(new Item(
                                    Objects.requireNonNull(dataSnapshot.child("location").getValue()).toString(),
                                    "$" + Objects.requireNonNull(dataSnapshot.child("price").getValue()).toString(),
                                    Objects.requireNonNull(dataSnapshot.child("shortDescription").getValue()).toString(),
                                    Objects.requireNonNull(dataSnapshot.child("description").getValue()).toString(),
                                    Objects.requireNonNull(dataSnapshot.child("image").getValue()).toString()
                            ));
                        }
                        // Notify adapter that data has changed
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Handle database error
                    }
                });

        // Set up RecyclerView with adapter and layout manager
        adapter = new HomeAdapter(getContext(), itemList, this);
        newOffersRV.setLayoutManager(new LinearLayoutManager(getContext()));
        newOffersRV.setAdapter(adapter);
    }

    // Handle item click events
    @Override
    public void onItemPosition(int position) {
        // Navigate to DetailsActivity with selected item details
        Intent intent = new Intent(getContext(), DetailsActivity.class);
        intent.putExtra("price", itemList.get(position).getPrice());
        intent.putExtra("location", itemList.get(position).getLocation());
        intent.putExtra("shortDescription", itemList.get(position).getShortDescription());
        intent.putExtra("description", itemList.get(position).getDescription());
        intent.putExtra("image", itemList.get(position).getImage());
        startActivity(intent);
    }
}
