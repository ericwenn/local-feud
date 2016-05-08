package com.chalmers.tda367.localfeud.control;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.chalmers.tda367.localfeud.R;
import com.chalmers.tda367.localfeud.data.Comment;
import com.chalmers.tda367.localfeud.net.IResponseAction;
import com.chalmers.tda367.localfeud.net.IResponseListener;
import com.chalmers.tda367.localfeud.net.IServerComm;
import com.chalmers.tda367.localfeud.net.ServerComm;

/**
 * Created by Daniel Ahlqvist on 2016-05-03.
 */
public class ChatActiveActivity extends AppCompatActivity
{
    private IServerComm server;
    private ImageButton postMessageButton;
    private ListView chatMessageList;
    private EditText chatMessageInput;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_chat);
        initViews();
        server = ServerComm.getInstance();
    }

    private void initViews()
    {
        postMessageButton = (ImageButton) findViewById(R.id.post_button);
        chatMessageList = (ListView) findViewById(R.id.chat_message_list);
        chatMessageInput = (EditText) findViewById(R.id.posttext);

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
}