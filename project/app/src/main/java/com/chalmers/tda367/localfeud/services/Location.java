package com.chalmers.tda367.localfeud.services;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

/**
 * Created by ericwenn on 5/13/16.
 */
public class Location {
    private static final String TAG = "Location";
    private android.location.Location lastKnownLocation;
    private static Location instance = null;
    private Context context;

    public synchronized static Location getInstance() {
        if (instance == null) {
            instance = new Location();
        }

        return instance;
    }

    public void startTracking(Context context) {
        this.context = context;


        // Acquire a reference to the system Location Manager
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        lastKnownLocation = locationManager.getLastKnownLocation( LocationManager.GPS_PROVIDER );
        if( lastKnownLocation == null ) {
            lastKnownLocation = locationManager.getLastKnownLocation( LocationManager.NETWORK_PROVIDER );
        }
        
        // Define a listener that responds to location updates
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(android.location.Location location) {
                // Called when a new location is found by the network location provider.
                lastKnownLocation = location;
                Log.i(TAG, "onLocationChanged: "+location);
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };

        // Register the listener with the Location Manager to receive location updates
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

    }


    public android.location.Location getLocation() {
        return lastKnownLocation;
    }

}
