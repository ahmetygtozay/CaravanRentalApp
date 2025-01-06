package com.example.caravanrentalapp.model;

import android.widget.ImageView;
import java.util.List;

public class Item {
    // Location of the caravan
    private String location;

    // Price of the caravan
    private String price;

    // Detailed description of the caravan
    private String description;

    // Short description of the caravan
    private String shortDescription;

    // Image URL of the caravan
    public String image;

    // Default constructor for Firebase deserialization
    public Item() {
    }

    // Constructor with all fields
    public Item(String location, String price, String description, String shortDescription, String image) {
        this.location = location;
        this.price = price;
        this.description = description;
        this.shortDescription = shortDescription;
        this.image = image;
    }

    // Getter for image
    public String getImage() {
        return image;
    }

    // Setter for image
    public void setImage(String image) {
        this.image = image;
    }

    // Constructor with basic fields
    public Item(String location, String price, String shortDescription) {
        this.location = location;
        this.price = price;
        this.shortDescription = shortDescription;
    }

    // Getter for location
    public String getLocation() {
        return location;
    }

    // Setter for location
    public void setLocation(String location) {
        this.location = location;
    }

    // Getter for price
    public String getPrice() {
        return price;
    }

    // Setter for price
    public void setPrice(String price) {
        this.price = price;
    }

    // Getter for description
    public String getDescription() {
        return description;
    }

    // Setter for description
    public void setDescription(String description) {
        this.description = description;
    }

    // Getter for short description
    public String getShortDescription() {
        return shortDescription;
    }

    // Setter for short description
    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }
}
