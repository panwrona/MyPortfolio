package com.panwrona.myportfolio.customviews;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import com.panwrona.myportfolio.R;

public class SpellingTextView extends TextView {

    private static final int DEFAULT_ANIMATION_LENGTH = 3000;
    private String mText;
    private int mCurrentLetter;
    private long mAnimationLength;
    private ValueAnimator mShowingLettersAnimator;

    public SpellingTextView(Context context) {
        super(context);
    }

    public SpellingTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SpellingTextView, 0, 0);
        try {
            mText = array.getString(R.styleable.SpellingTextView_text);
            mAnimationLength = array.getInt(R.styleable.SpellingTextView_animationLength, DEFAULT_ANIMATION_LENGTH);
        } finally {
            array.recycle();
        }
        setupAnimations();
    }

    private void setupAnimations() {
        final Animation in = new AlphaAnimation(0.0f, 1.0f);
        in.setDuration(mAnimationLength / mText.length());
        in.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //todo
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        mShowingLettersAnimator = ValueAnimator.ofInt(0, mText.length());
        mShowingLettersAnimator.setInterpolator(new LinearInterpolator());
        mShowingLettersAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                setText(mText.substring(0, (int)valueAnimator.getAnimatedValue()));
                in.start();
            }
        });
        mShowingLettersAnimator.setDuration(mAnimationLength);
    }

    public void start() {
        mShowingLettersAnimator.start();
    }
}
