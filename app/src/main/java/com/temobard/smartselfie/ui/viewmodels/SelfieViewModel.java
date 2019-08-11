package com.temobard.smartselfie.ui.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.graphics.Bitmap;
import android.graphics.Matrix;

import com.temobard.smartselfie.data.sources.CameraController;
import com.temobard.smartselfie.data.sources.StorageSource;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SelfieViewModel extends BaseViewModel {

    private MutableLiveData<String> selfiePathSetter = new MutableLiveData<>();

    private CameraController cameraController;
    private StorageSource storageSource;

    @Inject
    public SelfieViewModel(CameraController cameraController, StorageSource storageSource) {
        this.cameraController = cameraController;
        this.storageSource = storageSource;
    }

    public void onSnapPhotoClicked() {
        Disposable snapDisposable = cameraController.snap()
                .subscribeOn(Schedulers.io())
                //Flip bitmap vertically. Possibly optional later, for now we just assume front camera.
                .map(bitmap -> {
                    Matrix matrix = new Matrix();
                    matrix.postScale(-1, 1, bitmap.getWidth() / 2f, bitmap.getHeight() / 2f);
                    return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                })
                //save bitmap into file and return file path
                .map(bitmap -> storageSource.saveImage(bitmap))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(selfiePath -> selfiePathSetter.setValue(selfiePath));

        compositeDisposable.add(snapDisposable);
    }

    public MutableLiveData<String> getSelfiePath() {
        return selfiePathSetter;
    }
}
