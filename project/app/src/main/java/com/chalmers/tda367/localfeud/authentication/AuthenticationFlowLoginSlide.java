package com.chalmers.tda367.localfeud.authentication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_authentication_flow_login_slide, container, false);
        LoginButton loginButton = (LoginButton) v.findViewById(R.id.fb_login);
        loginButton.setFragment(this);


        callbackManager = CallbackManager.Factory.create();
        return v;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
