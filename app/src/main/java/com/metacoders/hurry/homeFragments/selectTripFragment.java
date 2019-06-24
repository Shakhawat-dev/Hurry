package com.metacoders.hurry.homeFragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.metacoders.hurry.R;

public class selectTripFragment  extends Fragment {

    View view;

    public selectTripFragment() {

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


            LinearLayout car ;




        view = inflater.inflate(R.layout.home_grid_layout, container, false);


        car = (LinearLayout) view.findViewById(R.id.carLatout);



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
