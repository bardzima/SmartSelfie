package com.temobard.smartselfie.tests;

import android.support.test.rule.ActivityTestRule;

import com.temobard.smartselfie.ui.activities.MainActivity;

import org.junit.Before;
import org.junit.Rule;

public class TestBase {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class,
            true,
            true);


    @Before
    public void setUp() {
    }

    void delay(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
