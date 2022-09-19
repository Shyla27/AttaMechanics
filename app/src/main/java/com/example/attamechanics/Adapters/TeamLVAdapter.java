package com.example.attamechanics.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.attamechanics.Admin.MyMechanics;
import com.example.attamechanics.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TeamLVAdapter  extends ArrayAdapter<EmployeeDets> {


    public TeamLVAdapter(@NonNull Context context,  ArrayList<EmployeeDets> employeeDetsArrayList) {
        super(context,0, employeeDetsArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitemView = convertView;
        if (listitemView == null) {
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.image_lv_item, parent, false);
        }
        EmployeeDets employeeDets = getItem(position);
        TextView nameTV = listitemView.findViewById(R.id.idTVtext);
        ImageView courseIV = listitemView.findViewById(R.id.idIVimage);
        nameTV.setText(employeeDets.getEmployeename());
      //  Picasso.get().load(employeeDets.getImgUrl()).into(courseIV);
        listitemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Item clicked is : " + employeeDets.getEmployeename(), Toast.LENGTH_SHORT).show();
            }
        });
        return listitemView;
    }
}
