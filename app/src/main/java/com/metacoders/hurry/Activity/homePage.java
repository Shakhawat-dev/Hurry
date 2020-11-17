package com.metacoders.hurry.Activity;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

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
    NavigationView navigationView;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);


        BottomNavigationView navView = findViewById(R.id.navView);

        navController = Navigation.findNavController(homePage.this, R.id.fragment_container_view_tag);

        NavigationUI.setupWithNavController(navView, navController);

        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.profileFragment) {
                    navController.navigate(R.id.profileFragment, null, getNavOptions());
                } else if (item.getItemId() == R.id.pastTripListFragment) {
                    navController.navigate(R.id.pastTripListFragment, null, getNavOptions());
                } else if (item.getItemId() == R.id.onGoingList) {
                    navController.navigate(R.id.onGoingList, null, getNavOptions());
                } else if (item.getItemId() == R.id.selectTripFragment) {
                    navController.navigate(R.id.selectTripFragment, null, getNavOptions());
                }


                return true;
            }

        });

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

    protected NavOptions getNavOptions() {

//        NavOptions navOptions = new NavOptions.Builder()
//                .setEnterAnim(R.anim.slide_in_right)
//                .setExitAnim(R.anim.slide_out_left)
//                .setPopEnterAnim(R.anim.slide_in_left)
//                .setPopExitAnim(R.anim.slide_out_right)
//
//                .build();

        NavOptions navOptions = new NavOptions.Builder()
                .setEnterAnim(R.anim.fade_in)
                .setExitAnim(R.anim.fade_out)
                .setPopEnterAnim(R.anim.fade_in)
                .setPopExitAnim(R.anim.fade_out)

                .build();

        return navOptions;
    }
}
