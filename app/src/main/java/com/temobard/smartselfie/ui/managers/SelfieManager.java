package com.temobard.smartselfie.ui.managers;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.temobard.smartselfie.ui.interfaces.ActivityResultInquirer;
import com.temobard.smartselfie.ui.viewmodels.CropViewModel;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;

public class SelfieManager implements ActivityResultInquirer {

    private Uri selfieUri;
    private Activity activity;
    private CropViewModel cropViewModel;

    public SelfieManager(Activity activity, String selfiePath, CropViewModel cropViewModel) {
        selfieUri = Uri.fromFile(new File(selfiePath));
        this.activity = activity;
        this.cropViewModel = cropViewModel;

        cropViewModel.setSelfiePath(selfiePath);
        startCropping();
    }

    public void startCropping() {
        CropImage.activity(selfieUri)
                .start(activity);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == Activity.RESULT_OK) {
                cropViewModel.setSelfiePath(result.getUri().getPath());
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(activity, "Error while cropping", Toast.LENGTH_LONG).show();
            }
        }
    }
}
