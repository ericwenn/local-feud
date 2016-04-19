package com.chalmers.tda367.localfeud.control;

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
    private Button postbutton, postsettingsbutton, postbackbutton;
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
        postsettingsbutton = (Button) findViewById(R.id.postsettingsbutton);
        postbackbutton = (Button) findViewById(R.id.postbackbutton);

        postbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v,
                        posttext.getText(),
                        Snackbar.LENGTH_SHORT)
                        .show();
            }
        });

        postbackbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        postsettingsbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v,
                        "Åhh, va bra att du kan ändra inställningar här!",
                        Snackbar.LENGTH_SHORT)
                        .show();
            }
        });
    }
}
