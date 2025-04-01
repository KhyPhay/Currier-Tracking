package com.example.couriertracking;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "courier_tracking.db";
    private static final int DATABASE_VERSION = 3;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create tables
        String CREATE_USERS_TABLE = "CREATE TABLE users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "email TEXT UNIQUE NOT NULL, " +
                "password TEXT NOT NULL, " +
                "phone TEXT, " +
                "created_at DATETIME DEFAULT CURRENT_TIMESTAMP" +
                ")";
        db.execSQL(CREATE_USERS_TABLE);

        String CREATE_ADDRESSES_TABLE = "CREATE TABLE addresses (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "user_id INTEGER, " +
                "name TEXT NOT NULL, " +
                "phone TEXT NOT NULL, " +
                "address_line TEXT NOT NULL, " +
                "city TEXT NOT NULL, " +
                "state TEXT NOT NULL, " +
                "country TEXT NOT NULL, " +
                "created_at DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                "FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET NULL" +
                ")";
        db.execSQL(CREATE_ADDRESSES_TABLE);

        String CREATE_COURIERS_TABLE = "CREATE TABLE couriers (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "tracking_number TEXT UNIQUE NOT NULL, " +
                "sender_phone TEXT NOT NULL, " +
                "sender_address_id INTEGER, " +
                "receiver_address_id INTEGER, " +
                "receiver_phone TEXT NOT NULL, " +
                "weight DECIMAL(10,2), " +
                "status TEXT CHECK(status IN ('pending', 'in_transit', 'out_for_delivery', 'delivered', 'cancelled')) DEFAULT 'pending', " +
                "estimated_delivery DATE, " +
                "created_at DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                "updated_at DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                "FOREIGN KEY (sender_address_id) REFERENCES addresses(id) ON DELETE CASCADE, " +
                "FOREIGN KEY (receiver_address_id) REFERENCES addresses(id) ON DELETE CASCADE" +
                ")";
        db.execSQL(CREATE_COURIERS_TABLE);

        String CREATE_TRACKING_STATUS_TABLE = "CREATE TABLE tracking_status (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "courier_id INTEGER NOT NULL, " +
                "status TEXT CHECK(status IN ('pending', 'in_transit', 'out_for_delivery', 'delivered', 'cancelled')) NOT NULL, " +
                "location TEXT, " +
                "status_date DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                "timestamp DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                "FOREIGN KEY (courier_id) REFERENCES couriers(id) ON DELETE CASCADE" +
                ")";
        db.execSQL(CREATE_TRACKING_STATUS_TABLE);

        // Seed data
        seedData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS tracking_status");
//        db.execSQL("DROP TABLE IF EXISTS couriers");
//        db.execSQL("DROP TABLE IF EXISTS addresses");
//        db.execSQL("DROP TABLE IF EXISTS users");
//        onCreate(db);
        if (oldVersion < 3) { // If database version is 1, add the columns without dropping data
//            db.execSQL("ALTER TABLE couriers ADD COLUMN deliver_price REAL");
//            db.execSQL("ALTER TABLE couriers ADD COLUMN item_cost REAL");
            db.execSQL("ALTER TABLE couriers ADD COLUMN item_cost status_text");
        }
    }

    private void seedData(SQLiteDatabase db) {
        // Insert sample users
        ContentValues userValues = new ContentValues();
        userValues.put("name", "John Doe");
        userValues.put("email", "john@example.com");
        userValues.put("password", "password123");
        userValues.put("phone", "123456789");
        db.insert("users", null, userValues);

        userValues.clear();
        userValues.put("name", "Jane Smith");
        userValues.put("email", "jane@example.com");
        userValues.put("password", "password456");
        userValues.put("phone", "987654321");
        db.insert("users", null, userValues);

        // Insert sample addresses
        ContentValues addressValues = new ContentValues();
        addressValues.put("user_id", 1);  // John's address
        addressValues.put("name", "John's Address");
        addressValues.put("phone", "123456789");
        addressValues.put("address_line", "123 Main St");
        addressValues.put("city", "CityX");
        addressValues.put("state", "StateX");
        addressValues.put("country", "CountryX");
        db.insert("addresses", null, addressValues);

        addressValues.clear();
        addressValues.put("user_id", 2);  // Jane's address
        addressValues.put("name", "Jane's Address");
        addressValues.put("phone", "987654321");
        addressValues.put("address_line", "456 Oak St");
        addressValues.put("city", "CityY");
        addressValues.put("state", "StateY");
        addressValues.put("country", "CountryY");
        db.insert("addresses", null, addressValues);

        // Insert sample couriers
        ContentValues courierValues = new ContentValues();
        courierValues.put("tracking_number", "C12345");
        courierValues.put("sender_phone", "123456789");
        courierValues.put("sender_address_id", 1); // John's address
        courierValues.put("receiver_address_id", 2); // Jane's address
        courierValues.put("receiver_phone", "987654321");
        courierValues.put("weight", 2.5);
        courierValues.put("status", "pending");
        courierValues.put("estimated_delivery", "2025-03-30");
        db.insert("couriers", null, courierValues);

        // Insert sample tracking status
        ContentValues trackingStatusValues = new ContentValues();
        trackingStatusValues.put("courier_id", 1);
        trackingStatusValues.put("status", "pending");
        trackingStatusValues.put("location", "Warehouse");
        db.insert("tracking_status", null, trackingStatusValues);
    }
    // Method to check user credentials
    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE email=? AND password=?", new String[]{email, password});

        boolean userExists = cursor.getCount() > 0;
        cursor.close();
        db.close();

        return userExists;
    }

    public List<Shipment> getAllShipments() {
        List<Shipment> shipments = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM couriers" , null);
        if (cursor.moveToFirst()) {
            do {
                shipments.add(new Shipment(
                        cursor.getString(1),  // tracking_number
                        cursor.getString(7),  // status
                        cursor.getString(10)   // date
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return shipments;
    }
}
