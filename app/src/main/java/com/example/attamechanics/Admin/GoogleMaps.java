package com.example.attamechanics.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.view.MenuItem;
import android.widget.Switch;
import android.widget.Toast;

import android.location.Location;
import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.model.Direction;
import com.example.attamechanics.MainActivity;
import com.example.attamechanics.Notifications;
import com.example.attamechanics.R;
import com.example.attamechanics.objects.DriverObject;
import com.example.attamechanics.objects.RideObject;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class GoogleMaps extends FragmentActivity implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, DirectionCallback {
    private GoogleMap map;
    private Switch mWorkingSwitch;
    DriverObject mDriver = new DriverObject();
    int MAX_SEARCH_DISTANCE = 20;

    Location mLastLocation;
    LocationRequest mLocationRequest;

    DatabaseReference mUser;
    boolean started = false;
    boolean zoomUpdated = false;
    RideObject mCurrentRide;
    BottomNavigationView bottomNavigation;
    GeoQuery geoQuery;
    private FusedLocationProviderClient mFusedLocationClient;
    List<RideObject> requestList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_maps);
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

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);




        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mUser = FirebaseDatabase.getInstance().getReference().child("users").child("admin").child(FirebaseAuth.getInstance().getUid());


        mWorkingSwitch = findViewById(R.id.workingSwitch);
        mWorkingSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!mDriver.getActive()) {
                Toast.makeText(GoogleMaps.this, R.string.not_approved, Toast.LENGTH_LONG).show();
                mWorkingSwitch.setChecked(false);
                return;
            }
            if (isChecked) {
                connectDriver();
            } else {
                disconnectDriver();
            }
        });

    }



    private void disconnectDriver() {
        mWorkingSwitch.setChecked(false);
        if (mFusedLocationClient != null) {
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
        }
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("driversAvailable").child(userId);
        ref.removeValue();
    }

    private void connectDriver() {

        mWorkingSwitch.setChecked(true);
        checkLocationPermission();
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
        if (map != null) {
            map.setMyLocationEnabled(true);
        }
    }

    LocationCallback mLocationCallback =new LocationCallback() {
        @Override
        public void onLocationResult(@NonNull LocationResult locationResult) {
            if (locationResult == null) {
                return;
            }

            for (Location location : locationResult.getLocations()) {
                if (getApplicationContext() != null) {

                    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    DatabaseReference refWorking = FirebaseDatabase.getInstance().getReference("driversWorking");
                    GeoFire geoFireWorking = new GeoFire(refWorking);

                    if (!mWorkingSwitch.isChecked()) {
                        geoFireWorking.removeLocation(userId, (key, error) -> {
                        });
                        return;
                    }


                    geoFireWorking.setLocation(userId, new GeoLocation(location.getLatitude(), location.getLongitude()), (key, error) -> {
                    });

                    if (mCurrentRide != null && mLastLocation != null) {
                        mCurrentRide.setRideDistance(mCurrentRide.getRideDistance() + mLastLocation.distanceTo(location) / 1000);
                    }

                    mLastLocation = location;

                    if (!started) {
                        getRequestsAround();
                        started = true;
                    }

                    Map<String, Object> newUserMap = new HashMap<>();
                    newUserMap.put("last_updated", ServerValue.TIMESTAMP);
                    mUser.updateChildren(newUserMap);

                    if (!zoomUpdated) {
                        map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude())));
                        map.animateCamera(CameraUpdateFactory.zoomTo(16));
                        zoomUpdated = true;
                    }
                }
            }
        }
    };

    private void getRequestsAround() {
        if (mLastLocation == null) {
            return;
        }

        DatabaseReference requestLocation = FirebaseDatabase.getInstance().getReference().child("customer_requests");

        GeoFire geoFire = new GeoFire(requestLocation);
        geoQuery = geoFire.queryAtLocation(new GeoLocation(mLastLocation.getLatitude(), mLastLocation.getLongitude()), MAX_SEARCH_DISTANCE);
        geoQuery.removeAllListeners();

        geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
            @Override
            public void onKeyEntered(String key, GeoLocation location) {
                if(!mWorkingSwitch.isChecked()){
                    return;
                }

                if (mCurrentRide == null) {
                    for (RideObject mRideIt : requestList) {
                        if (mRideIt.getId().equals(key)) {
                            return;
                        }
                    }

                    getRequestInfo(key);

                }else{
                    requestList.clear();
                }
            }

            @Override
            public void onKeyExited(String key) {
            }

            @Override
            public void onKeyMoved(String key, GeoLocation location) {
            }

            @Override
            public void onGeoQueryReady() {
            }

            @Override
            public void onGeoQueryError(DatabaseError error) {
            }
        });
    }

    private void getRequestInfo(String key) {
        FirebaseDatabase.getInstance().getReference().child("ride_info").child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    return;
                }

            }

            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            } else {
                checkLocationPermission();
            }
        }



    }

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION) && ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                new android.app.AlertDialog.Builder(this)
                        .setTitle("give permission")
                        .setMessage("give permission message")
                        .setPositiveButton("OK", (dialogInterface, i) -> ActivityCompat.requestPermissions(GoogleMaps.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1))
                        .create()
                        .show();
            } else {
                ActivityCompat.requestPermissions(GoogleMaps.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.CALL_PHONE}, 1);
            }
        }
    }

    @Override
    public void onDirectionSuccess(Direction direction, String rawBody) {

    }

    @Override
    public void onDirectionFailure(Throwable t) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}