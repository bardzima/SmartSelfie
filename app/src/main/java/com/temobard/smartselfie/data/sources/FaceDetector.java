package com.temobard.smartselfie.data.sources;

import com.temobard.smartselfie.domain.Face;
import io.reactivex.Observable;

public interface FaceDetector {
    Observable<Face> getFace();
}
