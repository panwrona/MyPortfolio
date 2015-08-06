package com.panwrona.myportfolio.customviews.skills_level_view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import com.panwrona.myportfolio.R;
import com.panwrona.myportfolio.customviews.skills_level_view.entities.Skill;
import java.util.ArrayList;
import java.util.List;

public class SkillsLevelView extends View {
	private static final int DEFAULT_SKILL_SCALE = 8;

	private Paint backgroundPaint;
	private Paint foregroundPaint;
	private Paint textPaint;

	private float radius;
	private List<Skill> skillList = new ArrayList<>();
	private List<Animator> showUpAnimatorList = new ArrayList<>();
	private int skillScale;

	private int currentSkill;

	public SkillsLevelView(Context context) {
		super(context);
	}

	public SkillsLevelView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initViews(context, attrs);
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
			textColor = array.getColor(R.styleable.SkillsLevelView_textColor, getTextColor(context));
			textSize = array.getDimension(R.styleable.SkillsLevelView_textSize,
				getTextSize(context));
			skillScale = array.getInteger(R.styleable.SkillsLevelView_skillScale, DEFAULT_SKILL_SCALE);
			radius = array.getDimension(R.styleable.SkillsLevelView_radius, 0);
		} finally {
			array.recycle();
		}
		backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		foregroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		textPaint = new Paint();
		textPaint.setColor(textColor);
		textPaint.setTextSize(textSize);
		backgroundPaint.setColor(backgroundColor);
		foregroundPaint.setColor(foregroundColor);
		setupAnimations(skillList);
	}

	private void setupAnimations(List<Skill> skillList) {
		for(Skill skill : skillList) {
			ObjectAnimator fadeInAnimator = ObjectAnimator.ofFloat(this, View.ALPHA, 0, 1);
			fadeInAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
				@Override public void onAnimationUpdate(ValueAnimator animation) {
					backgroundPaint.setAlpha((int) animation.getAnimatedValue());
					invalidate();
				}
			});
			fadeInAnimator.setDuration(200);
			showUpAnimatorList.add(fadeInAnimator);
		}
		ValueAnimator animator = ValueAnimator.ofInt(0, 5);
		animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override public void onAnimationUpdate(ValueAnimator animation) {

			}
		});
	}

	private int getForegroundColor(Context context) {
		return context.getResources().getColor(R.color.text_icon_color);
	}

	private int getBackgroundColor(Context context) {
		return context.getResources().getColor(R.color.light_primary_color);
	}

	private float getTextSize(Context context) {
		return context.getResources().getDimension(R.dimen.skill_level_text_size);
	}

	private int getTextColor(Context context) {
		return context.getResources().getColor(R.color.text_icon_color);
	}

	public void setSkillsList(List<Skill> skillList) {
		this.skillList = skillList;
		setupAnimations(skillList);
	}

	@Override protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		for(int i=0; i < skillScale; i++) {
			canvas.drawCircle(radius / 2, radius / 2 + radius*i, radius, backgroundPaint);
		}
	}

	@Override protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);


	}
}
