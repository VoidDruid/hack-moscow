package com.hackmoskow.mobile.domain.models;

import com.here.android.mpa.common.GeoCoordinate;

public class Places {
    private GeoCoordinate coordinate;
    private int distance;
    private String title;
    private String category;

    public Places(GeoCoordinate coordinate, String title, String category, int distance) {
        this.coordinate = coordinate;
        this.title = title;
        this.category = category;
        this.distance = distance;
    }

    public GeoCoordinate getCoordinate() {
        return coordinate;
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
}
