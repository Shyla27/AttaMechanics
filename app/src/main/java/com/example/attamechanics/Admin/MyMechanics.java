package com.example.attamechanics.Admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.attamechanics.Adapters.EmployeeDets;
import com.example.attamechanics.Adapters.TeamLVAdapter;
import com.example.attamechanics.MainActivity;
import com.example.attamechanics.Notifications;
import com.example.attamechanics.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MyMechanics extends AppCompatActivity {
    BottomNavigationView bottomNavigation;
    ListView myMechsLv;
    ArrayList<EmployeeDets> employeeDetsArrayList;
    private ArrayList<String> MechanicsArrayList = new ArrayList<>();
    private DatabaseReference reference;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_mechanics);

        myMechsLv = findViewById(R.id.idMyMechanics);
        employeeDetsArrayList = new ArrayList<>();

        loadDatainMechsListview();
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = FirebaseDatabase.getInstance().getReference().child("MechanicsDetails");

        bottomNavigation = findViewById(R.id.bottom_navigation);
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
                case R.id.notify:

                    startActivity(new Intent(getApplicationContext(), Notifications.class));
                    overridePendingTransition(0, 0);
                    return true;
            }
            return false;
        });
        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("My Mechanics");

    }

    private void loadDatainMechsListview() {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, MechanicsArrayList);
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String value = snapshot.child("garagename").getValue(String.class);
                MechanicsArrayList.add(value);
                // Map<String, Object> map = (Map<String, Object>) snapshot.getValue();
                //  garagesArrayList.add(snapshot.getValue(String.class));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                MechanicsArrayList.remove(snapshot.getValue(String.class));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        myMechsLv.setAdapter(adapter);
    }
}