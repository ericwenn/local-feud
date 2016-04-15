package com.chalmers.tda367.localfeud.ui;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;

import com.chalmers.tda367.localfeud.R;

/**
 * Created by Daniel Ahlqvist on 2016-04-14.
 */
public class NewPostActivity extends AppCompatActivity
{
    private Button postbutton;
    private EditText posttext;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newpost);
        initViews();
    }

    private void initViews()
    {
        postbutton = (Button) findViewById(R.id.postbutton);
        posttext = (EditText) findViewById(R.id.posttext);
        postbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v,
                        posttext.getText(),
                        Snackbar.LENGTH_SHORT)
                        .show();
            }
        });
    }
}
