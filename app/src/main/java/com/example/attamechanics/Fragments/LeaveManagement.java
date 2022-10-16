package com.example.attamechanics.Fragments;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
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

import com.example.attamechanics.Adapters.LeaveManagementRVAdapter;
import com.example.attamechanics.R;

public class LeaveManagement extends Fragment {

    static  private LeaveManagementViewModel leaveManagementViewModel;
    static RecyclerView recyclerView;
    private Boolean isUndo = false;
    private LeaveManagementRVAdapter leaveManagementRVAdapter;
    private ConstraintLayout constraintLayout;

    public static void sort(Context baseContext, Bundle leaveSortBundle) {
        Toast.makeText( baseContext, "sorting...", Toast.LENGTH_LONG).show();
        leaveManagementViewModel.sort(leaveSortBundle);
        LeaveManagementRVAdapter leaveManagementRVAdapter = new LeaveManagementRVAdapter(leaveManagementViewModel,baseContext);
        recyclerView.setAdapter(leaveManagementRVAdapter);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        leaveManagementViewModel =
                ViewModelProviders.of(this).get(LeaveManagementViewModel.class);
        View root = inflater.inflate(R.layout.fragment_leave_management, container, false);

        constraintLayout = root.findViewById(R.id.cl_leave_management);
        recyclerView = root.findViewById(R.id.rc_worker_leave_requests);
        leaveManagementRVAdapter = new LeaveManagementRVAdapter(leaveManagementViewModel,getContext());
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(leaveManagementRVAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        enableSwipeToCompleteAndUndo();

        return root;
    }

    private void enableSwipeToCompleteAndUndo() {

    }



}