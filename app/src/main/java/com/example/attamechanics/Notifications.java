package com.example.attamechanics;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.attamechanics.Admin.AdminProfile;
import com.example.attamechanics.Mechs.MechProfile;
import com.example.attamechanics.Mechs.Mytasks;
import com.example.attamechanics.Users.NearbyGarages;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.onesignal.OneSignal;

public class Notifications extends AppCompatActivity {
    private static final String ONESIGNAL_APP_ID = "de917e11-b228-49ce-ab2e-d6b436fceb48";
   // BottomNavigationView bottomNavigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

//
//        bottomNavigation = findViewById(R.id.bottom_navigation);
//        bottomNavigation.setOnNavigationItemSelectedListener(item -> {
//            switch(item.getItemId())
//            {
//                case R.id.myaccount:
//                    startActivity(new Intent(getApplicationContext(), AdminProfile.class));
//                    overridePendingTransition(0,0);
//                    return true;
//                case R.id.notificationsmechs:
//                    return true;
//                case R.id.navigation_home:
//                    startActivity(new Intent(getApplicationContext(), Notifications.class));
//                    overridePendingTransition(0,0);
//                    return true;
//
//                case R.id.action_nearby:
//                    startActivity(new Intent(getApplicationContext(), NearbyGarages.class));
//                    overridePendingTransition(0,0);
//                    return true;
//
//            }
//            return false;
//        });


        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        // OneSignal Initialization
        OneSignal.initWithContext(this);
        OneSignal.setAppId(ONESIGNAL_APP_ID);

        // promptForPushNotifications will show the native Android notification permission prompt.
        // We recommend removing the following code and instead using an In-App Message to prompt for notification permission (See step 7)
        OneSignal.promptForPushNotifications();


    }
}