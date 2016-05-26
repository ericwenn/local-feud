package com.chalmers.tda367.localfeud.util;

import com.chalmers.tda367.localfeud.R;

/**
 * Created by Daniel Ahlqvist on 2016-04-28.
 */
public class DistanceColor
{
    /**
     * Determines which color will be used as background for a post header.
     *
     * @param distance the distance between the user device and the post coordinates.
     * @return the color as an integer value.
     */
    public static int distanceColor(int distance) throws NullPointerException
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

    /**
     * Determines which color will be used for the text for a post header.
     *
     * @param color the background color of the header
     * @return the color as an integer value.
     */
    public static int distanceTextColor(int color) throws NullPointerException
    {
        if (color == R.color.distRed)
        {
            return android.R.color.white;
        }
        else
        {
            return R.color.alphaBlack;
        }
    }

    /**
     * Determines which style will be used for the PostClickedActivity.
     *
     * @param color the background color of the post header
     * @return the style as an integer value.
     */
    public static int distanceStyle(int color) throws NullPointerException
    {
        if (color == R.color.distRed)
        {
            return R.style.DistRedTheme;
        }
        else if (color == R.color.distOrange)
        {
            return R.style.DistOrangeTheme;
        }
        else if (color == R.color.distYellow)
        {
            return R.style.DistYellowTheme;
        }
        else if (color == R.color.distGreen)
        {
            return R.style.DistGreenTheme;
        }
        else
        {
            return R.style.DistBlueTheme;
        }
    }
}
