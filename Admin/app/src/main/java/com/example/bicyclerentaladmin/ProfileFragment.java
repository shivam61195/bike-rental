package com.example.bicyclerentaladmin;

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
import com.example.bicyclerentaladmin.databinding.FragmentProfileBinding;
import com.example.bicyclerentaladmin.models.User;
import com.example.bicyclerentaladmin.profileadapter.ProfileRVAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding = null;

    List<User> users;

    private ProfileRVAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentProfileBinding.inflate(inflater, container, false);

        users = new ArrayList<>();

//        loadData();

        try {
            adapter = new ProfileRVAdapter(getContext(), Constants.getUserList());

            binding.profileRV.setLayoutManager(new LinearLayoutManager(getContext()));
            binding.profileRV.addItemDecoration(new ItemSpacingDecoration(getContext(),16));
            binding.profileRV.setAdapter(adapter);

            Log.e("TAG","size: "+users.size());

        } catch (NullPointerException e) {
            Log.e("TAG", "NULL");
        }

//        loadData();


        return binding.getRoot();
    }

//    void loadData() {
//
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("user");
//
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                users.clear();
//
//                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
//                    // Loop through each child (user) under the "users" reference
//                    String userUid = userSnapshot.getKey(); // User UID
//
//                    if (userUid != null) {
//                        reference.child(userUid).addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                                User user = snapshot.getValue(User.class);
//
//                                User demoUser = new User();
//
//                                demoUser.setEmail(user.getEmail());
//                                demoUser.setUsername(user.getUsername());
//                                demoUser.setPassword(user.getPassword());
//
//                                users.add(demoUser);
//
//                                Log.e("TAG", "user name: " + user.getUsername());
//                                Log.e("TAG", "user mail: " + user.getEmail());
//                                Log.e("TAG", "user pass: " + user.getPassword());
//
//
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError error) {
//                                Toast.makeText(getContext(), "error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                    }
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(getContext(), "error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }

    void loadData() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("user");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Clear the list before adding new data
                users.clear();

                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String userUid = userSnapshot.getKey(); // User UID

                    if (userUid != null) {
                        DatabaseReference userRef = reference.child(userUid);
                        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                User user = snapshot.getValue(User.class);

                                if (user != null) {
                                    users.add(user);
                                    Log.e("TAG", "user name: " + user.getUsername());
                                    Log.e("TAG", "user mail: " + user.getEmail());
                                    Log.e("TAG", "user pass: " + user.getPassword());
                                }

                                Log.e("TAG","size: "+users.size());
                                // Notify your adapter or UI that the data has changed
                                // adapter.notifyDataSetChanged();
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



//    List<User> getData() {
//        List<User> mList = new ArrayList<>();
//
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("user");
//
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
//                    // Loop through each child (user) under the "users" reference
//                    String userUid = userSnapshot.getKey(); // User UID
//
//                    if (userUid != null) {
//                        reference.child(userUid).addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                User user = new User();
//
//                                user = snapshot.getValue(User.class);
////                                Log.e("TAG", "user name: " + user.getUsername());
////                                Log.e("TAG", "user mail: " + user.getEmail());
////                                Log.e("TAG", "user pass: " + user.getPassword());
//                                mList.add(user);
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError error) {
//                                Toast.makeText(getContext(), "error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                    }
//
//                    // Do something with the user data, e.g., display it in your admin app
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(getContext(), "error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        for (User u1 : mList) {
//            Log.e("TAG", "user name: " + u1.getUsername());
//            Log.e("TAG", "user mail: " + u1.getEmail());
//            Log.e("TAG", "user pass: " + u1.getPassword());
//        }
//
//        return mList;
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}