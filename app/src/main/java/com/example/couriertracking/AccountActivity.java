package com.example.couriertracking;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AccountActivity extends AppCompatActivity {

    private TextView tvEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        tvEdit = findViewById(R.id.tvEdit);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.nav_account);

        tvEdit.setOnClickListener(v -> Toast.makeText(this, "Edit Profile Clicked", Toast.LENGTH_SHORT).show());

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.nav_home) {
                    startActivity(new Intent(AccountActivity.this, HomeActivity.class));
                    return true;
                } else if (itemId == R.id.nav_package) {
                    startActivity(new Intent(AccountActivity.this, PackageActivity.class));
                    return true;
                } else if (itemId == R.id.nav_message) {
//                  startActivity(new Intent(AccountActivity.this, MessagesActivity.class));
                    return true;
                } else if (itemId == R.id.nav_account) {
                    return true; // Already in AccountActivity, so do nothing
                }
                return false;
            }
        });
    }
}
