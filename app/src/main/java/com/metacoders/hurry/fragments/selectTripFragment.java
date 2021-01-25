package com.metacoders.hurry.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.metacoders.hurry.Activity.rentCar;
import com.metacoders.hurry.Activity.rentTruck;
import com.metacoders.hurry.Constants.constants;
import com.metacoders.hurry.R;
import com.metacoders.hurry.SignInController.completeProfilePage;
import com.metacoders.hurry.Utils.SharedPrefManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class selectTripFragment extends Fragment {

    View view;
    FirebaseAuth mauth;
    MaterialCardView  TruckCard;
    LinearLayout ambulacneCard;
    CardView compeleteProfileBtn;
    LinearLayout helpUs;
    String uid;

    Context context;


    public selectTripFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        LinearLayout car;


        view = inflater.inflate(R.layout.home_grid_layout, container, false);
        uid = FirebaseAuth.getInstance().getUid().toString();
        context = view.getContext();
        car = (LinearLayout) view.findViewById(R.id.carLatout);
        ambulacneCard = view.findViewById(R.id.ambulanceID);
        mauth = FirebaseAuth.getInstance();
        helpUs = view.findViewById(R.id.call_us);
        TruckCard = view.findViewById(R.id.Trucks);
        compeleteProfileBtn = view.findViewById(R.id.compeleteBtn);
        //  Toast.makeText(getContext() , "UID : " + mauth.getUid()  , Toast.LENGTH_LONG ).show();

        // getting the fare


        compeleteProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, completeProfilePage.class);
                startActivity(i);
            }
        });


        helpUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:01961666333"));
                startActivity(intent);

            }
        });

        car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (SharedPrefManager.getInstance(getContext()).getUser().isIs_completed()) {
                    Intent i = new Intent(context, rentCar.class);
                    startActivity(i);

                } else {
                    Toast.makeText(getContext(), "Account not Activated !!", Toast.LENGTH_SHORT).show();
                }


            }
        });

        ambulacneCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                AlertDialog dialog;

                builder.setTitle("Ambulance Request")
                        .setMessage("Do you want to send us request for ambulance ?")
                        .setPositiveButton("Send Request", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                sendAmbulanceRequsest();

                            }
                        }).setNegativeButton("Cancel", null);


                dialog = builder.create();
                dialog.show();

            }
        });

        TruckCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getContext(), rentTruck.class));

//                AlertDialog dialog = new AlertDialog.Builder(context).create();
//
//                dialog.setTitle("Error ");
//                dialog.setCancelable(true);
//                dialog.setMessage("Firebase Limit Exceeded ..");
//                dialog.show();
            }
        });


        return view;
    }

    private void sendAmbulanceRequsest() {

        Date todayDate = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String date = formatter.format(todayDate);
        DatabaseReference mref = FirebaseDatabase.getInstance().getReference(constants.ambulanceRequestLink);

        String pushKey = mref.push().getKey();

        HashMap<String, String> map = new HashMap<>();
        map.put("req_date",date );
        map.put("user_id", uid);
        map.put("post_id", pushKey);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        map.put("user_ph", user.getPhoneNumber().toString());
        map.put("status", "pending");

        mref.child(pushKey).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                AlertDialog dialog = new AlertDialog.Builder(context).create();
                dialog.setTitle("Ambulance Request Sent !!");
                dialog.setCancelable(true);
                dialog.setIcon(R.drawable.ambulance_icon);
                dialog.setMessage("You Will Receive A Phone Call Soon...");
                dialog.show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Error : try again !!" , Toast.LENGTH_SHORT).show();
            }
        }) ;



    }


    @Override
    public void onResume() {
        super.onResume();
        if (SharedPrefManager.getInstance(getContext()).getUser().isIs_completed()) {
            compeleteProfileBtn.setVisibility(View.GONE);
        } else {
            compeleteProfileBtn.setVisibility(View.VISIBLE);
        }

    }
}
