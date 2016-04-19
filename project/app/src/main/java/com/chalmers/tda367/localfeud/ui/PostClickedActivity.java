package com.chalmers.tda367.localfeud.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.chalmers.tda367.localfeud.R;
import com.chalmers.tda367.localfeud.data.Comment;
import com.chalmers.tda367.localfeud.data.Post;
import com.chalmers.tda367.localfeud.util.GsonHandler;
import com.chalmers.tda367.localfeud.util.TagHandler;

/**
 * Created by Daniel Ahlqvist on 2016-04-18.
 */
public class PostClickedActivity extends AppCompatActivity
{
    private RecyclerView recyclerView;
    private PostClickedAdapter postClickedAdapter;
    private FloatingActionButton commentFab;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_clicked);
        initViews();
    }

    private void initViews() {
        commentFab = (FloatingActionButton) findViewById(R.id.post_feed_create_new_fab);
        commentFab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Snackbar.make(v,
                        "Skriv en fantastisk kommentar som alla älskar!",
                        Snackbar.LENGTH_SHORT)
                        .show();
            }
        });
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
        postClickedAdapter.addCommentToAdapter(new Comment());
    }
}