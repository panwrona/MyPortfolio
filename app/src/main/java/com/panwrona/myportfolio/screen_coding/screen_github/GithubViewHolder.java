package com.panwrona.myportfolio.screen_coding.screen_github;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.panwrona.myportfolio.R;
import com.panwrona.myportfolio.data.entities.GithubRepo;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GithubViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.github_tv_description)
    TextView mTvDescription;
    @Bind(R.id.github_tv_forks)
    TextView mTvForks;
    @Bind(R.id.github_tv_reponame)
    TextView mTvReponame;
    @Bind(R.id.github_tv_watchers)
    TextView mTvWatchers;
    @Bind(R.id.github_tv_stars)
    TextView mTvStars;

    public GithubViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void updateViews(GithubRepo githubRepo) {
        new Handler(Looper.getMainLooper()).post(() -> {
            if(githubRepo.getName() != null) {
                mTvReponame.setText(githubRepo.getName());
            }
            if(githubRepo.getDescription() != null) {
                mTvDescription.setText(githubRepo.getDescription());
            }
            mTvForks.setText("" + githubRepo.getForksCount());
            mTvStars.setText("" + githubRepo.getStargazersCount());
            mTvWatchers.setText("" + githubRepo.getWatchersCount());
        });
    }
}
