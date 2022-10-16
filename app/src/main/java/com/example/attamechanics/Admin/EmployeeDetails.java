package com.example.attamechanics.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.attamechanics.Adapters.EmployeeDets;
import com.example.attamechanics.Adapters.Model;
import com.example.attamechanics.Auth.Login;
import com.example.attamechanics.Garage.Garageinfo;
import com.example.attamechanics.MainActivity;
import com.example.attamechanics.Notifications;
import com.example.attamechanics.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class EmployeeDetails extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    com.example.attamechanics.Adapters.EmployeeDets employeeDetails;

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;

    private DatabaseReference reference;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private String onlineUserID;

    private ProgressDialog loader;

    private String key = "";
    private String employeename;
    private String employeeemail;
    private String employeenumber;
    private String speciality;

    BottomNavigationView bottomNavigation;
    private Button assigntask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_details);
        Button proceed = findViewById(R.id.proceed);
        EditText employeename = findViewById(R.id.employeesname);
        EditText email = findViewById(R.id.employeesemail);
        EditText mobilenumber = findViewById(R.id.employeemobilenumber);
        EditText specialty = findViewById(R.id.employeespecialty);

        assigntask = findViewById(R.id.assigntasktoemployee);
        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.myaccount:
                    startActivity(new Intent(getApplicationContext(), MyGarage.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.navigation_home:
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    overridePendingTransition(0, 0);

                    return true;
                case R.id.action_nearby:
                    startActivity(new Intent(getApplicationContext(), GoogleMaps.class));
                    overridePendingTransition(0, 0);
                    return true;

            }
            return false;
        });


            Toolbar mToolbar = findViewById(R.id.toolbar);
            setSupportActionBar(mToolbar);
            Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("My Mechanics");
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
        reference = FirebaseDatabase.getInstance().getReference().child("GarageInfo/MechanicDetails").child(onlineUserID);

        floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMech();
            }
        });

    }

    private void addMech() {
        AlertDialog.Builder myDialog = new AlertDialog.Builder(this); //create a alert dialog
        LayoutInflater inflater = LayoutInflater.from(this);

        //use the input_file layout as the view
        View myView = inflater.inflate(R.layout.input_employee, null);
        myDialog.setView(myView);

        final AlertDialog dialog = myDialog.create();
        dialog.setCancelable(false); //touch outside doesn't cancel

        //mke a round-corner dialog
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //initialize the texts and btns
        final EditText employeename = myView.findViewById(R.id.employeesname);
        final EditText employemail = myView.findViewById(R.id.employeesemail);
        final EditText employeenumber = myView.findViewById(R.id.employeemobilenumber);
        final EditText specality = myView.findViewById(R.id.employeespecialty);


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
                String employename = employeename.getText().toString().trim();
                String employeeemail = employemail.getText().toString().trim();
                String employeesnumber = employeenumber.getText().toString().trim();
                String speciality = specality.getText().toString().trim();
                String id = reference.push().getKey();


                if (TextUtils.isEmpty(employename)) {
                    employeename.setError("Employee Required");
                    return;
                }
                if (TextUtils.isEmpty(employeeemail)) {
                    employemail.setError("Description Required");
                    return;
                } else {
                    loader.setMessage("Adding Employee");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();

                    //use the Model class to pack up the data
                    EmployeeDets employeeDets = new EmployeeDets(employename, employeeemail, id, employeesnumber, speciality);
                    reference.child(id).setValue(employeeDets).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(EmployeeDetails.this, "Employee has been added successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                String error = task.getException().toString();
                                Toast.makeText(EmployeeDetails.this, "Failed: " + error, Toast.LENGTH_SHORT).show();
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

        FirebaseRecyclerOptions<EmployeeDets> options = new FirebaseRecyclerOptions.Builder<EmployeeDets>().setQuery(reference, EmployeeDets.class).build();

        FirebaseRecyclerAdapter<EmployeeDets, MyViewHolder> adapter = new FirebaseRecyclerAdapter<EmployeeDets, MyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull EmployeeDets model) {
                holder.setEmployeename(model.getEmployeename());
                holder.setemail(model.getEmployeeemail());
                holder.setnumber(model.getEmployeenumber());
                holder.setspeciality(model.getSpeciality());

                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        key = getRef(position).getKey();
                        employeename = model.getEmployeename();
                        employeeemail = model.getEmployeeemail();
                        employeenumber = model.getEmployeenumber();
                        speciality = model.getSpeciality();

                        updateMechDetails();
                    }
                });

            }

            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.retrieved_mech, parent, false);
                return new MyViewHolder(view);
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    private void updateMechDetails() {
        AlertDialog.Builder myDialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.update_mechdets, null);
        myDialog.setView(view);
        AlertDialog dialog = myDialog.create();

        //make the corner round
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        EditText mName = view.findViewById(R.id.mEditedName);
        EditText mEmail = view.findViewById(R.id.mEditedEmail);
        EditText mNumber = view.findViewById(R.id.mEditedNumber);
        EditText mSpeciality = view.findViewById(R.id.mEditedSpeciality);

        mName.setText(employeename);
        mName.setSelection(employeename.length());

        mEmail.setText(employeeemail);
        mEmail.setSelection(employeeemail.length());

        mNumber.setText(employeenumber);
        mNumber.setSelection(employeenumber.length());

        mSpeciality.setText(speciality);
        mSpeciality.setSelection(speciality.length());

        Button deleteBtn = view.findViewById(R.id.btnDelete);
        Button updateBtn = view.findViewById(R.id.btnUpdate);
        Button assigntask = view.findViewById(R.id.assigntasktoemployee);
        assigntask.setOnClickListener(view1 -> { Intent i = new Intent(getBaseContext(), Chat.class);
        startActivity(i);
        });
                updateBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        employeename = mName.getText().toString().trim();
                        employeeemail = mEmail.getText().toString().trim();
                        employeenumber = mNumber.getText().toString().trim();
                        speciality = mSpeciality.getText().toString().trim();

//                String date = DateFormat.getDateInstance().format(new Date());

                        // Model model = new Model(task, description, key, date);
                        EmployeeDets employeeDets = new EmployeeDets(employeename, employeeemail, key, employeenumber, speciality);

                        reference.child(key).setValue(employeeDets).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(EmployeeDetails.this, "Employee Details have been updated successfully", Toast.LENGTH_SHORT).show();
                                } else {
                                    String error = task.getException().toString();
                                    Toast.makeText(EmployeeDetails.this, "Update failed" + error, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        dialog.dismiss();
                    }
                });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference.child(key).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(EmployeeDetails.this, "Employee Details have been deleted successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            String error = task.getException().toString();
                            Toast.makeText(EmployeeDetails.this, "Delete failed" + error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                dialog.dismiss();
            }
        });

        dialog.show();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {


        View mView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setEmployeename(String employeename) {
            TextView nameTextView = mView.findViewById(R.id.nameTv);
            nameTextView.setText(employeename);
        }

        public void setemail(String employeeemail) {
            TextView emailTextView = mView.findViewById(R.id.emailTv);
            emailTextView.setText(employeeemail);
        }

        public void setnumber(String employeenumber) {
            TextView numberTextView = mView.findViewById(R.id.numberTv);
            numberTextView.setText(employeenumber);
        }

        public void setspeciality(String speciality) {
            TextView specialTextView = mView.findViewById(R.id.speciality);
            specialTextView.setText(speciality);
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
                Intent intent = new Intent(EmployeeDetails.this, Login.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}