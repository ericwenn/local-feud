package com.chalmers.tda367.localfeud.net.responseListeners;

import com.chalmers.tda367.localfeud.control.ChatListAdapter;
import com.chalmers.tda367.localfeud.data.Chat;
import com.chalmers.tda367.localfeud.net.IResponseAction;
import com.chalmers.tda367.localfeud.net.IResponseListener;
import com.chalmers.tda367.localfeud.net.responseActions.RequestChatsResponseAction;

import java.util.Collections;
import java.util.List;

/**
 * Text om metoden
 *
 * @author David Söderberg
 * @since 16-05-09
 */
public class RequestChatsResponseListener implements IResponseListener {

    private ChatListAdapter adapter;

    public RequestChatsResponseListener(ChatListAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public void onResponseSuccess(IResponseAction source) {
        if (source instanceof RequestChatsResponseAction){
            RequestChatsResponseAction responseAction = (RequestChatsResponseAction) source;
            List<Chat> chatList = responseAction.getChats();
            Collections.reverse(chatList);
            adapter.addChatListToAdapter(chatList);
        }
    }

    @Override
    public void onResponseFailure(IResponseAction source) {
        if (source instanceof RequestChatsResponseAction) {
            RequestChatsResponseAction responseAction = (RequestChatsResponseAction) source;
            adapter.showError(responseAction.getResponseError());
        }
    }
}
