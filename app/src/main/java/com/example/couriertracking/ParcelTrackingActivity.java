package com.example.couriertracking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;


public class ParcelTrackingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parcel_tracking);

        // Initialize views
        ImageView ivBack = findViewById(R.id.backButton);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ParcelTrackingActivity.this, PackageActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
