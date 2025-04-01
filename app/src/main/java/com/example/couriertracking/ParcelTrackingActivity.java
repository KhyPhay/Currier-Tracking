package com.example.couriertracking;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ParcelTrackingActivity extends AppCompatActivity {

    private static final String TAG = "ParcelTrackingActivity"; // Tag for logging

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parcel_tracking);

        String shipmentId = getIntent().getStringExtra("shipment_id");

        if (shipmentId != null) {
            TextView shipmentTextView = findViewById(R.id.trackingIdTextView);
            shipmentTextView.setText("#" + shipmentId);

            // Fetch and display parcel data
            fetchParcelData(shipmentId);
        } else {
            Toast.makeText(this, "No Shipment ID Found", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "No Shipment ID received in intent.");
        }

        // Initialize back button
        ImageView ivBack = findViewById(R.id.backButton);
        ivBack.setOnClickListener(v -> startActivity(new Intent(ParcelTrackingActivity.this, PackageActivity.class)));
    }

    private void fetchParcelData(String trackingId) {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM couriers WHERE tracking_number = ?", new String[]{trackingId});

            if (cursor.moveToFirst()) {
                // Extract data
                String fromPhone = cursor.getString(cursor.getColumnIndexOrThrow("sender_phone"));
                String fromAddress = cursor.getString(cursor.getColumnIndexOrThrow("sender_address_id")); // Consider joining addresses table
                String toPhone = cursor.getString(cursor.getColumnIndexOrThrow("receiver_phone"));
                String toAddress = cursor.getString(cursor.getColumnIndexOrThrow("receiver_address_id"));
                String itemCost = cursor.getString(cursor.getColumnIndexOrThrow("item_cost"));
                String deliveryCost = cursor.getString(cursor.getColumnIndexOrThrow("deliver_price"));

                // Update UI
                ((TextView) findViewById(R.id.fromPhoneTextView)).setText("Phone: " + fromPhone);
//                ((TextView) findViewById(R.id.fromAddressTextView)).setText("Address ID: " + fromAddress);
                ((TextView) findViewById(R.id.toPhoneTextView)).setText("Phone: " + toPhone);
//                ((TextView) findViewById(R.id.toAddressTextView)).setText("Address ID: " + toAddress);
                ((TextView) findViewById(R.id.itemCostTextView)).setText(itemCost + "$");
                ((TextView) findViewById(R.id.deliveryCostTextView)).setText(deliveryCost + "$");


                String senderAddressQuery = "SELECT sender_address_id FROM couriers WHERE tracking_number = ?";
                Cursor senderCursor = db.rawQuery(senderAddressQuery, new String[]{trackingId});
                String senderAddress = "";
                if (senderCursor.moveToFirst()) {
                    int senderAddressId = senderCursor.getInt(senderCursor.getColumnIndexOrThrow("sender_address_id"));

                    // Query to fetch sender address from addresses table
                    String querySender = "SELECT * FROM addresses WHERE id = ?";
                    Cursor senderAddressCursor = db.rawQuery(querySender, new String[]{String.valueOf(senderAddressId)});

                    if (senderAddressCursor.moveToFirst()) {
                        senderAddress = senderAddressCursor.getString(senderAddressCursor.getColumnIndexOrThrow("name"));
                        ((TextView) findViewById(R.id.fromAddressTextView)).setText("Address: " + senderAddress);
                    }
                    senderAddressCursor.close();
                }
                senderCursor.close();

                String receiverAddressQuery = "SELECT receiver_address_id FROM couriers WHERE tracking_number = ?";
                Cursor receiverCursor = db.rawQuery(receiverAddressQuery, new String[]{trackingId});

                String receiverAddress = "";
                if (receiverCursor.moveToFirst()) {
                    int receiverAddressId = receiverCursor.getInt(receiverCursor.getColumnIndexOrThrow("receiver_address_id"));

                    // Query to fetch receiver address from addresses table
                    String queryReceiver = "SELECT * FROM addresses WHERE id = ?";
                    Cursor receiverAddressCursor = db.rawQuery(queryReceiver, new String[]{String.valueOf(receiverAddressId)});

                    if (receiverAddressCursor.moveToFirst()) {
                        receiverAddress = receiverAddressCursor.getString(receiverAddressCursor.getColumnIndexOrThrow("name"));
                        ((TextView) findViewById(R.id.toAddressTextView)).setText("Address: " + receiverAddress);
                    }
                    receiverAddressCursor.close();
                }
                receiverCursor.close();

            } else {
                Toast.makeText(this, "No data found for Tracking ID: " + trackingId, Toast.LENGTH_SHORT).show();
                Log.w(TAG, "No data found for tracking number: " + trackingId);
            }
        } catch (Exception e) {
            Log.e(TAG, "Database error while fetching parcel data: " + e.getMessage(), e);
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }
    }
    private void fetchParcelStatus(String trackingId) {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Query to get parcel status updates for the tracking ID
        String query = "SELECT status, status_date, timestamp FROM parcel_status WHERE tracking_id = ? ORDER BY timestamp DESC";
        Cursor cursor = db.rawQuery(query, new String[]{trackingId});

        // Check if data exists for the tracking ID
        if (cursor.moveToFirst()) {
            // Iterate through all status updates
            do {
                // Get data from the cursor
                String status = cursor.getString(cursor.getColumnIndexOrThrow("status"));
                String statusDate = cursor.getString(cursor.getColumnIndexOrThrow("status_date"));
                String timestamp = cursor.getString(cursor.getColumnIndexOrThrow("timestamp"));

                // Dynamically display each status update
                displayStatus(status, statusDate, timestamp);
            } while (cursor.moveToNext());
        } else {
            // Show a message if no data is found for the given tracking ID
            Toast.makeText(this, "No data found for Tracking ID: " + trackingId, Toast.LENGTH_SHORT).show();
        }

        cursor.close();
        db.close();
    }
    private void displayStatus(String status, String statusDate, String timestamp) {
        // Find the container where status updates will be added
        LinearLayout statusLayout = findViewById(R.id.statusLayout);

        // Create a new LinearLayout for each status item
        LinearLayout statusItem = new LinearLayout(this);
        statusItem.setOrientation(LinearLayout.HORIZONTAL);
        statusItem.setPadding(0, 16, 0, 16);

        // Create TextView for displaying status date
        TextView dateTextView = new TextView(this);
        dateTextView.setText(statusDate);  // Status date (e.g., "17 Sept")
        dateTextView.setTextSize(16f);
        statusItem.addView(dateTextView);

        // Create TextView for displaying status description
        TextView statusTextView = new TextView(this);
        statusTextView.setText(status);  // Parcel status (e.g., "Delivered", "In-Transit")
        statusTextView.setTextSize(16f);
        statusItem.addView(statusTextView);

        // Create TextView for displaying timestamp (optional, you can format the timestamp as needed)
        TextView timestampTextView = new TextView(this);
        timestampTextView.setText("Timestamp: " + timestamp);  // Optional: Display timestamp for the status update
        timestampTextView.setTextSize(14f);
        statusItem.addView(timestampTextView);

        // Add the dynamically created status item to the parent layout
        statusLayout.addView(statusItem);
    }
}
