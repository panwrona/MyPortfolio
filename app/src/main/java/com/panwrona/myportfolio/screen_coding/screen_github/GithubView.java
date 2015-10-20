package com.panwrona.myportfolio.screen_coding.screen_github;

import com.panwrona.myportfolio.data.entities.GithubRepo;
import com.panwrona.myportfolio.mvp.MvpView;
import java.util.List;

interface GithubView extends MvpView {
    void loadGithubRepos(List<GithubRepo> githubRepos);

    void showGithubReposError();

    void showLoadingIndicator();

    void hideLoadingIndicator();
}
