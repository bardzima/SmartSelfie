package com.temobard.smartselfie.framework.storage;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;

import com.temobard.smartselfie.data.sources.StorageSource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Image storage class
 */
public class SelfieStorageSource implements StorageSource {

    private static final String IMAGE_PREFIX = "selfie_";
    private static final String IMAGE_EXT = "jpg";

    private String directory;

    public SelfieStorageSource(String directory) {
        this.directory = directory;
    }

    @Override
    public String saveImage(Bitmap bitmap) throws IOException {
        @SuppressLint("DefaultLocale") String filename = directory +
                String.format("%s%d.%s",
                        IMAGE_PREFIX,
                        System.currentTimeMillis() / 1000,
                        IMAGE_EXT);

        File dirFile = new File(directory);
        if (!dirFile.exists()) {
            if (!dirFile.mkdirs()) throw new IOException();
        }

        try (FileOutputStream out = new FileOutputStream(filename)) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        }

        return filename;
    }
}
