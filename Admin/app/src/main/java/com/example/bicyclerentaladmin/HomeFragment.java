package com.example.bicyclerentaladmin;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bicyclerentaladmin.bicycleadapter.ItemSpacingDecoration;
import com.example.bicyclerentaladmin.databinding.FragmentHomeBinding;
import com.example.bicyclerentaladmin.models.BicycleData;
import com.example.bicyclerentaladmin.models.Booking;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding = null;

    List<BicycleData> bicycleDataList;

    List<Booking> orderList;

    OrderAdapter orderAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        bicycleDataList = new ArrayList<>();

        orderList = new ArrayList<>();

        binding.btnAddRent.setOnClickListener(view -> {
            startActivity(new Intent(getContext(), AddToRentActivity.class));
        });

        orderAdapter = new OrderAdapter(getContext(),orderList);

        binding.orderRV.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.orderRV.addItemDecoration(new ItemSpacingDecoration(getContext(),16));
        binding.orderRV.setAdapter(orderAdapter);

        getBookedData();


        return binding.getRoot();
    }

    void getBookedData() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("booking");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                orderList.clear();

                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {

                    String userUID = userSnapshot.getKey(); // get the user UID

                    if (userUID != null) {
                        DatabaseReference userRef = reference.child(userUID);

                        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                //BicycleData bicycleData = snapshot.getValue(BicycleData.class);

                                Booking booking = snapshot.getValue(Booking.class);

                                Log.e("MY_TAG", "||||||" + booking.getBicycleName());
                                Log.e("MY_TAG", "||||||" + booking.getUserName());
                                Log.e("MY_TAG", "||||||" + booking.getImageURL());
                                Log.e("MY_TAG", "||||||" + booking.getRate());
                                Log.e("MY_TAG", "||||||" + booking.getHour());

                                orderList.add(booking);

                                Booking obj = new Booking();
                                obj.setBicycleName(booking.getBicycleName());
                                obj.setUserName(obj.getUserName());
                                obj.setImageURL(obj.getImageURL());
                                obj.setRate(obj.getRate());
                                obj.setHour(obj.getHour());

                                Log.e("MY_TAG", "||" + obj.getBicycleName());
                                Log.e("MY_TAG", "||" + obj.getUserName());
                                Log.e("MY_TAG", "||" + obj.getImageURL());
                                Log.e("MY_TAG", "||" + obj.getRate());
                                Log.e("MY_TAG", "||" + obj.getHour());


                                Log.e("MY_TAG_size", "size:" + orderList.size());
                                orderAdapter.notifyDataSetChanged();

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(getContext(), "error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "error: " + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}