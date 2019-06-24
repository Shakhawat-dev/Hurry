package com.metacoders.hurry.homeFragments;




import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class viewPagerAdapter extends FragmentPagerAdapter {
        private  final List<Fragment> fragmentList = new ArrayList<>();
        private  final  List<String>fragmeantListTitles = new ArrayList<>();

    public viewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return fragmentList.get(i);
    }

    @Override
    public int getCount() {
        return fragmeantListTitles.size();

    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmeantListTitles.get(position);
    }

    public void  AddFragment(Fragment fragment , String Title){
        fragmentList.add(fragment);
        fragmeantListTitles.add(Title);

    }
 }
