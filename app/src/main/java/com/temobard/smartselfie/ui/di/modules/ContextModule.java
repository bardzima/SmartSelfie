package com.temobard.smartselfie.ui.di.modules;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ContextModule {

    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application;
    }
}
