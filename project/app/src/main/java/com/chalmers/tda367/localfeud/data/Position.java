package com.chalmers.tda367.localfeud.data;

/**
 * Created by Alfred on 2016-04-11.
 */
public class Position {
    private double latitude;
    private double longitude;
    private double distance;

    public Position(double latitude, double longitude){
        this.setLatitude(latitude);
        this.setLongitude(longitude);
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
