package com.temobard.smartselfie.data.sources;

import android.graphics.Bitmap;

import java.io.IOException;

public interface StorageSource {
    String saveImage(Bitmap bitmap) throws IOException;
}
