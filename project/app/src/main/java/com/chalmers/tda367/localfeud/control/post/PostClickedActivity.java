package com.chalmers.tda367.localfeud.control.post;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.chalmers.tda367.localfeud.R;
import com.chalmers.tda367.localfeud.data.Chat;
import com.chalmers.tda367.localfeud.data.Comment;
import com.chalmers.tda367.localfeud.data.Like;
import com.chalmers.tda367.localfeud.data.Post;
import com.chalmers.tda367.localfeud.data.handler.DataHandlerFacade;
import com.chalmers.tda367.localfeud.data.handler.DataResponseError;
import com.chalmers.tda367.localfeud.data.handler.interfaces.AbstractDataResponseListener;
import com.chalmers.tda367.localfeud.data.handler.interfaces.DataResponseListener;
import com.chalmers.tda367.localfeud.util.TagHandler;

import java.util.List;

/**
 * Created by Daniel Ahlqvist on 2016-04-18.
 */
public class PostClickedActivity extends AppCompatActivity implements PostClickedAdapter.AdapterCallback {
    private RecyclerView recyclerView;
    private PostClickedAdapter postClickedAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Post post;
    private EditText writeCommentText;

    private ImageButton postCommentButton;
    private ImageButton backButton;

    // Tag for logging
    private static final String TAG = "PostClickedActivity";

    private DataResponseListener<List<Comment>> refreshCommentsListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        post = (Post) bundle.getSerializable("post");
        setContentView(R.layout.activity_post_clicked);

        TextView toolbarTextView = (TextView) findViewById(R.id.toolbar_title_textview);
        if (toolbarTextView != null) {
            toolbarTextView.setText(getString(R.string.app_name));
        }
        ImageButton backButton = (ImageButton) findViewById(R.id.post_clicked_back_btn);
        if (backButton != null) {
            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initViews();
    }

    private void initViews() {

        initRecyclerView();
        initSwipeRefreshLayout();


        writeCommentText = (EditText) findViewById(R.id.posttext);
        ImageButton postCommentButton = (ImageButton) findViewById(R.id.post_button);

        if (postCommentButton != null) {
            postCommentButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (!writeCommentText.getText().toString().isEmpty()) {
                        Comment comment = new Comment();
                        comment.setText(writeCommentText.getText().toString());
                        writeCommentText.setText("");
                        swipeRefreshLayout.post(new Runnable() {
                            @Override
                            public void run() {
                                swipeRefreshLayout.setRefreshing(true);
                            }
                        });
                        DataHandlerFacade.getCommentDataHandler().create(post, comment, new AbstractDataResponseListener<Comment>() {
                            @Override
                            public void onSuccess(Comment data) {
                                swipeRefreshLayout.setRefreshing(false);
                                Post newPost = post.clone();
                                newPost.setNumberOfComments(post.getNumberOfComments() + 1);
                                DataHandlerFacade.getPostDataHandler().triggerChange(post, newPost);
                                postClickedAdapter.changePostInAdapter(newPost);
                                setPost(newPost);
                                DataHandlerFacade.getCommentDataHandler().getList(post, refreshCommentsListener);
                            }

                            @Override
                            public void onFailure(DataResponseError error, String errormessage) {

                                Snackbar.make(recyclerView,
                                        R.string.comment_failed_to_post_msg,
                                        Snackbar.LENGTH_LONG)
                                        .show();
                                swipeRefreshLayout.setRefreshing(false);
                            }
                        });
                    } else {
                        Snackbar.make(recyclerView,
                                R.string.empty_comment_error_msg,
                                Snackbar.LENGTH_LONG)
                                .show();
                    }
                }
            });
        }

    }



    private void initRecyclerView() {
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
    }


    private void initSwipeRefreshLayout() {
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.post_clicked_refresh_layout);



        refreshCommentsListener = new AbstractDataResponseListener<List<Comment>>() {
            @Override
            public void onSuccess(List<Comment> data) {
                postClickedAdapter.addCommentListToAdapter(data);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(DataResponseError error, String errormessage) {
                Log.i(TAG, "onFailure: " + errormessage);
            }
        };

        swipeRefreshLayout.setColorSchemeResources(R.color.feedColorPrimary,
                R.color.feedColorAccent);


        DataHandlerFacade.getCommentDataHandler().getList(post, refreshCommentsListener);






        
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                DataHandlerFacade.getCommentDataHandler().getList( post, refreshCommentsListener );
            }
        });

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
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

        if (!isLiked) {
            DataHandlerFacade.getLikeDataHandler().create( post, new AbstractDataResponseListener<Like>() {
                @Override
                public void onSuccess(Like data) {
                    Post oldPost = post.clone();
                    post.setIsLiked(!isLiked);
                    DataHandlerFacade.getPostDataHandler().triggerChange(oldPost, post);
                }

                @Override
                public void onFailure(DataResponseError error, String errormessage) {
                    imageButton.setImageResource(originalLikeDrawable);
                    Snackbar.make(recyclerView, getString(R.string.like_error_msg), Snackbar.LENGTH_LONG).show();
                }
            } );
        }
        else {
            DataHandlerFacade.getLikeDataHandler().delete( post, new AbstractDataResponseListener<Void>() {
                @Override
                public void onSuccess(Void data) {
                    Post oldPost = post.clone();
                    post.setIsLiked(!isLiked);
                    DataHandlerFacade.getPostDataHandler().triggerChange(oldPost, post);
                }

                @Override
                public void onFailure(DataResponseError error, String errormessage) {
                    imageButton.setImageResource(originalLikeDrawable);
                    Snackbar.make(recyclerView, getString(R.string.like_error_msg), Snackbar.LENGTH_LONG).show();
                }
            });
        }
    }



    @Override
    // More-button on post is clicked
    public void onMoreClick(ImageButton button) {
        PopupMenu menu = new PopupMenu(this, button, Gravity.END);
        MenuInflater inflater = menu.getMenuInflater();
        inflater.inflate(R.menu.post_menu, menu.getMenu());

        // Make the post accessible by the listener below
        final Post post = this.post;

        PopupMenu.OnMenuItemClickListener listener = new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.send_chat_request:
                        sendChatRequest(post, post.getUser().getId());
                        return true;
                    case R.id.report:
                        Snackbar.make(recyclerView, "Wanna report huh?", Snackbar.LENGTH_LONG).show();
                        return true;
                    default:
                        return false;
                }
            }
        };

        menu.setOnMenuItemClickListener(listener);
        menu.show();
    }

    @Override
    public void onCommentMoreClick(final Comment comment, ImageButton button) {
        PopupMenu menu = new PopupMenu(this, button, Gravity.END);
        /*MenuInflater inflater = menu.getMenuInflater();
        inflater.inflate(R.menu.post_menu, menu.getMenu());*/

        final MenuItem deleteCommentMenuItem = menu.getMenu().add(Menu.NONE, 1, Menu.NONE, R.string.delete_comment);
        final MenuItem sendChatRequestMenuItem = menu.getMenu().add(Menu.NONE, 2, Menu.NONE, R.string.send_chat_request);
        final MenuItem reportMenuItem = menu.getMenu().add(Menu.NONE, 3, Menu.NONE, R.string.report);

        if (DataHandlerFacade.getMeDataHandler().getMe().getId() == comment.getUser().getId()) { //if comment is made by me
            menu.getMenu().removeItem(sendChatRequestMenuItem.getItemId());
            menu.getMenu().removeItem(reportMenuItem.getItemId());
        } else { //if comment is made by someone else
            menu.getMenu().removeItem(deleteCommentMenuItem.getItemId());
        }

        // Make the post accessible by the listener below
        final Post post = this.post;

        PopupMenu.OnMenuItemClickListener listener = new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == deleteCommentMenuItem.getItemId()){

                    swipeRefreshLayout.post(new Runnable() {

                        @Override
                        public void run() {
                            swipeRefreshLayout.setRefreshing(true);
                        }
                    });

                    DataHandlerFacade.getCommentDataHandler().delete(comment, new AbstractDataResponseListener<Void>() {
                        @Override
                        public void onSuccess(Void data) {
                            swipeRefreshLayout.setRefreshing(false);
                            Post newPost = post.clone();
                            newPost.setNumberOfComments(post.getNumberOfComments() - 1);
                            DataHandlerFacade.getPostDataHandler().triggerChange(post, newPost);
                            postClickedAdapter.changePostInAdapter(newPost);
                            setPost(newPost);
                            DataHandlerFacade.getCommentDataHandler().getList( post, refreshCommentsListener );
                            Snackbar.make(recyclerView, "Comment deleted successfully", Snackbar.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(DataResponseError error, String errormessage) {
                            swipeRefreshLayout.setRefreshing(false);
                            Snackbar.make(recyclerView, "Comment failed to be deleted: " + errormessage, Snackbar.LENGTH_LONG).show();
                        }
                    });
                    return true;
                } else if (item.getItemId() == sendChatRequestMenuItem.getItemId()) {
                    sendChatRequest(post, comment.getUser().getId());
                    return true;
                } else if (item.getItemId() == reportMenuItem.getItemId()) {
                    Snackbar.make(recyclerView, "Wanna report huh?", Snackbar.LENGTH_LONG).show();
                    return true;
                } else {
                    return false;
                }
            }
        };

        menu.setOnMenuItemClickListener(listener);
        menu.show();
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    private void sendChatRequest(Post post, int userID){

        DataHandlerFacade.getChatDataHandler().sendRequest(post, userID, new AbstractDataResponseListener<Chat>() {

            @Override
            public void onSuccess(Chat data) {
                Snackbar.make(recyclerView, "Chat created successfully", Snackbar.LENGTH_LONG).show();
                // TODO Start chat activity?
            }

            @Override
            public void onFailure(DataResponseError error, String errormessage) {
                Snackbar.make(recyclerView, "Chat creation failed: " + errormessage, Snackbar.LENGTH_LONG).show();

            }
        });
    }

    @Override
    public void onShowSnackbar(String text) {
        Snackbar.make(recyclerView, text, Snackbar.LENGTH_LONG).show();
    }


}