package com.temobard.smartselfie.ui;

import android.databinding.BindingAdapter;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

public class BindingAdapters {
    @BindingAdapter({"android:fromPath"})
    public static void loadImageFromPath(ImageView view, String path) {
        view.setImageBitmap(BitmapFactory.decodeFile(path));
    }
}