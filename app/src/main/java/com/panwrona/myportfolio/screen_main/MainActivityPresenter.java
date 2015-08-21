package com.panwrona.myportfolio.screen_main;

import com.panwrona.myportfolio.mvp.MvpPresenter;

public interface MainActivityPresenter extends MvpPresenter<MainActivityView> {
    void subscribe();
    void unsubscribe();
    void registerDataBus();
    void unregisterDataBus();
    void getGithubRepos();

}
