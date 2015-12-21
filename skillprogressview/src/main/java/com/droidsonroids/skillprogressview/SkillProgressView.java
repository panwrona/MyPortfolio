package com.droidsonroids.skillprogressview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
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
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

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
	private RectF mRectF;
	private Path mPath = new Path();
	private int currentSkillScalePoint = 1;
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
		mState = State.State_IDLE;

		mBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mBackgroundPaint.setStyle(Paint.Style.STROKE);
		mBackgroundPaint.setColor(backgroundColor);
		mBackgroundPaint.setStrokeWidth(strokeWidth);

		mForegroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mForegroundPaint.setColor(foregroundColor);
		mForegroundPaint.setStrokeWidth(strokeWidth);
		mForegroundPaint.setStyle(Paint.Style.STROKE);
		initAnimations();
	}

	private void initAnimations() {
		int skillLevel = (360 / mSkillScale) * mSkillLevel;
		mProgressAnimation = ValueAnimator.ofInt(0, skillLevel);
		mProgressAnimation.setDuration(300);
		mProgressAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
		mProgressAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				mAnimatedValue = (int) animation.getAnimatedValue();
				if (mAnimatedValue == (360 / mSkillScale) * currentSkillScalePoint) {
					currentSkillScalePoint++;
				}
				invalidate();
			}
		});
		mProgressAnimation.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationRepeat(Animator animation) {
				super.onAnimationRepeat(animation);
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
		mRectF.bottom = h / 2f - mRadius;
		mRectF.right = w / 2f - mRadius;
		mRectF.top = h / 2f + mRadius;
		mRectF.left = w / 2f + mRadius;

		for (int i = 0; i < mSkillScale; i++) {
			int scalePoint = (360 / mSkillScale) * i;
			mPath.addArc(mRectF, scalePoint - 5, scalePoint + 5);
		}

		//mBounds.top = h / 2f - mRadius;
		//mBounds.left = w / 2f - mRadius;
		//mBounds.bottom = h / 2f + mRadius;
		//mBounds.right = w / 2f + mRadius;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawCircle(mWidth / 2, mHeight / 2, mRadius, mBackgroundPaint);
		if (mState == State.STATE_RUNNING) {
			if (currentSkillScalePoint > 0) {
				canvas.drawArc(mRectF, ((360 / mSkillScale) * (currentSkillScalePoint - 1)) - 5,
					((360 / mSkillScale) * currentSkillScalePoint), false, mForegroundPaint);
			}
		}
	}
}
