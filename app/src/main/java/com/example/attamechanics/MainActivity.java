package com.example.attamechanics;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.model.Direction;
import com.example.attamechanics.Adapters.GaragesAdapter;
import com.example.attamechanics.Admin.AdminProfile;
import com.example.attamechanics.Admin.Appointments;
import com.example.attamechanics.Admin.GoogleMaps;
import com.example.attamechanics.Admin.MyMechanics;
import com.example.attamechanics.Admin.MyPrices;
import com.example.attamechanics.Auth.Login;
import com.example.attamechanics.Garage.Garageinfo;
import com.example.attamechanics.Mechs.MechProfile;
import com.example.attamechanics.Mechs.Mytasks;
import com.example.attamechanics.objects.DriverObject;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class MainActivity extends AppCompatActivity{

    BottomNavigationView bottomNavigation;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    GaragesAdapter garagesAdapter;
    LinearLayout profileCard;
    RelativeLayout appointments, carservice, team, prices;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // getdata();
        appointments = findViewById(R.id.appointments);
        carservice = findViewById(R.id.carService);
        team = findViewById(R.id.myTeam);
        prices = findViewById(R.id.myPrices);
        profileCard = findViewById(R.id.profileCard);
        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            switch(item.getItemId())
            {
                case R.id.myaccount:
                    startActivity(new Intent(getApplicationContext(), AdminProfile.class));
                    overridePendingTransition(0,0);
                    return true;
                case R.id.navigation_home:
                    return true;
                case R.id.action_nearby:
                    startActivity(new Intent(getApplicationContext(), GoogleMaps.class));
                    overridePendingTransition(0,0);
                    return true;
                case R.id.notify:

                    startActivity(new Intent(getApplicationContext(), Notifications.class));
                    overridePendingTransition(0, 0);
                    return true;
            }
            return false;
        });

        profileCard.setOnClickListener(view -> {
            Intent intent1 = new Intent(MainActivity.this, Garageinfo.class);
            startActivity(intent1);
            finish();
        });
        appointments.setOnClickListener(view -> {
            Intent intent1 = new Intent(MainActivity.this, Appointments.class);
            startActivity(intent1);
            finish();
        });
        carservice.setOnClickListener(view -> {
            Intent intent1 = new Intent(MainActivity.this, GoogleMaps.class);
            startActivity(intent1);
            finish();
        });

        team.setOnClickListener(view -> {
            Intent intent1 = new Intent(MainActivity.this, MyMechanics.class);
            startActivity(intent1);
            finish();
        });

        prices.setOnClickListener(view -> {
            Intent intent1 = new Intent(MainActivity.this, MyPrices.class);
            startActivity(intent1);
            finish();
        });
        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Atta Mechs");


    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_logout:
                FirebaseAuth.getInstance();
                Intent intent = new Intent(MainActivity.this, Login.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                return true;
            case R.id.nav_account:
                Intent intent1 = new Intent(MainActivity.this, AdminProfile.class);
                startActivity(intent1);
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation_menu, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

}