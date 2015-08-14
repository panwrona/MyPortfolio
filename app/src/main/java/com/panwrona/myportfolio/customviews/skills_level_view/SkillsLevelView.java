package com.panwrona.myportfolio.customviews.skills_level_view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
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
    private List<Skill> skillList = new ArrayList<>(5);
    private List<Animator> showUpAnimatorList = new ArrayList<>();
    private AnimatorSet setSkillAnimatorSet = new AnimatorSet();
    private int skillScale;

    private int currentSkill;
    private boolean hasAnimationStarted = false;
    private float mMeasureText = 0;

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
        backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        foregroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint = new Paint();
        textPaint.setColor(getBackgroundColor(context));
        textPaint.setTextSize(textSize);
        backgroundPaint.setColor(backgroundColor);
        foregroundPaint.setColor(foregroundColor);
        setupAnimations(skillList);
    }

    private void setupAnimations(List<Skill> skillList) {
        ObjectAnimator fadeInAnimator = ObjectAnimator.ofFloat(this, View.ALPHA, 0, 1);
        fadeInAnimator.setDuration(600);
        fadeInAnimator.setInterpolator(new DecelerateInterpolator());
        for (Skill skill : skillList) {
            skill.setTextWidth(textPaint.measureText(skill.getName()));
            if (textPaint.measureText(skill.getName()) > mMeasureText) {
                mMeasureText = textPaint.measureText(skill.getName());
            }
        }
        for(int j = 0; j < skillScale; j++) {

        }
        fadeInAnimator.addListener(new Animator.AnimatorListener() {
            @Override public void onAnimationStart(Animator animation) {

            }

            @Override public void onAnimationEnd(Animator animation) {
                hasAnimationStarted = true;

            }

            @Override public void onAnimationCancel(Animator animation) {

            }

            @Override public void onAnimationRepeat(Animator animation) {

            }
        });
        fadeInAnimator.start();
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
        for (int j = 0; j < skillList.size(); j++) {
            for (int i = 0; i < skillScale; i++) {
                canvas.drawCircle(mMeasureText * j + mMeasureText / 2, radius + radius * 2.5f * i,
                    radius, backgroundPaint);
            }
            canvas.drawText(skillList.get(j).getName(),
                mMeasureText * j + mMeasureText / 2 - skillList.get(j).getTextWidth() / 2,
                radius*2 + radius * 2.5f * skillScale, textPaint);
        }
    }

    @Override protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }
}
