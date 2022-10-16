package com.example.attamechanics.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.CheckBox;

import com.example.attamechanics.R;

public class Specialities extends AppCompatActivity {

    RecyclerView recyclerView;
    AppCompatButton c1,c2, c3,c5, c6, c7, c8, c9, c10, c11, c12, c13, c14, c15, c16, c17, c18, c19, c20, c21, c22, c23;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specialities);

        c1= findViewById(R.id.battery);
        c2 = findViewById(R.id.belts);
        c3 = findViewById(R.id.brakes);
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


    }
}