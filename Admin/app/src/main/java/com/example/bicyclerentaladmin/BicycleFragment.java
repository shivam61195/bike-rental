package com.example.bicyclerentaladmin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bicyclerentaladmin.bicycleadapter.BicycleAdapter;
import com.example.bicyclerentaladmin.bicycleadapter.ItemSpacingDecoration;
import com.example.bicyclerentaladmin.databinding.FragmentBicycleBinding;


public class BicycleFragment extends Fragment {

    FragmentBicycleBinding binding = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBicycleBinding.inflate(getLayoutInflater());

        BicycleAdapter bicycleAdapter = new BicycleAdapter(getContext(), Constants.getBicycleDataList());
        binding.bicycleRV.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.bicycleRV.addItemDecoration(new ItemSpacingDecoration(getContext(),16));
        binding.bicycleRV.setAdapter(bicycleAdapter);


        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}