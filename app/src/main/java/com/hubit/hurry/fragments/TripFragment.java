package com.hubit.hurry.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.hubit.hurry.R;
import com.hubit.hurry.fragments.RentalPackage.pastTripListFragment;
import com.hubit.hurry.fragments.RentalPackage.todayTripListFragment;
import com.hubit.hurry.fragments.RentalPackage.upcomingTripListFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TripFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TripFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    View view;
    TabLayout tabLayout;
    ViewPager viewPager;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TripFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment rentalFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TripFragment newInstance(String param1, String param2) {
        TripFragment fragment = new TripFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_rental, container, false);

        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);

        viewPager = view.findViewById(R.id.viewPager);

        viewPagerAdapter adapter = new viewPagerAdapter(getChildFragmentManager());

        adapter.AddFragment(new upcomingTripListFragment(), "UPCOMING");
        adapter.AddFragment(new todayTripListFragment(), "CURRENT");
        adapter.AddFragment(new pastTripListFragment(), "PAST");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);


        return view;
    }
}