package com.chalmers.tda367.localfeud.util;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

import com.chalmers.tda367.localfeud.data.Position;

/**
 * Text om klassen
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
        Location location = null;
        try {
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            return new Position(location.getLatitude(), location.getLongitude());
        } catch (SecurityException e) {
//            TODO: Vad ska vi göra om detta uppstår?
            return new Position(0, 0);
        }
    }
}
