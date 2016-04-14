package com.chalmers.tda367.localfeud.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.chalmers.tda367.localfeud.R;
import com.chalmers.tda367.localfeud.util.GsonHandler;

/**
 * Created by Daniel Ahlqvist on 2016-04-14.
 */
public class NewPostActivity extends AppCompatActivity
{
    private FloatingActionButton createNewFab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newpost);
        initViews();
    }

    private void initViews()
    {

    }
}
