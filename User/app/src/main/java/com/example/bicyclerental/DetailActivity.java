package com.example.bicyclerental;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bicyclerental.databinding.ActivityDetailBinding;
import com.example.bicyclerental.models.Bicycle;
import com.example.bicyclerental.models.Booking;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

public class DetailActivity extends AppCompatActivity {

    private ActivityDetailBinding binding = null;

    List<Bicycle> bicycleList;

    int INDEX;

    Dialog dialog;

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        String KEY = intent.getStringExtra("DATA");

        INDEX = intent.getIntExtra("INDEX", 0);

        Log.e("KEY", "data: " + KEY + "\n\n");

        bicycleList = Constants.getBicycleList();

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.book_tile);

        textView = dialog.findViewById(R.id.info);

        dialog.create();


        for (Bicycle b : bicycleList) {
            if (Objects.equals(b.getName(), KEY)) {

                binding.tvName.setText(b.getName());
                binding.tvDescriptions.setText(b.getDescription());
                binding.tvRate.setText("" + b.getRate() + " per hour");
                binding.tvRate.setText(b.getRating() + " ⭐ ");


                Picasso.get()
                        .load(b.getImageURL())
                        .into(binding.ivImage);

                Log.e("TAG", "name: " + b.getName());
                Log.e("TAG", "rate: " + b.getRate());
                Log.e("TAG", "rating: " + b.getRating());
                Log.e("TAG", "quantity: " + b.getQuantity());
                Log.e("TAG", "desc: " + b.getDescription());
                Log.e("TAG", "imageURL: " + b.getImageURL());
            }
        }

        binding.btnBookNow.setOnClickListener(view -> {
            if (binding.etHour.getText().toString().isEmpty()) {
                Toast.makeText(this, "Enter hour", Toast.LENGTH_SHORT).show();
            } else {
                BookBicycle();
            }
        });


    }

    void BookBicycle() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("booking").child(Constants.getUserUID());

        Bicycle b = bicycleList.get(INDEX);

        Booking booking = new Booking();

        booking.setBicycleName(b.getName());
        booking.setUserName(Constants.getUserName());
        booking.setImageURL(b.getImageURL());

        int hour = Integer.parseInt(binding.etHour.getText().toString());

        float finalRate = hour * b.getRate();
        textView.setText("Total cost: " + finalRate + " Rs");

        booking.setHour(hour);
        booking.setRate(finalRate);

        reference.setValue(booking).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                int quantity = b.getQuantity();
                Log.e("TAG", "" + quantity);
                b.setQuantity(quantity - 1);
                Log.e("TAG", "" + b.getQuantity());

                dialog.show();


            }
        });


        Log.e("TAG", "name: " + b.getName());
        Log.e("TAG", "rate: " + b.getRate());
        Log.e("TAG", "rating: " + b.getRating());
        Log.e("TAG", "quantity: " + b.getQuantity());
        Log.e("TAG", "desc: " + b.getDescription());
        Log.e("TAG", "imageURL: " + b.getImageURL());

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }

}