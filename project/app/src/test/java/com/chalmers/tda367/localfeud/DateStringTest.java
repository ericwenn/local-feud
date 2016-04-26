package com.chalmers.tda367.localfeud;

import com.chalmers.tda367.localfeud.util.DateString;

import org.junit.Test;

import java.util.Calendar;
import java.util.regex.Matcher;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class DateStringTest {



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
}