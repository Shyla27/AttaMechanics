package com.example.attamechanics.Adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.attamechanics.Admin.ActiveAppointments;
import com.example.attamechanics.Admin.AllAppointments;
import com.example.attamechanics.Admin.AssignTasks;

public class MyAdapter  extends FragmentPagerAdapter {
    private Context myContext;
    int totalTabs;

    public MyAdapter(Context context, @NonNull FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                AllAppointments allAppointments = new AllAppointments();
                return allAppointments;
            case 1:
                ActiveAppointments activeAppointments= new ActiveAppointments();
                return activeAppointments;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
