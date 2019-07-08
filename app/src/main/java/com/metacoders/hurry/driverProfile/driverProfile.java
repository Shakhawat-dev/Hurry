package com.metacoders.hurry.driverProfile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.metacoders.hurry.R;

public class driverProfile  extends AppCompatActivity {

String driverUID ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_profile);


        Intent i = getIntent();

        driverUID = i.getStringExtra("DRIVERUID") ;

        Toast.makeText(getApplicationContext()  , driverUID ,Toast.LENGTH_LONG)
                .show();





    }



}