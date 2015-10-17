package com.panwrona.myportfolio.screen_coding.screen_github;

import com.panwrona.myportfolio.data.api.ApiManager;
import com.panwrona.myportfolio.data.entities.GithubRepo;
import com.panwrona.myportfolio.mvp.MvpBasePresenter;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class GithubPresenterImpl extends MvpBasePresenter<GithubView> implements GithubPresenter {

    ApiManager apiManager = new ApiManager();

    @Override
    public void getGithubRepos() {
        apiManager.getGithubRepos(new Callback<List<GithubRepo>>() {
            @Override
            public void success(List<GithubRepo> githubRepos, Response response) {
                getView().loadGithubRepos(githubRepos);
            }

            @Override
            public void failure(RetrofitError error) {
                getView().showGithubReposError();
            }
        });
    }
}
