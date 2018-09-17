package com.toolbartabs.toolbartabs.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.toolbartabs.toolbartabs.Fragments.FirstFragment;
import com.toolbartabs.toolbartabs.Fragments.SecondFragment;
import com.toolbartabs.toolbartabs.Fragments.ThirdFragment;

public class PagerAdapter extends FragmentStatePagerAdapter{

    private int numberOfTabs;


    public PagerAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {


        switch (position){
            case 0:
                return new FirstFragment();
            case 1:
                return new ThirdFragment();
            case 2:
                return new SecondFragment();
            case 3:
                return null;
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}