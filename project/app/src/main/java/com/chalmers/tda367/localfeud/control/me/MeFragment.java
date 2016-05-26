package com.chalmers.tda367.localfeud.control.me;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chalmers.tda367.localfeud.R;
import com.facebook.login.widget.LoginButton;

/**
 *  A Fragment that's controlling users settings.
 */
public class MeFragment extends Fragment {

    public MeFragment() {

    }

    /**
     *  Should be used for creating a new instance of MeFragment,
     *  since constructors of Fragments can't have any arguments
     *  @return a new instance of MeFragment
     */
    public static MeFragment newInstance() {
        return new MeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        Changing to R.style.MeAppTheme
        final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.MeAppTheme);
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
        return localInflater.inflate(R.layout.fragment_me, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view, savedInstanceState);
    }

    /**
     *  Initializing the relevant components that layout contains.
     *  @param view the holder layout that's containing all components
     *  @param savedInstanceState saved state from last instance of fragment
     */
    private void initViews(View view, @Nullable Bundle savedInstanceState) {
        CoordinatorLayout root = (CoordinatorLayout) view.findViewById(R.id.me_root);

        CollapsingToolbarLayout collapsingToolbarLayout =
                (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar_layout);
        collapsingToolbarLayout.setTitle(getResources().getString(R.string.app_name));

        LoginButton loginButton = (LoginButton) view.findViewById(R.id.me_logout_btn);
        loginButton.setFragment(this);
    }
}
