package com.example.attamechanics.Admin;

import static com.example.attamechanics.Utils.Constants.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.attamechanics.Adapters.EmployeeDets;
import com.example.attamechanics.Adapters.GaragesAdapter;
import com.example.attamechanics.MainActivity;
import com.example.attamechanics.Notifications;
import com.example.attamechanics.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MyMechanics extends AppCompatActivity {
    BottomNavigationView bottomNavigation;
    ListView myMechsLv;
    ArrayList<String> employeeDets;
    private DatabaseReference reference;
    private FirebaseDatabase firebaseDatabase;
    private FloatingActionButton addemployee;


    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_mechanics);

        myMechsLv = findViewById(R.id.idMyMechanics);
        employeeDets = new ArrayList<String>();

        initializeListView(); 

        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("GarageInfo/MechanicDetails");
        bottomNavigation = findViewById(R.id.bottom_navigation);
        addemployee = findViewById(R.id.addMech);

        addemployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), EmployeeDetails.class);
                startActivity(i);
            }
        });
        bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            switch(item.getItemId())
            {
                case R.id.myaccount:
                    startActivity(new Intent(getApplicationContext(), AdminProfile.class));
                    overridePendingTransition(0,0);
                    return true;
                case R.id.navigation_home:
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    overridePendingTransition(0,0);

                    return true;
                case R.id.action_nearby:
                    startActivity(new Intent(getApplicationContext(), GoogleMaps.class));
                    overridePendingTransition(0,0);
                    return true;

            }
            return false;
        });

        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("My Mechanics");

    }

    private void initializeListView() {
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, employeeDets);

        // below line is used for getting reference
        // of our Firebase Database.

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String address = ds.child("address").getValue(String.class);
                    String name = ds.child("name").getValue(String.class);
                    Log.d("TAG", address + " / " + name);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
    }


//        reference.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//            //    Map<String, Object> map = (Map<String, Object>) snapshot.getValue();
//              //  Log.d(TAG, "Value is: " + map);
//        //  employeeDets.add(snapshot.getValue(String.class));
//                adapter.notifyDataSetChanged();
//
//                Map<String, Object> map = (Map<String, Object>) snapshot.getValue();
//                Log.d(TAG, "Value is: " + map);
//
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
//                employeeDets.remove(snapshot.getValue(String.class));
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//        myMechsLv.setAdapter(adapter);
//
//    }


    protected void populateView(View view, GaragesAdapter garagesAdapter, int position) {

    }
}