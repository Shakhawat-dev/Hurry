package com.metacoders.hurry.homeFragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.metacoders.hurry.R;

public class profileFragment extends Fragment {



        View view;

        public profileFragment() {

        }
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {




            view = inflater.inflate(R.layout.profile_fragment, container, false);




            return view ;
        }


    }



