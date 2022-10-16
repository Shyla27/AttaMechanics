package com.example.attamechanics.Adapters;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.attamechanics.R;
import com.example.attamechanics.objects.RideObject;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.util.List;

public class CardRequestAdapter extends ArrayAdapter<RideObject> {

    Context context;
    List<RideObject> items;

    public CardRequestAdapter(@NonNull Context context, int resource, List<RideObject> items) {
        super(context, resource, items);
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        RideObject card_item = getItem(position);
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_card_request, parent, false);
        }

        TextView mDistance = convertView.findViewById(R.id.distance);
        TextView mTime = convertView.findViewById(R.id.time);

        CircularProgressBar mProgressBar = convertView.findViewById(R.id.circularProgressBar);


        if (card_item != null) {
            mDistance.setText(card_item.getCalculatedRideDistance());
            mTime.setText(card_item.getCalculatedTime() + " min");
        }


        final Handler ha=new Handler();
        ha.postDelayed(new Runnable() {

            @Override
            public void run() {
                //call function
                if (card_item != null) {
                    card_item.setTimePassed(card_item.getTimePassed() + (float)0.5);
                }
                if (card_item != null) {
                    mProgressBar.setProgress(card_item.getTimePassed());
                }

                if (card_item != null && card_item.getTimePassed() > 100) {
                    items.remove(card_item);

                    notifyDataSetChanged();
                }

                ha.postDelayed(this, 25);

            }
        }, 25);

        return convertView;

    }
}
