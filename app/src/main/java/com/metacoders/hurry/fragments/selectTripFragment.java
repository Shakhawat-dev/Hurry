package com.metacoders.hurry.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.metacoders.hurry.Activity.rentCar;
import com.metacoders.hurry.R;

public class selectTripFragment extends Fragment {

    View view;
    FirebaseAuth mauth;
    MaterialCardView ambulacneCard, TruckCard;
    Context context;


    public selectTripFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        LinearLayout car;


        view = inflater.inflate(R.layout.home_grid_layout, container, false);

        context = view.getContext();
        car = (LinearLayout) view.findViewById(R.id.carLatout);
        ambulacneCard = view.findViewById(R.id.ambulanceID);
        mauth = FirebaseAuth.getInstance();
        TruckCard = view.findViewById(R.id.Trucks);

        //  Toast.makeText(getContext() , "UID : " + mauth.getUid()  , Toast.LENGTH_LONG ).show();

        // getting the fare
        car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, rentCar.class);
                startActivity(i);


            }
        });

        ambulacneCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog dialog = new AlertDialog.Builder(context).create();
                dialog.setTitle("Ambulance Request Sent !!");
                dialog.setCancelable(true);
                dialog.setIcon(R.drawable.ambulance_icon);
                dialog.setMessage("You Will Receive A Phone Call Soon..");
                dialog.show();
            }
        });

        TruckCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog dialog = new AlertDialog.Builder(context).create();

                dialog.setTitle("To Be Implemented");
                dialog.setCancelable(true);
                dialog.setMessage("Coming Soon ..");
                dialog.show();
            }
        });


        return view;
    }


}
