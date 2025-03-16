package com.example.couriertracking;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private List<Notification> notificationList;

    public NotificationAdapter(List<Notification> notificationList) {
        this.notificationList = notificationList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Notification notification = notificationList.get(position);
        holder.notificationText.setText(notification.getMessage());
        holder.notificationTime.setText(notification.getTime());
        holder.notificationIcon.setImageResource(notification.getIcon());
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView notificationText, notificationTime;
        ImageView notificationIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            notificationText = itemView.findViewById(R.id.notificationText);
            notificationTime = itemView.findViewById(R.id.notificationTime);
            notificationIcon = itemView.findViewById(R.id.notificationIcon);
        }
    }
}
