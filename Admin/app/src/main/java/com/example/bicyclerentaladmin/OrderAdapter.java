package com.example.bicyclerentaladmin;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bicyclerentaladmin.databinding.OrderTileBinding;
import com.example.bicyclerentaladmin.models.Booking;
import com.example.bicyclerentaladmin.models.Order;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderViewHolder> {

    Context context;
    List<Booking> orderList;

    public OrderAdapter(Context context, List<Booking> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        OrderTileBinding binding = OrderTileBinding.inflate(layoutInflater);
        return new OrderViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        holder.bind(orderList.get(position));
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
}
