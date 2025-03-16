package com.example.couriertracking;  // Make sure this matches your project package

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends AppCompatActivity {  // Check this name

    private RecyclerView recyclerView;
    private NotificationAdapter adapter;
    private List<Notification> notificationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        notificationList = new ArrayList<>();
        notificationList.add(new Notification("Your parcel has been delivered", "11:36 AM 16/09/23", R.drawable.ic_package_green));
        notificationList.add(new Notification("Your parcel has been delivered", "11:36 AM 16/09/23", R.drawable.ic_package_red));
        notificationList.add(new Notification("Your parcel has been delivered", "11:36 AM 16/09/23", R.drawable.ic_package_green));
        notificationList.add(new Notification("Your parcel has been delivered", "11:36 AM 16/09/23", R.drawable.ic_package_green));
        notificationList.add(new Notification("Your parcel has been delivered", "11:36 AM 16/09/23", R.drawable.ic_package_green));
        notificationList.add(new Notification("Your parcel has been delivered", "11:36 AM 16/09/23", R.drawable.ic_package_green));
        notificationList.add(new Notification("Your parcel has been delivered", "11:36 AM 16/09/23", R.drawable.ic_package_green));
        notificationList.add(new Notification("Your parcel has been delivered", "11:36 AM 16/09/23", R.drawable.ic_package_green));
        notificationList.add(new Notification("Your parcel has been delivered", "11:36 AM 16/09/23", R.drawable.ic_package_green));
        notificationList.add(new Notification("Your parcel has been delivered", "11:36 AM 16/09/23", R.drawable.ic_package_green));
        notificationList.add(new Notification("Your parcel has been delivered", "11:36 AM 16/09/23", R.drawable.ic_package_green));
        notificationList.add(new Notification("Your parcel has been delivered", "11:36 AM 16/09/23", R.drawable.ic_package_red));
        notificationList.add(new Notification("Your parcel has been delivered", "11:36 AM 16/09/23", R.drawable.ic_package_green));
        notificationList.add(new Notification("Your parcel has been delivered", "11:36 AM 16/09/23", R.drawable.ic_package_red));
        notificationList.add(new Notification("Your parcel has been delivered", "11:36 AM 16/09/23", R.drawable.ic_package_green));
        notificationList.add(new Notification("Your parcel has been delivered", "11:36 AM 16/09/23", R.drawable.ic_package_red));
        notificationList.add(new Notification("Your parcel has been delivered", "11:36 AM 16/09/23", R.drawable.ic_package_green));
        notificationList.add(new Notification("Your parcel has been delivered", "11:36 AM 16/09/23", R.drawable.ic_package_green));
        notificationList.add(new Notification("Your parcel has been delivered", "11:36 AM 16/09/23", R.drawable.ic_package_green));
        notificationList.add(new Notification("Your parcel has been delivered", "11:36 AM 16/09/23", R.drawable.ic_package_green));
        adapter = new NotificationAdapter(notificationList);
        recyclerView.setAdapter(adapter);
    }
}
