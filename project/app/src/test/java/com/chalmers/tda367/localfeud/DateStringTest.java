package com.chalmers.tda367.localfeud;

import com.chalmers.tda367.localfeud.util.DateString;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class DateStringTest {


    @Test
    public void tenWeeksAgo_isCorrect() throws Exception {
        Calendar fiveDaysAgo = Calendar.getInstance();
        fiveDaysAgo.add( Calendar.WEEK_OF_YEAR, -10);

        String testFiveDaysAgo = DateString.convert(fiveDaysAgo);
        assertThat(testFiveDaysAgo, is("10 weeks ago"));
    }

    @Test
    public void oneWeekAgo_isCorrect() throws Exception {
        Calendar fiveDaysAgo = Calendar.getInstance();
        fiveDaysAgo.add( Calendar.WEEK_OF_YEAR, -1);


        String testFiveDaysAgo = DateString.convert(fiveDaysAgo);
        assertThat(testFiveDaysAgo, is("1 week ago"));
    }

    @Test
    public void fiveDaysAgo_isCorrect() throws Exception {
        Calendar fiveDaysAgo = Calendar.getInstance();
        fiveDaysAgo.add( Calendar.DAY_OF_YEAR, -5);

        String testFiveDaysAgo = DateString.convert(fiveDaysAgo);
        assertThat(testFiveDaysAgo, is("5 days ago"));
    }



    @Test
    public void threeDaysAgo_isCorrect() throws Exception {
        Calendar threeDaysAgo = Calendar.getInstance();
        threeDaysAgo.add( Calendar.DAY_OF_YEAR, -3);

        String testThreeDaysAgo = DateString.convert(threeDaysAgo);
        assertThat(testThreeDaysAgo, is("3 days ago"));
    }




    @Test
    public void oneDayAgo_isCorrect() throws Exception {
        Calendar oneDayAgo = Calendar.getInstance();
        oneDayAgo.add( Calendar.DAY_OF_YEAR, -1);

        String testOneDayAgo = DateString.convert(oneDayAgo);
        assertThat(testOneDayAgo, is("1 day ago"));
    }



    @Test
    public void oneHourAgo_isCorrect() throws Exception {
        Calendar oneDayAgo = Calendar.getInstance();
        oneDayAgo.add( Calendar.HOUR, -1);

        String testOneDayAgo = DateString.convert(oneDayAgo);
        assertThat(testOneDayAgo, is("1 hour ago"));
    }




    @Test
    public void thirtyMinutesAgo_isCorrect() throws Exception {
        Calendar oneDayAgo = Calendar.getInstance();
        oneDayAgo.add( Calendar.MINUTE, -30);

        String testOneDayAgo = DateString.convert(oneDayAgo);
        assertThat(testOneDayAgo, is("30 minutes ago"));
    }



    @Test
    public void oneMinuteAgo_isCorrect() throws Exception {
        Calendar oneDayAgo = Calendar.getInstance();
        oneDayAgo.add( Calendar.MINUTE, -1);

        String testOneDayAgo = DateString.convert(oneDayAgo);
        assertThat(testOneDayAgo, is("Just now"));
    }

    @Test(expected = NullPointerException.class)
    public void nullCalendar_isCorrect() throws Exception {
        String testOneDayAgo = DateString.convert(null);
    }
}