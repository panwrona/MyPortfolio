package com.panwrona.myportfolio.customviews.skills_level_view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import com.panwrona.myportfolio.R;
import com.panwrona.myportfolio.customviews.skills_level_view.entities.Skill;
import java.util.ArrayList;
import java.util.List;

public class SkillsLevelView extends View {
	private static final int DEFAULT_SKILL_SCALE = 8;

	private Paint backgroundPaint;
	private Paint foregroundPaint;
	private Paint textPaint;

	private List<Skill> skillList = new ArrayList<>(5);
	private List<Animator> showUpAnimatorList = new ArrayList<>();
	private AnimatorSet setSkillAnimatorSet = new AnimatorSet();

	private int skillScale;
	private int currentSkill;

	private float radius;
	private float mMeasureText = 0;

	private boolean hasAnimationStarted = false;
	private float showUpZValue;
	private float showUpYValue;
	private int repeatCount;

	private enum State {
		IDLE,
		ANIMATING,
		ANIMATED
	}

	private State state;

	public SkillsLevelView(Context context) {
		super(context);
	}

	public SkillsLevelView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initViews(context, attrs);
		isInEditMode(); //TODO: remove before pushing to store
	}

	private void initViews(Context context, AttributeSet attrs) {
		int backgroundColor, foregroundColor, textColor;
		float textSize;

		TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SkillsLevelView);
		try {
			backgroundColor = array.getColor(R.styleable.SkillsLevelView_backgroundColor,
				getBackgroundColor(context));
			foregroundColor = array.getColor(R.styleable.SkillsLevelView_foregroundColor,
				getForegroundColor(context));
			textColor =
				array.getColor(R.styleable.SkillsLevelView_textColor, getTextColor(context));
			textSize =
				array.getDimension(R.styleable.SkillsLevelView_textSize, getTextSize(context));
			skillScale =
				array.getInteger(R.styleable.SkillsLevelView_skillScale, DEFAULT_SKILL_SCALE);
			radius = array.getDimension(R.styleable.SkillsLevelView_radius, 0);
		} finally {
			array.recycle();
		}
		repeatCount = skillScale - 1;
		backgroundPaint = new Paint();
		foregroundPaint = new Paint();
		textPaint = new Paint();
		textPaint.setColor(foregroundColor);
		textPaint.setTextSize(textSize);
		Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Light.ttf");
		textPaint.setTypeface(typeface);
		backgroundPaint.setColor(backgroundColor);
		foregroundPaint.setColor(foregroundColor);

		textPaint.setAntiAlias(true);
		backgroundPaint.setAntiAlias(true);
		foregroundPaint.setAntiAlias(true);

		setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		state = State.IDLE;
	}

	private void setupAnimations(List<Skill> skillList) {
		ObjectAnimator fadeInAnimator = ObjectAnimator.ofFloat(this, View.ALPHA, 0, 1);
		fadeInAnimator.setDuration(600);
		fadeInAnimator.setInterpolator(new LinearInterpolator());
		for (Skill skill : skillList) {
			skill.setTextWidth(textPaint.measureText(skill.getName()));
			if (textPaint.measureText(skill.getName()) > mMeasureText) {
				mMeasureText = textPaint.measureText(skill.getName());
			}
		}
		ObjectAnimator showUpZAnimator =
			ObjectAnimator.ofFloat(this, View.TRANSLATION_Z, 0, radius);
		showUpZAnimator.addUpdateListener(animation -> {
			showUpZValue = (float) animation.getAnimatedValue();
			invalidate();
		});
		showUpZAnimator.setRepeatCount(skillScale);
		showUpZAnimator.setDuration(300);
		showUpZAnimator.setStartDelay(50);
		showUpZAnimator.setRepeatMode(ObjectAnimator.RESTART);
		showUpZAnimator.setInterpolator(new OvershootInterpolator(3.5f));
		showUpZAnimator.addListener(new Animator.AnimatorListener() {
			@Override
			public void onAnimationStart(Animator animation) {
				state = State.ANIMATING;
			}

			@Override
			public void onAnimationEnd(Animator animation) {

			}

			@Override
			public void onAnimationCancel(Animator animation) {

			}

			@Override
			public void onAnimationRepeat(Animator animation) {
				repeatCount--;
				invalidate();
			}
		});
		fadeInAnimator.addListener(new Animator.AnimatorListener() {
			@Override public void onAnimationStart(Animator animation) {

			}

			@Override public void onAnimationEnd(Animator animation) {
				hasAnimationStarted = true;
				showUpZAnimator.start();
			}

			@Override public void onAnimationCancel(Animator animation) {

			}

			@Override public void onAnimationRepeat(Animator animation) {

			}
		});
		post(fadeInAnimator::start);
	}

	private int getForegroundColor(Context context) {
		return ContextCompat.getColor(context, R.color.text_icon_color);
	}

	private int getBackgroundColor(Context context) {
		return ContextCompat.getColor(context, R.color.skills_level_view_background);
	}

	private float getTextSize(Context context) {
		return context.getResources().getDimension(R.dimen.skill_level_text_size);
	}

	private int getTextColor(Context context) {
		return ContextCompat.getColor(context, R.color.text_icon_color);
	}

	public void setSkillsList(List<Skill> skillList) {
		this.skillList = skillList;
		setupAnimations(skillList);
	}

	@Override protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		for (int j = 0; j < skillList.size(); j++) {
			for (int i = 0; i < skillScale; i++) {
				canvas.drawCircle(mMeasureText * j + mMeasureText / 2, radius + radius * 2.5f * i,
					radius, backgroundPaint);
			}
			canvas.drawText(skillList.get(j).getName(),
				mMeasureText * j + mMeasureText / 2 - skillList.get(j).getTextWidth() / 2,
				radius * 2 + radius * 2.5f * skillScale, textPaint);
		}
		if (state == State.ANIMATING) {
			for (int j = 0; j < skillList.size(); j++) {
				if (skillList.get(j).getLevel() <= repeatCount) {
					canvas.drawCircle(mMeasureText * j + mMeasureText / 2,
						radius + radius * 2.5f * repeatCount, showUpZValue, foregroundPaint);
				}
			}
		}
		if (repeatCount < skillScale - 1) {
			for (int j = 0; j < skillList.size(); j++) {
				for (int i = skillScale - 1; i > repeatCount - 1; i--) {
					if (skillList.get(j).getLevel() <= i) {
						canvas.drawCircle(mMeasureText * j + mMeasureText / 2,
							radius + radius * 2.5f * i, radius, foregroundPaint);
					}
				}
			}
		}
	}
}
