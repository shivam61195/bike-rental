package com.example.bicyclerental;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bicyclerental.adapter.BicycleRVAdapter;
import com.example.bicyclerental.adapter.ItemSpacingDecoration;
import com.example.bicyclerental.databinding.FragmentBicycleBinding;
import com.example.bicyclerental.models.Bicycle;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class BicycleFragment extends Fragment {

    FragmentBicycleBinding binding = null;

    private BicycleRVAdapter bicycleRVAdapter;

    private List<Bicycle> bicycleList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentBicycleBinding.inflate(inflater, container, false);

        bicycleList = new ArrayList<>();

        loadDataToRV(Constants.getBicycleList());

//        retrieveDataFromFirebase();


        return binding.getRoot();
    }


    private void retrieveDataFromFirebase() {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("bicycle");

        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                bicycleList.clear(); // Clear the list to avoid duplicates

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    Bicycle bicycleData = snapshot.getValue(Bicycle.class);

                    Log.e("TAG", "1: " + bicycleData.getName());
                    Log.e("TAG", "1: " + bicycleData.getDescription());
                    Log.e("TAG", "1: " + String.valueOf(bicycleData.getRate()));
                    Log.e("TAG", "1: " + String.valueOf(bicycleData.getRating()));
                    Log.e("TAG", "1: " + String.valueOf(bicycleData.getQuantity()));
                    Log.e("TAG", "1: " + bicycleData.getImageURL());

                    if (bicycleData != null) {
                        // Convert BicycleData to Bicycle and add it to the list
                        Bicycle bicycle = new Bicycle();

                        bicycle.setName(bicycleData.getName());
                        bicycle.setDescription(bicycleData.getDescription());
                        bicycle.setQuantity(bicycleData.getQuantity());
                        bicycle.setRate(bicycleData.getRate());
                        bicycle.setImageURL(bicycleData.getImageURL());
                        bicycle.setRating(bicycleData.getRating());

                        Log.e("TAG", bicycle.getName());
                        Log.e("TAG", bicycle.getDescription());
                        Log.e("TAG", String.valueOf(bicycle.getRate()));
                        Log.e("TAG", String.valueOf(bicycle.getRating()));
                        Log.e("TAG", String.valueOf(bicycle.getQuantity()));
                        Log.e("TAG", bicycle.getImageURL());


                        bicycleList.add(bicycle);
                    }
                }

                loadDataToRV(bicycleList);
                // Now, bicycleList contains all the retrieved data
                // You can use it as needed
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "error: "+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    void loadDataToRV(List<Bicycle> mList) {
        bicycleRVAdapter = new BicycleRVAdapter(getContext(), mList);

        binding.bicycleRV.setLayoutManager(new LinearLayoutManager(getContext()));

        binding.bicycleRV.addItemDecoration(new ItemSpacingDecoration(getContext(), 16));

        binding.bicycleRV.setAdapter(bicycleRVAdapter);
    }


//    List<Bicycle> getData() {
//        List<Bicycle> list = new ArrayList<>();
//        list.add(new Bicycle("Demo", R.drawable.new_bg5, 100, 4.5f, "some text"));
//        list.add(new Bicycle("Demo", R.drawable.new_bg5, 100, 4.5f, ""));
//        list.add(new Bicycle("Demo", R.drawable.new_bg5, 100, 4.5f, ""));
//        list.add(new Bicycle("Demo", R.drawable.new_bg5, 100, 4.5f, ""));
//        list.add(new Bicycle("Demo", R.drawable.new_bg5, 100, 4.5f, ""));
//        list.add(new Bicycle("Demo", R.drawable.new_bg5, 100, 4.5f, ""));
//        list.add(new Bicycle("Demo", R.drawable.new_bg5, 100, 4.5f, ""));
//        list.add(new Bicycle("Demo", R.drawable.new_bg5, 100, 4.5f, ""));
//        list.add(new Bicycle("Demo", R.drawable.new_bg5, 100, 4.5f, ""));
//        list.add(new Bicycle("Demo", R.drawable.new_bg5, 100, 4.5f, ""));
//        list.add(new Bicycle("Demo", R.drawable.new_bg5, 100, 4.5f, ""));
//        return list;
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}