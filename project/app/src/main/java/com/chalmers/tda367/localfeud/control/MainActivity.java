package com.chalmers.tda367.localfeud.control;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.chalmers.tda367.localfeud.R;
import com.chalmers.tda367.localfeud.control.chat.ChatActiveActivity;
import com.chalmers.tda367.localfeud.control.chat.ChatFragment;
import com.chalmers.tda367.localfeud.control.chat.ChatListAdapter;
import com.chalmers.tda367.localfeud.control.me.MeFragment;
import com.chalmers.tda367.localfeud.control.post.FeedFragment;
import com.chalmers.tda367.localfeud.control.post.PostAdapter;
import com.chalmers.tda367.localfeud.control.post.PostClickedActivity;
import com.chalmers.tda367.localfeud.data.Chat;
import com.chalmers.tda367.localfeud.data.Like;
import com.chalmers.tda367.localfeud.data.Post;
import com.chalmers.tda367.localfeud.data.handler.DataHandlerFacade;
import com.chalmers.tda367.localfeud.data.handler.core.AbstractDataResponseListener;
import com.chalmers.tda367.localfeud.data.handler.core.DataResponseError;
import com.chalmers.tda367.localfeud.services.Location;
import com.chalmers.tda367.localfeud.services.LocationPermissionError;
import com.chalmers.tda367.localfeud.services.NotificationFacade;
import com.facebook.FacebookSdk;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

public class MainActivity extends AppCompatActivity implements PostAdapter.AdapterCallback, ChatListAdapter.AdapterCallback {

    private static final String TAG = "MainActivity";

    private BottomBar bottomBar;
    private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize Facebook SDK
        FacebookSdk.sdkInitialize(getApplicationContext());

        // Register this application for notifications
        if(NotificationFacade.checkPlayServices(this)){
            NotificationFacade.getInstance().registerForNotifications(this);
        }
        if (!Location.getInstance().isTracking()) {
            try {
                Location.getInstance().startTracking(getApplicationContext());
            } catch (LocationPermissionError locationPermissionError) {}
        }

        setContentView(R.layout.activity_main);
        initBottomBar(savedInstanceState);

        Intent intent = this.getIntent();
        Bundle intentExtras = intent.getExtras();

        // This is if MainActivity is started from a notification
        if (intentExtras != null && intentExtras.containsKey("chatid")){
            DataHandlerFacade.getChatDataHandler().getSingle(intentExtras.getInt("chatid"), new AbstractDataResponseListener<Chat>() {
                @Override
                public void onSuccess(Chat data) {
                    onChatClicked(data);
                    switchFragment(R.id.chat_item);
                    bottomBar.selectTabAtPosition(1,true);
                }

                @Override
                public void onFailure(DataResponseError error, String errormessage) {
                    showSnackbar("Failed to load chat: " + errormessage);
                }
            });
        }

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
                switchFragment(menuItemId);
            }
        });

        bottomBar.mapColorForTab(0, ContextCompat.getColor(this, R.color.feedColorPrimary));
        bottomBar.mapColorForTab(1, ContextCompat.getColor(this, R.color.chatColorPrimary));
        bottomBar.mapColorForTab(2, ContextCompat.getColor(this, R.color.meColorPrimary));
        bottomBar.setTextAppearance(R.style.BottomBarBadge_Text);
    }

    private void switchFragment(int menuItemId) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (menuItemId == R.id.feed_item) {
            if (currentFragment == null || currentFragment.getClass() != FeedFragment.class)
                window.setStatusBarColor(ContextCompat.getColor(this, R.color.feedColorPrimaryDark));
                currentFragment = FeedFragment.newInstance(this);
        } else if (menuItemId == R.id.chat_item) {
            if (currentFragment == null || currentFragment.getClass() != ChatFragment.class)
                window.setStatusBarColor(ContextCompat.getColor(this, R.color.chatColorPrimaryDark));
                currentFragment = ChatFragment.newInstance(this);
        } else if (menuItemId == R.id.me_item) {
            if (currentFragment == null || currentFragment.getClass() != MeFragment.class)
                window.setStatusBarColor(ContextCompat.getColor(this, R.color.meColorPrimaryDark));
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
    public void onPostClick(Post post, CardView view) {
        Intent i = new Intent(getApplicationContext(), PostClickedActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("post", post);
        i.putExtras(bundle);

        startActivity(i,
                ActivityOptions.makeSceneTransitionAnimation(this,
                        view,
                        getString(R.string.post_transition_target))
                        .toBundle());
    }

    @Override
    public void onChatClicked(Chat chat)
    {
        Intent i = new Intent(getApplicationContext(), ChatActiveActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("chat", chat);
        i.putExtras(bundle);
        startActivity(i);
    }

    @Override
    public void onLikeClick(final Post post, final ImageButton imageButton, final TextView likesDisplay) {
        imageButton.setEnabled(false);
        final boolean isLiked = post.isLiked();
        final int revertLikeDrawable, originalLikeDrawable;
        if (isLiked) {
            revertLikeDrawable = R.drawable.ic_favorite_border_black_24dp;
            originalLikeDrawable = R.drawable.ic_favorite_black_24dp;
            likesDisplay.setText(post.getNumberOfLikes()-1 + "");

        } else {
            revertLikeDrawable = R.drawable.ic_favorite_black_24dp;
            originalLikeDrawable = R.drawable.ic_favorite_border_black_24dp;
            likesDisplay.setText(post.getNumberOfLikes()+1 + "");
        }
        imageButton.setImageResource(revertLikeDrawable);

        if (!isLiked) {
            DataHandlerFacade.getLikeDataHandler().create( post, new AbstractDataResponseListener<Like>() {
                @Override
                public void onSuccess(Like data) {
                    Post oldPost = post.clone();
                    post.setIsLiked(!isLiked);
                    DataHandlerFacade.getPostDataHandler().triggerChange(oldPost, post);
                    post.setNumberOfLikes(oldPost.getNumberOfLikes()+1);
                    imageButton.setEnabled(true);
                }

                @Override
                public void onFailure(DataResponseError error, String errormessage) {
                    imageButton.setImageResource(originalLikeDrawable);
                    showSnackbar(getString(R.string.like_error_msg));
                    imageButton.setEnabled(true);
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
                    post.setNumberOfLikes(oldPost.getNumberOfLikes()-1);
                    imageButton.setEnabled(true);
                }

                @Override
                public void onFailure(DataResponseError error, String errormessage) {
                    imageButton.setImageResource(originalLikeDrawable);
                    showSnackbar(getString(R.string.like_error_msg));
                    imageButton.setEnabled(true);
                }
            });
        }
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


    @Override
    protected void onDestroy() {
        try {
                SharedPreferences prefs = getSharedPreferences("com.chalmers.tda367.localfeud", Context.MODE_PRIVATE);
            prefs.edit().putLong("last_latitude", Double.doubleToRawLongBits(Location.getInstance().getLocation().getLatitude()));
            prefs.edit().putLong("last_longitude", Double.doubleToRawLongBits(Location.getInstance().getLocation().getLongitude()));
            Location.getInstance().stopTracking();
        } catch (LocationPermissionError locationPermissionError) {}
        super.onDestroy();
    }
}