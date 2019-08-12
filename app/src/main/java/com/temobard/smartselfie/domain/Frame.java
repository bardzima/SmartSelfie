package com.temobard.smartselfie.domain;

/**
 * Base frame POJO
 * Contains four coordinates and a method to determine if another frame is withing its limits
 */
public class Frame {
    int left = 0;
    int top = 0;
    int right = 0;
    int bottom = 0;

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

    public void setLeft(int left) {
        this.left = left;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public void setBottom(int bottom) {
        this.bottom = bottom;
    }

    @Override
    public String toString() {
        return "left=" + left + ", top=" + top + ", right=" + right + ", bottom=" + bottom;
    }

    public Frame() {

    }

    public Frame(int left, int top, int right, int bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    public boolean isInsideFrame(Frame frame, float tolerance) {
        if (frame == null) return false;
        else return this.left >= frame.left * (1 - tolerance) &&
                this.right <= frame.right * (1 + tolerance) &&
                this.top >= frame.top * (1 - tolerance) &&
                this.bottom <= frame.bottom * (1 + tolerance);
    }
}
