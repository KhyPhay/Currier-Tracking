package com.example.couriertracking;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText etName, etPhone, etEmail, etPassword, etConfirmPassword;
    private Button btnRegister;
    private TextView tvLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);
        tvLogin = findViewById(R.id.tvLogin);

        btnRegister.setOnClickListener(view -> registerUser());

        tvLogin.setOnClickListener(view -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void registerUser() {
        String name = etName.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        // Check for empty fields
        if (name.isEmpty() || phone.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "All fields are required!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if passwords match
        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if the email or phone number is already registered
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Query the database to check if the email or phone number already exists
        Cursor cursor = db.query("users", null, "email=? OR phone=?", new String[]{email, phone}, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            // Email or phone already exists
            Toast.makeText(this, "Email or Phone is already registered!", Toast.LENGTH_SHORT).show();
            cursor.close();  // Don't forget to close the cursor!
            db.close();  // Close the database
            return;
        }

        cursor.close();  // Close the cursor if no match

        // Insert user data into the database
        ContentValues userValues = new ContentValues();
        userValues.put("name", name);
        userValues.put("phone", phone);
        userValues.put("email", email);
        userValues.put("password", password);  // Store password securely in real apps (hash it!)

        long userId = db.insert("users", null, userValues); // Insert user data

        if (userId == -1) {
            Toast.makeText(this, "Registration failed!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Account Created Successfully!", Toast.LENGTH_SHORT).show();

            // Redirect to Login
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        db.close();  // Close the database after insert operation
    }
}
