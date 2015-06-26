package com.panwrona.myportfolio.customviews;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Animatable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Property;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.panwrona.myportfolio.R;

import java.io.File;
import java.nio.MappedByteBuffer;

public class ProgressPhotoView extends ImageView implements Animatable {

    private final static int MIN_SWEEP_ANGLE = 30;
    private static final String TAG = ProgressPhotoView.class.getSimpleName();
    private final LinearInterpolator mAngleInterpolator = new LinearInterpolator();
    private final DecelerateInterpolator mSweepInterpolator = new DecelerateInterpolator();
    private final RectF mBounds = new RectF();
    private float mStrokeThickness;
    private float mCurrentGlobalAngle;
    private float mCurrentSweepAngle;
    private float mRadius;
    private float currentGlobalProgress = -1f;
    private int mProgressColor;
    private int mCurrentGlobalAngleOffset;
    private int mBackgroundColor;
    private boolean mRunning;
    private boolean mModeAppearing;

    private Bitmap mPhotoBitmap;

    private Paint mPaint;
    private ObjectAnimator mAngleObjectAnimator;
    private ObjectAnimator mSweepObjectAnimator;
    private ValueAnimator mProgressValueAnimator;

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
    private Property<ProgressPhotoView, Float> mProgressProperty = new Property<ProgressPhotoView, Float>(Float.class, "progress") {
        @Override
        public Float get(ProgressPhotoView object) {
            return object.getCurrentGlobalProgress();
        }

        @Override
        public void set(ProgressPhotoView object, Float value) {
            object.setCurrentGlobalProgress(value);
        }
    };
    private boolean isBitmapSet = false;

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
            mProgressColor = array.getColor(R.styleable.ProgressPhotoView_progresssColor, 0);
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
        mPhotoBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.portoflio);
        setupAnimations();
    }

    private void setupAnimations() {
        mAngleObjectAnimator = ObjectAnimator.ofFloat(this, mAngleProperty, 360f);
        mAngleObjectAnimator.setInterpolator(mAngleInterpolator);
        mAngleObjectAnimator.setDuration(1800);
        mAngleObjectAnimator.setRepeatMode(ValueAnimator.RESTART);
        mAngleObjectAnimator.setRepeatCount(1);
        mAngleObjectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mSweepObjectAnimator.cancel();
                mProgressValueAnimator.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        mProgressValueAnimator = ValueAnimator.ofInt(0, 360);
        mProgressValueAnimator.setInterpolator(mAngleInterpolator);
        mProgressValueAnimator.setDuration(1800);
        mProgressValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                currentGlobalProgress = (int) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        mProgressValueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                setImageBitmap(transformBitmapIntoCircle(mPhotoBitmap));
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

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
        if(currentGlobalProgress < 0) {
            canvas.drawArc(mBounds, startAngle, sweepAngle, false, mPaint);
        } else if(currentGlobalProgress == 360) {
            canvas.drawArc(mBounds, 0, 360, false, mPaint);
        } else {
            canvas.drawArc(mBounds, -120, MIN_SWEEP_ANGLE, false, mPaint);
            canvas.drawArc(mBounds, -90, currentGlobalProgress, false, mPaint);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mBounds.top = h / 2f  - mRadius;
        mBounds.left = w / 2f - mRadius;
        mBounds.bottom = h / 2f + mRadius;
        mBounds.right = w / 2f + mRadius;

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

    private Bitmap transformBitmapIntoCircle(Bitmap source) {
        int size = Math.min(source.getWidth(), source.getHeight());

        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;

        Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
        if (squaredBitmap != source) {
            source.recycle();
        }

        Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        BitmapShader shader = new BitmapShader(squaredBitmap,
                BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setAntiAlias(true);

        float r = size / 2f;
        canvas.drawCircle(r, r, r - mStrokeThickness*2, paint);

        squaredBitmap.recycle();
        return bitmap;
    };

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

    public float getCurrentGlobalProgress() {
        return currentGlobalProgress;
    }

    public void setCurrentGlobalProgress(float currentGlobalProgress) {
        this.currentGlobalProgress = currentGlobalProgress;
    }
}
