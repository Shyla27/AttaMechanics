package com.example.attamechanics.Auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.attamechanics.MainActivity;
import com.example.attamechanics.Mechs.MechanicsDashboard;
import com.example.attamechanics.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Login extends AppCompatActivity {


    private EditText inputEmail;
    private EditText inputPassword;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    DatabaseReference admindatabase , mechsdatabase;
    FirebaseDatabase firebaseDatabase;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inputEmail = findViewById(R.id.email);
        inputPassword = findViewById(R.id.password);
        progressBar = findViewById(R.id.progressBar);
        TextView btnSignup = findViewById(R.id.btn_signup);
        Button btnLogin = findViewById(R.id.btn_login);
        TextView btnReset = findViewById(R.id.btn_reset_password);
        Spinner userType = findViewById(R.id.userType);

        List<String> list = new ArrayList<>();
        list.add("Select Account Type");
        list.add("Mechanic Details");
        list.add("Garage Admin");

        ArrayAdapter adapter = new ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, list);
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        userType.setAdapter(adapter);
        userType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView.getItemAtPosition(i).toString().equals("Select Account Type")) {

                    type = adapterView.getItemAtPosition(i).toString();
                    inputPassword.setEnabled(false);
                    inputEmail.setEnabled(false);
                }
                else if (adapterView.getItemAtPosition(i).toString().equals("Mechanics")){
                    type= adapterView.getItemAtPosition(i).toString();
                    inputPassword.setEnabled(true);
                    inputEmail.setEnabled(true);
                } else {
                    type = adapterView.getItemAtPosition(i).toString();
                    inputEmail.setEnabled(true);
                    inputPassword.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        auth = FirebaseAuth.getInstance();


        btnSignup.setOnClickListener(view -> startActivity(new Intent(Login.this,Signup.class)));

        btnReset.setOnClickListener(view -> startActivity(new Intent(Login.this, ForgotPassword.class)));

        btnLogin.setOnClickListener(view -> {
            String email = inputEmail.getText().toString();
            final String password = inputPassword.getText().toString();

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(getApplicationContext(), "Enter email address! ", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(getApplicationContext(), "Enter Password!" , Toast.LENGTH_SHORT).show();
                return;
            }
            progressBar.setVisibility(View.VISIBLE);
            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(Login.this, task -> {
                        chooseUser();
                        progressBar.setVisibility(View.VISIBLE);
                        if (task.isSuccessful()) {
                            chooseUser();
                        }
                        else if (!task.isSuccessful()) {
                            if (password.length() < 6 ) {
                                inputPassword.setError(getString(R.string.minimum_password));
                            } else  Toast.makeText(Login.this,getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                        }
                    });

        });


    }

    private void chooseUser() {
        if (verifyType())

            return;
        if (type.equals("Mechanic Details")) {
            Toast.makeText(this, "Welcome Back! " ,Toast.LENGTH_SHORT).show();

            Intent Signup = new Intent(getApplicationContext(), MechanicsDashboard.class);
            startActivity(Signup);
            finish();
        }
        else if (type.equals("Garage Admin")) {
            DatabaseReference admindatabase = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers");
            Toast.makeText(this, "Welcome Back! " ,Toast.LENGTH_SHORT).show();

            Intent Signup = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(Signup);
            finish();
        }
    }
    private boolean verifyType(){
        if (type.equals("Select Account Type"))  {
            Toast.makeText(this, "Please select account type! " ,Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;

    }
}