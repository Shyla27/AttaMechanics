package com.example.attamechanics.Garage;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.attamechanics.Adapters.GaragesAdapter;
import com.example.attamechanics.Adapters.LocationTrack;
import com.example.attamechanics.Auth.Signup;
import com.example.attamechanics.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Garageinfo extends AppCompatActivity {

    private TextInputEditText garagename, speciality, officenumber, latitud, longitud;
    private Button proceed, livelocation,showonmap;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private static final int REQUEST_LOCATION = 1;
    private String garageID;
    private ProgressBar loadingPB;
    private ArrayList permissionsToRequest;
    private ArrayList permissionsRejected = new ArrayList();
    private ArrayList permissions = new ArrayList();

    private final static int ALL_PERMISSIONS_RESULT = 101;
    LocationTrack locationTrack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garageinfo);
        garagename = findViewById(R.id.garagename);
        speciality = findViewById(R.id.speciality);
        officenumber = findViewById(R.id.officenumber);
        proceed = findViewById(R.id.proceed);
        loadingPB = findViewById(R.id.progressBar);
        latitud= findViewById(R.id.latitude);
        longitud = findViewById(R.id.longitude);
        livelocation = findViewById(R.id.livelocation);

        livelocation.setOnClickListener(view -> {
            locationTrack = new LocationTrack(Garageinfo.this);


            if (locationTrack.canGetLocation()) {


                double longitude = locationTrack.getLongitude();
                double latitude = locationTrack.getLatitude();
                latitud.setText(String.valueOf(latitude));
                longitud.setText(String.valueOf(longitude));
                Toast.makeText(getApplicationContext(), "Longitude:" + Double.toString(longitude) + "\nLatitude:" + Double.toString(latitude), Toast.LENGTH_SHORT).show();
            } else {

                locationTrack.showSettingsAlert();
            }

        });


        firebaseDatabase = FirebaseDatabase.getInstance();
      // firebaseDatabase.getReference("garagedets/profile").child("garagename").setValue(garagename);
       // firebaseDatabase.getReference("garagedets/profile").child("garagename").setValue(garagename);

        databaseReference = firebaseDatabase.getReference("garagedets");

        Button proceed = findViewById(R.id.proceed);

        permissions.add(ACCESS_FINE_LOCATION);
        permissions.add(ACCESS_COARSE_LOCATION);
        permissionsToRequest = findUnAskedPermissions(permissions);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


            if (permissionsToRequest.size() > 0)
                requestPermissions((String[]) permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
        }
        proceed.setOnClickListener(view -> {
            loadingPB.setVisibility(View.VISIBLE);
            String garagenam = garagename.getText().toString();
            String specialit = speciality.getText().toString();
            String officenum = officenumber.getText().toString();
            String latitde = latitud.getText().toString();
            String longiude = longitud.getText().toString();
            garageID = garagenam;
            GaragesAdapter garagesAdapter = new GaragesAdapter(garageID, garagenam, specialit, officenum, latitde,longiude);
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    databaseReference.child(garageID).setValue(garagesAdapter);
                    Toast.makeText(Garageinfo.this, "Garage Added..", Toast.LENGTH_SHORT).show();
                    // starting a main activity.
                    startActivity(new Intent(Garageinfo.this, Documentation.class));

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(Garageinfo.this, "Fail to add Course..", Toast.LENGTH_SHORT).show();

                }
            });


        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {

            case ALL_PERMISSIONS_RESULT:
                for (Object perms : permissionsToRequest) {
                    if (!hasPermission((String) perms)) {
                        permissionsRejected.add(perms);
                    }
                }

                if (permissionsRejected.size() > 0) {


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale((String) permissionsRejected.get(0))) {
                            showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions((String[]) permissionsRejected.toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    });
                            return;
                        }
                    }

                }

                break;
        }
    }

    private ArrayList findUnAskedPermissions(ArrayList permissions) {
        ArrayList result = new ArrayList();

        for (Object perm : permissions) {
            if (!hasPermission((String) perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    private boolean hasPermission(String perm) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(perm) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);

    }



    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(Garageinfo.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationTrack.stopListener();
    }
}