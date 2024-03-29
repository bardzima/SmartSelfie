package com.temobard.smartselfie.ui;

import android.app.Activity;

import com.temobard.smartselfie.ui.di.components.DaggerAppComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.support.DaggerApplication;

public class App extends DaggerApplication implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    AndroidInjector<? extends DaggerApplication> injector;

    @Override
    public void onCreate() {
        injector = DaggerAppComponent
                .builder()
                .application(this)
                .build();
        super.onCreate();
    }

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return injector;
    }
}
