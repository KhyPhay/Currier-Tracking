package com.example.couriertracking;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button loginButton = findViewById(R.id.loginButton);
        Button createAccountButton = findViewById(R.id.createAccountButton);

        loginButton.setOnClickListener(view -> {
            // Open Login Activity
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        createAccountButton.setOnClickListener(view -> {
            // Open Register Activity
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
        // Create or open the database
        DatabaseHelper dbHelper = new DatabaseHelper(this);

        // Get writable database instance to create tables and seed data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
    }
}
