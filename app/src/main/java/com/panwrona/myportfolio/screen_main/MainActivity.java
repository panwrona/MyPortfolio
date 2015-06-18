package com.panwrona.myportfolio.screen_main;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.panwrona.myportfolio.R;
import com.panwrona.myportfolio.customviews.ProgressPhotoView;
import com.panwrona.myportfolio.customviews.SpellingTextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends ActionBarActivity implements IMainActivityView {

    IMainPresenter mPresenter;
    @InjectView(R.id.ppv)
    ProgressPhotoView mProgressPhotoView;
//    @InjectView(R.id.stv)
//    SpellingTextView mSpellingTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
    }

    @Override
    public void initViews() {
    }

    @OnClick(R.id.click)
    public void onClick() {
        //mSpellingTextView.start();
        mProgressPhotoView.start();
    }

}
