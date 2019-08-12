package com.temobard.smartselfie.tests;

import android.app.Instrumentation;
import android.support.test.rule.ActivityTestRule;

import com.temobard.smartselfie.TestApp;
import com.temobard.smartselfie.di.DaggerMockAppComponent;
import com.temobard.smartselfie.di.MockAppComponent;
import com.temobard.smartselfie.ui.activities.MainActivity;

import org.junit.Before;
import org.junit.Rule;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

public class TestBase {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class,
            true,
            true);


    @Before
    public void setUp() {
        Instrumentation instrumentation = getInstrumentation();
        TestApp app = (TestApp) instrumentation.getTargetContext()
                .getApplicationContext();

        MockAppComponent testComponent = DaggerMockAppComponent.builder()
                .application(app)
                .build();
    }

    protected void delay(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
