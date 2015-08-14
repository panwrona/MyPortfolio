package com.panwrona.myportfolio.mvp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.transition.Transition;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public abstract class MvpActivity<V extends MvpView, P extends MvpPresenter> extends AppCompatActivity implements MvpView {

    protected P presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        ButterKnife.bind(this);
        if(presenter == null)
            presenter = createPresenter();
        presenter.attachView(this);

        if(getEnterTransition() != null)
            getWindow().setEnterTransition(getEnterTransition());
        if(getExitTransition() != null)
            getWindow().setExitTransition(getExitTransition());
        if(getReenterTransition() != null)
            getWindow().setReenterTransition(getReenterTransition());
        if(getReturnTransition() != null)
            getWindow().setReturnTransition(getReturnTransition());
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    protected abstract P createPresenter();

    protected abstract Transition getEnterTransition();
    protected abstract Transition getExitTransition();
    protected abstract Transition getReturnTransition();
    protected abstract Transition getReenterTransition();

    @LayoutRes
    protected abstract int getLayout();
}
