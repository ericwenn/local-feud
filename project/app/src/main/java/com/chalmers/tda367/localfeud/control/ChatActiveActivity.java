package com.chalmers.tda367.localfeud.control;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.chalmers.tda367.localfeud.R;
import com.chalmers.tda367.localfeud.data.Chat;
import com.chalmers.tda367.localfeud.data.ChatMessage;
import com.chalmers.tda367.localfeud.data.Comment;
import com.chalmers.tda367.localfeud.net.IResponseAction;
import com.chalmers.tda367.localfeud.net.IResponseListener;
import com.chalmers.tda367.localfeud.net.IServerComm;
import com.chalmers.tda367.localfeud.net.ServerComm;
import com.chalmers.tda367.localfeud.net.responseListeners.RequestChatMessageResponseListener;
import com.chalmers.tda367.localfeud.net.responseListeners.RequestCommentsResponseListener;

/**
 * Created by Daniel Ahlqvist on 2016-05-03.
 */
public class ChatActiveActivity extends AppCompatActivity implements ChatActiveAdapter.AdapterCallback
{
    private IServerComm server;
    private ImageButton postMessageButton;
    private RecyclerView chatMessageList;
    private EditText chatMessageInput;
    private ChatActiveAdapter chatActiveAdapter;
    private Chat chat;
    private RequestChatMessageResponseListener requestChatMessagesResponseListener;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_chat);
        initViews();
    }

    private void initViews()
    {
        chatMessageList = (RecyclerView) findViewById(R.id.chat_message_list);
        chatMessageInput = (EditText) findViewById(R.id.posttext);
        postMessageButton = (ImageButton) findViewById(R.id.post_button);

        chatActiveAdapter = new ChatActiveAdapter(this);

        chatMessageList.setLayoutManager(new LinearLayoutManager(this));
        chatMessageList.setAdapter(chatActiveAdapter);

        requestChatMessagesResponseListener = new RequestChatMessageResponseListener(chatActiveAdapter);
        postMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(chatMessageList,
                        chatMessageInput.getText(),
                        Snackbar.LENGTH_LONG)
                        .show();

                ChatMessage newMessage = new ChatMessage();
                newMessage.setText(chatMessageInput.getText().toString());
            }
        });
    }

    public class RefreshChatMessageResponseListener extends RequestChatMessageResponseListener
    {
        private boolean isAfterChatMessagePosted;

        public RefreshChatMessageResponseListener(ChatActiveAdapter adapter, boolean isAfterChatMessagePosted)
        {
            super(adapter);
            this.isAfterChatMessagePosted = isAfterChatMessagePosted;
        }

        @Override
        public void onResponseSuccess(IResponseAction source)
        {
            super.onResponseSuccess(source);
        }

        @Override
        public void onResponseFailure(IResponseAction source)
        {
            super.onResponseFailure(source);
        }
    }
}