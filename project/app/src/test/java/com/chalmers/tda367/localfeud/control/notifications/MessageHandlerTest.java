package com.chalmers.tda367.localfeud.control.notifications;

import android.test.mock.MockContext;

import com.chalmers.tda367.localfeud.services.notifications.IMessageListener;
import com.chalmers.tda367.localfeud.util.MapEntry;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by Alfred on 2016-05-19.
 */
public class MessageHandlerTest {
    MessageHandler msgHandler;

    Map<String, Object> defData;

    IMessageListener listener1, listener2, listener3;

    @Before
    public void setUp() throws Exception {
        // Add MockContext to enable unit testing
        msgHandler = new MessageHandler(new MockContext());

        defData = new HashMap<>();
        defData.put("user_id", 12);
        defData.put("from", "Alfred Björk");

        listener1 = new IMessageListener() {
            @Override
            public void onMessageReceived(Map<String, Object> data) {
                assertEquals(defData, data);
                System.out.println("Listens to only the message type");
            }
        };

        listener2 = new IMessageListener() {
            @Override
            public void onMessageReceived(Map<String, Object> data) {
                assertEquals(defData, data);
                System.out.println("Listens to all messages with the user_id equals to 12");
            }
        };

        listener3 = new IMessageListener() {
            @Override
            public void onMessageReceived(Map<String, Object> data) {
                assertEquals(defData, data);
                System.out.println("Listens to all messages with from equals to Alfred Björk");
            }
        };
    }

    @Test
    public void mainTest() throws Exception{
        testAddMessageListener();
        testHasListeners();
        testHandleMessage();
        testRemoveMessageListener();
    }

    //@Test
    public void testAddMessageListener() throws Exception {
        msgHandler.addMessageListener(MessageHandler.CHAT_MESSAGE_RECIEVED, listener1);

        msgHandler.addMessageListener(MessageHandler.CHAT_MESSAGE_RECIEVED, new MapEntry<String, Object>("user_id", 12), listener2);

        msgHandler.addMessageListener(MessageHandler.CHAT_MESSAGE_RECIEVED, new MapEntry<String, Object>("from", "Alfred Björk"), listener3);
        testHasListeners();
    }

    public void testHasListeners() throws Exception{
        assertTrue(msgHandler.hasListeners(MessageHandler.CHAT_MESSAGE_RECIEVED));
        assertTrue(msgHandler.hasListeners(MessageHandler.CHAT_MESSAGE_RECIEVED, new MapEntry<String, Object>("user_id", 12)));
        assertTrue(msgHandler.hasListeners(MessageHandler.CHAT_MESSAGE_RECIEVED, new MapEntry<String, Object>("from", "Alfred Björk")));
    }

    public void testHandleMessage() throws Exception {

        JSONObject json = new JSONObject(defData);

        msgHandler.handleMessage(MessageHandler.CHAT_MESSAGE_RECIEVED, json);
    }


    public void testRemoveMessageListener() throws Exception {
        msgHandler.removeMessageListener(MessageHandler.CHAT_MESSAGE_RECIEVED, new MapEntry<String, Object>("user_id", 12), listener2);
        msgHandler.removeMessageListener(MessageHandler.CHAT_MESSAGE_RECIEVED, new MapEntry<String, Object>("from", "Alfred Björk"), listener3);
        msgHandler.removeMessageListener(MessageHandler.CHAT_MESSAGE_RECIEVED, listener1);

        boolean hasListeners = msgHandler.hasListeners(MessageHandler.CHAT_MESSAGE_RECIEVED, new MapEntry<String, Object>("user_id", 12));
        assertFalse(hasListeners);
    }

}