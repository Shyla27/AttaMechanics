package com.example.attamechanics.Admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.attamechanics.Adapters.AppointmentsRV;
import com.example.attamechanics.Adapters.AppointmentsRVAdapter;
import com.example.attamechanics.Auth.Login;
import com.example.attamechanics.MainActivity;
import com.example.attamechanics.Notifications;
import com.example.attamechanics.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Appointments extends AppCompatActivity implements AppointmentsRVAdapter.AppointmentClickInterface {

    ListView coursesLV;
    ArrayList<AppointmentsRV> dataModalArrayList;
    FirebaseFirestore db;
    private AppointmentsRVAdapter appointmentsRVAdapter;
    private FloatingActionButton addCourseFAB;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private RecyclerView courseRV;
    private FirebaseAuth mAuth;
    private ProgressBar loadingPB;
    private RelativeLayout homeRL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointments);
        dataModalArrayList = new ArrayList<>();
        courseRV = findViewById(R.id.idRVCourses);
        homeRL = findViewById(R.id.appointments);
        loadingPB = findViewById(R.id.idPBLoading);
        addCourseFAB = findViewById(R.id.idFABAddCourse);
        firebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        dataModalArrayList = new ArrayList<>();
        databaseReference = firebaseDatabase.getReference("Appointments");
        addCourseFAB.setOnClickListener(view -> {
            Intent i = new Intent(Appointments.this, AddAppointment.class);
            startActivity(i);

        });
        appointmentsRVAdapter = new AppointmentsRVAdapter(dataModalArrayList, this, this::onAppointmentClick);
        // setting layout malinger to recycler view on below line.
        courseRV.setLayoutManager(new LinearLayoutManager(this));
        // setting adapter to recycler view on below line.
        courseRV.setAdapter(appointmentsRVAdapter);
        // on below line calling a method to fetch courses from database.
        getCourses();

    }

    private void onAppointmentClick(int i) {
        displayBottomSheet(dataModalArrayList.get(i));
    }

    private void displayBottomSheet(AppointmentsRV dataModal) {

        final BottomSheetDialog bottomSheetTeachersDialog = new BottomSheetDialog(this, R.style.BottomSheetDialogTheme);
        // on below line we are inflating our layout file for our bottom sheet.
        View layout = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_layout, homeRL);
        // setting content view for bottom sheet on below line.
        bottomSheetTeachersDialog.setContentView(layout);
        // on below line we are setting a cancelable
        bottomSheetTeachersDialog.setCancelable(false);
        bottomSheetTeachersDialog.setCanceledOnTouchOutside(true);
        // calling a method to display our bottom sheet.
        bottomSheetTeachersDialog.show();
        // on below line we are creating variables for
        // our text view and image view inside bottom sheet
        // and initialing them with their ids.
        TextView courseNameTV = layout.findViewById(R.id.idTVCourseName);
        TextView courseDescTV = layout.findViewById(R.id.idTVCourseDesc);
        TextView suitedForTV = layout.findViewById(R.id.idTVSuitedFor);
        TextView priceTV = layout.findViewById(R.id.idTVCoursePrice);
        ImageView courseIV = layout.findViewById(R.id.idIVCourse);
        // on below line we are setting data to different views on below line.
        courseNameTV.setText(dataModal.getAppointmentName());
        priceTV.setText("2022" + dataModal.getTimeDate());
        Picasso.get().load(dataModal.getCarImage()).into(courseIV);
        Button viewBtn = layout.findViewById(R.id.idBtnVIewDetails);
        Button editBtn = layout.findViewById(R.id.idBtnEditCourse);

        editBtn.setOnClickListener(view -> {
            Intent i = new Intent(Appointments.this, EditAppointment.class);
            // on below line we are passing our course courseRVModal
            i.putExtra("course", dataModal);
            startActivity(i);
        });
//        viewBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(Intent.ACTION_VIEW);
//                i.setData(Uri.parse(dataModal.getCourseLink()));
//                startActivity(i);
//            }
//        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.idLogOut:
                // displaying a toast message on user logged out inside on click.
                Toast.makeText(getApplicationContext(), "User Logged Out", Toast.LENGTH_LONG).show();
                // on below line we are signing out our user.
                mAuth.signOut();
                // on below line we are opening our login activity.
                Intent i = new Intent(Appointments.this, Login.class);
                startActivity(i);
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void getCourses() {
        dataModalArrayList.clear();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                // adding snapshot to our array list on below line.
                dataModalArrayList.add(snapshot.getValue(AppointmentsRV.class));
                // notifying our adapter that data has changed.
appointmentsRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                appointmentsRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                appointmentsRVAdapter.notifyDataSetChanged();
                loadingPB.setVisibility(View.GONE);

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                appointmentsRVAdapter.notifyDataSetChanged();
                loadingPB.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onCourseClick(int position) {
        displayBottomSheet(dataModalArrayList.get(position));
    }
}