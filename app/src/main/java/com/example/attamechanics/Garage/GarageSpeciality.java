package com.example.attamechanics.Garage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.example.attamechanics.Adapters.GarageSpecialityAdapter;
import com.example.attamechanics.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GarageSpeciality extends AppCompatActivity {
    Button savecontent;
    CheckBox c1,c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12, c13, c14, c15, c16, c17, c18, c19, c20, c21, c22, c23;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    GarageSpecialityAdapter garageSpecialityAdapter;
    int i = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garage_speciality);

        databaseReference = database.getInstance().getReference().child("Speciality");

        garageSpecialityAdapter = new GarageSpecialityAdapter();

        savecontent = findViewById(R.id.savecontent);
        c1= findViewById(R.id.battery);
        c2 = findViewById(R.id.belts);
        c3 = findViewById(R.id.brakes);
        c4 = findViewById(R.id.carPurchase);
        c5 = findViewById(R.id.clutch);
        c6 = findViewById(R.id.doors);
        c7 = findViewById(R.id.engine);
        c8= findViewById(R.id.exhaust);
        c9 =findViewById(R.id.filters);
        c10 = findViewById(R.id.fluids);
        c11 = findViewById(R.id.fuel);
        c12 = findViewById(R.id.heating);
        c13 = findViewById(R.id.hoses);
        c14 = findViewById(R.id.ignition);
        c15 = findViewById(R.id.lights);
        c16 = findViewById(R.id.mirrors);
        c17 = findViewById(R.id.sensors);
        c18 = findViewById(R.id.suspension);
        c19 = findViewById(R.id.switches);
        c20 = findViewById(R.id.tires);
        c21 = findViewById(R.id.windows);
        c22 = findViewById(R.id.wiper);
        c23 = findViewById(R.id.diagnostics);

        String s1 = "Battery";
        String s2 = "Belts";
        String s3 = "Brakes";
        String s4 = "Car Buying";
        String s5 = "Clutch &amp; Transmission";
        String s6 = "Doors";
        String s7 = "Engine (Under the Hood)";
        String s8 = "Exhaust System";
        String s9 = "Filters";
        String s10 = "Fluids";
        String s11= "Fuel System";
        String s12 = "Heating &amp; AC";
        String s13= "Hoses";
        String s14 = "Ignition System";
        String s15 = "Lights";
        String s16= "Mirrors";
        String s17 = "Sensors";
        String s18 = "Suspension &amp; Steering";
        String s19 = "Switches";
        String s20 = "Tires";
        String s21= "Windows";
        String s22 = "Wiper System";
        String s23 = "Diagnostics";

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
            i = (int) snapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        savecontent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (c1.isChecked()) {
                garageSpecialityAdapter.setSpeciality(s1);
                databaseReference.child(String.valueOf(i+i)).setValue(garageSpecialityAdapter);
                } else {

                }
                if (c2.isChecked()) {
                    garageSpecialityAdapter.setSpeciality(s2);
                    databaseReference.child(String.valueOf(i+i)).setValue(garageSpecialityAdapter);


                } else {

                }
                if (c3.isChecked()) {
                    garageSpecialityAdapter.setSpeciality(s3);
                    databaseReference.child(String.valueOf(i+i)).setValue(garageSpecialityAdapter);


                } else {

                }
                if (c4.isChecked()) {
                    garageSpecialityAdapter.setSpeciality(s4);
                    databaseReference.child(String.valueOf(i+i)).setValue(garageSpecialityAdapter);

                } else {

                }
                if (c5.isChecked()) {
                    garageSpecialityAdapter.setSpeciality(s5);
                    databaseReference.child(String.valueOf(i+i)).setValue(garageSpecialityAdapter);

                } else {

                }
                if (c6.isChecked()) {
                    garageSpecialityAdapter.setSpeciality(s6);
                    databaseReference.child(String.valueOf(i+i)).setValue(garageSpecialityAdapter);

                } else {

                }  if (c7.isChecked()) {
                    garageSpecialityAdapter.setSpeciality(s7);
                    databaseReference.child(String.valueOf(i+i)).setValue(garageSpecialityAdapter);

                } else {

                }
                if (c8.isChecked()) {
                    garageSpecialityAdapter.setSpeciality(s8);
                    databaseReference.child(String.valueOf(i+i)).setValue(garageSpecialityAdapter);

                } else {

                }
                if (c9.isChecked()) {
                    garageSpecialityAdapter.setSpeciality(s9);
                    databaseReference.child(String.valueOf(i+i)).setValue(garageSpecialityAdapter);

                } else {

                }
                if (c10.isChecked()) { garageSpecialityAdapter.setSpeciality(s10);
                    databaseReference.child(String.valueOf(i+i)).setValue(garageSpecialityAdapter);

                } else {

                }
                if (c11.isChecked()) {
                    garageSpecialityAdapter.setSpeciality(s11);
                    databaseReference.child(String.valueOf(i+i)).setValue(garageSpecialityAdapter);

                } else {

                }
                if (c12.isChecked()) {
                    garageSpecialityAdapter.setSpeciality(s12);
                    databaseReference.child(String.valueOf(i+i)).setValue(garageSpecialityAdapter);

                } else {

                }
                if (c13.isChecked()) {
                    garageSpecialityAdapter.setSpeciality(s13);
                    databaseReference.child(String.valueOf(i+i)).setValue(garageSpecialityAdapter);

                } else {

                }
                if (c14.isChecked()) {
                    garageSpecialityAdapter.setSpeciality(s14);
                    databaseReference.child(String.valueOf(i+i)).setValue(garageSpecialityAdapter);

                } else {

                }

                if (c15.isChecked()) {
                    garageSpecialityAdapter.setSpeciality(s15);
                    databaseReference.child(String.valueOf(i+i)).setValue(garageSpecialityAdapter);

                } else {

                }

                if (c16.isChecked()) {
                    garageSpecialityAdapter.setSpeciality(s16);
                    databaseReference.child(String.valueOf(i+i)).setValue(garageSpecialityAdapter);

                } else {

                }
                if (c17.isChecked()) {
                    garageSpecialityAdapter.setSpeciality(s17);
                    databaseReference.child(String.valueOf(i+i)).setValue(garageSpecialityAdapter);

                } else {

                }
                if (c18.isChecked()) {
                    garageSpecialityAdapter.setSpeciality(s18);
                    databaseReference.child(String.valueOf(i+i)).setValue(garageSpecialityAdapter);

                } else {

                }

                if (c19.isChecked()) {
                    garageSpecialityAdapter.setSpeciality(s19);
                    databaseReference.child(String.valueOf(i+i)).setValue(garageSpecialityAdapter);

                } else {

                }
                if (c20.isChecked()) {
                    garageSpecialityAdapter.setSpeciality(s20);
                    databaseReference.child(String.valueOf(i+i)).setValue(garageSpecialityAdapter);

                } else {

                }
                if (c21.isChecked()) {
                    garageSpecialityAdapter.setSpeciality(s21);
                    databaseReference.child(String.valueOf(i+i)).setValue(garageSpecialityAdapter);

                } else {

                }
                if (c22.isChecked()) {
                    garageSpecialityAdapter.setSpeciality(s22);
                    databaseReference.child(String.valueOf(i+i)).setValue(garageSpecialityAdapter);

                } else {

                }  if (c23.isChecked()) {
                    garageSpecialityAdapter.setSpeciality(s23);
                    databaseReference.child(String.valueOf(i+i)).setValue(garageSpecialityAdapter);

                } else {

                }

            }
        });

    }
}