package com.metacoders.hurry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Trip_Running_details extends AppCompatActivity {

String driverName , carModel  , fromLoc , toLoc , fare , time , postID , driverID   , driverNottificationID ;
EditText description ;
Button submit  ;


TextView DriverNAME , CARMODEL ,drivername2  , FROMLOC  ,TOLOC , FARE ,TIME , POSTID , DRIVERID , DRIVERNOTTIFICATIONID  ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip__running_details);

        getSupportActionBar().setTitle("Finish Your Trip");

        /*                       o.putExtra("DRIVERNAME" , getItem(postion).getDriverName()) ;
                                o.putExtra("CARMODEL", getItem(postion).getCarModl()) ;
                                o.putExtra("FORMLOC", getItem(postion).getFromLoc()) ;
                                o.putExtra("TOLOC", getItem(postion).getToLoc()) ;
                                o.putExtra("FARE", getItem(postion).getFare()) ;
                                o.putExtra("TIME", getItem(postion).getTimeDate()) ;
                                o.putExtra("POSTID", getItem(postion).getPostId()) ;
                                o.putExtra("DRIVERUID", getItem(postion).getDriverId()) ;
                                o.putExtra("DRIVERNOTIFICATIONID", getItem(postion).getDriverNotificationID()) ;

        */
        String action;
        Intent i = getIntent();
        driverName =  i.getStringExtra("DRIVERNAME" ) ;
        carModel =  i.getStringExtra("CARMODEL")  ;
        fromLoc =  i.getStringExtra("FORMLOC") ;
        toLoc =  i.getStringExtra("TOLOC") ;
        fare =  i.getStringExtra("FARE") ;
        time =  i.getStringExtra("TIME");
        postID =  i.getStringExtra("POSTID") ;
        driverID  =  i.getStringExtra("DRIVERUID")  ;
        driverNottificationID  =  i.getStringExtra("DRIVERNOTIFICATIONID");


        //init The View ;
        drivername2 = findViewById(R.id.driverNameTripDeatils2);
        DriverNAME =findViewById(R.id.driverNameTripDeatils)  ;
        CARMODEL=findViewById(R.id.carModelTripDetails)  ;
        FROMLOC =findViewById(R.id.locationFromTripDetails) ;
        TOLOC=findViewById(R.id.locationToTripDetalis) ;
        FARE=findViewById(R.id.priceViewInTripDetails) ;
        TIME =findViewById(R.id.dateOfTripDetails);
        description = findViewById(R.id.feedBackDetails);
        submit = findViewById(R.id.sumbutBTnTripdetails);


        //setting the data to the views ;

        DriverNAME.setText(driverName);
        drivername2.setText(driverName);
        CARMODEL.setText(carModel);
        FROMLOC.setText(fromLoc);
        TOLOC.setText(toLoc);
        TIME.setText(time);
        FARE.setText(fare);




        // listening for submit button click
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String tripDetails = description.getText().toString() ;




            }
        });










    }
}
