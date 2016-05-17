package com.chalmers.tda367.localfeud.services.gcm;

import android.content.Intent;
import android.util.Log;

import com.chalmers.tda367.localfeud.control.MainActivity;
import com.chalmers.tda367.localfeud.services.NotificationFacade;
import com.google.android.gms.iid.InstanceIDListenerService;

/**
 * Created by Alfred on 2016-05-16.
 */
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
        NotificationFacade.registerForNotifications(getApplicationContext());
    }
    // [END refresh_token]
}
