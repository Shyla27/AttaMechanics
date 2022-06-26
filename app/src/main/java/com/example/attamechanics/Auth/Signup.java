package com.example.attamechanics.Auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.attamechanics.Adapters.User;
import com.example.attamechanics.Garage.AllGarages;
import com.example.attamechanics.Garage.Documentation;
import com.example.attamechanics.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Signup extends AppCompatActivity {

    ImageView userphoto;
    static int PreReqCode = 1;
    static int REQUESTCODE = 1;
    Uri pickedImgUri;
    private FirebaseDatabase database;
    private DatabaseReference mdatabase;
    String userID;
    FirebaseFirestore fStore;
    private Spinner userType;
    private String type;
    private EditText inputEmail;
    private EditText inputPassword;
    private EditText name;
    private EditText mobilenumber;
    private EditText confirmpassword;
    private Button btnSignIn, btnSignUp;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private User user;
    private String username, fname, email, password, phone, confirmpass;
    private static final String USERS = "users";
    private String TAG = "Signup";
    private String memberID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        auth = FirebaseAuth.getInstance();
        database= FirebaseDatabase.getInstance();
        btnSignIn = findViewById(R.id.sign_in_button);
        btnSignUp = findViewById(R.id.sign_up_button);
        inputEmail = findViewById(R.id.useremail);
        inputPassword = findViewById(R.id.password);
        confirmpassword = findViewById(R.id.confirmpassword);
        mobilenumber = findViewById(R.id.mobilenumber);
        name = findViewById(R.id.username);
        progressBar = findViewById(R.id.progressBar);
        userType=(Spinner)findViewById(R.id.userType);
        database= FirebaseDatabase.getInstance();
        mdatabase = database.getReference("garagedets");

        List<String> list = new ArrayList<>();
        list.add("Select Account Type");
        list.add("Mechanic Details");
        list.add("Admin Details");

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
                } else if (adapterView.getItemAtPosition(i).toString().equals("Mechanics Details")) {
                    type = adapterView.getItemAtPosition(i).toString();
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

        btnSignIn.setOnClickListener(view -> startActivity(new Intent(Signup.this, Login.class)));

        progressBar.setVisibility(View.INVISIBLE);
        auth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();




        btnSignUp.setOnClickListener(view -> {
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
                    showMessage("Please Verify all fields");
                    btnSignUp.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                } else {
                    CreateUserAccount(email, fullname, password, mobilenumber);
                }
            }
           );


    }

    private void CreateUserAccount(String email, String fullname, String password, String mobilenumber) {
        User user = new User(fullname, email);
        database.getReference("garagedets/profile").child("officenumber").setValue(memberID);

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        showMessage("Account Created");
                        userID = auth.getCurrentUser().getUid();
                        Map<String, Object> users = new HashMap<>();
                        users.put("Name", fullname);
                        users.put("Email", email);
                        users.put("password",password);
                        users.put("mobilenumber", mobilenumber);
                        updateUI();

                    }
                    else {
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        Toast.makeText(Signup.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }

                });

    }

    public void updateUI() {
        String keyid = mdatabase.push().getKey();

        mdatabase.child("officenumber").setValue(memberID);

        if (verifyType())

            return;
        if (type.equals("Mechanic Details")) {
            Intent Signup = new Intent(getApplicationContext(), AllGarages.class);
            startActivity(Signup);
            finish();
        }
        else if (type.equals("Admin Details")) {
            Intent Signup = new Intent(getApplicationContext(), Documentation.class);
            startActivity(Signup);
            finish();
        }
    }

    private boolean verifyType() {
        if (type.equals("Select Account Type"))  {
            Toast.makeText(this, "Please select account type! " ,Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    private void showMessage(String please_verify_all_fields) {
        Toast.makeText(getApplicationContext(), please_verify_all_fields, Toast.LENGTH_LONG).show();

    }
}