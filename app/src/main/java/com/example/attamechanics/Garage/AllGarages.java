package com.example.attamechanics.Garage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.attamechanics.Adapters.GaragesAdapter;
import com.example.attamechanics.Admin.AddAppointment;
import com.example.attamechanics.Auth.Login;
import com.example.attamechanics.Mechs.Approved;
import com.example.attamechanics.Mechs.MechanicsDashboard;
import com.example.attamechanics.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Map;

public class AllGarages extends AppCompatActivity {
    private Button proceed;
    private ListView garageLV;

    // creating a new array list.
    private ArrayList<String> garagesArrayList = new ArrayList<>();
    private DatabaseReference reference;
    private FirebaseDatabase firebaseDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_garages);

        proceed = findViewById(R.id.proceed);

        proceed.setOnClickListener(view -> {
            Intent i = new Intent(getBaseContext(), AddAppointment.class);
            startActivity(i);
        });
        garageLV = findViewById(R.id.idLVgarages);

        // initializing our array list
        garagesArrayList = new ArrayList<String>();
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = FirebaseDatabase.getInstance().getReference().child("garagedets");

        // calling a method to get data from
        // Firebase and set data to list view
        initializeListView();
    }

    private void initializeListView() {
         ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, garagesArrayList);
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String value = snapshot.child("garagename").getValue(String.class);
                garagesArrayList.add(value);
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
                garagesArrayList.remove(snapshot.getValue(String.class));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        garageLV.setAdapter(adapter);
    }
    protected void populateView(View view , GaragesAdapter garagesAdapter, int position) {

    }
}