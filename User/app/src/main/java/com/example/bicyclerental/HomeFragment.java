package com.example.bicyclerental;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bicyclerental.databinding.FragmentHomeBinding;
import com.example.bicyclerental.models.User;
import com.example.bicyclerental.utils.MyProgressBar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class HomeFragment extends Fragment {


    private FragmentHomeBinding binding = null;

    MyProgressBar myProgressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);

        myProgressBar = new MyProgressBar(getContext());

        loadData();

        binding.card.setOnClickListener(v -> {

            BicycleFragment bicycleFragment = new BicycleFragment();

            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.flFragment, bicycleFragment);
            transaction.addToBackStack(null); // Optionally, add to the back stack
            transaction.commit();
        });

        binding.trend.setOnClickListener(view -> {
            BicycleFragment bicycleFragment = new BicycleFragment();

            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.flFragment, bicycleFragment);
            transaction.addToBackStack(null); // Optionally, add to the back stack
            transaction.commit();
        });

        return binding.getRoot();
    }

    void loadData() {
        myProgressBar.showProgressBar();
        String uid = FirebaseAuth.getInstance().getUid();
        if (uid != null) {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("user").child(uid);


            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User user = snapshot.getValue(User.class);
                    if (user != null) {
                        try {
                            String name = user.getUsername();
                            binding.tvUserName.setText("Hello " + name + " 👋🏻 ");
                            Constants.setUserName(name);
                        } catch (NullPointerException e) {
                            Log.e("TAG", "error: " + e.getMessage());
                        }
                    }
                    myProgressBar.dismissProgressBar();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getContext(), "error: " + error, Toast.LENGTH_SHORT).show();
                    myProgressBar.dismissProgressBar();
                }
            });

        } else {
            Toast.makeText(getContext(), "some thing went wrong !!!", Toast.LENGTH_SHORT).show();
            myProgressBar.dismissProgressBar();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}