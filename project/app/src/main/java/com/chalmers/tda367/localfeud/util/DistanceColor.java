package com.chalmers.tda367.localfeud.util;

import android.graphics.Color;

import com.chalmers.tda367.localfeud.R;

/**
 * Created by Daniel Ahlqvist on 2016-04-28.
 */
public class DistanceColor
{
    public static int distanceColor(Double distance) throws NullPointerException
    {
        if (distance < 500)
        {
            return R.color.distRed;
        }
        else if (distance > 500 && distance < 1000)
        {
            return R.color.distOrange;
        }
        else if (distance > 1000 && distance < 2000)
        {
            return R.color.distYellow;
        }
        else if (distance > 2000 && distance < 3000)
        {
            return R.color.distGreen;
        }
        else
        {
            return R.color.distBlue;
        }
    }
}
