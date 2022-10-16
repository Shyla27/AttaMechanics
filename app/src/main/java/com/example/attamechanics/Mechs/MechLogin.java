package com.example.attamechanics.Mechs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.attamechanics.Auth.Login;
import com.example.attamechanics.Auth.Signup;
import com.example.attamechanics.MainActivity;
import com.example.attamechanics.R;
import com.example.attamechanics.ViewModel.LogInViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MechLogin extends AppCompatActivity {

    private EditText inputEmail;
    private EditText inputPassword;
    private TextView btn_signup;
    private Button btn_logIn;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private FirebaseAuth.AuthStateListener authStateListener;
    DatabaseReference admindatabase , mechsdatabase;
    FirebaseDatabase firebaseDatabase;
    private String type;
    LogInViewModel logInViewModel;
    String emailLog;
    String pwdLog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mech_login);

        authStateListener = firebaseAuth -> {
            FirebaseUser user = auth.getCurrentUser();
            if (user != null) {
                Intent intent = new Intent(MechLogin.this, MechanicsDashboard.class);
                startActivity(intent);
                finish();
            }
        };



        inputEmail = findViewById(R.id.mechemail);
        inputPassword = findViewById(R.id.mechpassword);
        progressBar = findViewById(R.id.progressBar);
        btn_signup = findViewById(R.id.btn_signup);
        btn_logIn = findViewById(R.id.btn_login);
        TextView btnReset = findViewById(R.id.btn_reset_password);
        Spinner userType = findViewById(R.id.userType);
        firebaseDatabase = FirebaseDatabase.getInstance();

        btn_signup.setOnClickListener(view -> {
            Intent intent = new Intent(getBaseContext(), MechanicsSignup.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            finish();
        });


    }



}