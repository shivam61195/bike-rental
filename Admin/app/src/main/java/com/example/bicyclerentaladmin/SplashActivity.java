package com.example.bicyclerentaladmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.example.bicyclerentaladmin.auth.LoginActivity;
import com.example.bicyclerentaladmin.models.BicycleData;
import com.example.bicyclerentaladmin.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends AppCompatActivity {

    FirebaseUser user = null;

    List<BicycleData> bicycleList;
    List<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        bicycleList = new ArrayList<>();
        retrieveDataFromFirebase();

        users = new ArrayList<>();
        loadData();

        user = FirebaseAuth.getInstance().getCurrentUser();

        Handler handler = new Handler();


        handler.postDelayed(() -> {

            if (user != null) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            } else {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }

        }, 3500);

    }

    private void retrieveDataFromFirebase() {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("bicycle");

        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                bicycleList.clear(); // Clear the list to avoid duplicates

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    BicycleData bicycleData = snapshot.getValue(BicycleData.class);

                    Log.e("TAG", "1: " + bicycleData.getName());
                    Log.e("TAG", "1: " + bicycleData.getDescription());
                    Log.e("TAG", "1: " + String.valueOf(bicycleData.getRate()));
                    Log.e("TAG", "1: " + String.valueOf(bicycleData.getRating()));
                    Log.e("TAG", "1: " + String.valueOf(bicycleData.getQuantity()));
                    Log.e("TAG", "1: " + bicycleData.getImageURL());

                    if (bicycleData != null) {
                        // Convert BicycleData to Bicycle and add it to the list
                        BicycleData bicycle = new BicycleData();

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

                Constants.setBicycleDataList(bicycleList);
                //loadDataToRV(bicycleList);
                // Now, bicycleList contains all the retrieved data
                // You can use it as needed
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

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

                                Constants.setUserList(users);

                                Log.e("TAG", "size: " + users.size());
                                // Notify your adapter or UI that the data has changed
                                // adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(getApplicationContext(), "error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}