package com.chalmers.tda367.localfeud.control.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chalmers.tda367.localfeud.R;
import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AuthenticationFlowLoginSlide#newInstance} factory method to
 * create an instance of this fragment.
 */

// * {@link AuthenticationFlowLoginSlide.OnFragmentInteractionListener}
public class AuthenticationFlowLoginSlide extends Fragment {

    private CallbackManager callbackManager;

    //private OnFragmentInteractionListener mListener;

    public AuthenticationFlowLoginSlide() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AuthenticationFlowLoginSlide.
     */
    public static AuthenticationFlowLoginSlide newInstance() {
        AuthenticationFlowLoginSlide fragment = new AuthenticationFlowLoginSlide();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_authentication_flow_login_slide, container, false);
        LoginButton loginButton = (LoginButton) v.findViewById(R.id.fb_login);
        loginButton.setFragment(this);
        loginButton.setReadPermissions("user_birthday");
        TextView termsButton = (TextView) v.findViewById(R.id.terms_button);
        termsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Snackbar.make(v,
                        "To use this application, you need to be at least 18 years old. Being an idiot is prohibited.",
                        Snackbar.LENGTH_LONG)
                        .show();
            }
        });

        callbackManager = CallbackManager.Factory.create();
        return v;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
