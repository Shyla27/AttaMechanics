package com.example.attamechanics.Auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.attamechanics.Garage.AllGarages;
import com.example.attamechanics.Garage.GarageSpeciality;
import com.example.attamechanics.Garage.Garageinfo;
import com.example.attamechanics.R;
import com.example.attamechanics.Users.UserLogin;

public class FirstPage extends AppCompatActivity {
    private Button newGarage, loginGarage;
    private TextView AddUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);
        newGarage  = findViewById(R.id.newGarage);
        loginGarage = findViewById(R.id.loginGarage);
        AddUser = findViewById(R.id.adduser);

newGarage.setOnClickListener(view -> {
    Intent i = new Intent(getBaseContext(), GarageSpeciality.class);
    startActivity(i);
});
loginGarage.setOnClickListener(view -> {
    Intent i = new Intent(getBaseContext(), Login.class);
    startActivity(i);
});

AddUser.setOnClickListener(view -> {
    Intent user = new Intent(getBaseContext(), UserLogin.class);
    startActivity(user);
});
    }
}