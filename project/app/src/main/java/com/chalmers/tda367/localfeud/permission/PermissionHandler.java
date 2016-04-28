package com.chalmers.tda367.localfeud.permission;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

/**
 * Created by ericwenn on 4/27/16.
 */
public class PermissionHandler {
    public static boolean hasPermissions(Context c) {
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(c, Manifest.permission.ACCESS_COARSE_LOCATION);
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(c, Manifest.permission.ACCESS_FINE_LOCATION);

        boolean sufficientPermission = hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasFineLocationPermission == PackageManager.PERMISSION_GRANTED;

        return sufficientPermission;
    }
}
