package com.temobard.smartselfie.ui.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.temobard.smartselfie.domain.Frame;

public class FaceFrameView extends View {

    private static final float FRAME_WIDTH = 1.5F;

    private int mWidth = 0;
    private int mHeight = 0;

    private Paint mOvalPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mOvalNegativePaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private OnFaceFrameSetListener faceFrameSetListener;

    public interface OnFaceFrameSetListener {
        void onFaceFrameSet(Frame faceFrame);
    }

    public void setOnFaceFrameSetListener(OnFaceFrameSetListener faceFrameSetListener) {
        this.faceFrameSetListener = faceFrameSetListener;
    }

    public FaceFrameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mOvalPaint.setColor(Color.DKGRAY);
        mOvalPaint.setStyle(Paint.Style.STROKE);
        mOvalPaint.setStrokeWidth(FRAME_WIDTH);

        mOvalNegativePaint.setColor(Color.WHITE);
        mOvalNegativePaint.setStyle(Paint.Style.STROKE);
        mOvalNegativePaint.setStrokeWidth(FRAME_WIDTH);

        setBackgroundColor(Color.alpha(0));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawOval(0, 0, mWidth, mHeight, mOvalPaint);
        canvas.drawOval(FRAME_WIDTH, FRAME_WIDTH, mWidth - FRAME_WIDTH, mHeight - FRAME_WIDTH, mOvalNegativePaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if (faceFrameSetListener != null)
            faceFrameSetListener.onFaceFrameSet(new Frame(left, top, right, bottom));
    }
}
