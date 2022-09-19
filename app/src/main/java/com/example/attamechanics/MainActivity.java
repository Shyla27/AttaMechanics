package com.example.attamechanics;

import static com.example.attamechanics.Utils.Constants.USER;

import android.app.LauncherActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.attamechanics.Adapters.GaragesAdapter;
import com.example.attamechanics.Adapters.User;
import com.example.attamechanics.Admin.AdminProfile;
import com.example.attamechanics.Admin.AttaPay;
import com.example.attamechanics.Admin.EmployeeDetails;
import com.example.attamechanics.Admin.GarageSettings;
import com.example.attamechanics.Admin.GoogleMaps;
import com.example.attamechanics.Admin.HistoryActivity;
import com.example.attamechanics.Admin.MyPrices;
import com.example.attamechanics.Admin.MyTasks;
import com.example.attamechanics.Admin.Payments;
import com.example.attamechanics.Auth.Signup;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    TextView username, description;
    ProgressDialog dialog;
    private String onlineUserID;
    CircleImageView profile_image;
    CardView team,appointments, carservice,prices, cartype;
    TextView garagename;
    private ArrayList<String> garagesArrayList = new ArrayList<>();
     DatabaseReference reference;
     FirebaseDatabase firebaseDatabase;
    FirebaseUser firebaseUser;
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
        profile_image = findViewById(R.id.profile_image);
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
                case R.id.notificationsmechs:
                    startActivity(new Intent(getApplicationContext(), Notifications.class));
                    overridePendingTransition(0,0);
                    return true;

                case R.id.action_nearby:
                    startActivity(new Intent(getApplicationContext(), GoogleMaps.class));
                    overridePendingTransition(0,0);
                    return true;
            }
            return false;
        });


        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        navigationView = findViewById(R.id.nav_vieew);
        navigationView.setNavigationItemSelectedListener(this);

        garagename = findViewById(R.id.garagename);
        garagesArrayList = new ArrayList<String>();
        firebaseDatabase = FirebaseDatabase.getInstance();
        onlineUserID = firebaseUser.getUid();
        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(onlineUserID);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);

//                if (user != null) {
//                    username.setText(user.getname());
//                    description.setText(user.getEmail());
//                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();


        appointments.setOnClickListener(view -> {
            Intent intent1 = new Intent(MainActivity.this, MyTasks.class);
            startActivity(intent1);
            finish();
        });

        cartype.setOnClickListener(view -> {
            Intent intent1 = new Intent(MainActivity.this, AttaPay.class);
            startActivity(intent1);
            finish();
        });
        carservice.setOnClickListener(view -> {
            Toast.makeText(getApplicationContext(), "Coming Soon!" , Toast.LENGTH_SHORT).show();
            Intent intent1 = new Intent(MainActivity.this, MyTasks.class);
            startActivity(intent1);
            finish();
        });

        team.setOnClickListener(view -> {
            Intent intent1 = new Intent(MainActivity.this, EmployeeDetails.class);
            startActivity(intent1);
            finish();
        });

        prices.setOnClickListener(view -> {
            Intent intent1 = new Intent(MainActivity.this, MyPrices.class);
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

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu) ;
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
        if (id == R.id.history) {
            Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
            intent.putExtra("customerOrDriver", "Drivers");
            startActivity(intent);
        } else if (id == R.id.settings) {
            Intent intent = new Intent(MainActivity.this, GarageSettings.class);
            startActivity(intent);
        } else if (id == R.id.payout) {
            Intent intent = new Intent(MainActivity.this, Payments.class);
            startActivity(intent);
        } else if (id == R.id.logout) {
            logOut();
        }
        DrawerLayout drawer = findViewById(R.id.my_drawer_layout ) ;
        drawer.close();
        return true;
    }

    private void logOut() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MainActivity.this, LauncherActivity.class);
        startActivity(intent);
        finish();
    }
}