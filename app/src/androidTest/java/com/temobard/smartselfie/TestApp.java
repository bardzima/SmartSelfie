package com.temobard.smartselfie;

import android.app.Activity;

//import com.temobard.smartselfie.di.DaggerMockAppComponent;

import com.temobard.smartselfie.di.DaggerMockAppComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.support.DaggerApplication;

public class TestApp extends DaggerApplication implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    AndroidInjector<? extends DaggerApplication> injector;

    @Override
    public void onCreate() {
        injector = DaggerMockAppComponent.builder().application(this).build();
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
