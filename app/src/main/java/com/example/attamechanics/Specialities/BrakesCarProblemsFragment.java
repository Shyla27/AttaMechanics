package com.example.attamechanics.Specialities;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.attamechanics.Admin.AssignTasks;
import com.example.attamechanics.Admin.MyPrices;
import com.example.attamechanics.MainActivity;
import com.example.attamechanics.R;

public class BrakesCarProblemsFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    LinearLayout Auxiliary, CableReplacement, replacement,battery;


    private String mParam1;
    private String mParam2;

    public BrakesCarProblemsFragment() {
        // Required empty public constructor
    }

    public static BrakesCarProblemsFragment newInstance(String param1, String param2) {
        BrakesCarProblemsFragment fragment = new BrakesCarProblemsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_brakes_car_problems,
                container, false);
        LinearLayout button = view.findViewById(R.id.Auxiliary);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent1 = new Intent(getActivity(), MyPrices.class);
                startActivity(intent1);
            }
        });
        return view;

    }
}