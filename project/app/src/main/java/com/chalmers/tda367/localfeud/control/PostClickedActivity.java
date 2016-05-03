package com.chalmers.tda367.localfeud.control;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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
import com.chalmers.tda367.localfeud.util.TagHandler;

/**
 * Created by Daniel Ahlqvist on 2016-04-18.
 */
public class PostClickedActivity extends AppCompatActivity implements PostClickedAdapter.AdapterCallback {
    private RecyclerView recyclerView;
    private PostClickedAdapter postClickedAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Post post;
    private TextView toolbarTextView;
    private Toolbar toolbar;
    private RelativeLayout postItemTopbar;
    private LinearLayout commentBar;
    private EditText writeCommentText;
    private ImageButton postCommentButton;
    private IServerComm server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        post = (Post) bundle.getSerializable("post");
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

    private void initViews() {
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.post_clicked_refresh_layout);

        recyclerView = (RecyclerView) findViewById(R.id.comment_feed_recyclerview);
        if (recyclerView != null) {
            recyclerView.setHasFixedSize(true);
        } else {
            Log.e(TagHandler.MAIN_TAG, "No RecyclerView found in activity_post_clicked.xml");
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        postClickedAdapter = new PostClickedAdapter(this, post);
        recyclerView.setAdapter(postClickedAdapter);
        ServerComm.getInstance().requestComments(post, new RefreshCommentsResponseListener(postClickedAdapter, false));
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ServerComm.getInstance().requestComments(post, new RefreshCommentsResponseListener(postClickedAdapter, false));
            }
        });
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
        writeCommentText = (EditText) findViewById(R.id.posttext);
        postCommentButton = (ImageButton) findViewById(R.id.post_button);

        postCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                IResponseListener responseListener = new IResponseListener() {
                    @Override
                    public void onResponseSuccess(IResponseAction source) {
                        swipeRefreshLayout.setRefreshing(false);
                        ServerComm.getInstance().requestComments(post, new RefreshCommentsResponseListener(postClickedAdapter, true));
                    }

                    @Override
                    public void onResponseFailure(IResponseAction source) {
                        Snackbar.make(recyclerView,
                                R.string.comment_failed_to_post_msg,
                                Snackbar.LENGTH_LONG)
                                .show();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                };
                if (!writeCommentText.getText().toString().isEmpty()) {
                    Comment comment = new Comment();
                    comment.setText(writeCommentText.getText().toString());
                    writeCommentText.setText("");
                    /**
                     *  TODO: VARFÖR POSTAS TVÅ KOMMENTARER?!?!?! PLEASE TELL ME
                     */
                    swipeRefreshLayout.post(new Runnable() {
                        @Override
                        public void run() {
                            swipeRefreshLayout.setRefreshing(true);
                        }
                    });
                    server.commentPost(post, comment, responseListener);
                } else {
                    Snackbar.make(recyclerView,
                            R.string.empty_comment_error_msg,
                            Snackbar.LENGTH_LONG)
                            .show();
                }
            }
        });

    }

    @Override
    public void onLikeClick(final Post post, final ImageButton imageButton) {
        final boolean isLiked = post.isLiked();
        final int revertLikeDrawable, originalLikeDrawable;
        if (isLiked) {
            revertLikeDrawable = R.drawable.ic_favorite_border_black_24dp;
            originalLikeDrawable = R.drawable.ic_favorite_black_24dp;
        } else {
            revertLikeDrawable = R.drawable.ic_favorite_black_24dp;
            originalLikeDrawable = R.drawable.ic_favorite_border_black_24dp;
        }
        imageButton.setImageResource(revertLikeDrawable);
        IResponseListener responseListener = new IResponseListener() {
            @Override
            public void onResponseSuccess(IResponseAction source) {
                post.setIsLiked(!isLiked);
            }

            @Override
            public void onResponseFailure(IResponseAction source) {
                imageButton.setImageResource(originalLikeDrawable);
                Snackbar.make(recyclerView, getString(R.string.like_error_msg), Snackbar.LENGTH_LONG).show();
            }
        };

        if (!isLiked) ServerComm.getInstance().likePost(post, responseListener);
        else ServerComm.getInstance().unlikePost(post, responseListener);
    }

    @Override
    public void onMoreClick(Post post) {
        Snackbar.make(recyclerView, "No more for you", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onShowSnackbar(String text) {
        Snackbar.make(recyclerView, text, Snackbar.LENGTH_LONG).show();
    }

    public class RefreshCommentsResponseListener extends RequestCommentsResponseListener {
        private boolean isAfterCommentPosted;

        public RefreshCommentsResponseListener(PostClickedAdapter adapter, boolean isAfterCommentPosted) {
            super(adapter);
            this.isAfterCommentPosted = isAfterCommentPosted;
        }

        @Override
        public void onResponseSuccess(IResponseAction source) {
            super.onResponseSuccess(source);
            swipeRefreshLayout.setRefreshing(false);
            if (isAfterCommentPosted) {
                recyclerView.scrollToPosition(postClickedAdapter.getItemCount() - 1);
            }
        }

        @Override
        public void onResponseFailure(IResponseAction source) {
            super.onResponseFailure(source);
            swipeRefreshLayout.setRefreshing(false);
        }
    }
}