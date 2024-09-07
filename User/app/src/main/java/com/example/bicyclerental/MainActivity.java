package com.example.bicyclerental;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.bicyclerental.models.Bicycle;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {


    HomeFragment homeFragment = new HomeFragment();
    ProfileFragment profileFragment = new ProfileFragment();
    BicycleFragment bicycleFragment = new BicycleFragment();

    BottomNavigationView bottomNavigationView;

    List<Bicycle> bicycleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bicycleList = new ArrayList<>();
        retrieveDataFromFirebase();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Constants.setUserUID(user.getUid());


        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flFragment, homeFragment)
                .commit();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.home) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, homeFragment)
                    .commit();
            return true;
        }
        if (item.getItemId() == R.id.profile) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, profileFragment)
                    .commit();
            return true;
        }
        if (item.getItemId() == R.id.bicycle) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, bicycleFragment)
                    .commit();
            return true;
        }
        return false;
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

                Constants.setBicycleList(bicycleList);
                //loadDataToRV(bicycleList);
                // Now, bicycleList contains all the retrieved data
                // You can use it as needed
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "error: "+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}