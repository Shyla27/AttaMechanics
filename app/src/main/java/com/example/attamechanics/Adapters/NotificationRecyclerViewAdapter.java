package com.example.attamechanics.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.attamechanics.Constants.Notification;
import com.example.attamechanics.Notification.OneSignalNotificationSender;
import com.example.attamechanics.R;
import com.example.attamechanics.Utils.Animate;

public class NotificationRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Animate animate;
    private LayoutInflater layoutInflater;

    private Context context;
    private Notification[] notifications;


    public NotificationRecyclerViewAdapter(Context context, Notification[] notifications) {
        this.context = context;
        this.notifications = notifications;

        this.animate = new Animate();
        this.layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public int getItemViewType(int position) {
        return 0;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.main_notifications_recycler_view_item_layout, parent, false);
        view.setHasTransientState(true);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((NotificationViewHolder) holder).setData(position, notifications[position]);
    }

    @Override
    public int getItemCount() {
        return notifications.length;
    }

    private class NotificationViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout notificationLinearLayout;
        private ImageView notificationImageView;
        private ProgressBar notificationProgressBar;
        private TextView notificationTextView;

        private Notification notification;


        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            notificationLinearLayout = itemView.findViewById(R.id.notification_recycler_view_item_linear_layout);
            notificationImageView = itemView.findViewById(R.id.notification_recycler_view_item_image_view);
            notificationProgressBar = itemView.findViewById(R.id.notification_recycler_view_item_progress_bar);
            notificationTextView = itemView.findViewById(R.id.notification_recycler_view_item_text_view);

        }
        private void setData(int position, Notification notification) {
            this.notification = notification;
            populateInterfaceElements(position);
        }

        private void populateInterfaceElements(int position) {
            animate.toggleAnimationView(true, View.INVISIBLE, notificationImageView, notificationProgressBar);

            notificationLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    OneSignalNotificationSender.sendDeviceNotification(notification);
                }
            });

            notificationTextView.setText(notification.getGroup());

            Glide.with(context)
                    .asBitmap()
                    .load(notification.getIconUrl())
                    .into(new BitmapImageViewTarget(notificationImageView) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            notificationImageView.setImageBitmap(resource);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    animate.toggleAnimationView(false, View.INVISIBLE, notificationImageView, notificationProgressBar);
                                }
                            }, 300);
                        }
                    });
        }

    }
}
