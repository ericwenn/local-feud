package com.chalmers.tda367.localfeud.control;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.chalmers.tda367.localfeud.R;
import com.chalmers.tda367.localfeud.data.Post;
import com.chalmers.tda367.localfeud.net.IResponseAction;
import com.chalmers.tda367.localfeud.net.ServerComm;
import com.chalmers.tda367.localfeud.net.responseListeners.RequestCommentsResponseListener;
import com.chalmers.tda367.localfeud.util.TagHandler;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        post = (Post) bundle.getSerializable("post");
        //Log.d(TagHandler.MAIN_TAG, "Postid: " + post.getId());
        setContentView(R.layout.activity_post_clicked);

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

    private void initViews() {
        Calendar current = Calendar.getInstance();
        long timeElapsedMs = current.getTimeInMillis() - post.getDatePosted().getTimeInMillis();
        long timeElapsedMin = TimeUnit.MILLISECONDS.toMinutes(timeElapsedMs);
        long timeElapsedHour = TimeUnit.MILLISECONDS.toHours(timeElapsedMs);
        long timeElapsedDay = TimeUnit.MILLISECONDS.toDays(timeElapsedMs);

        String timeSinceUpload;

        if (timeElapsedDay >= 1) {
            if (timeElapsedDay == 1)
                timeSinceUpload = "1 day ago";
            else
                timeSinceUpload = timeElapsedDay + " days ago";
        } else if (timeElapsedMin > 60) {
            if (timeElapsedHour == 1)
                timeSinceUpload = "1 hour ago";
            else
                timeSinceUpload = timeElapsedHour + " hours ago";
        } else {
            if (timeElapsedMin == 1)
                timeSinceUpload = "1 minute ago";
            else
                timeSinceUpload = timeElapsedMin + " minutes ago";
        }

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.post_clicked_refresh_layout);
        postText = (TextView) findViewById(R.id.post_item_msg_textview);
        senderText = (TextView) findViewById(R.id.post_item_sender_textview);
        distanceText = (TextView) findViewById(R.id.post_item_distance_textview);
        timeText = (TextView) findViewById(R.id.post_item_time_textview);
        timeElapsedText = (TextView) findViewById(R.id.post_item_time_elapsed_textview);
        postText.setText(post.getContent().getText());
        senderText.setText("" + post.getUser().getId());
        distanceText.setText("" + post.getLocation().getDistance());
        timeText.setText(post.getDatePosted().get(Calendar.HOUR_OF_DAY) + ":" +
                post.getDatePosted().get(Calendar.MINUTE));
        timeElapsedText.setText(timeSinceUpload);

        recyclerView = (RecyclerView) findViewById(R.id.comment_feed_recyclerview);
        if (recyclerView != null) {
            recyclerView.setHasFixedSize(true);
        } else {
            Log.e(TagHandler.MAIN_TAG, "No RecyclerView found in activity_post_clicked.xml");
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        postClickedAdapter = new PostClickedAdapter(this);
        recyclerView.setAdapter(postClickedAdapter);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ServerComm.getInstance().requestComments(post, new RefreshCommentsResponseListener(postClickedAdapter));
            }
        });
        ServerComm.getInstance().requestComments(post, new RefreshCommentsResponseListener(postClickedAdapter));
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