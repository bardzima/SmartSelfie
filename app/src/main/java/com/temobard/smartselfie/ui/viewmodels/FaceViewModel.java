package com.temobard.smartselfie.ui.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.temobard.smartselfie.data.FramedFaceProcessor;
import com.temobard.smartselfie.domain.CameraFrame;
import com.temobard.smartselfie.domain.Frame;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Face detection view model
 */
public class FaceViewModel extends BaseViewModel {

    private MutableLiveData<Boolean> faceFramed = new MutableLiveData<>();
    private FramedFaceProcessor framedFaceProcessor;

    @Inject
    public FaceViewModel(FramedFaceProcessor framedFaceProcessor) {
        this.framedFaceProcessor = framedFaceProcessor;

        Disposable faceTrackerDisposable = framedFaceProcessor.getFaceFramed()
                .subscribeOn(Schedulers.io())
                .throttleLast(200, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(faceInFrame -> faceFramed.setValue(faceInFrame));

        compositeDisposable.add(faceTrackerDisposable);
    }

    /**
     * @return LiveData stream of whether the face has been detected
     */
    public LiveData<Boolean> getFaceFramed() {
        return faceFramed;
    }

    /**
     * Sets frame within which the face should be placed
     * @param faceFrame
     */
    public void setFaceFrame(Frame faceFrame) {
        framedFaceProcessor.setFrame(faceFrame);
    }

    /**
     * Sets camera frame
     * @param cameraFrame
     */
    public void setCameraFrame(CameraFrame cameraFrame) {
        Log.d("FaceViewModel", "setCameraFrame");
        framedFaceProcessor.setCameraFrame(cameraFrame);
    }
}
