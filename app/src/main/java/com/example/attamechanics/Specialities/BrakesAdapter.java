package com.example.attamechanics.Specialities;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


public class BrakesAdapter extends FragmentPagerAdapter {
    private Context myContext;
    int totalTabs;

    public BrakesAdapter(@NonNull FragmentManager fm, int behavior, Context myContext) {
        super(fm, behavior);
        this.myContext = myContext;
        this.totalTabs= behavior;


    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                BrakesCarProblemsFragment brakesCarProblemsFragment = new BrakesCarProblemsFragment();
                return brakesCarProblemsFragment;
            case 1:
                BrakeServiceFeeFragment brakeServiceFeeFragment= new BrakeServiceFeeFragment();
                return brakeServiceFeeFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 0;
    }
}
