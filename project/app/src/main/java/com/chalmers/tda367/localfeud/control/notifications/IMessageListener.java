package com.chalmers.tda367.localfeud.control.notifications;

import java.util.Map;

/**
 * Created by Alfred on 2016-05-18.
 */
public interface IMessageListener {
    void onMessageReceived(Map<String, Object> data);
}
