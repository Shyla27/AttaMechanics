package com.example.attamechanics.Garage;

import static com.example.attamechanics.Utils.Constants.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.attamechanics.Adapters.GaragesAdapter;
import com.example.attamechanics.Mechs.MechanicsDashboard;
import com.example.attamechanics.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class AllGarages extends AppCompatActivity {
    private AppCompatButton proceed;
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
            Intent i = new Intent(getBaseContext(), MechanicsDashboard.class);
            startActivity(i);
        });
        garageLV = findViewById(R.id.idLVgarages);

        // initializing our array list
        garagesArrayList = new ArrayList<String>();
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("GarageInfo/Garages");

        // calling a method to get data from
        // Firebase and set data to list view
        initializeListView();
    }

    private void initializeListView() {
         ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, garagesArrayList);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                 Map<String, Object> map = (Map<String, Object>) snapshot.getValue();
                 // garagesArrayList.add(snapshot.getValue(String.class));
                Log.d(TAG, "Value is: " + map);
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