package com.hubit.hurry.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
import com.hubit.hurry.Constants.constants;
import com.hubit.hurry.R;
import com.hubit.hurry.SignInController.Sign_in;
import com.hubit.hurry.model.CompeletedList;
import com.hubit.hurry.model.userModel;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import de.hdodenhof.circleimageview.CircleImageView;

public class profileFragment extends Fragment {


    View view;
    CircleImageView imageView;
    FirebaseAuth mauth;
    String name, uid;
    TextView nameTv, spentLifeTime, tripLifeTimeTv, tripThisMonthTv, phoneTv;
    int total = 0;
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


        view.findViewById(R.id.terms).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link = "https://docs.google.com/document/d/1MJRrB0aGfRbYeCoNJk5c3iYjBpt7zVCL4C8yq6kQ-d8/edit?usp=sharing";
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                startActivity(browserIntent);

            }
        });

        view.findViewById(R.id.privacy_policy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link = "https://docs.google.com/document/d/1MJRrB0aGfRbYeCoNJk5c3iYjBpt7zVCL4C8yq6kQ-d8/edit?usp=sharing";
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                startActivity(browserIntent);

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

    private void loadCounter(String uid) {
        total = 0;
        DatabaseReference mref = FirebaseDatabase.getInstance().getReference(constants.userProfileDb).child(uid).child("compeletedList");
        mref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    tripLifeTimeTv.setText(snapshot.getChildrenCount() + "");
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        CompeletedList post = postSnapshot.getValue(CompeletedList.class);
                        try {
                            total += Integer.parseInt(post.getFareGained());
                            //   Log.d("TAG", "onDataChange: " + total) ;
                            //   Log.d("TAG", "onDataChange: " + post.getFareGained()) ;
                        } catch (Exception e) {
                            // Log.d("TAG", "onDataChange: " + e.getMessage()) ;
                        }

                    }
                    // Log.d("TAG", "onDataChange: " + total) ;
                    spentLifeTime.setText(total + " ");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    @Override
    public void onStart() {
        super.onStart();
        dwldUserDataFromServer();
        loadCounter(uid);
    }

    public static class StringFormatter {

        // convert UTF-8 to internal Java String format
        public static String convertUTF8ToString(String s) {
            String out = null;
            try {
                out = new String(s.getBytes("ISO-8859-1"), "UTF-8");
            } catch (java.io.UnsupportedEncodingException e) {
                return null;
            }
            return out;
        }

        // convert internal Java String format to UTF-8
        public static String convertStringToUTF8(String s) {
            String out = null;
            try {
                out = new String(s.getBytes("UTF-8"), "ISO-8859-1");
            } catch (java.io.UnsupportedEncodingException e) {
                return null;
            }
            return out;
        }

    }
}




