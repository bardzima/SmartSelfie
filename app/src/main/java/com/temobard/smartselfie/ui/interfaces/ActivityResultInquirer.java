package com.temobard.smartselfie.ui.interfaces;

import android.content.Intent;

public interface ActivityResultInquirer {
    void onActivityResult(int requestCode, int resultCode, Intent data);
}
