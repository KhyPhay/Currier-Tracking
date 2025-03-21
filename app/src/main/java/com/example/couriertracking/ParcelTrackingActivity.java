//package com.example.couriertracking;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.MenuItem;
//import android.widget.ImageView;
//
//import androidx.activity.EdgeToEdge;
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.google.android.material.bottomnavigation.BottomNavigationView;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class ParcelTrackingActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_parcel_tracking);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//    }
//}

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
import java.util.ArrayList;
import java.util.List;

public class ParcelTrackingActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ShipmentAdapter adapter;
    private List<Shipment> shipments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.recentShipmentsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        shipments = new ArrayList<>();
//        shipments.add(new Shipment("#HWDSF776567DS", "On the way", "24 June"));
//        shipments.add(new Shipment("#7XZ6V87Z6XCSA7", "Delivered", "24 May"));
//        shipments.add(new Shipment("#7XZ6V87Z6XCSA7", "Delivered", "24 May"));
//        shipments.add(new Shipment("#7XZ6V87Z6XCSA7", "Delivered", "24 May"));
//        shipments.add(new Shipment("#7XZ6V87Z6XCSA7", "Delivered", "24 May"));
//        shipments.add(new Shipment("#7XZ6V87Z6XCSA7", "Delivered", "24 May"));
//        shipments.add(new Shipment("#7XZ6V87Z6XCSA7", "Delivered", "24 May"));
//        adapter = new ShipmentAdapter(shipments);
        recyclerView.setAdapter(adapter);





        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.nav_home) {
                    return true;
                } else if (itemId == R.id.nav_package) {
                    startActivity(new Intent(ParcelTrackingActivity.this, PackageActivity.class));
                    return true;
                } else if (itemId == R.id.nav_message) {
//                    startActivity(new Intent(this, MessagesActivity.class));
                    return true;
                } else if (itemId == R.id.nav_account) {
                    startActivity(new Intent(ParcelTrackingActivity.this, AccountActivity.class));
                    return true;
                }
                return false;
            }
        });
    }
}