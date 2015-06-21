package com.panwrona.myportfolio.customviews;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
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

    private float mCenterX;
    private float mCenterY;
    private float mPaddingX;
    private float mPaddingY;

    private boolean isAnimationInitialized = false;
    private AnimatorSet mArrowToLineAnimatorSet;
    private boolean hasArrowLineToDotEnded = false;
    private OvershootInterpolator mOvershootInterpolator;
    private ValueAnimator mDotToProgressAnimation;
    private RectF mCircleBounds;
    private float mCurrentGlobalProgressValue;
    private ValueAnimator mProgressAnimation;
    private Paint mTextPaint;
    private ValueAnimator mSuccessAnimation;
    private float mSuccessValue;
    private Paint mProgressPaint;
    private Paint mProgressBackgroundPaint;
    private ValueAnimator mExpandAnimation;
    private float mExpandCollapseValue;
    private AnimatorSet mProgressAnimationSet;
    private ValueAnimator mCollapseAnimation;
    private float mErrorValue;
    private ValueAnimator mErrorAnimation;
    private String mProgressText = "Progress Text";
    private OnProgressUpdateListener mOnProgressUpdateListener;

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

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.DownloadProgressView, 0, 0);
        try {
            mRadius = array.getDimension(R.styleable.DownloadProgressView_circleRadius, 0);
            mStrokeWidth = array.getDimension(R.styleable.DownloadProgressView_strokeWidth, 0);
        } finally {
            array.recycle();
        }
    }

    private void init(Context context) {
        mCirclePaint = new Paint();
        mCirclePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setColor(getColor(R.color.progress_background));
        mCirclePaint.setStrokeWidth(mStrokeWidth);

        mDrawingPaint = new Paint();
        mDrawingPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mDrawingPaint.setStyle(Paint.Style.STROKE);
        mDrawingPaint.setColor(getColor(R.color.text_icon_color));
        mDrawingPaint.setStrokeWidth(mStrokeWidth);

        mTextPaint = new Paint();
        mTextPaint.setColor(getColor(R.color.dark_primary_color));
        mTextPaint.setTextSize(getResources().getDimension(R.dimen.textSize));
        mTextPaint.setTextAlign(Paint.Align.CENTER);

        mProgressPaint = new Paint();
        mProgressPaint.setColor(getColor(R.color.text_icon_color));
        mProgressPaint.setStyle(Paint.Style.FILL);

        mProgressBackgroundPaint = new Paint();
        mProgressBackgroundPaint.setColor(getColor(R.color.light_primary_color));
        mProgressBackgroundPaint.setStyle(Paint.Style.FILL);

        mState = State.IDLE;
    }

    private int getColor(int resourceId) {
        return getResources().getColor(resourceId);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mCenterX = w / 2f;
        mCenterY = h / 2f;
        mPaddingX = w / 2f - mRadius;
        mPaddingY = h / 2f - mRadius;

        mCircleBounds = new RectF();
        mCircleBounds.top = mPaddingY;
        mCircleBounds.left = mPaddingX;
        mCircleBounds.bottom = h / 2f + mRadius;
        mCircleBounds.right = w / 2f + mRadius;
        if (!isAnimationInitialized) {
            setupAnimations();
            isAnimationInitialized = true;
        }
    }

    private void setupAnimations() {
        mOvershootInterpolator  = new OvershootInterpolator(2.5f);
        mArrowLineToDot = ValueAnimator.ofFloat(0, mRadius / 4);
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
                mProgressAnimationSet.start();
                mState = State.ANIMATING_PROGRESS;

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        mProgressAnimation = ValueAnimator.ofFloat(0, 360f);
        mProgressAnimation.setStartDelay(500);
        mProgressAnimation.setInterpolator(new LinearInterpolator());
        mProgressAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mCurrentGlobalProgressValue = (float) valueAnimator.getAnimatedValue();
                if(mOnProgressUpdateListener != null) {
                    mOnProgressUpdateListener.onProgressUpdate(mCurrentGlobalProgressValue);
                }
                invalidate();
            }
        });
        mProgressAnimation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                mDotToProgressAnimatedValue = 0;
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
        mProgressAnimation.setDuration(1000);

        mExpandAnimation = ValueAnimator.ofFloat(0 , mRadius / 4);
        mExpandAnimation.setDuration(300);
        mExpandAnimation.setInterpolator(new DecelerateInterpolator());
        mExpandAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mExpandCollapseValue = (float) animation.getAnimatedValue();
                invalidate();
            }
        });

        mCollapseAnimation = ValueAnimator.ofFloat(mRadius / 4, mStrokeWidth / 2);
        mCollapseAnimation.setDuration(300);
        mCollapseAnimation.setStartDelay(300);
        mCollapseAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        mCollapseAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mExpandCollapseValue = (float) animation.getAnimatedValue();
                invalidate();
            }
        });

        mProgressAnimationSet = new AnimatorSet();
        mProgressAnimationSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mSuccessAnimation.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        mProgressAnimationSet.playSequentially(mExpandAnimation, mProgressAnimation, mCollapseAnimation);

        mErrorAnimation = ValueAnimator.ofFloat(0, mRadius / 4);
        mErrorAnimation.setDuration(600);
        mErrorAnimation.setStartDelay(500);
        mErrorAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        mErrorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mErrorValue = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        mErrorAnimation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                mState = State.ANIMATING_ERROR;
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mState = State.IDLE;
                        resetValues();
                        invalidate();
                    }
                }, 500);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        mSuccessAnimation = ValueAnimator.ofFloat(0, mRadius / 4);
        mSuccessAnimation.setDuration(600);
        mSuccessAnimation.setStartDelay(500);
        mSuccessAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
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
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mState = State.IDLE;
                        resetValues();
                        invalidate();
                    }
                }, 500);
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

    private void resetValues() {
        mArrowLineToDotAnimatedValue = 0;
        mArrowLineToHorizontalLineAnimatedValue = 0;
        mCurrentGlobalProgressValue = 0;
    }

    private void drawing(Canvas canvas) {
        canvas.drawCircle(mCenterX, mCenterY, mRadius, mCirclePaint);
        switch (mState) {
            case IDLE:
                canvas.drawLine(mCenterX, mCenterY - mRadius / 2, mCenterX, mCenterY + mRadius / 2, mDrawingPaint);
                canvas.drawLine(mCenterX - mRadius / 2, mCenterY, mCenterX, mCenterY + mRadius / 2, mDrawingPaint);
                canvas.drawLine(mCenterX, mCenterY + mRadius / 2, mCenterX + mRadius / 2, mCenterY, mDrawingPaint);
                break;
            case ANIMATING:
                if (!mDotToProgressAnimation.isRunning()) {
                    canvas.drawLine(
                            mCenterX,
                            mCenterY - mRadius / 2 + mArrowLineToDotAnimatedValue * 2 - mStrokeWidth / 2,
                            mCenterX,
                            mCenterY + mRadius / 2 - mArrowLineToDotAnimatedValue * 2 + mStrokeWidth / 2,
                            mDrawingPaint
                    );
                }
                canvas.drawLine(
                        mCenterX - mRadius / 2 - mArrowLineToHorizontalLineAnimatedValue / 2,
                        mCenterY,
                        mCenterX,
                        mCenterY + mRadius / 2 - mArrowLineToHorizontalLineAnimatedValue,
                        mDrawingPaint
                );
                canvas.drawLine(
                        mCenterX,
                        mCenterY + mRadius / 2 - mArrowLineToHorizontalLineAnimatedValue,
                        mCenterX + mRadius / 2 + mArrowLineToHorizontalLineAnimatedValue / 2,
                        mCenterY,
                        mDrawingPaint
                );
                break;
            case ANIMATING_PROGRESS:
                float progress = ((mCenterX + mRadius / 2 + mArrowLineToHorizontalLineAnimatedValue / 2) - (mCenterX - mRadius / 2 - mArrowLineToHorizontalLineAnimatedValue / 2)) / 360f;
                canvas.drawArc(mCircleBounds, -90, mCurrentGlobalProgressValue, false, mDrawingPaint);
                canvas.drawRect(
                        mCenterX - mRadius / 2 - mArrowLineToHorizontalLineAnimatedValue / 2,
                        mCenterY - mExpandCollapseValue,
                        mCenterX + mRadius / 2 + mArrowLineToHorizontalLineAnimatedValue / 2,
                        mCenterY + mExpandCollapseValue,
                        mProgressBackgroundPaint
                );
                canvas.drawRect(
                        mCenterX - mRadius / 2 - mArrowLineToHorizontalLineAnimatedValue / 2,
                        mCenterY - mExpandCollapseValue,
                        mCenterX - mRadius / 2 - mArrowLineToHorizontalLineAnimatedValue / 2 + progress * mCurrentGlobalProgressValue,
                        mCenterY + mExpandCollapseValue,
                        mProgressPaint
                );
                if(mProgressAnimation.isRunning()) {
                    canvas.drawText(
                            mProgressText,
                            mCenterX,
                            mCenterY + mTextPaint.getTextSize() / 3f,
                            mTextPaint
                    );
                }
                break;
            case ANIMATING_SUCCESS:
                canvas.drawArc(mCircleBounds, 0, 360, false, mDrawingPaint);
                canvas.drawLine(
                        mCenterX - mRadius / 2 + mSuccessValue * 2 - mSuccessValue / (float)Math.sqrt(2f) / 2,
                        mCenterY - mRadius / 2 + mSuccessValue * 2 + mSuccessValue,
                        mCenterX + mSuccessValue * 2 - mSuccessValue / (float)Math.sqrt(2f) / 2,
                        mCenterY - mSuccessValue * 2 + mSuccessValue,
                        mDrawingPaint
                );
                canvas.drawLine(
                        mCenterX - mSuccessValue - 2*mSuccessValue / (float)Math.sqrt(2f) / 2,
                        mCenterY - mSuccessValue + mSuccessValue,
                        mCenterX + mRadius / 2 - mSuccessValue * 2 - mSuccessValue / (float)Math.sqrt(2f) / 2,
                        mCenterY + mRadius /2 - mSuccessValue * 2 + mSuccessValue,
                        mDrawingPaint
                );
                break;
            case ANIMATING_ERROR:
                canvas.drawArc(mCircleBounds, 0, 360, false, mDrawingPaint);

                canvas.drawLine(
                        mCenterX - mRadius / 2 - mRadius / 4 + mErrorValue * 2,
                        mCenterY + mErrorValue,
                        mCenterX + mErrorValue,
                        mCenterY - mErrorValue,
                        mDrawingPaint
                );
                canvas.drawLine(
                        mCenterX - mErrorValue,
                        mCenterY - mErrorValue,
                        mCenterX + mRadius / 2 + mRadius / 4 - mErrorValue * 2,
                        mCenterY + mErrorValue,
                        mDrawingPaint
                );
                break;
        }
        if (mDotToProgressAnimatedValue > 0) {
            canvas.drawCircle(
                    mCenterX,
                    mCenterY - mDotToProgressAnimatedValue,
                    mStrokeWidth / 2,
                    mDrawingPaint
            );
        }

        if (mDotToProgressAnimation.isRunning() && !mArrowLineToHorizontalLine.isRunning()) {
            canvas.drawLine(
                    mCenterX - mRadius / 2 - mArrowLineToHorizontalLineAnimatedValue / 2,
                    mCenterY,
                    mCenterX + mRadius / 2 + mArrowLineToHorizontalLineAnimatedValue / 2,
                    mCenterY,
                    mDrawingPaint
            );
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawing(canvas);
    }

    public void play() {
        mArrowToLineAnimatorSet.start();
        invalidate();
    }

    public interface OnProgressUpdateListener {
        void onProgressUpdate(float currentPlayTime);
    }

    public void setOnProgressUpdateListener(OnProgressUpdateListener listener) {
        mOnProgressUpdateListener = listener;
    }

    public void setProgressText(String progressText) {
        mProgressText = progressText;
    }
}
