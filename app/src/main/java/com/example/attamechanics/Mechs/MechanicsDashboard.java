package com.example.attamechanics.Mechs;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.attamechanics.Notifications;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.attamechanics.Garage.GarageProfile;
import com.example.attamechanics.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class MechanicsDashboard extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private TextView currentUser;

    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    //    RecyclerView recyclerView;
//    Adapter adapter;
    private static String TAG = "MainActivity";
    BottomNavigationView bottomNavigation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanics_dashboard);


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




        mAuth = FirebaseAuth.getInstance();

        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Atta Mechs");
    }
}