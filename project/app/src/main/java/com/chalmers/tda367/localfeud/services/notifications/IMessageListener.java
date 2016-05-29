package com.chalmers.tda367.localfeud.services.notifications;

import java.util.Map;

public interface IMessageListener {
    void onMessageReceived(Map<String, Object> data);
}
