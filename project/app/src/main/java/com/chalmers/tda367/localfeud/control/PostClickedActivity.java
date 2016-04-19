package com.chalmers.tda367.localfeud.control;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.chalmers.tda367.localfeud.R;
import com.chalmers.tda367.localfeud.data.Post;
import com.chalmers.tda367.localfeud.util.TagHandler;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/**
 * Created by Daniel Ahlqvist on 2016-04-18.
 */
public class PostClickedActivity extends AppCompatActivity
{
    private RecyclerView recyclerView;
    private PostClickedAdapter postClickedAdapter;
    private Post post;
    private TextView postText, senderText, distanceText, timeText, timeElapsedText;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        post = (Post) bundle.getSerializable("post");
        //Log.d(TagHandler.MAIN_TAG, "Postid: " + post.getId());
        setContentView(R.layout.activity_post_clicked);
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
        String timeSinceUpload = String.format("%d", TimeUnit.MILLISECONDS.toMinutes(current.getTimeInMillis() - post.getDatePosted().getTimeInMillis()));

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
        timeElapsedText.setText(timeSinceUpload + " minuter sedan");

        recyclerView = (RecyclerView) findViewById(R.id.comment_feed_recyclerview);
        if (recyclerView != null) {
            recyclerView.setHasFixedSize(true);
        }
        else {
            Log.e(TagHandler.MAIN_TAG, "No RecyclerView found in activity_post_clicked.xml");
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        postClickedAdapter = new PostClickedAdapter(this);
        recyclerView.setAdapter(postClickedAdapter);
        /*for (Comment comment : dummyCommentList) {
            postClickedAdapter.addCommentToAdapter(comment);
        }*/
    }
}
