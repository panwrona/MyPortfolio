package com.panwrona.myportfolio.screen_splash;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.transition.Fade;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.panwrona.myportfolio.R;
import com.panwrona.myportfolio.mvp.MvpActivity;
import com.panwrona.myportfolio.screen_main.MainActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SplashScreenActivity extends MvpActivity<SplashScreenView, SplashScreenPresenter>
        implements SplashScreenView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        new Handler(Looper.getMainLooper()).postDelayed(SplashScreenActivity.this::startMainActivity, 600);
    }

    private void startMainActivity() {
        startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
        finish();
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


