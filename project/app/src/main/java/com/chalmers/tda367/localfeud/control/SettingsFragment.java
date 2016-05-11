package com.chalmers.tda367.localfeud.control;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chalmers.tda367.localfeud.R;
import com.facebook.login.widget.LoginButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {

    private MainActivity activity;

    public SettingsFragment() {
        // Required empty public constructor
    }

    public static SettingsFragment newInstance(MainActivity activity) {
        SettingsFragment fragment = new SettingsFragment();
        fragment.activity = activity;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.MeAppTheme);
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
        return localInflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view, savedInstanceState);
    }

    private void initViews(View view, Bundle savedInstanceState) {
        LoginButton loginButton = (LoginButton) view.findViewById(R.id.me_logout_btn);
        loginButton.setFragment(this);
    }

    @Override
    public String toString() {
        return activity.getString(R.string.settings_string);
    }
}
