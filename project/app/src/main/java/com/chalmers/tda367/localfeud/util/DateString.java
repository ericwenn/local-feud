package com.chalmers.tda367.localfeud.util;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/**
 * Created by ericwenn on 4/26/16.
 */
public class DateString {
    public static String convert(Calendar c) {

        Calendar current = Calendar.getInstance();
        long timeElapsedMs = current.getTimeInMillis() - c.getTimeInMillis();

        long timeElapsedMin = TimeUnit.MILLISECONDS.toMinutes(timeElapsedMs);
        long timeElapsedHour = TimeUnit.MILLISECONDS.toHours(timeElapsedMs);
        long timeElapsedDay = TimeUnit.MILLISECONDS.toDays(timeElapsedMs);

        String timeSince;


        if(timeElapsedDay >= 5) {

        }
        /**
        if (timeElapsedDay >= 1) {
            if (timeElapsedDay == 1)
                timeSince = "1 day ago";
            else
                timeSince = timeElapsedDay + " days ago";
        } else if (timeElapsedMin > 60) {
            if (timeElapsedHour == 1)
                timeSinceUpload = "1 hour ago";
            else
                timeSinceUpload = timeElapsedHour + " hours ago";
        } else {
            if (timeElapsedMin == 1)
                timeSinceUpload = "1 minute ago";
            else
                timeSinceUpload = timeElapsedMin + " minutes ago";
        }
         */
        return "hej";
    }
}
