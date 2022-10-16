package com.example.attamechanics.Admin;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attamechanics.Adapters.AppointmentsRV;
import com.example.attamechanics.Auth.Login;
import com.example.attamechanics.MainActivity;
import com.example.attamechanics.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class MyTasks extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;

    private DatabaseReference reference;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private String onlineUserID;

    private ProgressDialog loader;

    private String key = "";
    private String clientsdets;
    private String dateofbooking;
    private String carproblemo;
    private String cardetails;
    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tasks);


        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            switch(item.getItemId())
            {
                case R.id.myaccount:
                    startActivity(new Intent(getApplicationContext(), MyGarage.class));
                    overridePendingTransition(0,0);
                    return true;
                case R.id.navigation_home:
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    overridePendingTransition(0,0);

                    return true;
                case R.id.action_nearby:
                    startActivity(new Intent(getApplicationContext(), GoogleMaps.class));
                    overridePendingTransition(0,0);
                    return true;

            }
            return false;
        });
        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Our Appointments");
        mAuth = FirebaseAuth.getInstance();

        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        //set up a loader
        loader = new ProgressDialog(this);

        mUser = mAuth.getCurrentUser();
        onlineUserID = mUser.getUid();
        reference = FirebaseDatabase.getInstance().getReference("Appointments").child(onlineUserID);


        floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(view -> addAppointment());

    }

    private void addAppointment() {

        AlertDialog.Builder myDialog = new AlertDialog.Builder(this); //create a alert dialog
        LayoutInflater inflater = LayoutInflater.from(this);

        //use the input_file layout as the view
        View myView = inflater.inflate(R.layout.input_appointment, null);
        myDialog.setView(myView);

        final AlertDialog dialog = myDialog.create();
        dialog.setCancelable(false); //touch outside doesn't cancel

        //mke a round-corner dialog
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //initialize the texts and btns
        final EditText clientname = myView.findViewById(R.id.clientsname);
        final EditText description = myView.findViewById(R.id.description);
        final EditText date = myView.findViewById(R.id.timedate);
        final EditText cartype = myView.findViewById(R.id.carType);

        Button save = myView.findViewById(R.id.saveBtn);
        Button cancel = myView.findViewById(R.id.CancelBtn);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mclient = clientname.getText().toString().trim();
                String mDescription = description.getText().toString().trim();
                String mdate = date.getText().toString().trim();
               String cardet = cartype.getText().toString().trim();
                String id = reference.push().getKey();


                if (TextUtils.isEmpty(mclient)) {
                    clientname.setError("Name Required");
                    return;
                }
                if (TextUtils.isEmpty(mDescription)) {
                    description.setError("Description Required");
                    return;
                } else {
                    loader.setMessage("Adding Appointment");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();
AppointmentsRV appointmentsRV = new AppointmentsRV(mDescription, mclient, mdate,cardet);


                    //use the Model class to pack up the data
                   // Model model = new Model(mTask, mDescription, id, mdate);
                    reference.child(id).setValue(appointmentsRV).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(MyTasks.this, "Appointment has been added successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                String error = task.getException().toString();
                                Toast.makeText(MyTasks.this, "Failed: " + error, Toast.LENGTH_SHORT).show();
                            }
                            loader.dismiss();
                        }
                    });
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<AppointmentsRV> options = new FirebaseRecyclerOptions.Builder<AppointmentsRV>().setQuery(reference,  AppointmentsRV.class).build();

        FirebaseRecyclerAdapter<AppointmentsRV, MyTasks.MyViewHolder> adapter = new FirebaseRecyclerAdapter<AppointmentsRV, MyTasks.MyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyTasks.MyViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull AppointmentsRV appointmentsRV) {
                holder.setDate(appointmentsRV.getTimeDate());
                holder.setTask(appointmentsRV.getCarProblem());
                holder.setDesc(appointmentsRV.getClientname());
                holder.setCarType(appointmentsRV.getCarModel());

                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        key= getRef(position).getKey();
                        clientsdets = appointmentsRV.getAppointmentName();
                        carproblemo = appointmentsRV.getCarProblem();
                        dateofbooking = appointmentsRV.getTimeDate();
                        cardetails = appointmentsRV.getCarModel();

                        updateAppointment();
                    }
                });
            }



            @NonNull
            @Override
            public MyTasks.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.retrieved_appointments, parent, false);

                return new MyViewHolder(view);
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }



    private void updateAppointment() {
        AlertDialog.Builder myDialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.update_appointment, null);
        myDialog.setView(view);

        AlertDialog dialog = myDialog.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        EditText mClientName = view.findViewById(R.id.mappointmentname);
        EditText mCarProblem = view.findViewById(R.id.appointmentdetails);
        EditText mCarDescription = view.findViewById(R.id.cartypess);

        EditText mDateTime = view.findViewById(R.id.datetimeappointment);


        mClientName.setText(clientsdets);
        mClientName.setSelection(clientsdets.length());

        mCarDescription.setText(cardetails);
        mCarDescription.setSelection(cardetails.length());

        mCarProblem.setText(carproblemo);
        mCarProblem.setSelection(carproblemo.length());

        mDateTime.setText(dateofbooking);
        mDateTime.setSelection(dateofbooking.length());



        Button deleteBtn = view.findViewById(R.id.rejectappointment);
        Button updateBtn = view.findViewById(R.id.updateappointment);
        Button assigntask = view.findViewById(R.id.assigntoemployee);

        updateBtn.setOnClickListener(view1 -> {

            clientsdets = mClientName.getText().toString().trim();
            cardetails = mCarDescription.getText().toString().trim();
            carproblemo = mCarProblem.getText().toString().trim();
            dateofbooking = mDateTime.getText().toString().trim();

            AppointmentsRV appointmentsRV = new AppointmentsRV(clientsdets, cardetails, carproblemo,dateofbooking);
            reference.child(key).setValue(appointmentsRV).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(MyTasks.this, "Price has been updated successfully", Toast.LENGTH_SHORT).show();
                    } else{
                        String error = task.getException().toString();
                        Toast.makeText(MyTasks.this, "Update failed" + error, Toast.LENGTH_SHORT).show();
                    }
                }
            });
            dialog.dismiss();
        });

    }






    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        View mView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;

            itemView.setOnClickListener(view -> {
                Intent intent = new Intent(view.getContext(), AppointmentDets.class);
                view.getContext().startActivity(intent);

            });
        }

        public void setTask(String task) {
            TextView taskTextView = mView.findViewById(R.id.taskTv);
            taskTextView.setText(task);
        }

        public void setDesc(String desc) {
            TextView descTextView = mView.findViewById(R.id.descriptionTv);
            descTextView.setText(desc);
        }

        public void setDate(String date) {
            TextView dateTextView = mView.findViewById(R.id.dateTv);
            dateTextView.setText(date);
        }

        public void setCarType (String carType){
            TextView cartypes = mView.findViewById(R.id.cartype);
            cartypes.setText(carType);
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(view.getContext(), "success" + getLayoutPosition(), Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                mAuth.signOut();
                Intent intent = new Intent(MyTasks.this, Login.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}