package com.chalmers.tda367.localfeud.service.responseListeners;

import com.chalmers.tda367.localfeud.control.chat.ChatActiveAdapter;
import com.chalmers.tda367.localfeud.service.responseActions.IResponseAction;
import com.chalmers.tda367.localfeud.service.responseActions.RequestChatMessageResponseAction;

/**
 * Created by Daniel Ahlqvist on 2016-05-10.
 */
public class RequestChatMessageResponseListener implements IResponseListener {

    private final ChatActiveAdapter adapter;

    public RequestChatMessageResponseListener(ChatActiveAdapter adapter)
    {
        this.adapter = adapter;
    }

    public void onResponseSuccess(IResponseAction source)
    {
        if (source instanceof RequestChatMessageResponseAction){
            RequestChatMessageResponseAction responseAction = (RequestChatMessageResponseAction) source;
            adapter.addChatMessageListToAdapter(responseAction.getMessages());
        }
    }

    public void onResponseFailure(IResponseAction source)
    {

    }
}
