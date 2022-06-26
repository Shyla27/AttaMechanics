package com.example.attamechanics.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.example.attamechanics.Adapters.AppointmentsLVAdapter;
import com.example.attamechanics.Adapters.DataModal;
import com.example.attamechanics.Adapters.EmployeeDets;
import com.example.attamechanics.Adapters.TeamLVAdapter;
import com.example.attamechanics.MainActivity;
import com.example.attamechanics.Notifications;
import com.example.attamechanics.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
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
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_mechanics);

        myMechsLv = findViewById(R.id.idMyMechanics);
        employeeDetsArrayList = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        loadDatainMechsListview();

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
        db.collection("MechanicDetails").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()) {
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot d : list) {
                        EmployeeDets employeeDets = d.toObject(EmployeeDets.class);
                        employeeDetsArrayList.add(employeeDets);

                    }
                    TeamLVAdapter adapter = new TeamLVAdapter(MyMechanics.this,employeeDetsArrayList );
                    myMechsLv.setAdapter(adapter);
                } else {
                    Toast.makeText(MyMechanics.this, "No data found in Database", Toast.LENGTH_SHORT).show();

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MyMechanics.this, "Fail to load data..", Toast.LENGTH_SHORT).show();

            }
        });
    }
}