package com.metacoders.hurry.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.metacoders.hurry.Constants.constants;
import com.metacoders.hurry.R;
import com.metacoders.hurry.SignInController.Sign_in;
import com.metacoders.hurry.model.userModel;

import de.hdodenhof.circleimageview.CircleImageView;

public class profileFragment extends Fragment {


    View view;
    CircleImageView imageView;
    FirebaseAuth mauth;
    String name, uid;
    TextView nameTv, spentLifeTime, tripLifeTimeTv, tripThisMonthTv, phoneTv;

    Context context;

    public profileFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.profile_fragment, container, false);

        mauth = FirebaseAuth.getInstance();
        uid = FirebaseAuth.getInstance().getUid();
        context = view.getContext();
        imageView = view.findViewById(R.id.imageViewOnProfileFragment);
        nameTv = view.findViewById(R.id.nameTv);
        spentLifeTime = view.findViewById(R.id.lifeTimeSpentCount);
        tripLifeTimeTv = (TextView) view.findViewById(R.id.numberOFTotalTrip);
        tripThisMonthTv = view.findViewById(R.id.monty_trip_count);
        phoneTv = view.findViewById(R.id.phoneTv);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                mauth.signOut();

                Intent i = new Intent(getContext(), Sign_in.class);
                startActivity(i);
                getActivity().finish();

            }
        });


        return view;
    }

    private void dwldUserDataFromServer() {


        DatabaseReference mref = FirebaseDatabase.getInstance().getReference(constants.userProfileDb).child(uid);

        mref.keepSynced(true);

        mref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    userModel model = dataSnapshot.getValue(userModel.class);
                    nameTv.setText(model.getUserName());
                    spentLifeTime.setText(model.getUserTotalSpent().toString());
                    tripLifeTimeTv.setText(model.getUserTripCount());
                    Glide.with(context).load(model.getUserProPic())
                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                            .into(imageView);
                    phoneTv.setText(model.getPhone());


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //throw new RuntimeException("Test Crash");
    }


    @Override
    public void onStart() {
        super.onStart();
        dwldUserDataFromServer();
    }
}



