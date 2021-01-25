package com.metacoders.hurry.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.metacoders.hurry.Constants.constants;
import com.metacoders.hurry.R;
import com.metacoders.hurry.SignInController.Sign_in;
import com.metacoders.hurry.Utils.SharedPrefManager;
import com.metacoders.hurry.model.driverProfileModel;
import com.metacoders.hurry.model.userModel;

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
                String uid =  FirebaseAuth.getInstance().getUid() ;
                Log.d("TAG", "run: "+ uid);
                Intent i;
                if (muser != null) {
                    DatabaseReference mref = FirebaseDatabase.getInstance().getReference(constants.userProfileDb).child(uid);
                    mref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                userModel model = snapshot.getValue(userModel.class);
                                Gson gson = new Gson();
                                String ProfileData = gson.toJson(model);

                                Log.d("TAGE", "onDataChange 1: "+ ProfileData);
                                SharedPrefManager.getInstance(getApplicationContext())
                                        .userLogin(ProfileData);

                                Intent u = new Intent(getApplicationContext(), homePage.class);
                                startActivity(u);
                                finish();

                            } else {
                                Intent o = new Intent(getApplicationContext(), Sign_in.class);
                                startActivity(o);
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(getApplicationContext(), "Error : " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });


                } else {
                    i = new Intent(getApplicationContext(), Sign_in.class);
                    startActivity(i);
                    finish();
                }

            }
        }, 500);


    }
}