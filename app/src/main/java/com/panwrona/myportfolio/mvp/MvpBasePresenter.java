package com.panwrona.myportfolio.mvp;

import java.lang.ref.WeakReference;

/**
 * Created by Mariusz on 2015-07-06.
 */
public class MvpBasePresenter<V extends MvpView> implements MvpPresenter<V> {

    private WeakReference<V> viewRef;

    @Override public void attachView(V view) {
        viewRef = new WeakReference<V>(view);
    }

    protected V getView() {
        return viewRef == null ? null : viewRef.get();
    }

    protected boolean isViewAttached() {
        return viewRef != null && viewRef.get() != null;
    }

    @Override public void detachView() {
        if (viewRef != null) {
            viewRef.clear();
            viewRef = null;
        }
    }
}
