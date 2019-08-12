package com.temobard.smartselfie.domain;

/**
 * Camera frame data class. Extends base frame class with scale object.
 */
public class CameraFrame extends Frame {

    private float scale;

    public CameraFrame(int left, int top, int right, int bottom, float scale) {
        super(left, top, right, bottom);
        this.scale = scale;
    }

    public float getScale() {
        return scale;
    }

    public Frame scale(Frame frame) {
        int tLeft = (int) (frame.left * scale + left);
        int tRight = (int) (frame.right * scale + left);
        int tTop = (int) (frame.top * scale + top);
        int tBottom = (int) (frame.bottom * scale + top);

        return new Frame(tLeft, tTop, tRight, tBottom);
    }
}
