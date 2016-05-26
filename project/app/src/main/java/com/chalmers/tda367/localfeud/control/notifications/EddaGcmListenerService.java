package com.chalmers.tda367.localfeud.control.notifications;

import android.os.Bundle;
import android.util.Log;

import com.chalmers.tda367.localfeud.control.notifications.NotificationFacade;
import com.chalmers.tda367.localfeud.util.TagHandler;
import com.google.android.gms.gcm.GcmListenerService;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Alfred on 2016-05-16.
 */
public class EddaGcmListenerService extends GcmListenerService {

    /**
     * Called when message is received.
     * @param from SenderID of the sender.
     * @param data Data bundle containing message data as key/value pairs.
     *             For Set of keys use data.keySet().
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(String from, Bundle data) {
        Log.d(TagHandler.MAIN_TAG, "MESSAGE RECIEVED");

        String type = data.getString("title");
        String message = data.getString("message");

        try{
            JSONObject json = new JSONObject(message);
            NotificationFacade.getInstance().getMessageHandler().handleMessage(type, json);
        }catch (JSONException e){
            Log.e(TagHandler.MAIN_TAG, e.getMessage());
        }

        if (from.startsWith("/topics/")) {
            // message received from some topic.
        } else {
            // normal downstream message.
        }
    }
    // [END receive_message]
}
