package com.chalmers.tda367.localfeud.control;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.chalmers.tda367.localfeud.R;
import com.chalmers.tda367.localfeud.control.chat.ChatActiveActivity;
import com.chalmers.tda367.localfeud.control.chat.ChatFragment;
import com.chalmers.tda367.localfeud.control.chat.ChatListAdapter;
import com.chalmers.tda367.localfeud.control.me.MeFragment;
import com.chalmers.tda367.localfeud.control.permission.PermissionFlow;
import com.chalmers.tda367.localfeud.control.post.FeedFragment;
import com.chalmers.tda367.localfeud.control.post.PostAdapter;
import com.chalmers.tda367.localfeud.control.post.PostClickedActivity;
import com.chalmers.tda367.localfeud.data.Chat;
import com.chalmers.tda367.localfeud.data.Like;
import com.chalmers.tda367.localfeud.data.Post;
import com.chalmers.tda367.localfeud.data.handler.DataHandlerFacade;
import com.chalmers.tda367.localfeud.data.handler.DataResponseError;
import com.chalmers.tda367.localfeud.data.handler.interfaces.AbstractDataResponseListener;
import com.chalmers.tda367.localfeud.services.NotificationFacade;
import com.chalmers.tda367.localfeud.services.gcm.QuickstartPreferences;
import com.chalmers.tda367.localfeud.services.gcm.RegistrationIntentService;
import com.chalmers.tda367.localfeud.util.PermissionHandler;
import com.chalmers.tda367.localfeud.util.TagHandler;
import com.facebook.FacebookSdk;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

public class MainActivity extends AppCompatActivity implements PostAdapter.AdapterCallback, ChatListAdapter.AdapterCallback {

    private static final String TAG = "MainActivity";

    private BottomBar bottomBar;
    private Fragment currentFragment;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private boolean isReceiverRegistered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize Facebook SDK
        FacebookSdk.sdkInitialize(getApplicationContext());

        /*
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                SharedPreferences sharedPreferences =
                        PreferenceManager.getDefaultSharedPreferences(context);
                boolean sentToken = sharedPreferences
                        .getBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false);
                if (sentToken) {
                    Log.d(TagHandler.MAIN_TAG, "Token sent to GCM");
                } else {
                    Log.d(TagHandler.MAIN_TAG, "Token not sent to GCM");
                }
            }
        };

        // Registering BroadcastReceiver
        registerReceiver();*/

        NotificationFacade.registerForNotifications(this);

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
        bottomBar.mapColorForTab(2, ContextCompat.getColor(this, R.color.meColorPrimary));
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
                currentFragment = MeFragment.newInstance(this);
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
    public void onChatClicked(Chat chat)
    {
        Intent i = new Intent(getApplicationContext(), ChatActiveActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("chat", chat);
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



        if (!isLiked) {
            DataHandlerFacade.getLikeDataHandler().create( post, new AbstractDataResponseListener<Like>() {
                @Override
                public void onSuccess(Like data) {
                    post.setIsLiked(!isLiked);
                }

                @Override
                public void onFailure(DataResponseError error, String errormessage) {
                    imageButton.setImageResource(originalLikeDrawable);
                    showSnackbar(getString(R.string.like_error_msg));
                }
            } );
        }
        else {
            DataHandlerFacade.getLikeDataHandler().delete( post, new AbstractDataResponseListener<Void>() {
                @Override
                public void onSuccess(Void data) {
                    post.setIsLiked(!isLiked);
                }

                @Override
                public void onFailure(DataResponseError error, String errormessage) {
                    imageButton.setImageResource(originalLikeDrawable);
                    showSnackbar(getString(R.string.like_error_msg));
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

    private void initFlow() {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                if (!PermissionHandler.hasPermissions(getApplicationContext())) {
                    Intent i = new Intent(MainActivity.this, PermissionFlow.class);
                    startActivity(i);
                    finish();

                }
                return null;
            }
        }.execute();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}