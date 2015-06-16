package com.panwrona.myportfolio.customviews;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Animatable;
import android.util.AttributeSet;
import android.util.Property;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.panwrona.myportfolio.R;

public class ProgressPhotoView extends ImageView implements Animatable {

    private final static int MIN_SWEEP_ANGLE = 30;
    private final LinearInterpolator mAngleInterpolator = new LinearInterpolator();
    private final DecelerateInterpolator mSweepInterpolator = new DecelerateInterpolator();
    private final RectF mBounds = new RectF();
    private float mStrokeThickness;
    private float mCurrentGlobalAngle;
    private float mCurrentSweepAngle;
    private float mRadius;
    private int mProgressColor;
    private int mCurrentGlobalAngleOffset;
    private int mBackgroundColor;
    private boolean mRunning;
    private boolean mModeAppearing;

    private Paint mPaint;
    private ObjectAnimator mAngleObjectAnimator;
    private ObjectAnimator mSweepObjectAnimator;

    private Property<ProgressPhotoView, Float> mSweepProperty = new Property<ProgressPhotoView, Float>(Float.class, "sweep") {
        @Override
        public Float get(ProgressPhotoView object) {
            return object.getCurrentSweepAngle();
        }

        @Override
        public void set(ProgressPhotoView object, Float value) {
            object.setCurrentSweepAngle(value);
        }
    };

    private Property<ProgressPhotoView, Float> mAngleProperty = new Property<ProgressPhotoView, Float>(Float.class, "angle") {
        @Override
        public Float get(ProgressPhotoView object) {
            return object.getCurrentGlobalAngle();
        }

        @Override
        public void set(ProgressPhotoView object, Float value) {
            object.setCurrentGlobalAngle(value);
        }
    };

    public ProgressPhotoView(Context context) {
        super(context);
    }

    public ProgressPhotoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
        init(context);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ProgressPhotoView, 0, 0);
        try {
            mStrokeThickness = array.getDimension(R.styleable.ProgressPhotoView_strokeThickness, 0);
            mProgressColor = array.getColor(R.styleable.ProgressPhotoView_progressColor, 0);
            mBackgroundColor = array.getColor(R.styleable.ProgressPhotoView_backgroundColor, 0);
            mRadius = array.getDimension(R.styleable.ProgressPhotoView_radius, 0);

        } finally {
            array.recycle();
        }
    }

    private void init(Context context) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mStrokeThickness);
        mPaint.setColor(mProgressColor);
        setupAnimations();
    }

    private void setupAnimations() {
        mAngleObjectAnimator = ObjectAnimator.ofFloat(this, mAngleProperty, 360f);
        mAngleObjectAnimator.setInterpolator(mAngleInterpolator);
        mAngleObjectAnimator.setDuration(2000);
        mAngleObjectAnimator.setRepeatMode(ValueAnimator.RESTART);
        mAngleObjectAnimator.setRepeatCount(ValueAnimator.INFINITE);

        mSweepObjectAnimator = ObjectAnimator.ofFloat(this, mSweepProperty, 360f - MIN_SWEEP_ANGLE * 2);
        mSweepObjectAnimator.setInterpolator(mSweepInterpolator);
        mSweepObjectAnimator.setDuration(600);
        mSweepObjectAnimator.setRepeatMode(ValueAnimator.RESTART);
        mSweepObjectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mSweepObjectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                toggleAppearingMode();
            }
        });
        //mSweepObjectAnimator = ObjectAnimator.ofFloat(this, mSweepProperty, 360f - MIN_SWEEP_ANGLE * 2);

    }

    private void toggleAppearingMode() {
        mModeAppearing = !mModeAppearing;
        if (mModeAppearing) {
            mCurrentGlobalAngleOffset = (mCurrentGlobalAngleOffset + MIN_SWEEP_ANGLE * 2) % 360;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float startAngle = mCurrentGlobalAngle - mCurrentGlobalAngleOffset;
        float sweepAngle = mCurrentSweepAngle;
        if (!mModeAppearing) {
            startAngle = startAngle + sweepAngle;
            sweepAngle = 360 - sweepAngle - MIN_SWEEP_ANGLE;
        } else {
            sweepAngle += MIN_SWEEP_ANGLE;
        }
        mPaint.setColor(mBackgroundColor);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, mRadius, mPaint);
        mPaint.setColor(mProgressColor);
        canvas.drawArc(mBounds, startAngle, sweepAngle, false, mPaint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mBounds.left = w / 2 - mRadius + .5f;
        mBounds.right = w - mRadius  / 2f - mStrokeThickness - 2f;
        mBounds.top = h / 2 - mRadius + .5f;
        mBounds.bottom = h - mRadius / 2f - mStrokeThickness - 2f;
    }

    @Override
    public void start() {
        if(isRunning()) {
            return;
        }
        mRunning = true;
        mSweepObjectAnimator.start();
        mAngleObjectAnimator.start();
    }

    @Override
    public void stop() {
        if(!isRunning()) {
            return;
        }
        mRunning = false;
        mAngleObjectAnimator.cancel();
        mSweepObjectAnimator.cancel();
    }

    @Override
    public boolean isRunning() {
        return mRunning;
    }

    public void setCurrentGlobalAngle(float currentGlobalAngle) {
        mCurrentGlobalAngle = currentGlobalAngle;
        invalidate();
    }

    public float getCurrentGlobalAngle() {
        return mCurrentGlobalAngle;
    }

    public void setCurrentSweepAngle(float currentSweepAngle) {
        mCurrentSweepAngle = currentSweepAngle;
        invalidate();
    }

    public float getCurrentSweepAngle() {
        return mCurrentSweepAngle;
    }
}
