package com.example.caravanrentalapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.caravanrentalapp.R;
import com.example.caravanrentalapp.listeners.ItemListener;
import com.example.caravanrentalapp.model.Item;

import java.util.List;

/**
 * Adapter class for displaying a list of caravan rental items in a RecyclerView.
 * Each item shows an image, location, price, and a short description.
 */
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private Context context;
    private List<Item> itemList;
    private ItemListener listener;

    /**
     * Constructor to initialize the adapter with context, data, and a listener for item clicks.
     * @param context The context for inflating item layouts.
     * @param itemList List of items to be displayed.
     * @param listener Listener for handling click events on items.
     */
    public HomeAdapter(Context context, List<Item> itemList, ItemListener listener) {
        this.context = context;
        this.itemList = itemList;
        this.listener = listener;
    }


    //Creates and returns a ViewHolder object to represent an item view.
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout for each entry in the list
        View view = LayoutInflater.from(context)
                .inflate(R.layout.new_offers, parent, false);
        return new ViewHolder(view);
    }


    //  Binds data from an Item object to the views in the ViewHolder.
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = itemList.get(position);

        // Set text views with item details
        holder.location.setText(item.getLocation());
        holder.price.setText(item.getPrice());
        holder.shortDescription.setText(item.getDescription());

        // Load the item's image into the ImageView using Glide
        Glide.with(context)
                .load(item.getImage())
                .placeholder(R.drawable.campervan)
                .error(R.drawable.campervan)
                .into(holder.imageView);

        // Handle click events for the entire item view
        holder.itemView.setOnClickListener(v -> listener.onItemPosition(position));
    }


    // getItemCount function returns the total number of items in the list.

    @Override
    public int getItemCount() {
        return itemList.size();
    }


    //ViewHolder class represents the layout for an individual item in the RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView location, price, shortDescription;
        ImageView imageView;


        //Initializes views for displaying item details.
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            location = itemView.findViewById(R.id.location);
            price = itemView.findViewById(R.id.price);
            shortDescription = itemView.findViewById(R.id.short_description);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
