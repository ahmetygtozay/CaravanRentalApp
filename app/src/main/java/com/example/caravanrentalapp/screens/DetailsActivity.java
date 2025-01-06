package com.example.caravanrentalapp.screens;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.caravanrentalapp.R;

public class DetailsActivity extends AppCompatActivity {

    // UI components for displaying details
    private ImageView imageView; // ImageView to display the caravan image
    private TextView price; // TextView to display the price of the caravan
    private TextView shortDescription; // TextView to display a short description
    private TextView description; // TextView to display a detailed description
    private TextView location; // TextView to display the location of the caravan

    // Variables to store data passed via Intent
    String pr; // Variable to store price
    String dsc; // Variable to store description
    String sdsc; // Variable to store short description
    String loc; // Variable to store location
    String imgUrl; // Variable to store image URL (if image is loaded from an online source)
    int imgResId; // Variable to store drawable resource ID (if image is loaded locally)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details); // Set the layout for the activity

        // Initialize UI components with their respective IDs from the layout
        imageView = findViewById(R.id.imageView); // ImageView for displaying caravan image
        price = findViewById(R.id.price); // TextView for displaying price
        description = findViewById(R.id.short_description); // TextView for displaying description
        shortDescription = findViewById(R.id.description); // TextView for displaying short description
        location = findViewById(R.id.location); // TextView for displaying location

        // Retrieve data passed via Intent
        pr = getIntent().getStringExtra("price"); // Get price from Intent
        dsc = getIntent().getStringExtra("description"); // Get description from Intent
        sdsc = getIntent().getStringExtra("shortDescription"); // Get short description from Intent
        loc = getIntent().getStringExtra("location"); // Get location from Intent
        imgUrl = getIntent().getStringExtra("image"); // Get image URL from Intent
        imgResId = getIntent().getIntExtra("image", R.drawable.campervan); // Get image resource ID from Intent (default: campervan image)

        // Set retrieved data to the respective UI components
        price.setText(pr); // Set price to TextView
        description.setText(dsc); // Set description to TextView
        shortDescription.setText(sdsc); // Set short description to TextView
        location.setText(loc); // Set location to TextView

        // Check if an image URL is provided
        if (imgUrl != null && !imgUrl.isEmpty()) {
            // If imgUrl is not null or empty, load the image using Glide library
            Glide.with(this)
                    .load(imgUrl) // Load image from URL
                    .placeholder(R.drawable.campervan) // Show placeholder while loading
                    .into(imageView); // Display image in ImageView
        } else {
            // If no imgUrl is provided, use the drawable resource ID
            imageView.setImageResource(imgResId); // Set local drawable image
        }
        @SuppressLint("WrongViewCast") ImageButton shareButton = findViewById(R.id.share_button);
        Uri imageUri = Uri.parse("https://image.lexica.art/full_webp/5128375e-4779-4eac-94e6-95e5816a04b4");

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create Share Intent
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Caravan Rental");
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out Caravania App and please rate us!");
                shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
                // Start sharing options
                startActivity(Intent.createChooser(shareIntent, "Share via"));
            }
        });

        Button rentButton = findViewById(R.id.button_rent);
        rentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailsActivity.this, PaymentActivity.class);
                intent.putExtra("price", pr);
                startActivity(intent);
            }
        });



    }
}
