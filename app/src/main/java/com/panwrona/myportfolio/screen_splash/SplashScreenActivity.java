package com.panwrona.myportfolio.screen_splash;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.ActivityOptionsCompat;
import android.transition.Fade;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.panwrona.myportfolio.R;
import com.panwrona.myportfolio.mvp.MvpActivity;
import com.panwrona.myportfolio.screen_main.MainActivity;

public class SplashScreenActivity extends MvpActivity<SplashScreenView, SplashScreenPresenter> implements SplashScreenView {

    @Bind(R.id.stv)
    TextView mAuthor;
    @Bind(R.id.name)
    TextView mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        Fade fade = new Fade(Fade.OUT);
        fade.setDuration(300);
        getWindow().setExitTransition(fade);

        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator());
        fadeIn.setDuration(1800);
        fadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startMainActivity();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mTitle.startAnimation(fadeIn);
        mAuthor.startAnimation(fadeIn);
    }

    private void startMainActivity() {
            ActivityOptions options =
                ActivityOptions.makeSceneTransitionAnimation(SplashScreenActivity.this);
            startActivity(new Intent(SplashScreenActivity.this, MainActivity.class),
                options.toBundle());
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


