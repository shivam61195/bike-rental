package com.example.bicyclerentaladmin.profileadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bicyclerentaladmin.databinding.UserProfileTileBinding;
import com.example.bicyclerentaladmin.models.User;

import java.util.List;

public class ProfileRVAdapter extends RecyclerView.Adapter<ProfilesViewHolder> {

    private final Context context;
    private final List<User> userList;

    public ProfileRVAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public ProfilesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        UserProfileTileBinding binding = UserProfileTileBinding.inflate(layoutInflater);
        return new ProfilesViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfilesViewHolder holder, int position) {
        holder.bind(userList.get(position));
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}