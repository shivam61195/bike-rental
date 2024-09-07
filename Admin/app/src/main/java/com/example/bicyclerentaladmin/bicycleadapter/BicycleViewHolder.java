package com.example.bicyclerentaladmin.bicycleadapter;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bicyclerentaladmin.databinding.BicycleTileBinding;
import com.example.bicyclerentaladmin.models.BicycleData;
import com.squareup.picasso.Picasso;

public class BicycleViewHolder extends RecyclerView.ViewHolder {

    private final TextView Name;
    private final TextView Rate;
    private final TextView Rating;
    private final ImageView imageView;

    public BicycleViewHolder(@NonNull BicycleTileBinding binding) {
        super(binding.getRoot());
        Name = binding.tvName;
        Rate = binding.tvRate;
        Rating = binding.tvRating;
        imageView = binding.ivImage;
    }

    public void bind(BicycleData bicycleData) {
        Name.setText(bicycleData.getName());
        Rate.setText("" + bicycleData.getRate()+" per hour");
        Rating.setText(bicycleData.getRating() + " ⭐");
        Picasso.get()
                .load(bicycleData.getImageURL())
                .into(imageView);
    }

}
