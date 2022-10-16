package com.example.attamechanics.Users;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.attamechanics.Garage.AllGarages;
import com.example.attamechanics.R;
import com.google.android.material.navigation.NavigationView;

public class UserDashboard extends AppCompatActivity {
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Button bookappointment, getservice;
    DrawerLayout drawerLayout;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);
        bookappointment = findViewById(R.id.tasksassigned);
        getservice = findViewById(R.id.mechrequestservice);
        navigationView = findViewById(R.id.navview);
        drawerLayout = findViewById(R.id.drawer);

        //adding customised toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //toggle button
        actionBarDrawerToggle = new ActionBarDrawerToggle(this , drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getservice.setOnClickListener(view -> {
            Intent appoint = new Intent(UserDashboard.this, NearbyGarages.class);
            startActivity(appoint);
        });

        bookappointment.setOnClickListener(view -> {
            Intent appoint = new Intent(UserDashboard.this, AllGarages.class);
            startActivity(appoint);
        });

        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId())
            {
                case R.id.nav_account:
                    Toast.makeText(getApplicationContext(),"Home",Toast.LENGTH_SHORT).show();
                    //close drawer
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;

                case R.id.nav_settings:
                    Toast.makeText(getApplicationContext(),"Profile",Toast.LENGTH_SHORT).show();
                    //close drawer
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;

                case R.id.nav_logout:
                    Toast.makeText(getApplicationContext(),"Settings",Toast.LENGTH_SHORT).show();
                    //close drawer
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;



            }

            return true;
        });
    }
}