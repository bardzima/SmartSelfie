package com.temobard.smartselfie.ui.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.graphics.Rect;
import android.util.Log;

import com.temobard.smartselfie.data.FramedFaceProcessor;
import com.temobard.smartselfie.domain.Frame;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CameraViewModel extends BaseViewModel {

    private MutableLiveData<Boolean> faceFramed = new MutableLiveData<>();
    private FramedFaceProcessor framedFaceProcessor;

    @Inject
    CameraViewModel(FramedFaceProcessor framedFaceProcessor) {
        this.framedFaceProcessor = framedFaceProcessor;

        Disposable faceTrackerDisposable = framedFaceProcessor.getFaceFramed()
                .subscribeOn(Schedulers.io())
                .throttleLast(200, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(faceInFrame -> faceFramed.setValue(faceInFrame));

        compositeDisposable.add(faceTrackerDisposable);
    }

    public MutableLiveData<Boolean> getFaceFramed() {
        return faceFramed;
    }

    public void setFaceFrame(Frame faceFrame) {
        framedFaceProcessor.setFrame(faceFrame);
    }

    public void setCameraFrame(Rect cameraRect, float scale) {
        Log.d("CameraViewModel", "setCameraFrame");
        Frame faceFrame = new Frame(cameraRect.left, cameraRect.top, cameraRect.right, cameraRect.bottom);
        framedFaceProcessor.setCameraFrame(faceFrame, scale);
    }
}
