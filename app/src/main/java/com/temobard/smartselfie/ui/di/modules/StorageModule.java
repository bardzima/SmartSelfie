package com.temobard.smartselfie.ui.di.modules;

import android.content.Context;

import com.temobard.smartselfie.data.sources.StorageSource;
import com.temobard.smartselfie.framework.storage.SelfieStorageSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public
class StorageModule {

    private static final String IMAGE_DIR = "images/";

    @Singleton
    @Provides
    String providesImageDirectory(Context context) {
        return context.getFilesDir() + "/" + IMAGE_DIR;
    }

    @Provides
    StorageSource providesStorageSource(String defaultDirectory) {
        return new SelfieStorageSource(defaultDirectory);
    }
}
