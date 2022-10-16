package com.example.attamechanics.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attamechanics.Callback.EnumSelectionCallback;
import com.example.attamechanics.R;
import com.example.attamechanics.Utils.Font;

public class EnumSelectionRecyclerViewAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private Font font;
    private LayoutInflater layoutInflater;

    private Context context;
    private Object[] enums;
    private EnumSelectionCallback callback;



    public EnumSelectionRecyclerViewAdapter(Context context, Object[] enums, EnumSelectionCallback callback) {
        this.context = context;
        this.enums = enums;
        this.callback = callback;

        this.font = new Font(context);
        this.layoutInflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.enum_selection_recycler_view_item_layout, parent, false);
        view.setHasTransientState(true);
        return new EnumSelectionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((EnumSelectionViewHolder) holder).setData(position, enums[position].toString());
    }

    @Override
    public int getItemCount() {
        return enums.length;
    }

    public class EnumSelectionViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout enumRelativeLayout;
        private TextView enumTextView;

        private String title;

        EnumSelectionViewHolder(View itemView) {
            super(itemView);

            enumRelativeLayout = itemView.findViewById(R.id.enum_selection_recycler_view_item_relative_layout);
            enumTextView = itemView.findViewById(R.id.enum_selection_recycler_view_item_text_view);
        }

        private void setData(int position, String title) {
            this.title = title;
            populateInterfaceElements(position);
        }

        private void populateInterfaceElements(int position) {
            enumRelativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callback.onSelection(title);
                }
            });

            font.applyFont(enumTextView, font.saralaRegular);
            enumTextView.setText(title);
        }

    }
}
