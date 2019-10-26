package com.hackmoskow.mobile.domain.models;

import com.here.android.mpa.common.GeoCoordinate;

public class Places {
    private Location location;
    private int distance;
    private String title;
    private String category;

    public Places(GeoCoordinate coordinate, String title, String category, int distance) {
        this.location = new Location(coordinate.getLongitude(), coordinate.getLatitude());
        this.title = title;
        this.category = category;
        this.distance = distance;
    }

    public void changeCoordinate() {
        double t = location.getLat();
        location.setLat(location.getLongitude());
        location.setLongitude(t);
    }

    public GeoCoordinate getCoordinate() {
        return new GeoCoordinate(location.lat, location.longitude);
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public int getDistance() {
        return distance;
    }

    public class Location {
        private double longitude;
        private double lat;

        public Location(double longitude, double lat) {
            this.longitude = longitude;
            this.lat = lat;
        }

        public double getLongitude() {
            return longitude;
        }

        public double getLat() {
            return lat;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }
    }
}
