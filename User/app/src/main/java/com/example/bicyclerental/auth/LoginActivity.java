package com.example.bicyclerental.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.bicyclerental.MainActivity;
import com.example.bicyclerental.R;
import com.example.bicyclerental.databinding.ActivityLoginBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding = null;

    FirebaseAuth firebaseAuth = null;

    Dialog progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();


        progressBar = new Dialog(this);

        //  -------------------------------- Click Handling --------------------------------

        binding.tvSignUp.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
        });

        binding.btnSignIn.setOnClickListener(v -> {
            if (validateCredentials()) {
                String email = Objects.requireNonNull(binding.etEmail.getText()).toString();
                String password = Objects.requireNonNull(binding.etPass.getText()).toString();
                showProgressBar();
                loginUser(email, password);
            } else {
                Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void loginUser(String email, String password) {
        try {
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    dismissProgressBar();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                } else if (task.isCanceled()) {
                    dismissProgressBar();
                    Toast.makeText(LoginActivity.this, "Some thing went went wrong try Again ", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            dismissProgressBar();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }


    private Boolean validateCredentials() {
        if (Objects.requireNonNull(binding.etEmail.getText()).toString().isEmpty()) {
            binding.etEmail.setError("Enter Your Email ID");
            return false;
        }
        if (Objects.requireNonNull(binding.etPass.getText()).toString().isEmpty()) {
            binding.etPass.setError("Enter Your Password");
            return false;
        }
        return true;
    }

    void showProgressBar() {
        progressBar.setContentView(R.layout.my_progress_bar);
        progressBar.show();
    }

    void dismissProgressBar() {
        progressBar.dismiss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}