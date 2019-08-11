package com.temobard.smartselfie.ui.di.modules;

import com.temobard.smartselfie.ui.activities.ImageActivity;
import com.temobard.smartselfie.ui.activities.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract MainActivity provideMainActivity();

    @ContributesAndroidInjector
    abstract ImageActivity provideSelfieActivity();
}
