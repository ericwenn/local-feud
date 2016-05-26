package com.chalmers.tda367.localfeud.services;

import android.content.Context;

/**
 *  Used for tracking and receiving location of user.
 */
public interface ILocationHandler {

    /**
     *  Used for start tracking for users position.
     *  Should be used early in lifecycle.
     *  @param context from calling activity
     *  @throws LocationPermissionError when required permission isn't granted.
     */
    void startTracking(Context context) throws LocationPermissionError;

    /**
     *  Used for stop tracking after position changes.
     *  Should be use as quickly as possible for battery saving.
     *  @throws LocationPermissionError when required permission isn't granted.
     */
    void stopTracking() throws LocationPermissionError;

    /**
     *  Check if LocationHandler still is tracking.
     *  @return true if LocationHandler is tracking
     */
    boolean isTracking();

    /**
     *  Receiving the latest location
     *  @return last known location
     */
    android.location.Location getLocation();
}
