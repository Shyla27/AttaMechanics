package com.example.attamechanics.Garage;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.attamechanics.Adapters.GaragesAdapter;
import com.example.attamechanics.Auth.Signup;
import com.example.attamechanics.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Garageinfo extends AppCompatActivity {

    private TextInputEditText garagename, speciality, officenumber, getlocation;
    private Button proceed;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private static final int REQUEST_LOCATION = 1;
    private String garageID;
    private ProgressBar loadingPB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garageinfo);
        garagename = findViewById(R.id.garagename);
        speciality = findViewById(R.id.speciality);
        officenumber = findViewById(R.id.officenumber);
        proceed = findViewById(R.id.proceed);
        loadingPB = findViewById(R.id.progressBar);
        getlocation = findViewById(R.id.garagelocation);

        firebaseDatabase = FirebaseDatabase.getInstance();
      // firebaseDatabase.getReference("garagedets/profile").child("garagename").setValue(garagename);
       // firebaseDatabase.getReference("garagedets/profile").child("garagename").setValue(garagename);

        databaseReference = firebaseDatabase.getReference("garagedets");

        Button proceed = findViewById(R.id.proceed);


        proceed.setOnClickListener(view -> {
            loadingPB.setVisibility(View.VISIBLE);
            String garagenam = garagename.getText().toString();
            String specialit = speciality.getText().toString();
            String officenum = officenumber.getText().toString();
            String location = getlocation.getText().toString();
            garageID = garagenam;
            GaragesAdapter garagesAdapter = new GaragesAdapter(garageID, garagenam, specialit, officenum, location);
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    databaseReference.child(garageID).setValue(garagesAdapter);
                    Toast.makeText(Garageinfo.this, "Garage Added..", Toast.LENGTH_SHORT).show();
                    // starting a main activity.
                    startActivity(new Intent(Garageinfo.this, Signup.class));

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(Garageinfo.this, "Fail to add Course..", Toast.LENGTH_SHORT).show();

                }
            });


        });

    }


}