package com.temobard.smartselfie.ui.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.graphics.Bitmap;
import android.graphics.Matrix;

import com.google.android.gms.vision.CameraSource;
import com.temobard.smartselfie.data.sources.CameraController;
import com.temobard.smartselfie.data.sources.StorageSource;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Camera view model class that receives actions related to the camera
 */
public class CameraViewModel extends BaseViewModel {

    private MutableLiveData<String> selfiePathSetter = new MutableLiveData<>();
    private CameraSource cameraSource;
    private MutableLiveData<Boolean> cameraStarted = new MutableLiveData<>();

    private CameraController cameraController;
    private StorageSource storageSource;

    @Inject
    public CameraViewModel(
            CameraController cameraController,
            CameraSource cameraSource,
            StorageSource storageSource) {
        this.cameraController = cameraController;
        this.storageSource = storageSource;
        this.cameraSource = cameraSource;
    }

    /**
     * Receives the request to take a photo
     */
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

    /**
     * Selfie path getter
     * @return LiveData stream of the taken selfie image path
     */
    public LiveData<String> getSelfiePath() {
        return selfiePathSetter;
    }

    public CameraSource getCameraSource() {
        return cameraSource;
    }

    /**
     * @return reflects if the camera start request is sent
     */
    public MutableLiveData<Boolean> getCameraStarted() {
        return cameraStarted;
    }

    public void resumeCamera() {
        cameraStarted.setValue(true);
    }

    public void pauseCamera() {
        cameraStarted.setValue(false);
        cameraController.stop();
    }

    @Override
    protected void onCleared() {
        cameraController.dispose();
        super.onCleared();
    }
}
