package com.metacoders.hurry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import ir.samanjafari.easycountdowntimer.CountDownInterface;
import ir.samanjafari.easycountdowntimer.EasyCountDownTextview;


public class Mobile_Varification extends AppCompatActivity {

    EasyCountDownTextview countDownTextView ;
    TextView  resendText ;
    String phone ;
    ImageButton backButton ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile__varification);

        getSupportActionBar().hide();

        //receiving  phone number from  the previous activity
        Intent  o = getIntent();
        phone = o.getStringExtra("PHONE") ;


        //init view
        countDownTextView = findViewById(R.id.easyCountDownTextview) ;
        resendText = findViewById(R.id.resendTv) ;
        backButton = findViewById(R.id.backbtn);



        //setting my views
        countDownTextView.setVisibility(View.VISIBLE);
        resendText.setVisibility(View.GONE);






        countDownTextView.setOnTick(new CountDownInterface() {
            @Override
            public void onTick(long time) {

            }

            @Override
            public void onFinish() {

                countDownTextView.setVisibility(View.GONE);
                resendText.setVisibility(View.VISIBLE);


            }
        });


        // setting on click
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();


            }
        });

        resendText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resendText.setVisibility(View.GONE);
                countDownTextView.setVisibility(View.VISIBLE);

                countDownTextView.startTimer();

            }
        });





    }
}
