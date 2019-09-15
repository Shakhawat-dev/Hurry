package com.metacoders.hurry.fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.metacoders.hurry.R;

public class selectTripFragment  extends Fragment {

    View view;
    FirebaseAuth mauth ;


    public selectTripFragment() {

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


            LinearLayout car ;




        view = inflater.inflate(R.layout.home_grid_layout, container, false);


        car = (LinearLayout) view.findViewById(R.id.carLatout);

        mauth = FirebaseAuth.getInstance() ;


       Toast.makeText(getContext() , "UID : " + mauth.getUid()  , Toast.LENGTH_LONG )
      .show();

        // getting the fare
        car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i  = new Intent(getContext() , rentCar.class );
                startActivity(i);



            }
        });




        return view ;
    }


}
