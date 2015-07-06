package com.panwrona.myportfolio.mvp;

/**
 * Created by Mariusz on 2015-07-06.
 */
public interface MvpPresenter<V extends MvpView>  {

    void attachView(V view);
    void detachView();
}
