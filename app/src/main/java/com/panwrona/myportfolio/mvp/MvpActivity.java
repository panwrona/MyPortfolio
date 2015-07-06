package com.panwrona.myportfolio.mvp;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;

public abstract class MvpActivity<V extends MvpView, P extends MvpPresenter> extends AppCompatActivity implements MvpView {

    protected P presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        if(presenter == null)
            presenter = createPresenter();
        presenter.attachView(this);
    }

    protected abstract P createPresenter();

    @LayoutRes
    protected abstract int getLayout();
}
