package com.chalmers.tda367.localfeud.control.notifications;

import android.os.Bundle;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by Alfred on 2016-05-18.
 */
public interface IMessageHandler {
    void removeMessageListener(String type, IMessageListener listener);
    void addMessageListener(String type, IMessageListener listener);
    void handleMessage(String type, JSONObject data);
}
