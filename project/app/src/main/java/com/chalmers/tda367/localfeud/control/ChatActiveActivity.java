package com.chalmers.tda367.localfeud.control;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.chalmers.tda367.localfeud.R;
import com.chalmers.tda367.localfeud.data.Chat;
import com.chalmers.tda367.localfeud.net.IResponseAction;
import com.chalmers.tda367.localfeud.net.IServerComm;
import com.chalmers.tda367.localfeud.net.responseListeners.RequestChatMessageResponseListener;

/**
 * Created by Daniel Ahlqvist on 2016-05-03.
 */
public class ChatActiveActivity extends AppCompatActivity implements ChatActiveAdapter.AdapterCallback {
    private IServerComm server;
    private ImageButton postMessageButton;
    private RecyclerView chatMessageList;
    private EditText chatMessageInput;
    private ChatActiveAdapter chatActiveAdapter;
    private Chat chat;
    private RequestChatMessageResponseListener requestChatMessagesResponseListener;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.getSerializable("null") != null) {
            chat = (Chat) bundle.getSerializable("chat");
        } else {
            chat = new Chat();
        }
        setContentView(R.layout.activity_active_chat);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initViews();
    }

    private void initViews() {
        chatMessageList = (RecyclerView) findViewById(R.id.chat_message_list);
        chatMessageInput = (EditText) findViewById(R.id.posttext);
        postMessageButton = (ImageButton) findViewById(R.id.post_button);

        chatMessageList.setLayoutManager(new LinearLayoutManager(this));
        chatMessageList.setHasFixedSize(true);

        chatActiveAdapter = new ChatActiveAdapter(this);
        chatMessageList.setAdapter(chatActiveAdapter);

        requestChatMessagesResponseListener = new RequestChatMessageResponseListener(chatActiveAdapter);
        postMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(chatMessageList,
                        chatMessageInput.getText(),
                        Snackbar.LENGTH_LONG)
                        .show();
            }
        });
    }

    public class RefreshChatMessageResponseListener extends RequestChatMessageResponseListener {
        private boolean isAfterChatMessagePosted;

        public RefreshChatMessageResponseListener(ChatActiveAdapter adapter, boolean isAfterChatMessagePosted) {
            super(adapter);
            this.isAfterChatMessagePosted = isAfterChatMessagePosted;
        }

        @Override
        public void onResponseSuccess(IResponseAction source) {
            super.onResponseSuccess(source);
        }

        @Override
        public void onResponseFailure(IResponseAction source) {
            super.onResponseFailure(source);
        }
    }
}