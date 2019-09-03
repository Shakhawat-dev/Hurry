package com.metacoders.hurry.fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.metacoders.hurry.R;
import com.metacoders.hurry.SignInController.Sign_in;

import de.hdodenhof.circleimageview.CircleImageView;

public class profileFragment extends Fragment {



        View view;
        CircleImageView imageView ;
        FirebaseAuth mauth ;

        public profileFragment() {

        }
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {




            view = inflater.inflate(R.layout.profile_fragment, container, false);

            mauth = FirebaseAuth.getInstance();


            imageView = view.findViewById(R.id.imageViewOnProfileFragment) ;




imageView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {


        mauth.signOut();
        String action;
        Intent i  = new Intent(getContext()  , Sign_in.class);

        startActivity(i);
      getActivity().finish() ;

    }
});



            return view ;
        }


    }



