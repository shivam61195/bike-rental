package com.example.bicyclerentaladmin.bicycleadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bicyclerentaladmin.databinding.BicycleTileBinding;
import com.example.bicyclerentaladmin.models.BicycleData;

import java.util.List;

public class BicycleAdapter extends RecyclerView.Adapter<BicycleViewHolder> {

    Context context;
    List<BicycleData> bicycleDataList;

    public BicycleAdapter(Context context, List<BicycleData> bicycleDataList) {
        this.context = context;
        this.bicycleDataList = bicycleDataList;
    }

    @NonNull
    @Override
    public BicycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        BicycleTileBinding binding = BicycleTileBinding.inflate(layoutInflater);
        return new BicycleViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BicycleViewHolder holder, int position) {
        holder.bind(bicycleDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return bicycleDataList.size();
    }
}
