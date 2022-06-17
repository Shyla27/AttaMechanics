package com.example.attamechanics.Adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attamechanics.R;
import com.google.firebase.database.core.Context;

public class Adapter extends RecyclerView.Adapter<Adapter.MyAdapter> {

    Context context;

    public Adapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
     View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent,false);

        return new MyAdapter(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyAdapter holder, int position) {
        if (position ==0)
        {
        holder.image.setImageResource(R.drawable.attapage);
        holder.image1.setImageResource(R.drawable.appointment);
        holder.back.setBackgroundColor(Color.parseColor("#E6E53935"));
        holder.text.setText("Appointments");
        }

        if (position == 1) {
            holder.image.setImageResource(R.drawable.attapage);
            holder.image1.setImageResource(R.drawable.support);
            holder.back.setBackgroundColor(Color.parseColor("#E6E53935"));
            holder.text.setText("Car Services");
        }
        if (position == 2) {
            holder.image.setImageResource(R.drawable.attapage);
            holder.image1.setImageResource(R.drawable.team);
            holder.back.setBackgroundColor(Color.parseColor("#E6E53935"));
            holder.text.setText("My Team");
        }
        if (position == 3) {
            holder.image.setImageResource(R.drawable.attapage);
            holder.image1.setImageResource(R.drawable.prices);
            holder.back.setBackgroundColor(Color.parseColor("#E6E53935"));
            holder.text.setText("My Prices");
        }

    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public class MyAdapter  extends RecyclerView.ViewHolder{
        ImageView image, image1;
        TextView text;
        RelativeLayout back;

        public MyAdapter(@NonNull View itemView) {
            super(itemView);
            image  = itemView.findViewById(R.id.image);
            image1 = itemView.findViewById(R.id.image1);
            text = itemView.findViewById(R.id.text);
            back= itemView.findViewById(R.id.back);
        }
    }
}

