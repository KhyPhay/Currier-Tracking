package com.example.couriertracking;

public class Shipment {
    private String id, status, date;

    public Shipment(String id, String status, String date) {
        this.id = id;
        this.status = status;
        this.date = date;
    }

    public String getId() { return id; }
    public String getStatus() { return status; }
    public String getDate() { return date; }
}
