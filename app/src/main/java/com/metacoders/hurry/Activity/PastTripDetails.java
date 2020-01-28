package com.metacoders.hurry.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.metacoders.hurry.Constants.constants;
import com.metacoders.hurry.R;
import com.metacoders.hurry.model.driverProfileModel;
import com.metacoders.hurry.model.modelForCarRequest;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class PastTripDetails extends AppCompatActivity {

    TextView fromTv , toTv , dateTv , driverNameTv  , vehicleNameTv , vehicleTv     ;
    DatabaseReference mref ;
    String link;
    CircleImageView imageView  ;
    RatingBar ratingBar ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_trip_details);

        fromTv = findViewById(R.id.locationFromTripDetails) ;
        toTv= findViewById(R.id.locationToTripDetalis) ;
        dateTv = findViewById(R.id.dateOfTripDetails) ;
        driverNameTv= findViewById(R.id.driverNameTripDeatils2) ;
        vehicleNameTv= findViewById(R.id.vechileModel) ;
        imageView = findViewById(R.id.imageViewOnPastDeatil) ;
        ratingBar = findViewById(R.id.ratingBarpastTrip) ;





        Intent o = getIntent();
            link      =   o.getStringExtra("POSTID") ;

        mref = FirebaseDatabase.getInstance().getReference(constants.carRequestLink).child(link);





        mref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                modelForCarRequest   model =  dataSnapshot.getValue(modelForCarRequest.class) ;


                fromTv.setText(model.getFromLoc());
                toTv.setText(model.getToLoc());
                dateTv.setText(model.getTimeDate());
                driverNameTv.setText(model.getDriverName());

                downloadDriverData(model.getDriverId()) ;



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {



            }
        });






    }

    private void downloadDriverData(String driverId) {

     DatabaseReference   ref = FirebaseDatabase.getInstance().getReference(constants.driverProfileLink).child(driverId);

     ref.addListenerForSingleValueEvent(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

             driverProfileModel  model = dataSnapshot.getValue(driverProfileModel.class) ;

             Picasso.get().load(model.getProfile_picture().toString()).into(imageView);
             driverNameTv.setText(model.getDriverName());
             vehicleNameTv.setText(model.getCarLic());
             ratingBar.setRating(Float.valueOf(model.getDriverRating()));



         }

         @Override
         public void onCancelled(@NonNull DatabaseError databaseError) {

         }
     });




    }
}
