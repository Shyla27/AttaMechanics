package com.example.attamechanics.Specialities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.attamechanics.Adapters.MyAdapter;
import com.example.attamechanics.Admin.ActiveappointmentsFragment;
import com.example.attamechanics.Admin.AllappointmentsFragment;
import com.example.attamechanics.R;
import com.google.android.material.tabs.TabLayout;

public class Brakes extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    FrameLayout frameLayout;
    Fragment fragment = null;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tabLayout = findViewById(R.id.tabLaout);
        frameLayout = findViewById(R.id.frameLayot);
        viewPager= findViewById(R.id.viewager);

        fragment = new ActiveappointmentsFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayot, fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();


        tabLayout.addTab(tabLayout.newTab().setText("Possible Car Problems"));
        tabLayout.addTab(tabLayout.newTab().setText("Service Fees"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);



//        final MyAdapter adapter = new MyAdapter(this,getSupportFragmentManager(), tabLayout.getTabCount());
//        viewPager.setAdapter(adapter);
final  BrakesAdapter brakesAdapter = new BrakesAdapter(getSupportFragmentManager(), tabLayout.getTabCount(), this);
viewPager.setAdapter(brakesAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                Fragment fragment = null;
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new BrakesCarProblemsFragment();
                        break;
                    case 1 :
                        fragment = new BrakeServiceFeeFragment();
                        break;
                }

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayot,fragment);
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                fragmentTransaction.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}