package com.chalmers.tda367.localfeud.permissionflow;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.chalmers.tda367.localfeud.R;
import com.chalmers.tda367.localfeud.control.MainActivity;
import com.chalmers.tda367.localfeud.permission.PermissionHandler;
import com.chalmers.tda367.localfeud.util.TagHandler;
import com.github.paolorotolo.appintro.AppIntro2;

public class PermissionFlow extends AppIntro2 {

    // Please DO NOT override onCreate. Use init.
    @Override
    public void init(Bundle savedInstanceState) {


        addSlide(SlideFactory.newInstance(R.layout.fragment_permission_flow_slide1));

        // TODO Adds one more slide just to enable the permissions
        addSlide(SlideFactory.newInstance(R.layout.fragment_permission_flow_slide1));

        askForPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
    }


    @Override
    public void onDonePressed() {
        Log.d(TagHandler.PERMISSION_FLOW_TAG, "onDonePressed()");
        // Do something when users tap on Done button.
    }

    @Override
    public void onSlideChanged() {


        Log.d(TagHandler.PERMISSION_FLOW_TAG, "Permission check: " + PermissionHandler.hasPermissions(getApplicationContext()));
        if (!PermissionHandler.hasPermissions(getApplicationContext())) {
            Log.d(TagHandler.PERMISSION_FLOW_TAG, "Permission denied, going to first page");
            if (getPager().getCurrentItem() != 0) {
                getPager().setCurrentItem(0);
                askForPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            }
        } else {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            finish();
            Log.d(TagHandler.PERMISSION_FLOW_TAG, "Success");
        }
        // Do something when the slide changes.
    }

    @Override
    public void onNextPressed() {
        Log.d(TagHandler.PERMISSION_FLOW_TAG, "onNextPressed()");
        // Do something when users tap on Next button.
    }

}