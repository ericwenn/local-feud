package com.chalmers.tda367.localfeud.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;

import com.chalmers.tda367.localfeud.data.Position;

/**
 * Text om metoden
 *
 * @author David Söderberg
 * @since 16-04-27
 */
public class PositionHandler {
    private static PositionHandler instance;

    private PositionHandler() {}

    public static PositionHandler getInstance() {
        if (instance == null) instance = new PositionHandler();
        return instance;
    }

    public Position getPosition(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        return new Position(location.getLatitude(), location.getLongitude());
    }
}
