package com.temobard.smartselfie.domain;

public class Face {

    private Frame frame;

    private float eulerY = 0;
    private float eulerZ = 0;

    public Face(Frame frame) {
        this.frame = frame;
    }

    public Face(Frame frame, float eulerY, float eulerZ)
    {
        this(frame);
        this.eulerY = eulerY;
        this.eulerZ = eulerZ;
    }

    public Frame getBoundary() {
        return frame;
    }

    public float getEulerY() {
        return eulerY;
    }

    public float getEulerZ() {
        return eulerZ;
    }
}
