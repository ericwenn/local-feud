package com.chalmers.tda367.localfeud.control.notifications;

import android.support.annotation.Nullable;

import com.chalmers.tda367.localfeud.util.MapEntry;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by Alfred on 2016-05-18.
 */
public interface IMessageHandler {
    void removeMessageListener(String type, IMessageListener listener);
    void removeMessageListener(String type, @Nullable MapEntry<String, Object> data, IMessageListener listener);
    /**
     * Add a listener for messages of a certain type.
     * @param type The type of messages to listen for
     * @param listener The listener
     */
    void addMessageListener(String type, IMessageListener listener);
    /**
     * Add a listener for messages of a certain type and with a certain data
     * @param type The type of messages to listen for
     * @param data A pair of key and value to listen for
     *             (null the value to just listen for the key or
     *             null the pair to just listen for the type of message)
     * @param listener The listener that receives the messages
     */
    void addMessageListener(String type, @Nullable MapEntry<String, Object> data, IMessageListener listener);
    void handleMessage(String type, JSONObject data);
}
