package com.example.attamechanics.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attamechanics.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AppointmentsRVAdapter extends RecyclerView.Adapter<AppointmentsRVAdapter.ViewHolder> {
    private ArrayList<AppointmentsRV> appointmentsRVArrayList;
    private Context context;
    private AppointmentClickInterface appointmentClickInterface;
    int lastPos = -1;

    public AppointmentsRVAdapter(ArrayList<AppointmentsRV> appointmentsRVArrayList, Context context, AppointmentClickInterface appointmentClickInterface) {
       this.appointmentsRVArrayList = appointmentsRVArrayList;
       this.context = context;
       this.appointmentClickInterface = appointmentClickInterface;
    }

    @NonNull
    @Override
    public AppointmentsRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.appointmet_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentsRVAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull List<Object> payloads)  {
        AppointmentsRV appointmentsRV = appointmentsRVArrayList.get(position);
        holder.idTVCarName.setText(appointmentsRV.getAppointmentName());
        holder.idDateTime.setText("Date. " + appointmentsRV.getTimeDate());
        Picasso.get().load(appointmentsRV.getCarImage()).into(holder.idIVcar);
        setAnimation(holder.itemView, position);
        holder.idIVcar.setOnClickListener(view -> appointmentClickInterface.onCourseClick(position));

    }

    private void setAnimation(View itemView, int position) {
        if (position > lastPos) {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_from_bottom);
            itemView.setAnimation(animation);
            lastPos = position;

        }
    }


    @Override
    public int getItemCount() {
        return  appointmentsRVArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView idIVcar;
        private TextView idTVCarName, idDateTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            idDateTime = itemView.findViewById(R.id.idDateTime);
            idTVCarName = itemView.findViewById(R.id.idTVCarName);
            idIVcar = itemView.findViewById(R.id.carmodel);
        }
    }
    public interface AppointmentClickInterface {
        void onCourseClick(int i);
    }
}
