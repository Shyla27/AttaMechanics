package com.example.attamechanics.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.attamechanics.Adapters.AppointmentsRV;
import com.example.attamechanics.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddAppointment extends AppCompatActivity {
    private Button addCourseBtn;
    private TextInputEditText  courseNameEdt, courseImgEdt, date, employee;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private ProgressBar loadingPB;
    private String courseID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointment);
        addCourseBtn = findViewById(R.id.idBtnAddCourse);
        courseNameEdt =(TextInputEditText) findViewById(R.id.appointmentname);
        courseImgEdt = findViewById(R.id.idEdtCarLink);
        employee = findViewById(R.id.assignEmployee);
        date= findViewById(R.id.idEdtCourseLink);
        loadingPB = findViewById(R.id.idPBLoading);
        firebaseDatabase = FirebaseDatabase.getInstance();
        // on below line creating our database reference.
        databaseReference = firebaseDatabase.getReference().child("Appointments");

        addCourseBtn.setOnClickListener(view -> {
            loadingPB.setVisibility(View.VISIBLE);
            // getting data from our edit text.
            String courseName = courseNameEdt.getText().toString();
            String courseImg = courseImgEdt.getText().toString();
            String datetime = date.getText().toString();
            String employeetask = employee.getText().toString();
            courseID = courseName;
            // on below line we are passing all data to our modal class.
            AppointmentsRV dataModal = new AppointmentsRV(courseID, courseName, courseImg, datetime);

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    databaseReference.child(courseID).setValue(dataModal);
                    // displaying a toast message.
                    Toast.makeText(AddAppointment.this, "Appointment Added..", Toast.LENGTH_SHORT).show();
                    // starting a main activity.
                    startActivity(new Intent(AddAppointment.this, Appointments.class));

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(AddAppointment.this, "Fail to add Course..", Toast.LENGTH_SHORT).show();

                }
            });

        });
    }
}