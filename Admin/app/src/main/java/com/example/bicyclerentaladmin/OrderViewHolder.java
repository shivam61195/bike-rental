package com.example.bicyclerentaladmin;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bicyclerentaladmin.databinding.OrderTileBinding;
import com.example.bicyclerentaladmin.models.Booking;
import com.squareup.picasso.Picasso;


public class OrderViewHolder extends RecyclerView.ViewHolder {

    private final TextView bicycleName;
    private final TextView userName;
    private final ImageView imageView;

    private final TextView totalPrice;
    private final TextView hour;

    public OrderViewHolder(@NonNull OrderTileBinding binding) {
        super(binding.getRoot());
        bicycleName = binding.tvBicycleName;
        userName = binding.tvUserName;
        imageView = binding.ivImage;
        totalPrice = binding.tvPrice;
        hour = binding.tvHour;
    }


    public void bind(Booking booking) {
        bicycleName.setText(booking.getBicycleName());
        userName.setText("Order by " + booking.getUserName());
        totalPrice.setText("Total Price: "+booking.getRate()+" Rs");
        hour.setText("total hour: "+booking.getHour()+" hours");
        Picasso.get().load(booking.getImageURL()).into(imageView);
    }

}
