package com.panwrona.myportfolio.screen_splash;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
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
        Typeface robotoFont = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");
        Typeface lobsterFont = Typeface.createFromAsset(getAssets(), "fonts/LobsterTwo-Bold.ttf");
        mAuthor.setTypeface(robotoFont);
        mTitle.setTypeface(lobsterFont);

        final Activity activity = this;
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator());
        fadeIn.setDuration(1800);
        fadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(activity,
                        new Pair<View, String>(mTitle, "title"));
                activity.startActivity(new Intent(SplashScreenActivity.this, MainActivity.class), compat.toBundle());
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

    @Override
    protected int getLayout() {
        return R.layout.activity_splash;
    }

}


