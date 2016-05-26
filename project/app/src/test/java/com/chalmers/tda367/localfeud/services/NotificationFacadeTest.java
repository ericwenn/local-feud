package com.chalmers.tda367.localfeud.services;

import android.test.mock.MockContext;

import com.chalmers.tda367.localfeud.control.notifications.MessageHandler;
import com.chalmers.tda367.localfeud.control.notifications.NotificationFacade;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Alfred on 2016-05-19.
 */
public class NotificationFacadeTest {

    @Test
    public void testGetInstance() throws Exception {
        assertNotNull(NotificationFacade.getInstance());
        assertTrue(NotificationFacade.getInstance() instanceof NotificationFacade);
    }

    @Test
    public void testGetMessageHandler() throws Exception {
        // Do this just for testing
        NotificationFacade.getInstance().setContext(new MockContext());

        assertNotNull(NotificationFacade.getInstance().getMessageHandler());
        assertTrue(NotificationFacade.getInstance().getMessageHandler() instanceof MessageHandler);
    }
}