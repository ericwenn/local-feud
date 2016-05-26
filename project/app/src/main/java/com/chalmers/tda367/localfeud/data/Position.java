package com.chalmers.tda367.localfeud.data;

import android.location.Location;

import java.io.Serializable;

/**
 *  A Position object matching the object on server.
 */
public class Position implements Serializable {
    private double latitude;
    private double longitude;
    private double distance;

    public Position(double latitude, double longitude) {
        this.setLatitude(latitude);
        this.setLongitude(longitude);
    }

    public Position(Location loc) {
        this(loc.getLatitude(), loc.getLongitude());
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}
