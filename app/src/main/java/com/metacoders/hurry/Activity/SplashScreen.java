package com.metacoders.hurry.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.metacoders.hurry.R;
import com.metacoders.hurry.SignInController.Sign_in;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseUser muser = FirebaseAuth.getInstance().getCurrentUser();
                Intent i;
                if (muser != null) {
                    i = new Intent(getApplicationContext(), homePage.class);
                } else {
                    i = new Intent(getApplicationContext(), Sign_in.class);
                }
                startActivity(i);
                finish();
            }
        }, 1000);


    }
}