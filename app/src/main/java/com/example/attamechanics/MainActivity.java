package com.example.attamechanics;

import static com.example.attamechanics.Utils.Constants.USER;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.attamechanics.Adapters.GaragesAdapter;
import com.example.attamechanics.Adapters.User;
import com.example.attamechanics.Admin.AddEmployees;
import com.example.attamechanics.Admin.AdminProfile;
import com.example.attamechanics.Admin.AssignTasks;
import com.example.attamechanics.Admin.AttaPay;
import com.example.attamechanics.Admin.EmployeeDetails;
import com.example.attamechanics.Admin.MyGarage;
import com.example.attamechanics.Auth.Login;
import com.example.attamechanics.Auth.Signup;
import com.example.attamechanics.Users.NearbyGarages;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements FirebaseAuth.AuthStateListener,  NavigationView.OnNavigationItemSelectedListener{
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private GoogleSignInClient googleSignInClient;

    BottomNavigationView bottomNavigation;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    GaragesAdapter garagesAdapter;
//    TextView username;
    CircleImageView circleImageView;

    CardView team,appointments, carservice,prices, cartype;
    TextView garagename;
    private ArrayList<String> garagesArrayList = new ArrayList<>();
    private DatabaseReference reference;
    private FirebaseDatabase firebaseDatabase;

    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // getdata();
        appointments = findViewById(R.id.appointments);
        carservice = findViewById(R.id.carService);
        team = findViewById(R.id.cardteam);
        prices = findViewById(R.id.myPrices);
        cartype = findViewById(R.id.carTypes);

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
                    startActivity(new Intent(getApplicationContext(), NearbyGarages.class));
                    overridePendingTransition(0,0);
                    return true;
                case R.id.notify:

                    startActivity(new Intent(getApplicationContext(), Notifications.class));
                    overridePendingTransition(0, 0);
                    return true;
            }
            return false;
        });

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        garagename = findViewById(R.id.garagename);
        garagesArrayList = new ArrayList<String>();
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = FirebaseDatabase.getInstance().getReference().child("GarageInfo/Garages");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();


        appointments.setOnClickListener(view -> {
            Intent intent1 = new Intent(MainActivity.this, AssignTasks.class);
            startActivity(intent1);
            finish();
        });

        cartype.setOnClickListener(view -> {
            Intent intent1 = new Intent(MainActivity.this, AttaPay.class);
            startActivity(intent1);
            finish();
        });
        carservice.setOnClickListener(view -> {
           // Toast.makeText(getApplicationContext(), "Coming Soon!" , Toast.LENGTH_SHORT).show();
//            Intent intent1 = new Intent(MainActivity.this, AssignMech.class);
//            startActivity(intent1);
//            finish();
        });

        team.setOnClickListener(view -> {
            Intent intent1 = new Intent(MainActivity.this, EmployeeDetails.class);
            startActivity(intent1);
            finish();
        });

        prices.setOnClickListener(view -> {
            Intent intent1 = new Intent(MainActivity.this, AddEmployees.class);
            startActivity(intent1);
            finish();
        });

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.addAuthStateListener(authStateListener);



    }




    private void initGoogleSignInClient() {
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);
    }

    private User getUserFromIntent() {
        return (User) getIntent().getSerializableExtra(USER);
    }

    FirebaseAuth.AuthStateListener authStateListener = firebaseAuth -> {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser == null) {
            Intent intent = new Intent(MainActivity.this, Signup.class);
            startActivity(intent);
        } if (firebaseUser!= null ) {

        }
    };


    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.my_drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.nav_home) {

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation_menu, menu) ;
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser == null) {
            goToAuthInActivity();
        }
    }

    private void goToAuthInActivity() {
        Intent intent = new Intent(MainActivity.this, Signup.class);
        startActivity(intent);
    }

    private void signOut() {
        singOutFirebase();
        signOutGoogle();
    }

    private void signOutGoogle() {
        googleSignInClient.signOut();
    }

    private void singOutFirebase() {
        firebaseAuth.signOut();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId() ;
        if (id == R.id. nav_settings ) {
            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);        } else if (id == R.id. nav_account ) {
            Intent intent = new Intent(MainActivity.this, MyGarage.class);
            startActivity(intent);
        } else if (id == R.id. nav_logout ) {
            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
        }
        DrawerLayout drawer = findViewById(R.id.my_drawer_layout ) ;
        drawer.close();
        return true;
    }
}