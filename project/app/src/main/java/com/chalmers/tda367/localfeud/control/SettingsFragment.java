package com.chalmers.tda367.localfeud.control;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chalmers.tda367.localfeud.R;

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
    public String toString() {
        return activity.getString(R.string.settings_string);
    }
}
