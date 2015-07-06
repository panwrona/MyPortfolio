package com.panwrona.myportfolio.screen_main;

import android.os.Bundle;

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

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }
}
