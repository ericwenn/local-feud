package com.chalmers.tda367.localfeud.util;

import android.content.Context;

import com.chalmers.tda367.localfeud.R;

/**
 * Created by Daniel Ahlqvist on 2016-05-16.
 */
public class DistanceString
{
    /**
     * Selects a string to represent the distance between the user device and the post
     * coordinates.
     *
     * @param context the context which stores the resource where the strings could be found.
     * @param distance the distance between the user device and the post coordinates.
     * @return a string which will be displayed to the user.
     */
    public static String getDistanceString(Context context, int distance)
    {
        if (distance < 500)
        {
            return context.getResources().getString(R.string.dist500);
        }
        else if (distance > 500 && distance < 1000)
        {
            return context.getResources().getString(R.string.dist5001000);
        }
        else if (distance > 1000 && distance < 2000)
        {
            return context.getResources().getString(R.string.dist10002000);
        }
        else if (distance > 2000 && distance < 3000)
        {
            return context.getResources().getString(R.string.dist20003000);
        }
        else
        {
            return context.getResources().getString(R.string.distmore3000);
        }
    }
}
