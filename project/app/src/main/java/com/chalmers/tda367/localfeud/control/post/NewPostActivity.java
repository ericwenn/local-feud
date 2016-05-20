package com.chalmers.tda367.localfeud.control.post;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.chalmers.tda367.localfeud.R;
import com.chalmers.tda367.localfeud.data.Position;
import com.chalmers.tda367.localfeud.data.Post;
import com.chalmers.tda367.localfeud.data.handler.DataHandlerFacade;
import com.chalmers.tda367.localfeud.data.handler.core.DataResponseError;
import com.chalmers.tda367.localfeud.data.handler.core.AbstractDataResponseListener;
import com.chalmers.tda367.localfeud.services.Location;

import org.w3c.dom.Text;


/**
 * Created by Daniel Ahlqvist on 2016-04-14.
 */
public class NewPostActivity extends AppCompatActivity {
    private EditText postEditText;
    private TextView postInputCounter;

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
        postInputCounter = (TextView) findViewById(R.id.post_message_input_counter);
        final ImageButton postButton = (ImageButton) findViewById(R.id.post_button);
        root = (CoordinatorLayout) findViewById(R.id.newPostRoot);

        postEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                postInputCounter.setText(postEditText.getText().toString().length() + "/240");
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        if (postButton != null) {
            postButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    postButton.setEnabled(false);
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

                            DataHandlerFacade.getPostDataHandler().triggerChange(null, data);
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
