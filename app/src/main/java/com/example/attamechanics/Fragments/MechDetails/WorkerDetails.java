package com.example.attamechanics.Fragments.MechDetails;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.attamechanics.Adapters.EmployeeDets;
import com.example.attamechanics.Adapters.WorkerRVAdapter;
import com.example.attamechanics.Admin.EmployeeDetails;
import com.example.attamechanics.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class WorkerDetails extends Fragment {

    static private WorkerDetailsViewModel workerDetailsViewModel;
    static private RecyclerView recyclerView;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        workerDetailsViewModel = ViewModelProviders.of(this).get(WorkerDetailsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_worker_details, container, false);
        FloatingActionButton fab = root.findViewById(R.id.fab);

        recyclerView = root.findViewById(R.id.rc_worker_details);
        WorkerRVAdapter workerRVAdapter = new WorkerRVAdapter(workerDetailsViewModel,getContext());
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(workerRVAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent generateEmployeeId = new Intent(getContext(), EmployeeDetails.class);
                startActivity(generateEmployeeId);
            }
        });


        return root;

    }
    public static void sort(Context mContext, Bundle b)
    {
        Toast.makeText( mContext, "sorting...", Toast.LENGTH_LONG).show();
        workerDetailsViewModel.sort(b);
        WorkerRVAdapter workerRVAdapter = new WorkerRVAdapter(workerDetailsViewModel,mContext);
        recyclerView.setAdapter(workerRVAdapter);
    }

}