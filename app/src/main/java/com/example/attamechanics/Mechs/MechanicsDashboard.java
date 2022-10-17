package com.example.attamechanics.Mechs;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.attamechanics.Admin.AdminMaps;
import com.example.attamechanics.Admin.AdminProfile;
import com.example.attamechanics.Admin.GoogleMaps;
import com.example.attamechanics.Auth.Login;
import com.example.attamechanics.Garage.AllGarages;
import com.example.attamechanics.MainActivity;
import com.example.attamechanics.Notifications;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.attamechanics.Garage.GarageProfile;
import com.example.attamechanics.R;
import com.example.attamechanics.Users.MapCustomer;
import com.example.attamechanics.Users.NearbyGarages;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class MechanicsDashboard extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private TextView currentUser;
    private MaterialCardView checktask, requestservice, chat;

    private static String TAG = "MechanicsDashboard";
    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanics_dashboard);

        mAuth = FirebaseAuth.getInstance();

        checktask= findViewById(R.id.tasksassigned);
        requestservice= findViewById(R.id.mechrequestservice);
        chat = findViewById(R.id.Chats);

        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Atta Mechs");

        bottomNavigation = findViewById(R.id.bottom_navigate);
        bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            switch(item.getItemId())
            {
                case R.id.mechsaccount:
                    startActivity(new Intent(getApplicationContext(), AdminProfile.class));
                    overridePendingTransition(0,0);
                    return true;
                case R.id.mechshome:
                    return true;


                case R.id.mytasks:
                    startActivity(new Intent(getApplicationContext(), MapCustomer.class));
                    overridePendingTransition(0,0);
                    return true;
            }
            return false;
        });

        requestservice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent garage = new Intent(getApplicationContext(), AllGarages.class);
                startActivity(garage);
                finish();
            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent garage = new Intent(getApplicationContext(), MechChatView.class);
                startActivity(garage);
                finish();
            }
        });


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
                Intent intent = new Intent(MechanicsDashboard.this, Login.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                return true;
            case R.id.nav_account:
                Intent intent1 = new Intent(MechanicsDashboard.this, MechProfile.class);
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

    public void onWorkerDetailsSortingChanged(Bundle b) {
    }
}
