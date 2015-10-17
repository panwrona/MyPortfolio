package com.panwrona.myportfolio.screen_coding.screen_github;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.panwrona.myportfolio.R;
import com.panwrona.myportfolio.data.entities.GithubRepo;
import com.panwrona.myportfolio.mvp.MvpFragment;
import com.panwrona.myportfolio.screen_coding.views.WrapLinearLayoutManager;

import java.util.List;

import butterknife.Bind;

public class GithubFragment extends MvpFragment<GithubView, GithubPresenter> implements GithubView {

    @Bind(R.id.fragment_github_recyclerview)
    RecyclerView mRecyclerView;

    private GithubRecyclerViewAdapter mGithubRecyclerViewAdapter;

    @Override
    protected GithubPresenter createPresenter() {
        return new GithubPresenterImpl();
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_github;
    }

    public static GithubFragment newInstance() {
        return new GithubFragment();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mGithubRecyclerViewAdapter = new GithubRecyclerViewAdapter();
        mRecyclerView.setAdapter(mGithubRecyclerViewAdapter);
        mRecyclerView.setLayoutManager(new WrapLinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setNestedScrollingEnabled(false);
        presenter.getGithubRepos();
    }

    @Override
    public void loadGithubRepos(List<GithubRepo> githubRepos) {
        mGithubRecyclerViewAdapter.setGithubRepos(githubRepos);
    }

    @Override
    public void showGithubReposError() {
        Snackbar.make(getView(), "Couldn't load Github Repositories. Try again later...", Snackbar.LENGTH_LONG).show();
    }
}