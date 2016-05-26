package com.chalmers.tda367.localfeud.services;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

/**
 *  Used for receiving users position.
 */
public class LocationHandler implements ILocation {
    private android.location.Location lastKnownLocation;
    private static LocationHandler instance = null;
    private Context context;
    private LocationManager locationManager;
    private LocationListener locationListener;

    /**
     *  Getting a singleton instance of LocationHandler.
     */
    public synchronized static LocationHandler getInstance() {
        if (instance == null) {
            instance = new LocationHandler();
        }

        return instance;
    }

    /**
     *  Used for start tracking for users position.
     *  Should be used early in lifecycle.
     *  @param context from calling activity
     *  @throws LocationPermissionError when required permission isn't granted.
     */
    public void startTracking(Context context) throws LocationPermissionError {
        this.context = context;

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            throw new LocationPermissionError();
        }

//        Acquire a reference to the system Location Manager
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (lastKnownLocation == null) {
            lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }

//        Define a listener that responds to location updates
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(android.location.Location location) {
//                Called when a new location is found by the network location provider.
                lastKnownLocation = location;
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };

//        Register the listener with the Location Manager to receive location updates
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60000, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 60000, 0, locationListener);


    }

    /**
     *  Used for stop tracking after position changes.
     *  Should be use as quickly as possible for battery saving.
     *  @throws LocationPermissionError when required permission isn't granted.
     */
    public void stopTracking() throws LocationPermissionError {
        if (context != null && locationManager != null && locationListener != null) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                throw new LocationPermissionError();
            }
            locationManager.removeUpdates(locationListener);
            locationListener = null;
        }
    }

    /**
     *  Check if LocationHandler still is tracking.
     *  @return true if LocationHandler is tracking
     */
    public boolean isTracking() {
        return locationManager != null && locationListener != null;
    }

    /**
     *  Receiving the latest location
     *  @return last known location
     */
    public android.location.Location getLocation() {
        if (lastKnownLocation == null) {
//            If no last location is known, we receive a stored location from SharedPreferences.
            SharedPreferences prefs = context.getSharedPreferences("com.chalmers.tda367.localfeud", Context.MODE_PRIVATE);
            lastKnownLocation = new android.location.Location("");
            double latitude = (double) prefs.getLong("last_latitude", 1);
            double longitude = (double) prefs.getLong("last_longitude", 1);
            lastKnownLocation.setLatitude(latitude);
            lastKnownLocation.setLongitude(longitude);
        }
        return lastKnownLocation;
    }
}
