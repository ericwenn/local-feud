package com.chalmers.tda367.localfeud;

import android.content.Context;
import android.test.AndroidTestCase;

import com.chalmers.tda367.localfeud.util.DistanceString;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by Daniel Ahlqvist on 2016-05-25.
 */
public class DistanceStringTest
{
    @Test
    public void lessThan500_isCorrect() throws Exception
    {
        int distance = 398;
        AndroidTestCase testCase = new AndroidTestCase();
        Context context = testCase.getContext();

        String testLessThan500 = DistanceString.getDistanceString(context, distance);
        assertThat(testLessThan500, is("less than 500 meters away"));
    }
}
