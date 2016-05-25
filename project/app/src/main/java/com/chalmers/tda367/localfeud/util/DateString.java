package com.chalmers.tda367.localfeud.util;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/**
 * Created by ericwenn on 4/26/16.
 */
public class DateString {
    public static String convert(Calendar c) throws NullPointerException {


        Calendar current = Calendar.getInstance();
        long timeElapsedMs = current.getTimeInMillis() - c.getTimeInMillis();


        // More than one week ago
        if( TimeUnit.MILLISECONDS.toDays(timeElapsedMs) > 13 ) {
            return (int)Math.ceil((double) TimeUnit.MILLISECONDS.toDays(timeElapsedMs) / 7) + " weeks ago";
        }
        
        // One week ago
        if( TimeUnit.MILLISECONDS.toDays(timeElapsedMs) > 6) {
            return "1 week ago";
        }

        // More than one day ago
        if( TimeUnit.MILLISECONDS.toDays(timeElapsedMs) > 1) {
            return TimeUnit.MILLISECONDS.toDays(timeElapsedMs) + " days ago";
        }

        // One day ago
        if( TimeUnit.MILLISECONDS.toDays(timeElapsedMs) > 0) {
            return "1 day ago";
        }


        // More than one hour ago
        if( TimeUnit.MILLISECONDS.toHours(timeElapsedMs) > 1) {
            return TimeUnit.MILLISECONDS.toHours(timeElapsedMs) + " hours ago";
        }

        // One hour ago
        if( TimeUnit.MILLISECONDS.toHours(timeElapsedMs) > 0) {
            return "1 hour ago";
        }

        // More than 5 minutes ago
        if( TimeUnit.MILLISECONDS.toMinutes(timeElapsedMs) > 2) {
            return TimeUnit.MILLISECONDS.toMinutes(timeElapsedMs) + " minutes ago";
        }

        // Just now
        return "Just now";



    }
}
