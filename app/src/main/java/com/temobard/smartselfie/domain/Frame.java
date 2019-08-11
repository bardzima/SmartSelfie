package com.temobard.smartselfie.domain;

public class Frame {
    private int left;
    private int top;
    private int right;
    private int bottom;
    private float scale = 1.0F;

    public int getLeft() {
        return left;
    }

    public int getTop() {
        return top;
    }

    public int getRight() {
        return right;
    }

    public int getBottom() {
        return bottom;
    }

    @Override
    public String toString() {
        return "left=" + left + ", top=" + top + ", right=" + right + ", bottom=" + bottom;
    }

    public Frame(int left, int top, int right, int bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    public Frame(int left, int top, int right, int bottom, float scale) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.scale = scale;
    }

    public Frame translate(Frame frame) {
        int tLeft = (int) (left * frame.scale + frame.left);
        int tRight = (int) (right * frame.scale + frame.left);
        int tTop = (int) (top * frame.scale + frame.top);
        int tBottom = (int) (bottom * frame.scale + frame.top);

        return new Frame(tLeft, tTop, tRight, tBottom);
    }

    public boolean isInsideFrame(Frame frame, float tolerance) {
        if (frame == null) return false;
        else return this.left >= frame.left * (1 - tolerance) &&
                this.right <= frame.right * (1 + tolerance) &&
                this.top >= frame.top * (1 - tolerance) &&
                this.bottom <= frame.bottom * (1 + tolerance);
    }
}
