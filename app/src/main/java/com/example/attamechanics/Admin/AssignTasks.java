package com.example.attamechanics.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.attamechanics.Adapters.MyAdapter;
import com.example.attamechanics.R;
import com.google.android.material.tabs.TabLayout;

public class AssignTasks extends AppCompatActivity {


    TabLayout tabLayout;
    ViewPager viewPager;
    FrameLayout frameLayout;
    Fragment fragment = null;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_tasks);

        tabLayout=(TabLayout)findViewById(R.id.tabLayout);
        frameLayout = (FrameLayout) findViewById(R.id.frameLayout);
        viewPager=(ViewPager)findViewById(R.id.viewPager);

        fragment = new ActiveappointmentsFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();


        tabLayout.addTab(tabLayout.newTab().setText("Active Requests"));
        tabLayout.addTab(tabLayout.newTab().setText("All Appointments"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final MyAdapter adapter = new MyAdapter(this,getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                Fragment fragment = null;
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new ActiveappointmentsFragment();
                        break;
                    case 1 :
                        fragment = new AllappointmentsFragment();
                        break;
                }

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout,fragment);
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