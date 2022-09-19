package com.example.attamechanics.Auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.attamechanics.Admin.GoogleMaps;
import com.example.attamechanics.Garage.Documentation;
import com.example.attamechanics.Garage.EditGarageProfile;
import com.example.attamechanics.Garage.GarageSpeciality;
import com.example.attamechanics.Garage.Garageinfo;
import com.example.attamechanics.MainActivity;
import com.example.attamechanics.Mechs.MechProfile;
import com.example.attamechanics.R;
import com.example.attamechanics.Users.UserDashboard;
import com.example.attamechanics.Users.UserLogin;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        Thread background = new Thread() {
            public void run() {
                try {
                    // Thread will sleep for 5 seconds
                    sleep(5 * 1000);

                    // After 5 seconds redirect to another intent
                    Intent i = new Intent(getBaseContext(), FirstPage.class);
                    startActivity(i);

                    //Remove activity
                    finish();
                } catch (Exception e) {
                }
            }
        };
        // start thread
        background.start();
    }
}