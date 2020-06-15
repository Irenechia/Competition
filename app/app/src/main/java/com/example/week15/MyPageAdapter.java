package com.example.week15;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


public class MyPageAdapter extends FragmentPagerAdapter {

    public String[] titles = new String[]{"First","Second","Third"};

    public MyPageAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position==0){
            return new FirstFragment();
        }else if (position==1){
            return new SecondFragment();
        }else {
            return new ThirdFragment();
        }
    }

    public CharSequence getPageTitle(int position){
        return titles[position];
    }

    @Override
    public int getCount() {
        return 3;
    }
}
