package com.example.bicyclerental;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.bicyclerental.auth.LoginActivity;
import com.example.bicyclerental.databinding.ActivitySplashBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    ActivitySplashBinding binding = null;

    FirebaseUser user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.splashScreenLogo.playAnimation();

        user = FirebaseAuth.getInstance().getCurrentUser();

        Handler handler = new Handler();

        handler.postDelayed(() -> {

            if(user != null) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
            else {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }

        }, 2000);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}