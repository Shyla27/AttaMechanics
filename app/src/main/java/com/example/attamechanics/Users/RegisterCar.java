package com.example.attamechanics.Users;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.attamechanics.Adapters.CarsRVModal;
import com.example.attamechanics.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterCar extends AppCompatActivity {
    private EditText carMake, carModel, caryear, carappliedmodel, numberplates;
    private Button proceed, btnGetLocation;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    CarsRVModal carsRVModal;
    private static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;
    String latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_car);

        carMake = findViewById(R.id.garagname);
        carModel = findViewById(R.id.speciality);
        caryear = findViewById(R.id.officenumber);
        carappliedmodel = findViewById(R.id.appliedmodel);
        numberplates = findViewById(R.id.numberplates);
        proceed = findViewById(R.id.proceed);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("CarDetails");
        carsRVModal = new CarsRVModal(carMake, carModel, caryear, carappliedmodel, numberplates);


        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        Button proceed = findViewById(R.id.proceed);


        proceed.setOnClickListener(view -> {
            String carMak = carMake.getText().toString();
            String carmodel = carModel.getText().toString();
            String caryer = caryear.getText().toString();
            String carapliedmodel = carappliedmodel.getText().toString();
            String numberplate = numberplates.getText().toString();


            if (TextUtils.isEmpty(carMak) && TextUtils.isEmpty(carmodel) && TextUtils.isEmpty(caryer) && TextUtils.isEmpty(carapliedmodel) && TextUtils.isEmpty(numberplate)) {
                Toast.makeText(RegisterCar.this, "Please add some data", Toast.LENGTH_SHORT).show();
            } else {
                addToFirebase(carMake, carModel, caryear, carappliedmodel, numberplates);
            }


        });
    }

    private void addToFirebase(EditText carMake, EditText carModel, EditText caryear, EditText carappliedmodel, EditText numberplates) {

        carsRVModal.setCarMake(carMake);
        carsRVModal.setCarModel(carModel);
        carsRVModal.setCaryear(caryear);
        carsRVModal.setCarappliedmodel(carappliedmodel);
        carsRVModal.setNumberPlates(numberplates);
databaseReference.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        databaseReference.setValue(carsRVModal);
        Toast.makeText(RegisterCar.this, "data added", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(RegisterCar.this, UserDashboard.class);
        startActivity(intent);
        finish();

    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {
        Toast.makeText(RegisterCar.this, "Fail to add data " + error, Toast.LENGTH_SHORT).show();

    }
});
    }
}