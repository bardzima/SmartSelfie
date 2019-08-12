package com.temobard.smartselfie.tests;

import android.support.test.runner.AndroidJUnit4;

import com.temobard.smartselfie.R;
import com.temobard.smartselfie.domain.Frame;
import com.temobard.smartselfie.framework.MockFaceTracker;

import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class FaceDetectionTest extends TestBase {

    @Test
    public void snapCameraDisabledEnabled() {

        //"No face"
        MockFaceTracker.setFaceFrame(null);
        delay(500);

        //button should be disabled when there's no face detected
        onView(withId(R.id.snapButton)).check(matches(not(isEnabled())));

        //face shows up within the frame
        MockFaceTracker.setFaceFrame(new Frame(200, 200, 420, 430));

        delay(1000);

        //button should be enabled now
        onView(withId(R.id.snapButton)).check(matches(isEnabled()));

        //face moves out of the frame
        MockFaceTracker.setFaceFrame(new Frame(1000, 1000, 2000, 2000));

        delay(1000);

        //button should be disabled again now
        onView(withId(R.id.snapButton)).check(matches(not(isEnabled())));
    }
}
