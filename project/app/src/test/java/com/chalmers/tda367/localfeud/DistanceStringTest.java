package com.chalmers.tda367.localfeud;

import android.content.Context;
import android.test.AndroidTestCase;

import com.chalmers.tda367.localfeud.util.DistanceString;

import org.junit.Test;
import org.mockito.Mock;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Created by Daniel Ahlqvist on 2016-05-25.
 */

public class DistanceStringTest extends AndroidTestCase {

    private static final String FAKE_STRING = "less than5001000";
    @Mock
    Context mMockContext;

    @Test
    public void testLessThan500_isCorrect() throws Exception
    {

        when(mMockContext.getString(R.string.dist5001000))
                .thenReturn(FAKE_STRING);

        int distance = 398;



        String testLessThan500 = DistanceString.getDistanceString(mMockContext, distance);
        assertThat(testLessThan500, is("less than 500 meters awsay"));
    }
}
