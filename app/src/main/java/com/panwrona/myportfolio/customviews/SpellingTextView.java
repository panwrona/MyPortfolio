package com.panwrona.myportfolio.customviews;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

import com.panwrona.myportfolio.R;

import java.lang.reflect.Type;

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

        mShowingLettersAnimator = ValueAnimator.ofInt(1, mText.length());
        mShowingLettersAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                if((int)valueAnimator.getAnimatedValue() == 0) {
                    setText(mText.substring(0, (int)valueAnimator.getAnimatedValue()));
                } else {
                    setText(mText.substring(0, (int)valueAnimator.getAnimatedValue() - 1));
                }
                in.start();
            }
        });
        mShowingLettersAnimator.setDuration(mAnimationLength);
    }

    public void start() {
        mShowingLettersAnimator.start();
    }
}
