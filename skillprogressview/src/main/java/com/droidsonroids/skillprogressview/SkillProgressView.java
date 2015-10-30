package com.droidsonroids.skillprogressview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Property;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import java.util.ArrayList;
import java.util.List;

public class SkillProgressView extends View {
	private static final int DEFAULT_SKILL_SCALE = 8;
	private static final int DEFAULT_SKILL_LEVEL = 4;
	private static final String TAG = SkillProgressView.class.getSimpleName();

	private Paint mBackgroundPaint;
	private Paint mForegroundPaint;

	private int mSkillScale;
	private int mSkillLevel;
	private int mRadius;
	private int mWidth;
	private int mHeight;
	private boolean areDimensInitialized = false;
	private State mState;
	private int mAnimatedValue;
	private int[] currentSkillScalePoints;
	private int currentSkillScale = 0;
	private RectF mRectF;
	private Path mPath = new Path();
	private ValueAnimator mProgressAnimation;

	public void startAnimating() {
		mProgressAnimation.start();
	}

	private enum State {
		State_IDLE,
		STATE_RUNNING,
	}

	public SkillProgressView(Context context) {
		this(context, null);
	}

	public SkillProgressView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	private void init(Context context, AttributeSet attrs) {
		int backgroundColor;
		int foregroundColor;
		int strokeWidth;

		TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SkillProgressView);
		try {
			backgroundColor = array.getColor(R.styleable.SkillProgressView_spv_backgroundColor,
				getDefaultColor(context, R.color.skill_progress_background_color));
			foregroundColor = array.getColor(R.styleable.SkillProgressView_spv_foregroundColor,
				getDefaultColor(context, R.color.skill_progress_foreground_color));
			strokeWidth = array.getDimensionPixelSize(R.styleable.SkillProgressView_spv_strokeWidth,
				getDefaultDimension(context, R.dimen.skill_progress_stroke_width));
			mRadius = array.getDimensionPixelSize(R.styleable.SkillProgressView_spv_radius,
				getDefaultDimension(context, R.dimen.skill_progress_radius));
			mSkillScale =
				array.getInt(R.styleable.SkillProgressView_spv_skillScale, DEFAULT_SKILL_SCALE);
			mSkillLevel =
				array.getInt(R.styleable.SkillProgressView_spv_skillLevel, DEFAULT_SKILL_LEVEL);
		} finally {
			array.recycle();
		}

		currentSkillScalePoints = new int[mSkillScale + 1];

		mState = State.State_IDLE;

		mBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mBackgroundPaint.setStyle(Paint.Style.STROKE);
		mBackgroundPaint.setColor(backgroundColor);
		mBackgroundPaint.setStrokeWidth(strokeWidth);

		mForegroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mForegroundPaint.setColor(foregroundColor);
		mForegroundPaint.setStrokeWidth(strokeWidth);
		mForegroundPaint.setStyle(Paint.Style.STROKE);

		for(int i = 0; i <= mSkillScale; i++) {
			currentSkillScalePoints[i] = (360 / mSkillScale) * i;
		}
		initAnimations();
	}

	private void initAnimations() {
		 mProgressAnimation =
			ValueAnimator.ofInt(currentSkillScalePoints[currentSkillScale],
				currentSkillScalePoints[currentSkillScale + 1]);
		mProgressAnimation.setDuration(300);
		mProgressAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
		mProgressAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				mAnimatedValue = (int) animation.getAnimatedValue();
				invalidate();
			}
		});
		mProgressAnimation.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationRepeat(Animator animation) {
				super.onAnimationRepeat(animation);
				currentSkillScale++;
				mProgressAnimation.setIntValues(currentSkillScalePoints[currentSkillScale], currentSkillScalePoints[currentSkillScale + 1]);
				invalidate();
			}

			@Override
			public void onAnimationStart(Animator animation) {
				super.onAnimationStart(animation);
				mState = State.STATE_RUNNING;
			}

			@Override
			public void onAnimationEnd(Animator animation) {
				super.onAnimationEnd(animation);
			}
		});
		mProgressAnimation.setRepeatCount(mSkillLevel);
	}

	private int getDefaultDimension(Context context, @DimenRes int dimenRes) {
		return context.getResources().getDimensionPixelSize(dimenRes);
	}

	private int getDefaultColor(Context context, @ColorRes int colorRes) {
		return ContextCompat.getColor(context, colorRes);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		if (!areDimensInitialized) {
			mWidth = w;
			mHeight = h;
			areDimensInitialized = true;
		}
		mRectF = new RectF();
		mRectF.bottom = h - mRadius;
		mRectF.right = w - mRadius;
		mRectF.top = mRadius;
		mRectF.left = mRadius;

		for(int i = 0; i < mSkillScale; i++) {
			mPath.addArc(mRectF, currentSkillScalePoints[i] + 2, currentSkillScalePoints[i+1] -2);
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawCircle(mWidth / 2, mHeight / 2, mRadius, mBackgroundPaint);
		if (mState == State.STATE_RUNNING) {
			Log.d(TAG, "Animated val: "
				+ mAnimatedValue
				+ ", current point: "
				+ currentSkillScalePoints[currentSkillScale]
				+ ", plus ten: "
				+ (currentSkillScalePoints[currentSkillScale] + 10));
			if(mAnimatedValue <= currentSkillScalePoints[currentSkillScale] + 10) {
				canvas.drawPath(mPath, mForegroundPaint);
				//canvas.drawArc(mRectF, currentSkillScalePoints[currentSkillScale], mAnimatedValue, false, mBackgroundPaint);
			} else {
				//canvas.drawArc(mRectF, currentSkillScalePoints[currentSkillScale], mAnimatedValue, false, mForegroundPaint);
			}
			canvas.save();
		}
	}
}
