package com.example.couriertracking;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
//import java.util.ArrayList;
import java.util.List;

public class PackageActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ShipmentAdapter adapter;
    private List<Shipment> shipments;
    private ImageView notification;
    private DatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package);

        recyclerView = findViewById(R.id.recentShipmentsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_package);
        notification = findViewById(R.id.ivNotification);

        notification.setOnClickListener(v -> {
            startActivity(new Intent(PackageActivity.this, NotificationActivity.class));
        });

        dbHelper = new DatabaseHelper(this);

        notification = findViewById(R.id.ivNotification);
        notification.setOnClickListener(v ->
                startActivity(new Intent(PackageActivity.this, NotificationActivity.class))
        );

        // Fetch shipments from the database
        shipments = dbHelper.getAllShipments();
        adapter = new ShipmentAdapter(shipments);
        recyclerView.setAdapter(adapter);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.nav_home) {
                    startActivity(new Intent(PackageActivity.this, HomeActivity.class));
                    return true;
                } else if (itemId == R.id.nav_package) {
                    return true;
                } else if (itemId == R.id.nav_message) {
//                    startActivity(new Intent(this, MessagesActivity.class));
                    return true;
                } else if (itemId == R.id.nav_account) {
                    startActivity(new Intent(PackageActivity.this, AccountActivity.class));
                    return true;
                }
                return false;
            }
        });
    }
}
