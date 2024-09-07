package com.example.bicyclerental.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.bicyclerental.MainActivity;
import com.example.bicyclerental.utils.MyProgressBar;
import com.example.bicyclerental.models.User;
import com.example.bicyclerental.databinding.ActivityRegisterBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding binding = null;

    // Firebase Instance For Authentication Purpose
    FirebaseAuth firebaseAuth = null;
    DatabaseReference reference = null;

    Dialog progressBar;

    MyProgressBar myProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initializing FIREBASE Instances
        firebaseAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("user");

        progressBar = new Dialog(this);

        myProgressBar = new MyProgressBar(this);

        //  -------------------------------- Click Handling --------------------------------
        binding.tvSignIn.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        });

        binding.btnRegister.setOnClickListener(v -> {
            if (validateCredentials()) {
                showProgressBar();
                String name = Objects.requireNonNull(binding.NameEt.getText()).toString();
                String email = Objects.requireNonNull(binding.emailEt.getText()).toString();
                String password = Objects.requireNonNull(binding.passET.getText()).toString();
                registerUser(name, email, password);
            } else {
                Toast.makeText(this, "Please Enter Valid Credentials", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void registerUser(String name, String email, String password) {

        try {
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {

                if (task.isSuccessful()) {
                    User user = new User(name, email, password);
                    String uid = FirebaseAuth.getInstance().getUid();
                    try {
                        if (uid != null) {
                            reference.child(uid).setValue(user).addOnCompleteListener(task1 -> {
                                if (task1.isSuccessful()) {
                                    dismissProgressBar();
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                } else {
                                    dismissProgressBar();
                                    Toast.makeText(this, "Some Thing Went Wrong", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            Toast.makeText(this, "Some Thing Went Wrong", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception exception) {
                        dismissProgressBar();
                        Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                } else if (task.isCanceled()) {
                    dismissProgressBar();
                    Toast.makeText(this, "Some Thing Went Wrong Try Again", Toast.LENGTH_SHORT).show();
                } else {
                    dismissProgressBar();
                    Toast.makeText(this, "Some Thing Went Wrong", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            dismissProgressBar();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private Boolean validateCredentials() {
        if (Objects.requireNonNull(binding.emailEt.getText()).toString().isEmpty()) {
            binding.emailEt.setError("Enter Your Email ID");
            return false;
        }
        if (Objects.requireNonNull(binding.passET.getText()).toString().isEmpty()) {
            binding.passET.setError("Enter Your Password");
            return false;
        }
        if (Objects.requireNonNull(binding.NameEt.getText()).toString().isEmpty()) {
            binding.NameEt.setError("Enter Your Email ID");
            return false;
        }
        if (Objects.requireNonNull(binding.confirmPassEt.getText()).toString().isEmpty()) {
            binding.confirmPassEt.setError("Enter Your Password");
            return false;
        }
        if (!binding.passET.getText().toString().equals(binding.confirmPassEt.getText().toString())) {
            Toast.makeText(this, "Password Should Match", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    void showProgressBar() {
        myProgressBar.showProgressBar();
    }

    void dismissProgressBar() {
        myProgressBar.dismissProgressBar();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }

}