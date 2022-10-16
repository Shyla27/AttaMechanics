package com.example.attamechanics.Mechs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.attamechanics.Adapters.EmployeeDets;
import com.example.attamechanics.Auth.Login;
import com.example.attamechanics.Auth.Signup;
import com.example.attamechanics.Garage.AllGarages;
import com.example.attamechanics.Garage.GarageSpeciality;
import com.example.attamechanics.Garage.Garageinfo;
import com.example.attamechanics.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MechanicsSignup extends AppCompatActivity {

    private EditText inputEmail;
    private EditText inputPassword;
    private EditText name;
    private EditText mobilenumber;
    private EditText confirmpassword;
    private Button btnSignIn, btnSignUp;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private String memberID;
    private FirebaseDatabase database;
    private DatabaseReference mdatabase;
    private static final String USERS = "users";
    private String TAG = "Signup";
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanics_signup);



        auth = FirebaseAuth.getInstance();
        btnSignIn = findViewById(R.id.sign_upmech);
        btnSignUp = findViewById(R.id.loginmech);
        inputEmail = findViewById(R.id.mechemail);
        inputPassword = findViewById(R.id.passcode);
        confirmpassword = findViewById(R.id.confirmpasscode);
        mobilenumber = findViewById(R.id.mechmobilenumber);
        name = findViewById(R.id.mechusername);
        progressBar = findViewById(R.id.progressBar);
        //  userType=(Spinner)findViewById(R.id.userType);
        database= FirebaseDatabase.getInstance();
        mdatabase = database.getReference("GarageInfo/MechanicDetails");

        btnSignUp.setOnClickListener(view -> startActivity(new Intent(MechanicsSignup.this, MechLogin.class)));

        progressBar.setVisibility(View.INVISIBLE);
        auth = FirebaseAuth.getInstance();


        btnSignIn.setOnClickListener(view -> {
                    btnSignUp.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.VISIBLE);
                    String fullname = name.getText().toString().trim();
                    String email = inputEmail.getText().toString().trim();
                    String mobilenumber = inputEmail.getText().toString().trim();
                    String password = inputPassword.getText().toString().trim();
                    String confirmpass = confirmpassword.getText().toString().trim();

                    memberID = mobilenumber;



                    if (email.isEmpty() || fullname.isEmpty() || mobilenumber.isEmpty() ||
                            password.isEmpty() || !password.equals(confirmpass)) {
                        btnSignUp.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.INVISIBLE);
                    } else {
                        CreateMechanicAccount(email, userID, fullname, password, mobilenumber);
                    }
                }
        );


    }

    private void CreateMechanicAccount(String email, String userID, String fullname, String password, String mobilenumber) {
        EmployeeDets employeeDets = new EmployeeDets(email, userID, fullname,password, mobilenumber);
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        String user_id = auth.getCurrentUser().getUid();
                        DatabaseReference current_user_id = mdatabase.child(user_id);
                        current_user_id.child("Username").setValue(fullname);
                        current_user_id.child("contact").setValue(mobilenumber);
                        current_user_id.child("email").setValue(email);
                        current_user_id.child("password").setValue(password);
                        Intent Signup = new Intent(getApplicationContext(), GarageSpeciality.class);
                        startActivity(Signup);
                        finish();

                    }
                    else {
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        Toast.makeText(MechanicsSignup.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });


    }
}