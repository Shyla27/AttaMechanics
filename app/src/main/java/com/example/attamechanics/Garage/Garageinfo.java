package com.example.attamechanics.Garage;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import static androidx.constraintlayout.motion.widget.Debug.getLocation;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attamechanics.Adapters.GaragesAdapter;
import com.example.attamechanics.Adapters.LocationTrack;
import com.example.attamechanics.Admin.AdminProfile;
import com.example.attamechanics.Admin.EmployeeDetails;
import com.example.attamechanics.Admin.GoogleMaps;
import com.example.attamechanics.MainActivity;
import com.example.attamechanics.Notifications;
import com.example.attamechanics.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class Garageinfo extends AppCompatActivity {

    private TextInputEditText garagename, officenumber, latitud, longitud;
    private Button proceed, livelocation, showonmap;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private static final int REQUEST_LOCATION = 1;
    private String garageID;
    private ProgressBar loadingPB;
    private ArrayList permissionsToRequest;
    private final ArrayList permissionsRejected = new ArrayList();
    private final ArrayList permissions = new ArrayList();
    private FirebaseAuth auth;

    FusedLocationProviderClient fusedLocationProviderClient;

    private final static int ALL_PERMISSIONS_RESULT = 101;
    LocationTrack locationTrack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garageinfo);
        garagename = findViewById(R.id.garagename);
        officenumber = findViewById(R.id.officenumber);
        proceed = findViewById(R.id.proceed);
        loadingPB = findViewById(R.id.progressBar);
        latitud = findViewById(R.id.latitude);
        longitud = findViewById(R.id.longitude);
        livelocation = findViewById(R.id.livelocation);
        auth = FirebaseAuth.getInstance();

        livelocation.setOnClickListener(view -> {
//            locationTrack = new LocationTrack(Garageinfo.this);
//            if (locationTrack.canGetLocation()) {
//
//
//                double longitude = locationTrack.getLongitude();
//                double latitude = locationTrack.getLatitude();
//                latitud.setText(String.valueOf(latitude));
//                longitud.setText(String.valueOf(longitude));
//                Toast.makeText(getApplicationContext(), "Longitude:" + longitude + "\nLatitude:" + latitude, Toast.LENGTH_SHORT).show();
//            } else {
//
//                locationTrack.showSettingsAlert();
//            }

            if (ActivityCompat.checkSelfPermission(Garageinfo.this, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                getLocatian();
            } else {
                ActivityCompat.requestPermissions(Garageinfo.this, new String[]{ACCESS_FINE_LOCATION}, 44);
            }


        });

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Garages");
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String garagenam = Objects.requireNonNull(garagename.getText()).toString();
                String officenum = Objects.requireNonNull(officenumber.getText()).toString();
                String latitde = Objects.requireNonNull(latitud.getText()).toString();
                String longiude = Objects.requireNonNull(longitud.getText()).toString();
                garageID = garagenam;
                GaragesAdapter garagesAdapter = new GaragesAdapter(garageID, garagenam, officenum, latitde, longiude);
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        databaseReference.child(garageID).setValue(garagesAdapter);

                        String user_id = auth.getCurrentUser().getUid();
                        DatabaseReference current_user_id = databaseReference.child(user_id);
                        current_user_id.child("GarageName").setValue(garagenam);
                        current_user_id.child("OfficeNumber").setValue(officenum);
                        current_user_id.child("Latitude").setValue(latitde);
                        current_user_id.child("Longitude").setValue(longiude);


                        Toast.makeText(Garageinfo.this, "Garage Added..", Toast.LENGTH_SHORT).show();
                        // starting a main activity.
                        startActivity(new Intent(Garageinfo.this, Documentation.class));

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(Garageinfo.this, "Fail to add garage..", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);


    }

    private void getLocatian() {
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
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
    Location location = task.getResult();
    if (location != null) {
        try {
            Geocoder geocoder = new Geocoder(Garageinfo.this, Locale.getDefault());

            List<Address>  addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            latitud.setText(Html.fromHtml("" + addresses.get(0).getLatitude()));
            longitud.setText(Html.fromHtml("" + addresses.get(0).getLongitude()));


        } catch (IOException e ) {
            e.printStackTrace();
        }
    }
            }
        });
    }


}
