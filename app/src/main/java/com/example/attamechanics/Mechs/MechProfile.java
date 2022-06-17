package com.example.attamechanics.Mechs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.attamechanics.Notifications;
import com.example.attamechanics.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MechProfile extends AppCompatActivity {

    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mech_profile);


        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            switch(item.getItemId())
            {
                case R.id.mechsaccount:
                    startActivity(new Intent(getApplicationContext(), MechProfile.class));
                    overridePendingTransition(0,0);
                    return true;
                case R.id.mechshome:
                    return true;
                case R.id.mytasks:
                    startActivity(new Intent(getApplicationContext(), Mytasks.class));
                    overridePendingTransition(0,0);
                    return true;
                case R.id.notificationsmechs:

                    startActivity(new Intent(getApplicationContext(), Notifications.class));
                    overridePendingTransition(0, 0);
                    return true;
            }
            return false;
        });
    }
}