package com.chalmers.tda367.localfeud.control;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chalmers.tda367.localfeud.R;
import com.chalmers.tda367.localfeud.data.Comment;
import com.chalmers.tda367.localfeud.data.Post;
import com.chalmers.tda367.localfeud.net.IResponseAction;
import com.chalmers.tda367.localfeud.net.IResponseListener;
import com.chalmers.tda367.localfeud.net.IServerComm;
import com.chalmers.tda367.localfeud.net.ServerComm;
import com.chalmers.tda367.localfeud.net.responseListeners.RequestCommentsResponseListener;
import com.chalmers.tda367.localfeud.util.DateString;
import com.chalmers.tda367.localfeud.util.DistanceColor;
import com.chalmers.tda367.localfeud.util.TagHandler;

import java.util.Calendar;

/**
 * Created by Daniel Ahlqvist on 2016-04-18.
 */
public class PostClickedActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PostClickedAdapter postClickedAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Post post;
    private TextView postText, senderText, distanceText, timeText, timeElapsedText, toolbarTextView;
    private Toolbar toolbar;
    private RelativeLayout postItemTopbar;
    private EditText writeCommentText;
    private ImageButton postCommentButton;
    private CoordinatorLayout root;
    private IServerComm server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        post = (Post) bundle.getSerializable("post");
        //Log.d(TagHandler.MAIN_TAG, "Postid: " + post.getId());
        setContentView(R.layout.activity_post_clicked);
        server = ServerComm.getInstance();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbarTextView = (TextView) findViewById(R.id.toolbar_title_textview);
        toolbarTextView.setText("Post ID: " + post.getId());
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.empty_string);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initViews();
    }

    private void initViews()
    {
        int distanceColor = DistanceColor.distanceColor(post.getLocation().getDistance());
        int distanceTextColor = DistanceColor.distanceTextColor(distanceColor);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.post_clicked_refresh_layout);
        postText = (TextView) findViewById(R.id.post_item_msg_textview);
        senderText = (TextView) findViewById(R.id.post_item_sender_textview);
        distanceText = (TextView) findViewById(R.id.post_item_distance_textview);
        timeText = (TextView) findViewById(R.id.post_item_time_textview);
        timeElapsedText = (TextView) findViewById(R.id.post_item_time_elapsed_textview);
        postItemTopbar = (RelativeLayout) findViewById(R.id.post_item_topbar);
        postItemTopbar.setBackgroundColor(ContextCompat.getColor(this, distanceColor));
        postText.setText(post.getContent().getText());
        senderText.setText("" + post.getUser().getId());
        senderText.setTextColor(ContextCompat.getColor(this, distanceTextColor));
        distanceText.setText("" + post.getLocation().getDistance());
        distanceText.setTextColor(ContextCompat.getColor(this, distanceTextColor));

        timeText.setText(post.getDatePosted().get(Calendar.HOUR_OF_DAY) + ":" +
                post.getDatePosted().get(Calendar.MINUTE));
        timeElapsedText.setText(DateString.convert( post.getDatePosted()));

        recyclerView = (RecyclerView) findViewById(R.id.comment_feed_recyclerview);
        if (recyclerView != null) {
            recyclerView.setHasFixedSize(true);
        } else {
            Log.e(TagHandler.MAIN_TAG, "No RecyclerView found in activity_post_clicked.xml");
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        postClickedAdapter = new PostClickedAdapter(this);
        recyclerView.setAdapter(postClickedAdapter);
        ServerComm.getInstance().requestComments(post, new RefreshCommentsResponseListener(postClickedAdapter));
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ServerComm.getInstance().requestComments(post, new RefreshCommentsResponseListener(postClickedAdapter));
            }
        });
        root = (CoordinatorLayout) findViewById(R.id.newPostRoot);
        writeCommentText = (EditText) findViewById(R.id.posttext);
        postCommentButton = (ImageButton) findViewById(R.id.post_button);

        postCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Comment comment = new Comment();

                comment.setText(writeCommentText.getText().toString());

                IResponseListener responseListener = new IResponseListener() {
                    @Override
                    public void onResponseSuccess(IResponseAction source) {
                        Log.d(TagHandler.MAIN_TAG, "Comment skickad");
//                        TODO: Uppdatera kommentarflödet istället för att skicka tillbaka till Main
                        finish();
                    }

                    @Override
                    public void onResponseFailure(IResponseAction source) {
                        Snackbar.make(root,
                                "Kommentar lyckades inte skickas.",
                                Snackbar.LENGTH_LONG)
                                .show();
                    }
                };
                if(!comment.getText().equals(""))
                {
                    server.commentPost(post, comment, responseListener);
                }
            }
        });

    }

    public class RefreshCommentsResponseListener extends RequestCommentsResponseListener
    {
        public RefreshCommentsResponseListener(PostClickedAdapter adapter){
            super(adapter);
        }

        @Override
        public void onResponseSuccess(IResponseAction source){
            super.onResponseSuccess(source);
            swipeRefreshLayout.setRefreshing(false);
        }

        @Override
        public void onResponseFailure(IResponseAction source){
            super.onResponseFailure(source);
            swipeRefreshLayout.setRefreshing(false);
        }
    }
}