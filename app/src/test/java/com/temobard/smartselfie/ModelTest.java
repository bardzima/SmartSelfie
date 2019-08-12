package com.temobard.smartselfie;

import com.google.android.gms.common.images.Size;
import com.temobard.smartselfie.data.FramedFaceProcessor;
import com.temobard.smartselfie.data.sources.FaceTracker;
import com.temobard.smartselfie.domain.CameraFrame;
import com.temobard.smartselfie.domain.Frame;
import com.temobard.smartselfie.model.MockFaceTracker;
import com.temobard.smartselfie.ui.helpers.CameraHelper;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ModelTest {

    @Test
    public void test_CameraViewScreenResize() {
        Size cameraSize = new Size(300, 100);
        Frame layoutFrame = new Frame(0, 0, 400, 500);

        CameraFrame cameraFrame = CameraHelper.getAdjustedCameraFrame(cameraSize, layoutFrame, true);

        assertEquals(cameraFrame.getLeft(), 117);
        assertEquals(cameraFrame.getTop(), 0);
        assertEquals(cameraFrame.getRight(), 283);
        assertEquals(cameraFrame.getBottom(), 500);
        assertEquals(cameraFrame.getScale(), 1.66, 0.01);
    }

    @Test
    public void test_FaceFramedProcessor() {

        //set initial face location
        MockFaceTracker.faceFrame = new Frame(100, 100, 220, 230);

        FaceTracker faceTracker = new MockFaceTracker();
        FramedFaceProcessor faceProcessor = new FramedFaceProcessor(faceTracker);

        //set face frame and camera frames
        faceProcessor.setFrame(new Frame(50, 50, 500, 500));
        faceProcessor.setCameraFrame(new CameraFrame(20, 20, 700, 700, 1.0F));

        boolean faceFramed = faceProcessor.getFaceFramed().blockingFirst();

        //the result should be 'true' (face is within the frame)
        assertTrue(faceFramed);

        //now move the face outside the frame
        MockFaceTracker.faceFrame = new Frame(0, 0, 100, 100);

        faceFramed = faceProcessor.getFaceFramed().blockingFirst();

        //the result now should be 'false' (face outside the frame)
        assertFalse(faceFramed);
    }
}