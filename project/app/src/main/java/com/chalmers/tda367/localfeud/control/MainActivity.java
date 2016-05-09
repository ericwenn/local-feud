package com.chalmers.tda367.localfeud.control;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageButton;

import com.chalmers.tda367.localfeud.R;
import com.chalmers.tda367.localfeud.data.Chat;
import com.chalmers.tda367.localfeud.data.Post;
import com.chalmers.tda367.localfeud.net.IResponseAction;
import com.chalmers.tda367.localfeud.net.IResponseListener;
import com.chalmers.tda367.localfeud.net.ServerComm;
import com.chalmers.tda367.localfeud.permission.PermissionHandler;
import com.chalmers.tda367.localfeud.permissionflow.PermissionFlow;
import com.chalmers.tda367.localfeud.util.TagHandler;
import com.facebook.FacebookSdk;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

public class MainActivity extends AppCompatActivity implements PostAdapter.AdapterCallback, ChatListAdapter.AdapterCallback {

    private BottomBar bottomBar;
    private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize Facebook SDK
        FacebookSdk.sdkInitialize(getApplicationContext());

        initFlow();

        setContentView(R.layout.activity_main);
        initBottomBar(savedInstanceState);

    }

    private void initBottomBar(final Bundle savedInstanceState) {
        bottomBar = BottomBar.attach(this, savedInstanceState);
        bottomBar.noTopOffset();
        bottomBar.noResizeGoodness();
        bottomBar.noNavBarGoodness();
        bottomBar.setMaxFixedTabs(2);
        //TODO: Implement button functionality
        bottomBar.setItemsFromMenu(R.menu.bottombar_menu, new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                switchFragment(menuItemId);
            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {
                if (menuItemId == R.id.feed_item) {
                    // The user reselected item number one.
                } else if (menuItemId == R.id.chat_item) {
                    // The user reselected item number two.
                } else if (menuItemId == R.id.me_item) {
                    // The user reselected item number three.
                }
            }
        });

        bottomBar.mapColorForTab(0, ContextCompat.getColor(this, R.color.feedColorPrimary));
        bottomBar.mapColorForTab(1, ContextCompat.getColor(this, R.color.chatColorPrimary));
        bottomBar.mapColorForTab(2, "#FF9800");
    }

    private void switchFragment(int menuItemId) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (menuItemId == R.id.feed_item) {
            if (currentFragment == null || currentFragment.getClass() != FeedFragment.class)
                currentFragment = FeedFragment.newInstance(this);
        } else if (menuItemId == R.id.chat_item) {
            if (currentFragment == null || currentFragment.getClass() != ChatFragment.class)
                currentFragment = ChatFragment.newInstance(this);
        } else if (menuItemId == R.id.me_item) {
            if (currentFragment == null || currentFragment.getClass() != MeFragment.class)
                currentFragment = MeFragment.newInstance();
        }
        transaction.replace(R.id.main_root, currentFragment);
        transaction.commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        bottomBar.onSaveInstanceState(outState);
        currentFragment.onSaveInstanceState(outState);
    }

    @Override
    public void onPostClick(Post post) {
        Intent i = new Intent(getApplicationContext(), PostClickedActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("post", post);
        i.putExtras(bundle);
        startActivity(i);
    }

    @Override
    public void onLikeClick(final Post post, final ImageButton imageButton) {
//        Should check if post is liked
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
                showSnackbar(getString(R.string.like_error_msg));
            }
        };

        if (!isLiked) ServerComm.getInstance().likePost(post, responseListener);
        else ServerComm.getInstance().unlikePost(post, responseListener);
    }

    @Override
    public void onMoreClick(Post post) {
        showSnackbar("No more for you");
    }

    @Override
    public void onShowSnackbar(String text) {
        showSnackbar(text);
    }

    private void showSnackbar(String text) {
        if (currentFragment.getClass() == FeedFragment.class) {
            FeedFragment fragment = (FeedFragment) currentFragment;
            fragment.showSnackbar(text);
        }
        else if (currentFragment.getClass() == ChatFragment.class) {
            ChatFragment fragment = (ChatFragment) currentFragment;
            fragment.showSnackbar(text);
        }
    }

    private void initFlow() {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                if (!PermissionHandler.hasPermissions(getApplicationContext())) {
                    Log.d(TagHandler.PERMISSION_FLOW_TAG, "Permissions not granted.");

                    Intent i = new Intent(MainActivity.this, PermissionFlow.class);
                    startActivity(i);
                    finish();

                } else {
                    Log.d(TagHandler.PERMISSION_FLOW_TAG, "Permissions granted.");
                }
                return null;
            }
        }.execute();
    }

    @Override
    public void onChatClicked(Chat chat) {
        showSnackbar(chat.getUsers().get(0).getFirstName());
    }
}