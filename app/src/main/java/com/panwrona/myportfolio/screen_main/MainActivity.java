package com.panwrona.myportfolio.screen_main;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;
import butterknife.Bind;
import com.panwrona.myportfolio.R;
import com.panwrona.myportfolio.mvp.MvpActivity;

public class MainActivity extends MvpActivity<MainActivityView, MainActivityPresenter> implements MainActivityView {

    @Bind(R.id.toolbar) Toolbar mToolbar;
    @Bind(R.id.toolbar_rl) RelativeLayout mRlToolbarMainLayout;

    @Override
    protected MainActivityPresenter createPresenter() {
        return new MainActivityPresenterImpl();
    }

    @Override protected Transition getEnterTransition() {
        return null;
    }

    private void revealTransition() {
        int cx = mToolbar.getMeasuredWidth() / 2;
        int cy = mToolbar.getMeasuredHeight() / 2;

        int finalRadius = Math.max(mToolbar.getWidth(), mToolbar.getHeight()) / 2;
        Animator anim = ViewAnimationUtils.createCircularReveal(mToolbar, cx, cy, 0, finalRadius);
        anim.setDuration(500);
        mToolbar.setVisibility(View.VISIBLE);
        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(mRlToolbarMainLayout, View.ALPHA, 0, 1);
                alphaAnimator.setDuration(300);
                alphaAnimator.setInterpolator(new DecelerateInterpolator());
                alphaAnimator.addListener(new Animator.AnimatorListener() {
                    @Override public void onAnimationStart(Animator animation) {
                        mRlToolbarMainLayout.setVisibility(View.VISIBLE);
                    }

                    @Override public void onAnimationEnd(Animator animation) {

                    }

                    @Override public void onAnimationCancel(Animator animation) {

                    }

                    @Override public void onAnimationRepeat(Animator animation) {

                    }
                });
                alphaAnimator.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        anim.start();
    }

    @Override protected Transition getExitTransition() {
        return null;
    }

    @Override protected Transition getReturnTransition() {
        return null;
    }

    @Override protected Transition getReenterTransition() {
        return null;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar();
        mToolbar.post(new Runnable() {
            @Override public void run() {
                revealTransition();
            }
        });
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        if(getSupportActionBar() != null) {
            mToolbar.setTitle("");
            getSupportActionBar().setTitle("");
        }
    }
}
