package com.chalmers.tda367.localfeud.control.permission;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;

import com.chalmers.tda367.localfeud.R;
import com.chalmers.tda367.localfeud.control.MainActivity;
import com.chalmers.tda367.localfeud.services.LocationHandler;
import com.chalmers.tda367.localfeud.services.LocationPermissionError;
import com.chalmers.tda367.localfeud.util.SlideFactory;
import com.github.paolorotolo.appintro.AppIntro2;

/**
 *  The activity that's used when asking for required permissions.
 */
public class PermissionFlow extends AppIntro2 {
    private static final String TAG = "PermissionFlow";

//    Please DO NOT override onCreate. Use init.
    @Override
    public void init(Bundle savedInstanceState) {


        addSlide(SlideFactory.newInstance(R.layout.fragment_permission_flow_slide1));

        // TODO Adds one more slide just to enable the permissions
        addSlide(SlideFactory.newInstance(R.layout.fragment_permission_flow_slide1));

        askForPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
    }


    @Override
    public void onDonePressed() {}

    @Override

    public void onSlideChanged() {

        if (getPager().getCurrentItem() != 0) {

            askForPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);

            try {
                LocationHandler.getInstance().startTracking(getApplicationContext());
            } catch (LocationPermissionError locationPermissionError) {
                getPager().setCurrentItem(0);
            }
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            finish();
        }
    }

    @Override
    public void onNextPressed() {
        // Do something when users tap on Next button.
    }

}