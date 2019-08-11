package com.temobard.smartselfie.ui.di.modules;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.temobard.smartselfie.ui.di.scopes.ViewModelKey;
import com.temobard.smartselfie.ui.viewmodels.FaceViewModel;
import com.temobard.smartselfie.ui.viewmodels.CropViewModel;
import com.temobard.smartselfie.ui.viewmodels.CameraViewModel;
import com.temobard.smartselfie.ui.viewmodels.ViewModelFactory;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(FaceViewModel.class)
    public abstract ViewModel bindCameraViewModel(FaceViewModel viewModel);

    @Binds
    @IntoMap
    @Singleton
    @ViewModelKey(CameraViewModel.class)
    public abstract ViewModel bindSelfieViewModel(CameraViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(CropViewModel.class)
    public abstract ViewModel bindCropViewModel(CropViewModel viewModel);

    @Binds
    public abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);
}
