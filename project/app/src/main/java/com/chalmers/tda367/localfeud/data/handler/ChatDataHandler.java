package com.chalmers.tda367.localfeud.data.handler;

import com.chalmers.tda367.localfeud.data.Chat;
import com.chalmers.tda367.localfeud.data.Post;
import com.chalmers.tda367.localfeud.data.handler.interfaces.DataChangeListener;
import com.chalmers.tda367.localfeud.data.handler.interfaces.DataResponseListener;
import com.chalmers.tda367.localfeud.data.handler.interfaces.IChatDataHandler;

import java.util.HashMap;
import java.util.List;

/**
 * Created by ericwenn on 5/13/16.
 */
public class ChatDataHandler extends AbstractDataHandler implements IChatDataHandler {

    private static ChatDataHandler instance = null;

    public synchronized static ChatDataHandler getInstance() {
        if( instance == null) {
            instance = new ChatDataHandler();
        }
        return instance;
    }

    private ChatDataHandler() {}


    /**
     * {@inheritDoc}
     */
    @Override
    public void sendRequest(Post post, int userID, DataResponseListener<Chat> listener) {

        // Store parameters
        HashMap<String, String> params = new HashMap<>();
        params.put("userid", Integer.toString(userID));
        params.put("postid", Integer.toString(post.getId()));

        getClient().post("chats/", params, new RestResponseAction(listener));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void getList(DataResponseListener<List<Chat>> listener) {
        getClient().get("chats/", new RestResponseAction(listener));
    }

    @Override
    public void addChangeListener(DataChangeListener<Chat> listener) {
        this.listeners.add(listener);
    }

    @Override
    public void triggerChange(Chat oldValue, Chat newValue) {
        for (DataChangeListener<Chat> listener : listeners) {
            listener.onChange(oldValue, newValue);
        }
    }
}
