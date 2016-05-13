package com.chalmers.tda367.localfeud.control.post;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.chalmers.tda367.localfeud.R;
import com.chalmers.tda367.localfeud.data.Position;
import com.chalmers.tda367.localfeud.data.Post;
import com.chalmers.tda367.localfeud.data.handler.DataHandlerFacade;
import com.chalmers.tda367.localfeud.data.handler.DataResponseError;
import com.chalmers.tda367.localfeud.data.handler.interfaces.AbstractDataResponseListener;
import com.chalmers.tda367.localfeud.services.Location;


/**
 * Created by Daniel Ahlqvist on 2016-04-14.
 */
public class NewPostActivity extends AppCompatActivity {
    private EditText postEditText;

    private CoordinatorLayout root;

    private static final String TAG = "NewPostActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newpost);
        initViews();
    }

    private void initViews() {
        ImageButton backButton = (ImageButton) findViewById(R.id.new_post_back_btn);
        if (backButton != null) {
            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }

        postEditText = (EditText) findViewById(R.id.posttext);
        ImageButton postButton = (ImageButton) findViewById(R.id.post_button);
        root = (CoordinatorLayout) findViewById(R.id.newPostRoot);

        if (postButton != null) {
            postButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Post post = new Post();
                    Post.Content content = new Post.Content();

                    //TODO: Möjliggör bildinlägg
                    content.setType("text");
                    content.setText(postEditText.getText().toString());

                    post.setLocation(new Position(Location.getInstance().getLocation()));
                    post.setContent(content);

                    DataHandlerFacade.getPostDataHandler().create(post, new AbstractDataResponseListener<Post>() {
                        @Override
                        public void onSuccess(Post data) {
                            Log.d(TAG, "Post successfully created");
                            finish();
                        }

                        @Override
                        public void onFailure(DataResponseError error, String errormessage) {
                            Snackbar.make(root, "Fel:" + errormessage, Snackbar.LENGTH_LONG).show();
                        }
                    });
                }
            });

        }
    }
}
