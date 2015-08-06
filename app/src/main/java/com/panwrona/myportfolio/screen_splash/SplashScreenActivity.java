package com.panwrona.myportfolio.screen_splash;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.panwrona.myportfolio.R;
import com.panwrona.myportfolio.mvp.MvpActivity;
import com.panwrona.myportfolio.screen_main.MainActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SplashScreenActivity extends MvpActivity<SplashScreenView, SplashScreenPresenter> implements SplashScreenView {

    @InjectView(R.id.stv)
    TextView mAuthor;
    @InjectView(R.id.name)
    TextView mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.inject(this);

        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator());
        fadeIn.setDuration(1800);
        fadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override public void run() {
                        ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(SplashScreenActivity.this);
                        startActivity(new Intent(SplashScreenActivity.this, MainActivity.class),
                            activityOptions.toBundle());
                        finishAfterTransition();
                    }
                }, 1500);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mTitle.startAnimation(fadeIn);
        mAuthor.startAnimation(fadeIn);
    }

    @Override
    protected SplashScreenPresenter createPresenter() {
        return new SplashScreenPresenterImpl();
    }

    @Override protected Transition getEnterTransition() {
        return null;
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
        return R.layout.activity_splash;
    }

}


