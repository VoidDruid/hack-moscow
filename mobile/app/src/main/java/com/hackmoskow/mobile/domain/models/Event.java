package com.hackmoskow.mobile.domain.models;

public class Event {
    private int id;
    private String title;
    private String type;
    private String description;
    private double longitude;
    private double lat;

    public Event(int id, String title, String type, String description, double longitude, double lat) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.description = description;
        this.longitude = longitude;
        this.lat = lat;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLat() {
        return lat;
    }
}
