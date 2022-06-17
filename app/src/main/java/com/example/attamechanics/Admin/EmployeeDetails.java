package com.example.attamechanics.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.attamechanics.Garage.Garageinfo;
import com.example.attamechanics.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EmployeeDetails extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    com.example.attamechanics.Adapters.EmployeeDets employeeDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_details);
        Button addEmployee = findViewById(R.id.addEmployee);
        Button proceed = findViewById(R.id.proceed);
        EditText employeename = findViewById(R.id.employeename);
        EditText email = findViewById(R.id.employeesemail);
        EditText mobilenumber = findViewById(R.id.employeemobilenumber);
        EditText specialty = findViewById(R.id.employeespecialty);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("MechanicsDetails");
        //databaseReference = firebaseDatabase.getReference("MechanicsDetails/users/");
        employeeDetails = new com.example.attamechanics.Adapters.EmployeeDets();
        proceed.setOnClickListener(view -> startActivity(new Intent(EmployeeDetails.this, Garageinfo.class)));
        addEmployee.setOnClickListener(view -> {

            String name = employeename.getText().toString();
            String employeeemail = email.getText().toString();
            String number = mobilenumber.getText().toString();
            String special = specialty.getText().toString();

            if (TextUtils.isEmpty(name) && TextUtils.isEmpty(employeeemail) && TextUtils.isEmpty(number) && TextUtils.isEmpty(special)) {
                Toast.makeText(EmployeeDetails.this, "Please add some data", Toast.LENGTH_SHORT).show();
            } else {
                addToFirebase(name,employeeemail,number,special);
            }
        });
    }

    private void addToFirebase(String name, String employeeemail, String number, String special) {
        employeeDetails.setEmployeename(name);
        employeeDetails.setEmployeeemail(employeeemail);
        employeeDetails.setEmployeeContactNumber(number);
        employeeDetails.setEmployeeSpecialty(special);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    databaseReference.child("MechanicsDetails/users/").push().setValue(user);
                databaseReference.child("number").push().setValue(employeeDetails);
                Toast.makeText(EmployeeDetails.this, "data added", Toast.LENGTH_LONG).show();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EmployeeDetails.this, "Fail to add data " + error, Toast.LENGTH_SHORT).show();

            }
        });


    }
}