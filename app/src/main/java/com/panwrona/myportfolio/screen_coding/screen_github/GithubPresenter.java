package com.panwrona.myportfolio.screen_coding.screen_github;

import com.panwrona.myportfolio.mvp.MvpPresenter;

public interface GithubPresenter extends MvpPresenter<GithubView> {
    void getGithubRepos();
}
