package com.example.attamechanics.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attamechanics.Callback.PairItemActionCallback;

import java.util.ArrayList;
import java.util.Map;

public class PairRecyclerViewAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private LayoutInflater layoutInflater;

    private Context context;

    private ArrayList<Map.Entry> tags;
    private PairItemActionCallback callback;

    public PairRecyclerViewAdapter(Context context, ArrayList<Map.Entry> tags, PairItemActionCallback callback) {
        this.context = context;

        this.tags = tags;
        this.callback = callback;

        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
