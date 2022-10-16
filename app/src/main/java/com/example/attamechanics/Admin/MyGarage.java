package com.example.attamechanics.Admin;

import static com.example.attamechanics.Utils.Constants.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.attamechanics.Adapters.GaragesAdapter;
import com.example.attamechanics.Adapters.Model;
import com.example.attamechanics.MainActivity;
import com.example.attamechanics.Notifications;
import com.example.attamechanics.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MyGarage extends AppCompatActivity {

    TextView garage, descript, officenumber, garagenamemail, garagelocation;
    String garagename, description;
    FirebaseDatabase firebaseDatabase;
    private DatabaseReference reference;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private String onlineUserID;
    Button refresh_btn;
    private ProgressDialog loader;

    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_garage);
//        refresh_btn = findViewById(R.id.refresh_btn);
        garage= findViewById(R.id.garageName);
        descript = findViewById(R.id.garagedescription);
        officenumber = findViewById(R.id.officenumber);
        garagenamemail = findViewById(R.id.garagenamemail);
        garagelocation = findViewById(R.id.garagelocation);
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

            }
            return false;
        });
        mAuth = FirebaseAuth.getInstance();

        loader = new ProgressDialog(this);

        mUser = mAuth.getCurrentUser();
        onlineUserID = mUser.getUid();
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference().child("Garages").child(onlineUserID);

        String garagenam = Objects.requireNonNull(garage.getText()).toString();
        String officenum = Objects.requireNonNull(officenumber.getText()).toString();
        String bio_tv = Objects.requireNonNull(descript.getText()).toString();
        String email = Objects.requireNonNull(garagenamemail.getText()).toString();

       // GaragesAdapter garagesAdapter  = new GaragesAdapter(garagenam, officenum, email);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //reference.setValue(gar)

                GaragesAdapter garagesAdapter = snapshot.getValue(GaragesAdapter.class);
                assert garagesAdapter != null;
                garage.setText(garagesAdapter.getGaragename());
                descript.setText(garagesAdapter.getDescription());
                officenumber.setText(garagesAdapter.getOfficenumber());
                garagenamemail.setText(garagesAdapter.getIdnumber());



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }






    @Override
    protected void onStart() {
        super.onStart();

    }

}