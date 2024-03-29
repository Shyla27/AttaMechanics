package com.example.attamechanics.Auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.attamechanics.Admin.AdminDashboard;
import com.example.attamechanics.Admin.HistoryActivity;
import com.example.attamechanics.Garage.AllGarages;
import com.example.attamechanics.Garage.Documentation;
import com.example.attamechanics.Garage.EditGarageProfile;
import com.example.attamechanics.Garage.GarageSpeciality;
import com.example.attamechanics.Garage.Garageinfo;
import com.example.attamechanics.MainActivity;
import com.example.attamechanics.Mechs.MechProfile;
import com.example.attamechanics.Mechs.MechanicsDashboard;
import com.example.attamechanics.Mechs.MechanicsSignup;
import com.example.attamechanics.R;
import com.example.attamechanics.Users.UserDashboard;
import com.example.attamechanics.Users.UserLogin;

public class FirstPage extends AppCompatActivity {
    private Button newGarage, loginGarage ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);
        newGarage  = findViewById(R.id.newGarage);
        loginGarage = findViewById(R.id.loginGarage);

newGarage.setOnClickListener(view -> {
    Intent i = new Intent(getBaseContext(), Garageinfo.class);
    startActivity(i);
});
loginGarage.setOnClickListener(view -> {
    Intent i = new Intent(getBaseContext(), Login.class);
    startActivity(i);
});


    }
}