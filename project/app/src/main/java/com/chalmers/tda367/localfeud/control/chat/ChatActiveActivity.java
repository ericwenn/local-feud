package com.chalmers.tda367.localfeud.control.chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.chalmers.tda367.localfeud.R;
import com.chalmers.tda367.localfeud.data.AuthenticatedUser;
import com.chalmers.tda367.localfeud.data.Chat;
import com.chalmers.tda367.localfeud.data.ChatMessage;
import com.chalmers.tda367.localfeud.data.User;
import com.chalmers.tda367.localfeud.service.IServerComm;
import com.chalmers.tda367.localfeud.service.ServerComm;
import com.chalmers.tda367.localfeud.service.responseActions.IResponseAction;
import com.chalmers.tda367.localfeud.service.responseListeners.IResponseListener;
import com.chalmers.tda367.localfeud.service.responseListeners.RequestChatMessageResponseListener;

/**
 * Created by Daniel Ahlqvist on 2016-05-03.
 */
public class ChatActiveActivity extends AppCompatActivity implements ChatActiveAdapter.AdapterCallback {
    private IServerComm server;
    private RecyclerView chatMessageList;
    private EditText chatMessageInput;
    private ChatActiveAdapter chatActiveAdapter;
    private Chat chat;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        chat = (Chat) bundle.getSerializable("chat");
        server = ServerComm.getInstance();
        setTheme(R.style.ChatAppTheme);
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
        ImageButton postMessageButton = (ImageButton) findViewById(R.id.post_button);
        TextView chatTitle = (TextView) findViewById(R.id.chat_title);
        if (chatTitle != null) {
            chatTitle.setText(chat.getChatName());
        }
        ImageButton backButton = (ImageButton) findViewById(R.id.chat_view_back_btn);
        if (backButton != null) {
            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }

        chatMessageList.setLayoutManager(new LinearLayoutManager(this));
        chatMessageList.setHasFixedSize(true);

        chatActiveAdapter = new ChatActiveAdapter(this);
        chatMessageList.setAdapter(chatActiveAdapter);

        RequestChatMessageResponseListener requestChatMessagesResponseListener = new RequestChatMessageResponseListener(chatActiveAdapter);
        if (postMessageButton != null) {
            postMessageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    IResponseListener responseListener = new IResponseListener() {
                        @Override
                        public void onResponseSuccess(IResponseAction source)
                        {
                            refreshMessages();
                        }

                        @Override
                        public void onResponseFailure(IResponseAction source) {
                            Snackbar.make(chatMessageList,
                                    "Could not post message",
                                    Snackbar.LENGTH_LONG)
                                    .show();
                        }
                    };
                    if (!chatMessageInput.getText().toString().isEmpty()) {
                        String messageText = chatMessageInput.getText().toString();;
                        ChatMessage message = new ChatMessage(chat,messageText, new User(AuthenticatedUser.getInstance().getMe().getId(), AuthenticatedUser.getInstance().getMe().getAge(), AuthenticatedUser.getInstance().getMe().getGender()));
                        chatMessageInput.setText("");
                        server.sendChatMessage(chat, message, responseListener);
                    } else {
                        Snackbar.make(chatMessageList,
                                "Are you sick in your brain?! You must enter something in the box you know!",
                                Snackbar.LENGTH_LONG)
                                .show();
                    }
                    refreshMessages();
                }
            });
        }
        chatMessageInput.setOnClickListener(new View.OnClickListener()
        {
             @Override
             public void onClick(View v) {
                scrollToBottom();
             }
         });
        refreshMessages();
    }

    public void refreshMessages()
    {
        ServerComm.getInstance().requestChatMessages(chat, new RefreshChatMessageResponseListener(chatActiveAdapter, false));
        scrollToBottom();
    }
    public void scrollToBottom()
    {
        chatMessageList.scrollToPosition(chatActiveAdapter.getItemCount()-1);
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
            scrollToBottom();
        }

        @Override
        public void onResponseFailure(IResponseAction source) {
            super.onResponseFailure(source);
        }
    }
}