package com.chalmers.tda367.localfeud.control;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.chalmers.tda367.localfeud.R;
import com.chalmers.tda367.localfeud.net.IServerComm;
import com.chalmers.tda367.localfeud.net.ServerComm;

/**
 * Created by Daniel Ahlqvist on 2016-05-03.
 */
public class ChatActiveActivity extends AppCompatActivity
{
    private IServerComm server;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_chat);
        initViews();
        server = ServerComm.getInstance();
    }

    private void initViews()
    {

    }
}