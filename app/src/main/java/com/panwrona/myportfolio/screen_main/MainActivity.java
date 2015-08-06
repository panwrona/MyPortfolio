package com.panwrona.myportfolio.screen_main;

import android.os.Bundle;

import android.transition.Explode;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import com.panwrona.myportfolio.R;
import com.panwrona.myportfolio.mvp.MvpActivity;

public class MainActivity extends MvpActivity<MainActivityView, MainActivityPresenter> implements MainActivityView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected MainActivityPresenter createPresenter() {
        return new MainActivityPresenterImpl();
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
        return R.layout.activity_main;
    }
}
