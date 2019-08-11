package com.temobard.smartselfie;

import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.temobard.smartselfie.modules.MockCameraModule;
import com.temobard.smartselfie.ui.App;
import com.temobard.smartselfie.ui.activities.MainActivity;
import com.temobard.smartselfie.ui.di.components.AppComponent;

import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;

import javax.inject.Singleton;

import dagger.Component;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Singleton
    @Component(modules = MockCameraModule.class)
    public interface MockAppComponent extends AppComponent {
    }

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class,
            true,     // initialTouchMode
            false);   // launchActivity.

    @Before
    public void setUp() {
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        App app = (App) instrumentation.getTargetContext()
                .getApplicationContext();

        MockAppComponent testComponent = .builder()
                .mockNetworkModule(new MockCameraModule())
                .build();
//        app.setComponent(testComponent);
//        mModel = testComponent.contributorsModel();
    }
}
