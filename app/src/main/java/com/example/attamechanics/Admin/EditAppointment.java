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
import com.example.attamechanics.MainActivity;
import com.example.attamechanics.R;
import com.example.attamechanics.Users.UserDashboard;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class EditAppointment extends AppCompatActivity {
    private TextInputEditText courseNameEdt, courseImgEdt, date;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    AppointmentsRV dataModal;
    private ProgressBar loadingPB;
    // creating a string for our course id.
    private String courseID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_appointment);

        Button addCourseBtn = findViewById(R.id.idBtnAddCourse);
        courseNameEdt = findViewById(R.id.idEdtCourseName);
        date = findViewById(R.id.idEdtSuitedFor);
        loadingPB = findViewById(R.id.idPBLoading);
        firebaseDatabase = FirebaseDatabase.getInstance();
        // on below line we are getting our modal class on which we have passed.
        dataModal = getIntent().getParcelableExtra("course");
        Button deleteCourseBtn = findViewById(R.id.idBtnDeleteCourse);

        if (dataModal != null) {
            // on below line we are setting data to our edit text from our modal class.
            courseNameEdt.setText(dataModal.getAppointmentName());
            date.setText(dataModal.getTimeDate());
            courseImgEdt.setText(dataModal.getCarImage());
        }

        // on below line we are initialing our database reference and we are adding a child as our course id.
        databaseReference = firebaseDatabase.getReference("Appointments").child(courseID);

        addCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingPB.setVisibility(View.VISIBLE);
                String name = courseNameEdt.getText().toString();
                String courseImg = courseImgEdt.getText().toString();
                String datetime = date.getText().toString();

                Map<String, Object> map = new HashMap<>();
                map.put("appointmentName", name);
                map.put("carImage", courseImg);
                map.put("day", datetime);
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        loadingPB.setVisibility(View.GONE);
                        // adding a map to our database.
                        databaseReference.updateChildren(map);
                        // on below line we are displaying a toast message.
                        Toast.makeText(EditAppointment.this, "Appointment Updated..", Toast.LENGTH_SHORT).show();
                        // opening a new activity after updating our coarse.
                        startActivity(new Intent(EditAppointment.this, UserDashboard.class));

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(EditAppointment.this, "Fail to update course..", Toast.LENGTH_SHORT).show();

                    }
                });
    deleteCourseBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            deleteCourse();
        }
    });
            }
        });
    }

    private void deleteCourse() {
        databaseReference.removeValue();
        // displaying a toast message on below line.
        Toast.makeText(this, "Appointment Deleted..", Toast.LENGTH_SHORT).show();
        // opening a main activity on below line.
        startActivity(new Intent(EditAppointment.this, Appointments.class));

    }
}