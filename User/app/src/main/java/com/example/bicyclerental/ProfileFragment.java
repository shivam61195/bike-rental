package com.example.bicyclerental;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bicyclerental.auth.LoginActivity;
import com.example.bicyclerental.databinding.FragmentProfileBinding;
import com.example.bicyclerental.models.User;
import com.example.bicyclerental.utils.MyProgressBar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;


public class ProfileFragment extends Fragment {

    FragmentProfileBinding binding = null;

    DatabaseReference reference = null;

    MyProgressBar myProgressBar = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);

        myProgressBar = new MyProgressBar(getContext());


        showUserData();

        binding.btnSignOut.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getContext(), LoginActivity.class));
        });

        binding.btnUpdate.setOnClickListener(v -> {
            if (isValidData()) {
                updateData(Objects.requireNonNull(binding.etName.getText()).toString(),
                        Objects.requireNonNull(binding.etEmail.getText()).toString());
            }
        });


        return binding.getRoot();
    }

    void updateData(String userName, String email) {
        myProgressBar.showProgressBar();

        String uid = FirebaseAuth.getInstance().getUid();

        if (uid != null) {
            reference = FirebaseDatabase.getInstance().getReference("user").child(uid);
        }


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
//                if (user != null) {
//                    Log.e("MYTAG", "user name: " + user.getUsername() + "\nuser mail: "
//                            + user.getEmail() + "\nuser password: " + user.getPassword());
//                } else {
//                    Log.e("MYTAG", "user is null");
//                }

                if (user != null) {
                    user.setUsername(userName);
                    user.setEmail(email);

                    try {
                        reference.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getContext(), "User Updated  Successfully", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getContext(), "Some thing went wrong", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } catch (Exception e) {
                        Toast.makeText(getContext(), "Some thing went wrong" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    myProgressBar.dismissProgressBar();
                } else {
                    Toast.makeText(getContext(), "Some thing went wrong", Toast.LENGTH_SHORT).show();
                    myProgressBar.dismissProgressBar();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "error: " + error, Toast.LENGTH_SHORT).show();
                myProgressBar.dismissProgressBar();
            }
        });


    }

    void showUserData() {
        myProgressBar.showProgressBar();

        String uid = FirebaseAuth.getInstance().getUid();
        if (uid != null) {
            reference = FirebaseDatabase.getInstance().getReference("user").child(uid);
        }

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                User user;
                user = snapshot.getValue(User.class);

                if (user != null) {
                    binding.etEmail.setText(user.getEmail());
                    binding.etName.setText(user.getUsername());
                }
                if (user != null) {
                    Log.e("MYTAG", "user name: " + user.getUsername() + "\nuser mail: " + user.getEmail() + "\nuser password: " + user.getPassword());
                } else {
                    Log.e("MYTAG", "user is null");
                }
                myProgressBar.dismissProgressBar();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "error: " + error, Toast.LENGTH_SHORT).show();
                myProgressBar.dismissProgressBar();
            }
        });

    }

    private Boolean isValidData() {
        if (Objects.requireNonNull(binding.etName.getText()).toString().isEmpty()) {
            binding.etName.setError("Enter Your Name");
            return false;
        }
        if (Objects.requireNonNull(binding.etEmail.getText()).toString().isEmpty()) {
            binding.etEmail.setError("Enter Your Email");
            return false;
        }
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}