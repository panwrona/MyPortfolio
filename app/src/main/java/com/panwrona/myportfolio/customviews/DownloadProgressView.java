package com.panwrona.myportfolio.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.panwrona.myportfolio.R;

public class DownloadProgressView extends View {

    private Paint mCirclePaint;
    private Paint mDrawingPaint;
    private float mRadius;
    private float mStrokeWidth;

    public DownloadProgressView(Context context) {
        super(context);
    }

    public DownloadProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mCirclePaint = new Paint();
        mCirclePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setColor(getResources().getColor(R.color.light_primary_color));
        mCirclePaint.setStrokeWidth(mStrokeWidth);

        mDrawingPaint = new Paint();
        mDrawingPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mDrawingPaint.setStyle(Paint.Style.STROKE);
        mDrawingPaint.setColor(getResources().getColor(R.color.text_icon_color));
        mDrawingPaint.setStrokeWidth(mStrokeWidth);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.DownloadProgressView, 0, 0);
        try {
            mRadius = array.getDimension(R.styleable.DownloadProgressView_circleRadius, 0);
            mStrokeWidth = array.getDimension(R.styleable.DownloadProgressView_strokeWidth, 0);
        } finally {
            array.recycle();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, mRadius, mCirclePaint);
        canvas.drawLine(getWidth() / 2, getHeight() / 4, getWidth() / 2, getHeight() - getHeight() / 4, mDrawingPaint);
        canvas.drawLine(getWidth() / 4, getHeight() / 2, getWidth() / 2, getHeight() - getHeight() / 4, mDrawingPaint);
        canvas.drawLine(getWidth() / 2, getHeight() - getHeight() / 4, getWidth() - getWidth() / 4, getHeight() / 2, mDrawingPaint);
    }
}
