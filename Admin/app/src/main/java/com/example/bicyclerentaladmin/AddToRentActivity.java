package com.example.bicyclerentaladmin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import com.example.bicyclerentaladmin.databinding.ActivityAddToRentBinding;
import com.example.bicyclerentaladmin.models.BicycleData;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;
import java.util.UUID;

public class AddToRentActivity extends AppCompatActivity {

    private ActivityAddToRentBinding binding = null;

    private Uri selectedImageUri = null;


    Dialog progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddToRentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressBar = new Dialog(this);

        binding.btnRegisterBicycle.setOnClickListener(view -> {
            if (checkData()) {
                uploadData();
            } else {
                Toast.makeText(this, "Enter Proper Data", Toast.LENGTH_SHORT).show();
            }
        });

        binding.ivImage.setOnClickListener(view -> {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, 1);
        });

    }

    void uploadData() {

        if (selectedImageUri != null) {
            showProgressBar();
            // Upload the image to Firebase Storage
            StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("images/" + UUID.randomUUID().toString());
            storageRef.putFile(selectedImageUri).addOnSuccessListener(taskSnapshot -> {
                storageRef.getDownloadUrl().addOnSuccessListener(uri -> {

                    String imageURL = uri.toString();

                    BicycleData bicycleData = new BicycleData();

                    bicycleData.setName(Objects.requireNonNull(binding.etBicycleName.getText()).toString());
                    bicycleData.setRate(Float.parseFloat(Objects.requireNonNull(binding.etBicycleRate.getText()).toString()));
                    bicycleData.setRating(Float.parseFloat(Objects.requireNonNull(binding.etBicycleRating.getText()).toString()));
                    bicycleData.setDescription(Objects.requireNonNull(binding.etBicycleDesc.getText()).toString());
                    bicycleData.setQuantity(Integer.parseInt(Objects.requireNonNull(binding.etBicycleQuantity.getText()).toString()));
                    bicycleData.setImageURL(imageURL);

                    // Store the data in the Realtime Database
                    DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("bicycle");

                    databaseRef.push().setValue(bicycleData).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            dismissProgressBar();
                            Toast.makeText(this, "Data Added Successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            dismissProgressBar();
                            Toast.makeText(this, "Some Thing went wrong", Toast.LENGTH_SHORT).show();
                        }
                    });


                });
            }).addOnFailureListener(e -> {
                dismissProgressBar();
                Toast.makeText(this, "error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            });
        } else {
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            binding.ivImage.setImageURI(selectedImageUri);
        }
    }

    Boolean checkData() {

        if (selectedImageUri == null) {
            Toast.makeText(this, "Please Select an Image", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (binding.etBicycleName.getText().toString().isEmpty()) {
            binding.etBicycleName.setError("Please Enter a Name");
            return false;
        }
        if (binding.etBicycleRate.getText().toString().isEmpty()) {
            binding.etBicycleRate.setError("Please Enter a Name");
            return false;
        }
        if (binding.etBicycleRating.getText().toString().isEmpty()) {
            binding.etBicycleRating.setError("Please Enter a Name");
            return false;
        }
        if (binding.etBicycleDesc.getText().toString().isEmpty()) {
            binding.etBicycleDesc.setError("Please Enter a Name");
            return false;
        }
        if (binding.etBicycleQuantity.getText().toString().isEmpty()) {
            binding.etBicycleQuantity.setError("Enter Quantity ");
            return false;
        }
        return true;
    }

    public void showProgressBar() {
        progressBar.setContentView(R.layout.my_progress_bar);
        progressBar.show();
    }

    public void dismissProgressBar() {
        progressBar.dismiss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}