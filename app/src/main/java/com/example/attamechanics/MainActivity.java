package com.example.attamechanics;

import static com.example.attamechanics.Utils.Constants.TAG;
import static com.example.attamechanics.Utils.Constants.USER;

import android.app.Dialog;
import android.app.LauncherActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.attamechanics.Adapters.GaragesAdapter;
import com.example.attamechanics.Adapters.User;
import com.example.attamechanics.Admin.AdminMaps;
import com.example.attamechanics.Admin.AdminProfile;
import com.example.attamechanics.Admin.AdminSettings;
import com.example.attamechanics.Admin.AllAppointments;
import com.example.attamechanics.Admin.AppointmentDets;
import com.example.attamechanics.Admin.AttaPay;
import com.example.attamechanics.Admin.CarServices;
import com.example.attamechanics.Admin.Chat;
import com.example.attamechanics.Admin.ChatView;
import com.example.attamechanics.Admin.EmployeeDetails;
import com.example.attamechanics.Admin.GarageAppointments;
import com.example.attamechanics.Admin.GarageSettings;
import com.example.attamechanics.Admin.GoogleMaps;
import com.example.attamechanics.Admin.HistoryActivity;
import com.example.attamechanics.Admin.MyGarage;
import com.example.attamechanics.Admin.MyPrices;
import com.example.attamechanics.Admin.MyTasks;
import com.example.attamechanics.Admin.Payments;
import com.example.attamechanics.Admin.PayoutActivity;
import com.example.attamechanics.Auth.FirstPage;
import com.example.attamechanics.Auth.Signup;
import com.example.attamechanics.Fragments.ServicesFragment;
import com.example.attamechanics.Garage.Garageinfo;
import com.example.attamechanics.Mechs.MechChatList;
import com.example.attamechanics.Mechs.MechChatView;
import com.example.attamechanics.Specialities.Brakes;
import com.example.attamechanics.Users.NearbyGarages;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    CardView bankCard, folderCard, ideasCard, filesCard;
    private Toolbar toolbar;
    BottomNavigationView bottomNavigation;
    TextView mUsername, mLogout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bankCard = findViewById(R.id.bankCard);
        folderCard = findViewById(R.id.services);
        ideasCard = findViewById(R.id.ideasCard);
        filesCard = findViewById(R.id.filesCard);
        Toolbar toolbar = findViewById(R.id.toolbar);

        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.myaccount:
                    startActivity(new Intent(getApplicationContext(), AdminProfile.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.navigation_home:
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    overridePendingTransition(0, 0);

                    return true;
//                case R.id.notificationsmechs:
//                    startActivity(new Intent(getApplicationContext(), Notifications.class));
//                    overridePendingTransition(0, 0);
//
//                    return true;

                case R.id.chats:
                    startActivity(new Intent(getApplicationContext(), Chat.class));
                    overridePendingTransition(0, 0);

                    return true;
                case R.id.action_nearby:
                    startActivity(new Intent(getApplicationContext(), AdminMaps.class));
                    overridePendingTransition(0, 0);
                    return true;

            }
            return false;
        });

        bankCard.setOnClickListener(this);
        folderCard.setOnClickListener(this);
        ideasCard.setOnClickListener(this);
        filesCard.setOnClickListener(this);

//
//        setSupportActionBar(toolbar);
//        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setTitle("Atta Mechanics");
        //mAuth = FirebaseAuth.getInstance();
//
//
//
//        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.open, R.string.close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();
////
        //   NavigationView navigationView = findViewById(R.id.nav_view);
        //  navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);


//        ImageView mDrawerButton = findViewById(R.id.drawerButton);
//        mDrawerButton.setOnClickListener(v -> drawer.openDrawer(Gravity.LEFT));
//        mLogout = findViewById(R.id.logout);
//        mLogout.setOnClickListener(v -> logOut());

    }

    private void logOut() {

        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MainActivity.this, FirstPage.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
//        DrawerLayout drawer = findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        int id = item.getItemId();
//
//        if (id == R.id.history) {
//            Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
//            intent.putExtra("customerOrDriver", "Drivers");
//            startActivity(intent);
//        } else if (id == R.id.settings) {
//            Intent intent = new Intent(MainActivity.this, EmployeeDetails.class);
//            startActivity(intent);
//        } else if (id == R.id.payout) {
//            Intent intent = new Intent(MainActivity.this, MyPrices.class);
//            startActivity(intent);
//        } else if (id == R.id.cartypes){
//            Intent intent = new Intent(MainActivity.this, AttaPay.class);
//            startActivity(intent);
//
//        }
//
//        else if (id == R.id.home) {
//            Intent intent = new Intent(MainActivity.this, MainActivity.class);
//            startActivity(intent);
//        }else if (id == R.id.logout) {
//            logOut();
//        }
//
//        DrawerLayout drawer = findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
 return true;

    }



    @Override
    public void onClick(View view) {
        Intent i;
        switch (view.getId()) {
            case R.id.bankCard:
                i = new Intent(this, MyTasks.class);
                startActivity(i);
                break;

            case R.id.ideasCard:
                i = new Intent(this, MyPrices.class);
                startActivity(i);
                break;
            case R.id.filesCard:
                i = new Intent(this, EmployeeDetails.class);
                startActivity(i);
                break;
            case R.id.services:
                i = new Intent(this, AdminMaps.class);
                startActivity(i);
            default:
                break;
        }
    }
}