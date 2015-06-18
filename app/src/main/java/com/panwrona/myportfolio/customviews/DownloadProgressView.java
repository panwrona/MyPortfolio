package com.panwrona.myportfolio.customviews;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.renderscript.Sampler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

import com.panwrona.myportfolio.R;

public class DownloadProgressView extends View {

    private static final String TAG = DownloadProgressView.class.getSimpleName();
    private Paint mCirclePaint;
    private Paint mDrawingPaint;

    private float mRadius;
    private float mStrokeWidth;
    private float mArrowLineToDotAnimatedValue;
    private float mArrowLineToHorizontalLineAnimatedValue;
    private float mDotToProgressAnimatedValue;
    private boolean isAnimationInitialized = false;
    private AnimatorSet mArrowToLineAnimatorSet;
    private boolean hasArrowLineToDotEnded = false;
    private OvershootInterpolator mOvershootInterpolator = new OvershootInterpolator(3f);
    private ValueAnimator mDotToProgressAnimation;
    private RectF mCircleBounds;
    private float mCurrentGlobalProgressValue;
    private ValueAnimator mProgressAnimation;
    private RectF mSinusBounds;
    private Paint mTextPaint;
    private Path mSinePath;
    private ValueAnimator mSuccessAnimation;
    private float mSuccessValue;

    private enum State {ANIMATING, ANIMATING_TO_DOT, IDLE, ANIMATING_SUCCESS, ANIMATING_ERROR, ANIMATING_PROGRESS}
    private State mState;

    private ValueAnimator mArrowLineToDot;
    private ValueAnimator mArrowLineToHorizontalLine;



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
        mCirclePaint.setColor(getResources().getColor(R.color.progress_background));
        mCirclePaint.setStrokeWidth(mStrokeWidth);

        mDrawingPaint = new Paint();
        mDrawingPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mDrawingPaint.setStyle(Paint.Style.STROKE);
        mDrawingPaint.setColor(getResources().getColor(R.color.text_icon_color));
        mDrawingPaint.setStrokeWidth(mStrokeWidth);

        mTextPaint = new Paint();
        mTextPaint.setColor(getResources().getColor(R.color.text_icon_color));
        mTextPaint.setTextSize(getResources().getDimension(R.dimen.textSize));
        mTextPaint.setTextAlign(Paint.Align.CENTER);

        mSinePath = new Path();
        mState = State.IDLE;
    }

    private void setupAnimations(int width, int height) {
        mArrowLineToDot = ValueAnimator.ofFloat(0, height / 4);
        mArrowLineToDot.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mArrowLineToDotAnimatedValue = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        mArrowLineToDot.setDuration(200);
        mArrowLineToDot.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                mState = State.ANIMATING;
            }

            @Override
            public void onAnimationEnd(Animator animator) {

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        mArrowLineToDot.setInterpolator(new AccelerateInterpolator());

        mArrowLineToHorizontalLine = ValueAnimator.ofFloat(0, mRadius / 2);
        mArrowLineToHorizontalLine.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mArrowLineToHorizontalLineAnimatedValue = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        mArrowLineToHorizontalLine.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                mState = State.ANIMATING_TO_DOT;
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        mArrowLineToHorizontalLine.setDuration(600);
        mArrowLineToHorizontalLine.setStartDelay(400);
        mArrowLineToHorizontalLine.setInterpolator(mOvershootInterpolator);

        mDotToProgressAnimation = ValueAnimator.ofFloat(0, mRadius);
        mDotToProgressAnimation.setDuration(600);
        mDotToProgressAnimation.setStartDelay(600);
        mDotToProgressAnimation.setInterpolator(mOvershootInterpolator);
        mDotToProgressAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mDotToProgressAnimatedValue = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        mDotToProgressAnimation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                hasArrowLineToDotEnded = true;
                mProgressAnimation.start();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        mProgressAnimation = ValueAnimator.ofFloat(0, 360f);
        mProgressAnimation.setStartDelay(300);
        mProgressAnimation.setInterpolator(new LinearInterpolator());
        mProgressAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mCurrentGlobalProgressValue = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        mProgressAnimation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                mState = State.ANIMATING_PROGRESS;
                mDotToProgressAnimatedValue = 0;
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                mSuccessAnimation.start();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        mProgressAnimation.setDuration(1000);

        mSuccessAnimation = ValueAnimator.ofFloat(0 , mRadius / 4);
        mSuccessAnimation.setDuration(1000);
        mSuccessAnimation.setStartDelay(500);
        mSuccessAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mSuccessValue = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        mSuccessAnimation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                mState = State.ANIMATING_SUCCESS;
            }

            @Override
            public void onAnimationEnd(Animator animator) {

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        mArrowToLineAnimatorSet = new AnimatorSet();
        mArrowToLineAnimatorSet.playTogether(mArrowLineToDot, mArrowLineToHorizontalLine, mDotToProgressAnimation);
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
//        canvas.drawCircle(getWidth() / 2, getHeight() / 2, mRadius, mCirclePaint);
//        if(mState == State.IDLE) {
//            canvas.drawLine(getWidth() / 2, getHeight() / 4, getWidth() / 2, getHeight() - getHeight() / 4, mDrawingPaint);
//            canvas.drawLine(getWidth() / 4, getHeight() / 2, getWidth() / 2, getHeight() - getHeight() / 4, mDrawingPaint);
//            canvas.drawLine(getWidth() / 2, getHeight() - getHeight() / 4, getWidth() - getWidth() / 4, getHeight() / 2, mDrawingPaint);
//        } else if(mState == State.ANIMATING) {
//            canvas.drawLine(getWidth() / 2, getHeight() / 4 + mArrowLineToDotAnimatedValue - mStrokeWidth, getWidth() / 2, getHeight() - getHeight() / 4 - mArrowLineToDotAnimatedValue, mDrawingPaint);
//            canvas.drawLine(getWidth() / 4 - mArrowLineToHorizontalLineAnimatedValue / 2, getHeight() / 2, getWidth() / 2, getHeight() - getHeight() / 4 - mArrowLineToHorizontalLineAnimatedValue, mDrawingPaint);
//            canvas.drawLine(getWidth() / 2, getHeight() - getHeight() / 4 - mArrowLineToHorizontalLineAnimatedValue, getWidth() - getWidth() / 4 + mArrowLineToHorizontalLineAnimatedValue / 2, getHeight() / 2, mDrawingPaint);
//            Log.d(TAG, "current val: " + (getHeight() / 2 - mArrowLineToDotAnimatedValue) + ", overshoot interpolation: " + (getHeight() / 2 - mOvershootInterpolator.getInterpolation(3f)));
//            if(getHeight() / 2 - mArrowLineToHorizontalLineAnimatedValue < getHeight() / 2 - mOvershootInterpolator.getInterpolation(3f)) {
//                mDotToProgressAnimation.start();
//            }
//        }
//        if(mDotToProgressAnimatedValue > 0) {
//            canvas.drawLine(getWidth() / 2 - mStrokeWidth / 2, getHeight() / 2 - mDotToProgressAnimatedValue - mStrokeWidth, getWidth() / 2 + mStrokeWidth / 2, getHeight() / 2 - mDotToProgressAnimatedValue, mDrawingPaint);
//        }

        canvas.drawCircle(getWidth() / 2, getHeight() / 2, mRadius, mCirclePaint);
        if(mState == State.IDLE) {
            canvas.drawLine(getWidth() / 2, getHeight() / 2 - mRadius / 2, getWidth() / 2, getHeight() / 2 + mRadius / 2, mDrawingPaint);
            canvas.drawLine(getWidth() / 2 - mRadius / 2, getHeight() / 2, getWidth() / 2, getHeight() / 2 + mRadius / 2, mDrawingPaint);
            canvas.drawLine(getWidth() / 2, getHeight() / 2 + mRadius / 2, getWidth() / 2 + mRadius / 2, getHeight() / 2, mDrawingPaint);
        } else if(mState == State.ANIMATING) {
            if(!mDotToProgressAnimation.isRunning()) {
                canvas.drawLine(getWidth() / 2, getHeight() / 2 - mRadius / 2 + mArrowLineToDotAnimatedValue / 2, getWidth() / 2, getHeight() / 2 + mRadius / 2 - mArrowLineToDotAnimatedValue, mDrawingPaint);
            }
            canvas.drawLine(getWidth() / 2 - mRadius / 2 - mArrowLineToHorizontalLineAnimatedValue / 2, getHeight() / 2, getWidth() / 2, getHeight() / 2 + mRadius / 2 - mArrowLineToHorizontalLineAnimatedValue, mDrawingPaint);
            canvas.drawLine(getWidth() / 2, getHeight() / 2 + mRadius / 2 - mArrowLineToHorizontalLineAnimatedValue, getWidth() / 2 + mRadius / 2 + mArrowLineToHorizontalLineAnimatedValue / 2, getHeight() / 2, mDrawingPaint);
        } else if(mState == State.ANIMATING_PROGRESS) {
            canvas.drawArc(mCircleBounds, -90, mCurrentGlobalProgressValue, false, mDrawingPaint);
            canvas.drawText(mProgressAnimation.getCurrentPlayTime() % 1000 + "." + mProgressAnimation.getCurrentPlayTime() % 100, getWidth() / 2, getHeight() / 2 + mRadius / 2, mTextPaint);
                        //canvas.drawLine(getWidth() / 2 - mRadius / 2 - mArrowLineToHorizontalLineAnimatedValue / 2, getHeight() / 2, getWidth() / 2 - mRadius / 2 - mArrowLineToHorizontalLineAnimatedValue / 2 + mCurrentGlobalProgressValue,getHeight () / 2 + (float) Math.sin(mCurrentGlobalProgressValue) + mStrokeWidth, mDrawingPaint );canvas.drawLine(getWidth() / 2 - mRadius / 2 - mArrowLineToHorizontalLineAnimatedValue / 2, getHeight() / 2, getWidth() / 2 + mRadius / 2 + mArrowLineToHorizontalLineAnimatedValue / 2, getHeight() / 2, mDrawingPaint);
            canvas.drawLine(getWidth() / 2 - mRadius / 2 - mArrowLineToHorizontalLineAnimatedValue / 2 , getHeight() / 2, getWidth() / 2 + mRadius / 2 + mArrowLineToHorizontalLineAnimatedValue / 2, getHeight() / 2, mDrawingPaint);

        } else if(mState == State.ANIMATING_SUCCESS) {
            canvas.drawArc(mCircleBounds, 0, 360, false, mDrawingPaint);
            canvas.drawLine(getWidth() / 2 - mRadius / 2 - mArrowLineToHorizontalLineAnimatedValue / 2 + mSuccessValue * 2, getHeight() / 2, getWidth() / 2, getHeight() / 2 + mSuccessValue * (3/2), mDrawingPaint);
            canvas.drawLine(getWidth() / 2 + mRadius / 2 + mArrowLineToHorizontalLineAnimatedValue / 2 - mSuccessValue * 3, getHeight() / 2 + mSuccessValue * (3/2), getWidth() / 2 + mSuccessValue * 2, getHeight() / 2 - mSuccessValue, mDrawingPaint);
        } else if(mState == State.ANIMATING_ERROR) {
            canvas.drawArc(mCircleBounds, 0, 360, false, mDrawingPaint);

        }

        if(mDotToProgressAnimatedValue > 0) {
            canvas.drawLine(getWidth() / 2 - mStrokeWidth / 2, getHeight() / 2 - mDotToProgressAnimatedValue - mStrokeWidth, getWidth() / 2 + mStrokeWidth / 2, getHeight() / 2 - mDotToProgressAnimatedValue, mDrawingPaint);
        }

        if(mDotToProgressAnimation.isRunning() && !mArrowLineToHorizontalLine.isRunning()) {
            canvas.drawLine(getWidth() / 2 - mRadius / 2 - mArrowLineToHorizontalLineAnimatedValue / 2, getHeight() / 2, getWidth() / 2 + mRadius / 2 + mArrowLineToHorizontalLineAnimatedValue / 2, getHeight() / 2, mDrawingPaint);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCircleBounds = new RectF();
        mCircleBounds.top = h / 2f  - mRadius;
        mCircleBounds.left = w / 2f - mRadius;
        mCircleBounds.bottom = h / 2f + mRadius;
        mCircleBounds.right = w / 2f + mRadius;

        mSinusBounds = new RectF();
        mSinusBounds.top = h / 2f - mRadius / 2f;
        mSinusBounds.left = w / 2f - mRadius + 12;
        mSinusBounds.bottom = h / 2f + mRadius / 2f;
        mSinusBounds.right = w / 2f + mRadius - 12;

        if(!isAnimationInitialized) {
            setupAnimations(w, h);
            isAnimationInitialized = true;
        }
    }

    public void play() {
        mArrowToLineAnimatorSet.start();
        invalidate();
    }
}
