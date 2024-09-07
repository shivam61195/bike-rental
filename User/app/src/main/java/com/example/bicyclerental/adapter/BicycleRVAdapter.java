package com.example.bicyclerental.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bicyclerental.DetailActivity;
import com.example.bicyclerental.databinding.BicycleTileBinding;
import com.example.bicyclerental.models.Bicycle;

import java.util.List;

public class BicycleRVAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private final Context context;
    private final List<Bicycle> bicycleList;

    public BicycleRVAdapter(Context context, List<Bicycle> bicycleList) {
        this.bicycleList = bicycleList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        BicycleTileBinding binding = BicycleTileBinding.inflate(layoutInflater);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(bicycleList.get(position));
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("DATA", bicycleList.get(position).getName());
            intent.putExtra("INDEX",position);
            context.startActivity(intent);
//            Toast.makeText(context, "Hello " + position + "clicked ", Toast.LENGTH_LONG).show();
        });
    }

    @Override
    public int getItemCount() {
        return bicycleList.size();
    }

}
