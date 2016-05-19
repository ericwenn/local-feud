package com.chalmers.tda367.localfeud.services;

import android.content.Context;
import android.location.*;

/**
 * Created by ericwenn on 5/19/16.
 */
public interface ILocation {

    void startTracking(Context context);
    android.location.Location getLocation();
}
