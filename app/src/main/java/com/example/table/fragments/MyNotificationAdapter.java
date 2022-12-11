package com.example.table.fragments;

import android.content.Context;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.table.MainActivity;
import com.example.table.R;

public class MyNotificationAdapter extends RecyclerView.Adapter<MyNotificationAdapter.ViewHolder>{

    MyNotifications [] myNotifications;
    Context context;

    public MyNotificationAdapter(MyNotifications [] myNotifications, NotificationFragment notificationFragment){

        this.myNotifications = myNotifications;
        this.context = notificationFragment.getContext();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.notification_items_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    final MyNotifications myNotificationsList = myNotifications[position];
    Log.i("TEST", "start");
    holder.image_notification.setImageResource(myNotificationsList.getNotification_image());
    Log.i("TEST", "image");
    holder.event_name_notification.setText(myNotificationsList.getEvent_name());
    holder.event_date_notification.setText(myNotificationsList.getDate());
    holder.event_time_notification.setText(myNotificationsList.getTime());
    holder.avatar_image_notification.setImageResource(myNotificationsList.getAvatar_image());
    holder.host_name_notification.setText(myNotificationsList.getHost_name());
    holder.type_of_notification.setText(myNotificationsList.getType_of_notification());

    holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Toast.makeText(context,myNotificationsList.getEvent_name(),Toast.LENGTH_LONG).show();
        }
    });

    }

    @Override
    public int getItemCount() {
        return myNotifications.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image_notification;
        TextView event_name_notification;
        TextView event_date_notification;
        TextView event_time_notification;
        ImageView avatar_image_notification;
        TextView host_name_notification;
        TextView type_of_notification;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image_notification = itemView.findViewById(R.id.image_notification);
            event_name_notification = itemView.findViewById(R.id.event_name_notification);
            event_date_notification = itemView.findViewById(R.id.event_date_notification);
            event_time_notification = itemView.findViewById(R.id.event_time_notification);
            avatar_image_notification = itemView.findViewById(R.id.avatar_image_notification);
            host_name_notification = itemView.findViewById(R.id.host_name_notification);
            type_of_notification = itemView.findViewById(R.id.type_of_notification);
        }
    }
}
