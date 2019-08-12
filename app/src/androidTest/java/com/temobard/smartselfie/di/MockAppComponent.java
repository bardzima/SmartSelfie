package com.temobard.smartselfie.di;

import android.app.Application;

import com.temobard.smartselfie.TestApp;
import com.temobard.smartselfie.di.modules.MockCameraSource;
import com.temobard.smartselfie.di.modules.MockDetectorModule;
import com.temobard.smartselfie.tests.FaceDetectionTest;
import com.temobard.smartselfie.ui.App;
import com.temobard.smartselfie.ui.di.modules.ActivityModule;
import com.temobard.smartselfie.ui.di.modules.CameraModule;
import com.temobard.smartselfie.ui.di.modules.ContextModule;
import com.temobard.smartselfie.ui.di.modules.DetectorModule;
import com.temobard.smartselfie.ui.di.modules.StorageModule;
import com.temobard.smartselfie.ui.di.modules.ViewModelModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        ActivityModule.class,
        ViewModelModule.class,
        ContextModule.class,
        MockCameraSource.class,
        StorageModule.class,
        MockDetectorModule.class
})
public interface MockAppComponent extends AndroidInjector<TestApp> {

    void inject(FaceDetectionTest faceDetectionTest);

    @Component.Builder
    interface MockBuilder {
        @BindsInstance
        MockBuilder application(Application application);
        MockAppComponent build();
    }
}