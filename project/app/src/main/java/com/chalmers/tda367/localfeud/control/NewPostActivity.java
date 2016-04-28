package com.chalmers.tda367.localfeud.control;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.chalmers.tda367.localfeud.R;
import com.chalmers.tda367.localfeud.data.Position;
import com.chalmers.tda367.localfeud.data.Post;
import com.chalmers.tda367.localfeud.net.IResponseAction;
import com.chalmers.tda367.localfeud.net.IResponseListener;
import com.chalmers.tda367.localfeud.net.IServerComm;
import com.chalmers.tda367.localfeud.net.ServerComm;
import com.chalmers.tda367.localfeud.util.TagHandler;

/**
 * Created by Daniel Ahlqvist on 2016-04-14.
 */
public class NewPostActivity extends AppCompatActivity {
    private ImageButton postButton;
    private EditText postEditText;
    private Toolbar toolbar;
    private IServerComm server;
    private CoordinatorLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newpost);
        initViews();
        server = ServerComm.getInstance();
    }

    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.posttoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.empty_string);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        postEditText = (EditText) findViewById(R.id.posttext);
        postButton = (ImageButton) findViewById(R.id.post_button);
        root = (CoordinatorLayout) findViewById(R.id.newPostRoot);

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Post post = new Post();
                Post.Content content = new Post.Content();

                //TODO: Möjliggör bildinlägg
                content.setType("text");
                content.setText(postEditText.getText().toString());

                post.setLocation(new Position(53.123123, 11.123123));
                post.setContent(content);

                IResponseListener responseListener = new IResponseListener() {
                    @Override
                    public void onResponseSuccess(IResponseAction source) {
                        Log.d(TagHandler.MAIN_TAG, "Post successfully created");
                        finish();
                    }

                    @Override
                    public void onResponseFailure(IResponseAction source) {
                        Snackbar.make(root,
                                "Fel",
                                Snackbar.LENGTH_LONG)
                                .show();
                    }
                };

                server.createPost(post, responseListener);
            }
        });
    }
}
