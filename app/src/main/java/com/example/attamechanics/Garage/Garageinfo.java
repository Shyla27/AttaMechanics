package com.example.attamechanics.Garage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.attamechanics.Adapters.GaragesAdapter;
import com.example.attamechanics.MainActivity;
import com.example.attamechanics.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Garageinfo extends AppCompatActivity implements LocationListener {

    private EditText garagename, speciality, officenumber, getlocation;
    private Button proceed, btnGetLocation;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    GaragesAdapter garagesAdapter;
    private static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;
    String latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garageinfo);
        garagename = findViewById(R.id.garagname);
        speciality = findViewById(R.id.speciality);
        officenumber = findViewById(R.id.officenumber);
        proceed = findViewById(R.id.proceed);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("GarageDetails");
        garagesAdapter = new GaragesAdapter(garagename, speciality, officenumber);


        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        getlocation = findViewById(R.id.showLocation);
        btnGetLocation = findViewById(R.id.btnGetLocation);
        btnGetLocation.setOnClickListener(view -> getLocation());
        Button proceed = findViewById(R.id.proceed);


        proceed.setOnClickListener(view -> {

            String garagenam = garagename.getText().toString();
            String specialit = speciality.getText().toString();
            String officenum = officenumber.getText().toString();
            String location = getlocation.getText().toString();

            if (TextUtils.isEmpty(garagenam) && TextUtils.isEmpty(specialit) && TextUtils.isEmpty(officenum)) {
                Toast.makeText(Garageinfo.this, "Please add some data", Toast.LENGTH_SHORT).show();
            } else {
                addToFirebase(garagenam, specialit, officenum);
            }


        });

    }

    private void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, this);
        }catch (SecurityException e) {
            e.printStackTrace();

        }
    }

    private void addToFirebase(String garagenam, String specialit, String officenum) {
        garagesAdapter.setGaragename(garagenam);
        garagesAdapter.setOfficenumber(officenum);
        garagesAdapter.setSpeciality(specialit);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                databaseReference.setValue(garagesAdapter);
                Toast.makeText(Garageinfo.this, "data added", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Garageinfo.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Garageinfo.this, "Fail to add data " + error, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

    }
}