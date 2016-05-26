package com.chalmers.tda367.localfeud.control.notifications;

import android.util.Log;

public class EddaInstanceIDListenerService extends com.google.android.gms.iid.InstanceIDListenerService {
    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. This call is initiated by the
     * InstanceID provider.
     */
    // [START refresh_token]
    @Override
    public void onTokenRefresh() {
        Log.d("GCM", "onTokenRefresh()");
        // Fetch updated Instance ID token and notify our app's server of any changes (if applicable).
        NotificationFacade.getInstance().registerForNotifications(getApplicationContext());
    }
    // [END refresh_token]
}
