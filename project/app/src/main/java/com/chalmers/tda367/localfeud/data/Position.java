package com.chalmers.tda367.localfeud.data;

import java.io.Serializable;

/**
 * Created by Alfred on 2016-04-11.
 */
public class Position implements Serializable{
    private double latitude;
    private double longitude;
    private double distance;

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
