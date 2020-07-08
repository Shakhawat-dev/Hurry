package com.metacoders.hurry.Activity;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.metacoders.hurry.R;
import com.metacoders.hurry.fragments.onGoingList;
import com.metacoders.hurry.fragments.pastTripListFragment;
import com.metacoders.hurry.fragments.profileFragment;
import com.metacoders.hurry.fragments.selectTripFragment;
import com.metacoders.hurry.fragments.viewPagerAdapter;

public class homePage extends AppCompatActivity {


//    TabLayout tabLayout  ;
//    AppBarLayout appBarLayout ;
//    ViewPager viewPager ;

    private NavController navController ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);


        BottomNavigationView navView = findViewById(R.id.navView);

        navController = Navigation.findNavController(homePage.this , R.id.fragment_container_view_tag) ;

        NavigationUI.setupWithNavController(navView , navController);



//        tabLayout= (TabLayout)findViewById(R.id.tabLayout) ;
//        //  appBarLayout = findViewById(R.id.appbarId) ;
//        viewPager =findViewById(R.id.viewPager) ;


//        viewPagerAdapter adapter = new viewPagerAdapter(getSupportFragmentManager());
//
//        adapter.AddFragment(new selectTripFragment() , "Plan Trip");
//        adapter.AddFragment(new onGoingList() , "Ongoing Trip");
//        adapter.AddFragment(new pastTripListFragment() , "Past Trip");
//        adapter.AddFragment(new profileFragment() , "Profile");
//
//
//        viewPager.setAdapter(adapter) ;
//        tabLayout.setupWithViewPager(viewPager) ;

    }
}
