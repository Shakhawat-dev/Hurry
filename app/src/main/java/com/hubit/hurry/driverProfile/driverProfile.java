package com.hubit.hurry.driverProfile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hubit.hurry.Activity.GalleryView;
import com.hubit.hurry.Constants.constants;
import com.hubit.hurry.R;
import com.hubit.hurry.model.driverProfileModel;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class driverProfile  extends AppCompatActivity {

String driverUID ;
DatabaseReference mref ;
CircleImageView circleImageView ;
TextView nameTextView , lic , mbl , carModel   ;
driverProfileModel Oldmodel = new driverProfileModel() ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_profile);
        lic = findViewById(R.id.lic) ;
        mbl = findViewById(R.id.mbl);
        carModel = findViewById(R.id.carModel) ;


        circleImageView = findViewById(R.id.pp) ;
        nameTextView = findViewById(R.id.drivername) ;

        Intent i = getIntent();
        driverUID = i.getStringExtra("DRIVERUID") ;
        mref = FirebaseDatabase.getInstance()
                .getReference(constants.driverProfileLink)
                .child(driverUID);

        mref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    driverProfileModel model = snapshot.getValue(driverProfileModel.class) ;
                    Glide.with(getApplicationContext())
                            .load(model.getProfile_picture())
                            .into(circleImageView) ;

                    Oldmodel = model ;

                    nameTextView.setText(model.getDriverName());
                    lic.setText(model.getCarLic());
                    mbl.setText(model.getDriverName());
                    carModel.setText(model.getCarModel());



                }
                else {
                    Toast.makeText(getApplicationContext() ,
                            "Error No Profile Found" ,
                            Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        findViewById(R.id.gallery_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // making the list
                ArrayList<String> arrayList = new ArrayList<String>();
                arrayList.add(Oldmodel.getProfile_picture())  ;
              //  arrayList.add(Oldmodel.getDriver_license_image())  ;
              //  arrayList.add(Oldmodel.getNid_card_image())  ;
              //  arrayList.add(Oldmodel.getFitness_license_image())  ;
              //  arrayList.add(Oldmodel.getTax_token_image())  ;
              //  arrayList.add(Oldmodel.getVehicle_reg_image())  ;
                arrayList.add(Oldmodel.getCarPics().getCar_back_image())  ;
                arrayList.add(Oldmodel.getCarPics().getCar_front_image())  ;
                arrayList.add(Oldmodel.getCarPics().getCar_inside_image())  ;
                arrayList.add(Oldmodel.getCarPics().getCar_side_image())  ;

                Intent p = new Intent(getApplicationContext() , GalleryView.class);
                p.putExtra( "LIST", arrayList) ;
                startActivity(p);


            }
        });



    }



}