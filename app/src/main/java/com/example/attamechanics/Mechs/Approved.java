package com.example.attamechanics.Mechs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.attamechanics.R;

public class Approved extends AppCompatActivity {
    private Button tomain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approved);
        tomain = findViewById(R.id.tomain);

        tomain.setOnClickListener(view -> {
            Intent i = new Intent(getBaseContext(), MechanicsDashboard.class);
            startActivity(i);
        });
    }
}