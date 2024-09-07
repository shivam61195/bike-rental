package com.example.bicyclerental.adapter;

import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bicyclerental.databinding.BicycleTileBinding;
import com.example.bicyclerental.models.Bicycle;
import com.squareup.picasso.Picasso;

public class MyViewHolder extends RecyclerView.ViewHolder {

    private final TextView bicycleName;
    private final TextView bicycleRate;
    private final TextView bicycleRating;

    private final ImageView bicycleImage;

    public MyViewHolder(@NonNull BicycleTileBinding binding) {
        super(binding.getRoot());
        bicycleName = binding.tvName;
        bicycleImage = binding.ivImage;
        bicycleRate = binding.tvRate;
        bicycleRating = binding.tvRating;
    }

    public void bind(Bicycle bicycle) {
        bicycleName.setText(bicycle.getName());
        bicycleRate.setText(""+bicycle.getRate()+" Rs");
        bicycleRating.setText("" + bicycle.getRating() + " ⭐");
        Log.e("TAG","Description: "+bicycle.getDescription());
        Log.e("TAG","Quantity: "+bicycle.getQuantity());

        String imageURL = bicycle.getImageURL();

        try {
            Picasso.get()
                    .load(imageURL)
                    .into(bicycleImage);
        }
        catch (Exception e) {
            Log.e("TAG","error: "+e.getMessage());
        }

    }

}
