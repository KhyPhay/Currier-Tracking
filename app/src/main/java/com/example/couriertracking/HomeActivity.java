package com.example.couriertracking;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ShipmentAdapter adapter;
    private List<Shipment> shipments;
    private ImageView notification;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.recentShipmentsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        notification = findViewById(R.id.notificationIcon);

        notification.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, NotificationActivity.class));
        });


        DatabaseHelper dbHelper = new DatabaseHelper(this);

        // Fetch shipments from the database
        shipments = dbHelper.getAllShipments();
        adapter = new ShipmentAdapter(shipments);
        recyclerView.setAdapter(adapter);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.nav_home) {
                    return true;
                } else if (itemId == R.id.nav_package) {
                    startActivity(new Intent(HomeActivity.this, PackageActivity.class));
                    return true;
                } else if (itemId == R.id.nav_message) {
                    startActivity(new Intent(HomeActivity.this, ParcelTrackingActivity.class));
                    return true;
                } else if (itemId == R.id.nav_account) {
                    startActivity(new Intent(HomeActivity.this, AccountActivity.class));
                    return true;
                }
                return false;
            }
        });
    }
}
