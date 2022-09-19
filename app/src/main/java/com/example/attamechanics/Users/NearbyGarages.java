package com.example.attamechanics.Users;

import static androidx.constraintlayout.motion.widget.Debug.getLocation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.location.Address;
import android.Manifest;
import android.app.LauncherActivity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Route;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.example.attamechanics.Adapters.CardRequestAdapter;
import com.example.attamechanics.Admin.GarageSettings;
import com.example.attamechanics.Admin.HistoryActivity;
import com.example.attamechanics.Admin.Payments;
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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;
import com.ncorti.slidetoact.SlideToActView;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NearbyGarages extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, DirectionCallback {

    BottomNavigationView bottomNavigation;
    int MAX_SEARCH_DISTANCE = 20;

    private GoogleMap mMap;
    Location mlocation;
    LocationRequest mLocationRequest;
    private FusedLocationProviderClient mFusedLocationClient;

    private SlideToActView mRideStatus;

    private Switch mWorkingSwitch;


    private LinearLayout mCustomerInfo, mBringUpBottomLayout;

    private TextView mCustomerName;
    DatabaseReference mUser;

    RideObject mCurrentRide;

    Marker pickupMarker, destinationMarker;

    DriverObject mDriver = new DriverObject();

    TextView mUsername, mLogout;

    private ValueEventListener driveHasEndedRefListener;

    private CardRequestAdapter cardRequestAdapter;

    List<RideObject> requestList = new ArrayList<>();

    View mBottomSheet;
    BottomSheetBehavior<View> mBottomSheetBehavior;

    GeoQuery geoQuery;

    boolean started = false;
    boolean zoomUpdated = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_garages);


        Toolbar toolbar = findViewById(R.id.toolbar);


        polylines = new ArrayList<>();


        final DrawerLayout drawer = findViewById(R.id.drawer_layoutmaps);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.nav_open, R.string.nav_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        mUser = FirebaseDatabase.getInstance().getReference().child("GarageInfo").child("Garages").child(FirebaseAuth.getInstance().getUid());
        mCustomerInfo = findViewById(R.id.customerInfo);

        mBringUpBottomLayout = findViewById(R.id.bringUpBottomLayout);

        mCustomerName = findViewById(R.id.name);
        mUsername = navigationView.getHeaderView(0).findViewById(R.id.usernameDrawer);
        FloatingActionButton mMaps = findViewById(R.id.openMaps);
        FloatingActionButton mCall = findViewById(R.id.phone);
        ImageView mCancel = findViewById(R.id.cancel);
        mRideStatus = findViewById(R.id.rideStatus);
        mLogout = findViewById(R.id.logout);

        mWorkingSwitch = findViewById(R.id.workingSwitch);

        mLogout.setOnClickListener(v -> logOut());

        mWorkingSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!mDriver.getActive()) {
                Toast.makeText(NearbyGarages.this, R.string.not_approved, Toast.LENGTH_LONG).show();
                mWorkingSwitch.setChecked(false);
                return;
            }
            if (isChecked) {
                connectGarage();
            } else {
                disconnectGarage();
            }
        });

        mRideStatus.setOnSlideCompleteListener(v -> {
            switch (mCurrentRide.getState()) {
                case 1:
                    if (mCurrentRide == null) {
                        return;
                    }
                    mCurrentRide.pickedCustomer();
                    break;
                case 2:
                    if (mCurrentRide != null)
                        mCurrentRide.recordRide();
                    break;
            }
        });

        mMaps.setOnClickListener(view -> {
            if (mCurrentRide.getState() == 1) {
                openMaps(mCurrentRide.getPickup().getCoordinates(), mCurrentRide.getPickup().getCoordinates());
            } else {
                openMaps(mCurrentRide.getDestination().getCoordinates(), mCurrentRide.getDestination().getCoordinates());
            }


        });

        mCall.setOnClickListener(view -> {
            if (mCurrentRide == null) {
                Snackbar.make(findViewById(R.id.drawer_layout), getString(R.string.garage_no_phone), Snackbar.LENGTH_LONG).show();
            }
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mCurrentRide.getCustomer().getPhone()));
                startActivity(intent);
            } else {
                Snackbar.make(findViewById(R.id.drawer_layout), getString(R.string.no_phone_call_permissions), Snackbar.LENGTH_LONG).show();
            }
        });

        mCancel.setOnClickListener(v -> {
            mCurrentRide.cancelRide();
            endRide();
        });
        ImageView mDrawerButton = findViewById(R.id.drawerButton);
        mDrawerButton.setOnClickListener(v -> drawer.openDrawer(Gravity.LEFT));

        mBringUpBottomLayout = findViewById(R.id.bringUpBottomLayout);
        mBringUpBottomLayout.setOnClickListener(v -> {
            if (mBottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED)
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            else
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

            if (mCurrentRide == null) {
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        });

        getUserData();
        initializeRequestCardSwipe();
        isRequestInProgress();

        ViewTreeObserver vto = mBringUpBottomLayout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(this::initializeBottomLayout);


        LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        String provider = locationManager.getBestProvider(criteria, true);
        Location location = locationManager.getLastKnownLocation(provider);
    }


    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION) && ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                new android.app.AlertDialog.Builder(this)
                        .setTitle("give permission")
                        .setMessage("give permission message")
                        .setPositiveButton("OK", (dialogInterface, i) -> ActivityCompat.requestPermissions(NearbyGarages.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1))
                        .create()
                        .show();
            } else {
                ActivityCompat.requestPermissions(NearbyGarages.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.CALL_PHONE}, 1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                    mFusedLocationClient.requestLocationUpdates(mLocationRequest,  mLocationCallback, Looper.myLooper());
                    mMap.setMyLocationEnabled(true);
                }
            } else {
                Toast.makeText(getApplicationContext(), "Please provide the permission", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void openMaps(Object latitude, Object longitude) {
        try {
            String url = "https://waze.com/ul?ll=" + latitude + "," + longitude + "&navigate=yes";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        } catch (ActivityNotFoundException exception) {
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                    Uri.parse("http://maps.google.com/maps?daddr=" + latitude + "," + longitude));
            startActivity(intent);
        }
    }



    private void disconnectGarage() {
        mWorkingSwitch.setChecked(false);
        if (mFusedLocationClient != null) {

            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
        }
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("driversAvailable").child(userId);
        ref.removeValue();
    }

    private void getUserData() {

        String driverId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference assignedCustomerRef = FirebaseDatabase.getInstance().getReference().child("Users").child("Drivers").child(driverId);
        assignedCustomerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    mDriver.parseData(dataSnapshot);

                    mUsername.setText(mDriver.getName());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        FirebaseDatabase.getInstance().getReference("driversWorking").child(driverId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    connectGarage();
                } else {
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    disconnectGarage();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void isRequestInProgress() {

        FirebaseDatabase.getInstance().getReference().child("service_info").orderByChild("garageId").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid()).limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()) {
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                }
                for (DataSnapshot mData : snapshot.getChildren()) {
                    mCurrentRide = new RideObject();
                    mCurrentRide.parseData(mData);

                    if (mCurrentRide.getCancelled() || mCurrentRide.getEnded()) {
                        endRide();
                        return;
                    }

                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    requestListener();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void requestListener() {
        if (mCurrentRide == null) {
            return;
        }

        driveHasEndedRefListener = mCurrentRide.getRideRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()) {
                    return;
                }
                mCurrentRide.parseData(snapshot);

                //if drive has ended or been cancelled then call endRide to retrieve all variables to their default state
                if (mCurrentRide.getCancelled() || mCurrentRide.getEnded()) {
                    endRide();
                    return;
                }

                checkRequestState();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void checkRequestState() {

        switch (mCurrentRide.getState()) {
            case 1:
                destinationMarker = mMap.addMarker(new MarkerOptions().position((LatLng) mCurrentRide.getDestination().getCoordinates()).title("Destination").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_radio_filled)));
                pickupMarker = mMap.addMarker(new MarkerOptions().position((LatLng) mCurrentRide.getPickup().getCoordinates()).title("Pickup").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_radio)));

                mRideStatus.setText(getResources().getString(R.string.picked_customer));
                mRideStatus.resetSlider();

                mCustomerName.setText(mCurrentRide.getDestination().getName());

                getAssignedCustomerInfo();

                requestList.clear();
                cardRequestAdapter.notifyDataSetChanged();
                erasePolylines();
                getRouteToMarker((LatLng) mCurrentRide.getPickup().getCoordinates());
                break;
            case 2:
                erasePolylines();
//                if
//                (mCurrentRide.getDestination().getCoordinates().latitude != 0.0
//                        && mCurrentRide.getDestination().getCoordinates().longitude != 0.0)
//                {
//                    getRouteToMarker((LatLng) mCurrentRide.getDestination().getCoordinates());
//                }
                mRideStatus.setText(getResources().getString(R.string.drive_complete));
                mRideStatus.resetSlider();
                break;
            default:
                endRide();

        }
    }

    private void getRouteToMarker(LatLng coordinates) {
        String serverKey = getResources().getString(R.string.api_key);
        if (coordinates != null && mlocation != null) {
            GoogleDirection.withServerKey(serverKey)
                    //  .from(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()))
                    .from(new LatLng(mlocation.getLatitude(), mlocation.getLongitude()))
                    .to(coordinates)
                    .transportMode(TransportMode.DRIVING)
                    .execute(this);
        }

    }

    private void getAssignedCustomerInfo() {

        if (mCurrentRide.getCustomer().getId() == null) {
            return;
        }
        DatabaseReference mCustomerDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(mCurrentRide.getCustomer().getId());
        mCustomerDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()) {
                    return;
                }

                if (mCurrentRide != null) {
                    mCurrentRide.getCustomer().parseData(snapshot);

                    mCustomerName.setText(mCurrentRide.getCustomer().getName());
                }

                mCustomerInfo.setVisibility(View.VISIBLE);
                mBottomSheetBehavior.setHideable(false);
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initializeBottomLayout() {

        mBottomSheet = findViewById(R.id.bottomSheet);
        mBottomSheetBehavior = BottomSheetBehavior.from(mBottomSheet);
        mBottomSheetBehavior.setHideable(true);
        mBottomSheetBehavior.setPeekHeight(mBringUpBottomLayout.getHeight());


        mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (mCurrentRide == null) {
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }

    private void initializeRequestCardSwipe() {


        cardRequestAdapter = new CardRequestAdapter(getApplicationContext(), R.layout.item_card_request, requestList);

        final SwipeFlingAdapterView flingContainer = findViewById(R.id.frame);

        flingContainer.setAdapter(cardRequestAdapter);

        //Handling swipe of cards
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                requestList.remove(0);
                cardRequestAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                RideObject mRide = (RideObject) dataObject;
                requestList.remove(mRide);
                cardRequestAdapter.notifyDataSetChanged();
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                RideObject mRide = (RideObject) dataObject;

                if (mRide.getDriver() == null) {

                    try {
                        mCurrentRide = (RideObject) mRide.clone();
                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                    }
                    mCurrentRide.confirmDriver();
                    requestListener();
                }

            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
            }

            @Override
            public void onScroll(float scrollProgressPercent) {
            }
        });
    }

    private void endRide() {
    }

    private void connectGarage() {
        mWorkingSwitch.setChecked(true);
        checkLocationPermission();
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(mLocationRequest,  mLocationCallback, Looper.myLooper());
        if (mMap != null) {
//            double latitude = mlocation.getLatitude();
//            double longitude = mlocation.getLongitude();
            double lat = mlocation.getLatitude();
            double lng = mlocation.getLongitude();
            LatLng latLng = new LatLng(lat, lng);
            mMap.setMyLocationEnabled(true);
        }
    }


    private void logOut() {
        disconnectGarage();

        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(NearbyGarages.this, LauncherActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onDirectionSuccess(Direction direction, String rawBody) {
        if (direction.isOK()) {
            Route route = direction.getRouteList().get(0);

            ArrayList<LatLng> directionPositionList = route.getLegList().get(0).getDirectionPoint();
            Polyline polyline = mMap.addPolyline(DirectionConverter.createPolyline(this, directionPositionList, 5, Color.BLACK));
            polylines.add(polyline);
            setCameraWithCoordinationBounds(route);
        }
    }

    private List<Polyline> polylines;


    private void erasePolylines() {
        for (Polyline line : polylines) {
            line.remove();
        }
        polylines.clear();
    }

    private void setCameraWithCoordinationBounds(Route route) {

        LatLng southwest = route.getBound().getSouthwestCoordination().getCoordination();
        LatLng northeast = route.getBound().getNortheastCoordination().getCoordination();
        LatLngBounds bounds = new LatLngBounds(southwest, northeast);
        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
    }

    @Override
    public void onDirectionFailure(Throwable t) {

    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        googleMap.setMapStyle(new MapStyleOptions(getResources().getString(R.string.style_json)));

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(5000);//interval with which the driver location will be updated
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            } else {
                checkLocationPermission();
            }
        }
    }

    LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(@NonNull LocationResult locationResult) {
           super.onLocationResult(locationResult);

            for (Location location : locationResult.getLocations()) ;

            if (getApplicationContext() != null) {
                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                DatabaseReference refWorking = FirebaseDatabase.getInstance().getReference("driversWorking");
                GeoFire geoFireWorking = new GeoFire(refWorking);
                if (mlocation != null) {
                    double latitude = mlocation.getLatitude();
                    double longitude = mlocation.getLongitude();
                    geoFireWorking.setLocation(userId, new GeoLocation(mlocation.getLatitude(), mlocation.getLongitude()));
                }

                if (!mWorkingSwitch.isChecked()) {

                    geoFireWorking.removeLocation(userId, (key, error) -> {
                    });
                    return;
                }
                geoFireWorking.setLocation(userId, new GeoLocation(mlocation.getLatitude(), mlocation.getLongitude()), ((key, error) -> {


                }));
                if (mCurrentRide != null && mlocation != null) {
                    mCurrentRide.setRideDistance(mCurrentRide.getRideDistance() + mlocation.distanceTo(mlocation) / 1000);
                }



                if (!started) {
                    getRequestsAround();
                    started = true;
                }

                Map<String, Object> newUserMap = new HashMap<>();
                newUserMap.put("last_updated", ServerValue.TIMESTAMP);
                mUser.updateChildren(newUserMap);

                if (!zoomUpdated) {
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(mlocation.getLatitude(), mlocation.getLongitude())));
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(16));
                    zoomUpdated = true;
                }
            }
        }


        private void getRequestsAround() {
            if (mlocation == null) {
                return;
            }

            DatabaseReference requestLocation = FirebaseDatabase.getInstance().getReference().child("customer_requests");

            GeoFire geoFire = new GeoFire(requestLocation);
            geoQuery = geoFire.queryAtLocation(new GeoLocation(mlocation.getLatitude(), mlocation.getLongitude()), MAX_SEARCH_DISTANCE);
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
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (!snapshot.exists()) {
                        return;
                    }

                    if (mCurrentRide != null) {
                        return;
                    }


                    RideObject mRide = new RideObject();
                    mRide.parseData(snapshot);


                    if (!mRide.getRequestService().equals(mDriver.getService())) {
                        return;
                    }


                    for (RideObject mRideIt : requestList) {
                        if (mRideIt.getId().equals(mRide.getId())) {
                            if (mRide.getCancelled() || mRide.getEnded() || mRide.getDriver() != null) {
                                requestList.remove(mRideIt);
                                cardRequestAdapter.notifyDataSetChanged();
                            }
                            return;
                        }
                    }

                    if (!mRide.getCancelled() && !mRide.getEnded() && mRide.getDriver() == null && mRide.getState() == 0) {
                        requestList.add(mRide);
                        cardRequestAdapter.notifyDataSetChanged();
                        makeSound();

                        HashMap<String, Object> map = new HashMap<String, Object>();
                        map.put("timestamp_last_driver_read", ServerValue.TIMESTAMP);
                        FirebaseDatabase.getInstance().getReference().child("ride_info").child(key).updateChildren(map);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        private void makeSound() {
            final MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.garagenotification);
            mp.start();
        }

    };
        @Override
        public void onBackPressed() {
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        }

        @Override
        public boolean onCreateOptionsMenu(@NonNull Menu menu) {
            getMenuInflater().inflate(R.menu.main_menu, menu);
            return true;

        }

        @Override
        public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();
            if (id == R.id.settings) {
                return true;
            }
            return super.onOptionsItemSelected(item);
        }

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();

            if (id == R.id.history) {
                Intent intent = new Intent(NearbyGarages.this, HistoryActivity.class);
                intent.putExtra("customerOrDriver", "Drivers");
                startActivity(intent);
            } else if (id == R.id.settings) {
                Intent intent = new Intent(NearbyGarages.this, GarageSettings.class);
                startActivity(intent);
            } else if (id == R.id.payout) {
                Intent intent = new Intent(NearbyGarages.this, Payments.class);
                startActivity(intent);
            } else if (id == R.id.logout) {
                logOut();
            }

            DrawerLayout drawer = findViewById(R.id.drawer_layout);
           // drawer.closeDrawer(GravityCompat.START);
            drawer.close();
            return true;

        }
    };
