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
 * Created by ericwenn on 5/13/16.
 */
public class Location implements ILocation {
    private static final String TAG = "Location";
    private android.location.Location lastKnownLocation;
    private static Location instance = null;
    private Context context;
    private LocationManager locationManager;
    private LocationListener locationListener;

    public synchronized static Location getInstance() {
        if (instance == null) {
            instance = new Location();
        }

        return instance;
    }

    public void startTracking(Context context) throws LocationPermissionError {
        this.context = context;


        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            throw new LocationPermissionError();
        }

        // Acquire a reference to the system Location Manager
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        lastKnownLocation = locationManager.getLastKnownLocation( LocationManager.GPS_PROVIDER );
        if( lastKnownLocation == null ) {
            lastKnownLocation = locationManager.getLastKnownLocation( LocationManager.NETWORK_PROVIDER );
        }

        // Define a listener that responds to location updates
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(android.location.Location location) {
                // Called when a new location is found by the network location provider.
                lastKnownLocation = location;
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };

        // Register the listener with the Location Manager to receive location updates
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60000, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 60000, 0, locationListener);


    }

    public void stopTracking() throws LocationPermissionError {
        if (context != null && locationManager != null && locationListener != null) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                throw new LocationPermissionError();
            }
            locationManager.removeUpdates(locationListener);
            locationListener = null;
        }
    }

    public boolean isTracking() {
        return locationManager != null && locationListener != null;
    }

    public android.location.Location getLocation() {
        if (lastKnownLocation == null) {
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
