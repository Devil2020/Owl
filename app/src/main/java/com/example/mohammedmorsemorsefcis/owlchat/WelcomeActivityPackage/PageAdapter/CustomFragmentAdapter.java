package com.example.mohammedmorsemorsefcis.owlchat.WelcomeActivityPackage.PageAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class CustomFragmentAdapter extends FragmentPagerAdapter {
   FragmentManager fragmentManager ;
   ArrayList<Fragment> fragments;
    public CustomFragmentAdapter(FragmentManager fm , ArrayList<Fragment> list) {
        super(fm);
        fragmentManager=fm;
        fragments=list;
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
