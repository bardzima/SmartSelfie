package com.temobard.smartselfie.ui.di.components;

import android.app.Application;

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
        CameraModule.class,
        StorageModule.class,
        DetectorModule.class
})
public interface AppComponent extends AndroidInjector<App> {

    void inject(Application application);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}
