package com.example.project;

public class TripItem {
    private int id;
    private int tripRecordId;
    private String location;
    private String imageUri;
    private int cost;
    private String note;

    public TripItem() {
    }

    public TripItem(int id, int tripRecordId, String location, String imageUri, int cost, String note) {
        this.id = id;
        this.tripRecordId = tripRecordId;
        this.location = location;
        this.imageUri = imageUri;
        this.cost = cost;
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTripRecordId() {
        return tripRecordId;
    }

    public void setTripRecordId(int tripRecordId) {
        this.tripRecordId = tripRecordId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getImage() {
        return imageUri;
    }

}
